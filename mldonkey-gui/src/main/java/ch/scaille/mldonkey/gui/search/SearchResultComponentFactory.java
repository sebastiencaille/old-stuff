/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import static ch.scaille.gui.swing.factories.SwingBindings.multipleSelection;
import static ch.scaille.gui.swing.factories.SwingBindings.selection;
import static ch.scaille.gui.swing.jtable.TableColumnWithPolicy.fixedTextLength;
import static ch.scaille.gui.swing.jtable.TableColumnWithPolicy.fixedWidth;
import static ch.scaille.gui.swing.jtable.TableColumnWithPolicy.percentOfAvailableSpace;
import static ch.scaille.gui.swing.factories.BindingDependencies.preserveOnUpdateOf;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.jtable.PolicyTableColumnModel;
import ch.scaille.javabeans.IBindingController;
import ch.scaille.mldonkey.gui.renderers.MlSizeRenderer;
import ch.scaille.mldonkey.gui.renderers.WarningRenderer;
import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.model.FileQueryResult;

/**
 * This creates one search result
 */
public class SearchResultComponentFactory {

	/**
	 * One search result
	 */
	public static class SearchResult {
		IBindingController selectedResultsController;
		IBindingController lastSelectedResult;
		FileQuery fileQuery;
		JComponent swingComponent;
		ListModel<FileQueryResult> filtered;

		// For close
		final List<IBindingController> bindings = new ArrayList<>();

		/**
		 * Becomes the visible search view
		 */
		public void attach() {
			lastSelectedResult.getVetoer().attach();
			lastSelectedResult.forceViewUpdate();
			selectedResultsController.getVetoer().attach();
			selectedResultsController.forceViewUpdate();
		}

		/**
		 * Becomes the hidden search view
		 */
		public void detach() {
			lastSelectedResult.getVetoer().detach();
			selectedResultsController.getVetoer().detach();
		}
		
		public void close() {
			bindings.forEach(IBindingController::unbind);
		}
	}

	private final SearchQueryController controller;

	public SearchResultComponentFactory(final SearchQueryController controller) {
		this.controller = controller;
	}

	public SearchResult create(final FileQuery query) {
		final var searchPanelModel = this.controller.getSearchPanelModel();

		final var searchResult = new SearchResult();
		searchResult.fileQuery = query;

		final var filter = new ResultFilter();
		searchResult.bindings.add(searchPanelModel.getShowWarnings().bind(filter.showWarning()));
		searchResult.bindings.add(searchPanelModel.getShowNotComplete().bind(filter.showNotComplete()));
		searchResult.bindings.add(searchPanelModel.getShowScam().bind(filter.showScam()));

		final var filteredModel = query.getResults().child(ListViews.sortedFiltered((o1, o2) -> {
			int comp = o2.completeSources() - o1.completeSources();
			if (comp == 0) {
				comp = Long.compare(o2.getFileSize(), o1.getFileSize());
			}
			if (comp == 0) {
				comp = o2.getId() - o1.getId();
			}
			return comp;
		}, filter)).withName("Filtered results of " + query);

		final var resultModel = new SearchResultModel(filteredModel);
		final var table = new JTable(resultModel);
		final var columnModel = new PolicyTableColumnModel<SearchResultModel.Columns>(table);

		table.setColumnModel(columnModel);
		columnModel.configureColumn(percentOfAvailableSpace(SearchResultModel.Columns.NAME, 100));
		columnModel.configureColumn(fixedTextLength(SearchResultModel.Columns.SIZE, 10).with(new MlSizeRenderer()));
		columnModel.configureColumn(fixedTextLength(SearchResultModel.Columns.COMPLETE, 8));
		columnModel.configureColumn(fixedTextLength(SearchResultModel.Columns.TYPE, 8));
		columnModel.configureColumn(fixedWidth(SearchResultModel.Columns.WARNING, 2).with(new WarningRenderer()));
		table.setAutoCreateColumnsFromModel(false);

		final var lastSelectedResult = searchPanelModel.getLastSelectedResult().bind(selection(table, resultModel));
		final var selectedResultsController = searchPanelModel.getSelectedResults()
				.bind(multipleSelection(table, resultModel))
				.addDependency(preserveOnUpdateOf(filteredModel));
		searchResult.bindings.add(lastSelectedResult);
		searchResult.bindings.add(selectedResultsController);
		searchResult.lastSelectedResult = lastSelectedResult;
		searchResult.selectedResultsController = selectedResultsController;

		searchResult.detach(); // Only attach when selected

		searchPanelModel.getSelectedSearchBindingSelector()
				.add(searchResult, lastSelectedResult, selectedResultsController);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					SearchResultComponentFactory.this.controller.downloadSelected();
				}
			}
		});
		table.addMouseListener(new AbstractJTablePopup<>(table, resultModel, searchPanelModel.getLastSelectedResult(),
				searchPanelModel.getSelectedResults()) {

			@Override
			protected void buildPopup(final JPopupMenu popupMenu, final FileQueryResult clicked) {
				popupMenu.removeAll();

				final var asQuery = new JMenuItem("As query");
				asQuery.addActionListener(SearchResultComponentFactory.this.controller.asQueryAction());
				popupMenu.add(asQuery);

				final var downloadSelected = new JMenuItem("Download");
				downloadSelected
						.addActionListener(SearchResultComponentFactory.this.controller.downloadSelectedAction());
				popupMenu.add(downloadSelected);

				final var blackListSelected = new JMenuItem("BlackList");
				blackListSelected
						.addActionListener(SearchResultComponentFactory.this.controller.getBlackListSelectedAction());

				popupMenu.add(blackListSelected);
			}
		});
		searchResult.swingComponent = table;
		searchResult.filtered = filteredModel;

		return searchResult;
	}

}
