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
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import ch.scaille.gui.model.views.DynamicListView;
import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.jtable.PolicyTableColumnModel;
import ch.scaille.mldonkey.gui.renderers.MlSizeRenderer;
import ch.scaille.mldonkey.gui.renderers.WarningRenderer;
import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.model.WarningLevel;
import org.jspecify.annotations.NullMarked;

/**
 * This creates one search result
 */
@NullMarked
public class SearchResultComponentFactory {


    public record ResultFilter(boolean warning, boolean showNotComplete, boolean showScam) {

    }

    private static final int SCAM_SIZE = 5939876;

    private final SearchQueryController controller;


    public SearchResultComponentFactory(final SearchQueryController controller) {
        this.controller = controller;
    }


    public SearchResult create(final FileQuery query) {
        final var searchPanelModel = this.controller.getSearchPanelModel();



        final var view = new DynamicListView<FileQueryResult, ResultFilter>(new ResultFilter(false, false, false),
                (o1, o2, _) -> {
                    var comp = o2.completeSources() - o1.completeSources();
                    if (comp == 0) {
                        comp = Long.compare(o2.getFileSize(), o1.getFileSize());
                    }
                    if (comp == 0) {
                        comp = o2.getId() - o1.getId();
                    }
                    return comp;
                }, (value, filter) ->
                value.getWarnings() != WarningLevel.BLACK_LISTED
                        && (value.getWarnings() != WarningLevel.WARN || filter.warning())
                        && (value.completeSources() > 0 || filter.showNotComplete())
                        && (value.getFileSize() != SCAM_SIZE || filter.showScam())
        );
        final var filterBinding = searchPanelModel.getFilter().bind(view);

        final var filteredModel = query.getResults().child(view).withName("Filtered results of " + query);

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

        final var searchResult = new SearchResult(query, filteredModel, table,
                selectedResultsController, lastSelectedResult,
                List.of(lastSelectedResult, selectedResultsController, filterBinding));


        searchResult.detach(); // Only attach when selected

        searchPanelModel.getSelectedSearchBindingSelector()
                .add(searchResult, lastSelectedResult, selectedResultsController);
        return searchResult;
    }

}
