/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import ch.scaille.gui.mvc.BindingSelector;
import ch.scaille.gui.mvc.properties.BooleanProperty;
import ch.scaille.gui.mvc.properties.ListProperty;
import ch.scaille.gui.mvc.properties.ObjectProperty;
import ch.scaille.mldonkey.gui.search.SearchResultComponentFactory.SearchResult;
import ch.scaille.mldonkey.model.FileQueryGuiModel;
import ch.scaille.mldonkey.model.FileQueryResult;

public class SearchPanelModel extends FileQueryGuiModel {
	final ObjectProperty<SearchResultComponentFactory.SearchResult> selectedQuery;
	final ObjectProperty<FileQueryResult> lastSelectedResult;
	final ListProperty<FileQueryResult> selectedResults;
	final BindingSelector<SearchResult> selectedSearchBindingSelector;
	final BooleanProperty showWarnings;
	final BooleanProperty showNotComplete;
	final BooleanProperty showScam;

	public SearchPanelModel(final ModelConfiguration config) {
		super(config);
		this.selectedQuery = new ObjectProperty<>("SelectedQuery", this.getPropertySupport());
		this.lastSelectedResult = new ObjectProperty<>("LastSelectedResult", this.getPropertySupport());
		this.selectedResults = new ListProperty<>("SelectedResults", this.getPropertySupport());
		this.selectedSearchBindingSelector = new BindingSelector<>(this.selectedQuery);
		this.showWarnings = new BooleanProperty("showWarnings", this.getPropertySupport());
		this.showNotComplete = new BooleanProperty("showNotComplete", this.getPropertySupport());
		this.showScam = new BooleanProperty("showScam", this.getPropertySupport());
	}

	public ListProperty<FileQueryResult> getSelectedResults() {
		return this.selectedResults;
	}

	public BooleanProperty getShowScam() {
		return this.showScam;
	}

	public BooleanProperty getShowWarnings() {
		return this.showWarnings;
	}

	public BooleanProperty getShowNotComplete() {
		return this.showNotComplete;
	}

	public ObjectProperty<SearchResult> getSelectedSearch() {
		return this.selectedQuery;
	}

	public ObjectProperty<FileQueryResult> getLastSelectedResult() {
		return this.lastSelectedResult;
	}

	public BindingSelector<SearchResult> getSelectedSearchBindingSelector() {
		return this.selectedSearchBindingSelector;
	}
}
