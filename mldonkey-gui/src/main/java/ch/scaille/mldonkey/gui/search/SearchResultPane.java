/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import ch.scaille.gui.model.IListModelListener;
import ch.scaille.gui.model.ListModelRef;
import ch.scaille.gui.swing.AbstractPopup;
import ch.scaille.gui.swing.bindings.JTabbedPaneSelectionBinding;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.search.SearchResultComponentFactory.SearchResult;
import ch.scaille.mldonkey.model.FileQuery;

/**
 * Pane that displays searches
 */
public class SearchResultPane extends JTabbedPane implements ListDataListener {
	private final Set<FileQuery> tabs = new HashSet<>();
	private final SearchResultComponentFactory searchResultComponentFactory;

	public SearchResultPane(final MLDonkeyGui gui, final SearchQueryController controller) {

		this.searchResultComponentFactory = new SearchResultComponentFactory(controller);

		gui.getQueries().addListDataListener(this);
		
		// Search removed
		gui.getQueries()
				.addListener(IListModelListener
						.valueRemoved(event -> SearchResultPane.this.tabs.removeAll(event.getObjects())));

		final var searchPanelModel = controller.getSearchPanelModel();

		searchPanelModel.getSelectedSearch()
				.bind(new JTabbedPaneSelectionBinding<>(this, SearchResultComponentFactory.SearchResult.class));
		searchPanelModel.getSelectedSearch().addListener(e -> {
			if (e.getOldValue() instanceof SearchResult s) {
				s.detach();
			}
			if (e.getNewValue() instanceof SearchResult s) {
				s.attach();
			}
		});
		this.addMouseListener(new AbstractPopup<>(searchPanelModel.getSelectedSearch()) {

			@Override
			protected void buildPopup(final JPopupMenu popupMenu,
					final SearchResultComponentFactory.SearchResult selectedSearchResult) {
				popupMenu.removeAll();
				
				final var close = new JMenuItem("Close");
				popupMenu.add(close);
				close.addActionListener(controller.getCloseAction(selectedSearchResult.fileQuery));
				close.addActionListener(e -> SearchResultPane.this.removeTab());
				
				final var asQuery = new JMenuItem("As query");
				asQuery.addActionListener(controller.getAsQueryAction());
				popupMenu.add(asQuery);
			}


		});
	}

	private void addTab(final FileQuery query) {
		if (query.getSearchText() != null && !this.tabs.contains(query)) {
			this.tabs.add(query);
			final var searchResult = this.searchResultComponentFactory.create(query);
			final var pane = new JScrollPane(searchResult.swingComponent);
			JTabbedPaneSelectionBinding.setValueForTab(this, pane, searchResult);
			this.add(pane, query.getSearchText());
		}
	}


	private void removeTab() {
		final var selectedIndex = SearchResultPane.this.getSelectedIndex();
		final var selectedComponent = SearchResultPane.this.getComponentAt(selectedIndex);
		JTabbedPaneSelectionBinding.getValueForTab(SearchResultPane.this, selectedComponent, SearchResult.class).close();
		SearchResultPane.this.remove(selectedComponent);
	}
	
	@Override
	public void contentsChanged(final ListDataEvent event) {
		final var sourceModel = ((ListModelRef<FileQuery>) event.getSource()).getListModel();
		this.addTab(sourceModel.getValueAt(event.getIndex0()));
	}

	@Override
	public void intervalAdded(final ListDataEvent event) {
		contentsChanged(event);
	}

	@Override
	public void intervalRemoved(final ListDataEvent event) {
		// nope
	}

}
