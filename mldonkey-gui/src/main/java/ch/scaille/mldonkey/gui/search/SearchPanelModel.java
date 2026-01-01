/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import ch.scaille.javabeans.BindingSelector;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.javabeans.properties.PropertiesAggregator;
import ch.scaille.mldonkey.gui.model.FileQueryGuiModel;
import ch.scaille.mldonkey.model.FileQueryResult;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@Getter
public class SearchPanelModel extends FileQueryGuiModel {

	private final ObjectProperty<@Nullable SearchResult> selectedQuery;
	private final ObjectProperty<@Nullable FileQueryResult> lastSelectedResult;
	private final ListProperty<FileQueryResult> selectedResults;
	private final BindingSelector<SearchResult> selectedSearchBindingSelector;
	private final BooleanProperty showWarnings;
	private final BooleanProperty showNotComplete;
	private	final BooleanProperty showScam;
	private final PropertiesAggregator<SearchResultComponentFactory.ResultFilter> filter;

	public SearchPanelModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super(config);
		this.selectedQuery = new ObjectProperty<>("SelectedQuery", this.getPropertySupport(), null);
		this.lastSelectedResult = new ObjectProperty<>("LastSelectedResult", this.getPropertySupport(), null);
		this.selectedResults = new ListProperty<>("SelectedResults", this.getPropertySupport());
		this.selectedSearchBindingSelector = new BindingSelector<>(this.selectedQuery);
		this.showWarnings = new BooleanProperty("showWarnings", this.getPropertySupport());
		this.showNotComplete = new BooleanProperty("showNotComplete", this.getPropertySupport());
		this.showScam = new BooleanProperty("showScam", this.getPropertySupport());

		this.filter = new PropertiesAggregator<SearchResultComponentFactory.ResultFilter>("searchFilter", this.getPropertySupport())
				.add(this.getShowWarnings(), this.getShowNotComplete(), this.getShowScam(),
						(showWarningsVal, showNotCompleteVal, showScamVal) ->
								new SearchResultComponentFactory.ResultFilter(showWarningsVal.get(), showNotCompleteVal.get(), showScamVal.get()));
	}

}
