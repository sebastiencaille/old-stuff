/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileQuery;

public class SearchQueryController extends GuiController {
	private final MLDonkeyGui gui;
	private final SearchPanelModel searchModel;

	public SearchQueryController(final MLDonkeyGui gui) {
		this.searchModel = new SearchPanelModel(of(this));
		this.gui = gui;
	}

	public SearchPanelModel getSearchPanelModel() {
		return this.searchModel;
	}

	public ActionListener copyAndGetPerformSearchAction() {
		return e -> {
			final var query = SearchQueryController.this.gui.createFileQuery();
			searchModel.setCurrentObject(query);
			searchModel.save();
			SearchQueryController.this.gui.executeQuery(query);
		};
	}

	public ActionListener getPerformSearchAction() {
		return e -> {
			final var query = SearchQueryController.this.gui.createFileQuery();
			searchModel.setCurrentObject(query);
			searchModel.save();
			SearchQueryController.this.gui.executeQuery(query);
		};
	}

	public ActionListener getSearchMoreAction() {
		return e -> SearchQueryController.this.gui.searchMore();
	}

	public ActionListener asQueryAction() {
		return e -> {
			final var value = searchModel.lastSelectedResult.getValue();
			if (value != null) {
				searchModel.getSearchTextProperty().setValue(this, value.getFileNames().getFirst());
			}
		};
	}

	public ActionListener downloadSelectedAction() {
		return e -> searchModel.getSelectedResults().getValue().forEach(SearchQueryController.this.gui::download);
	}

	public void downloadSelected() {
		this.gui.download(this.searchModel.lastSelectedResult.getValue());
	}

	public ActionListener getBlackListSelectedAction() {
		return e -> {
			searchModel.getSelectedResults().getValue().forEach(SearchQueryController.this.gui::blackList);
			SearchQueryController.this.gui.recheckData();
		};
	}

	public ActionListener getCloseAction(final FileQuery selected) {
		return e -> SearchQueryController.this.gui.closeQuery(selected);
	}

	public ActionListener getAsQueryAction() {
		return e -> {
			var content = searchModel.getSelectedSearch().getValue().fileQuery.getSearchText();
			if (content.startsWith("CONTAINS[")) {
				content = content.substring(9, content.length() - 1);
			}
			searchModel.getSearchTextProperty().setValue(this, content);
		};
	}

}
