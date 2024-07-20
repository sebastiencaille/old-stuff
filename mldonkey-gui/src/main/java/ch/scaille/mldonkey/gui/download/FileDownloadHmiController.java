/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.search.SearchQueryController;
import ch.scaille.mldonkey.model.FileDownload;

public class FileDownloadHmiController extends GuiController {
	private final MLDonkeyGui gui;
	final FileDownloadModel model;
	private final SearchQueryController searchQueryController;

	public FileDownloadHmiController(final MLDonkeyGui gui, final SearchQueryController searchQueryController) {
		this.gui = gui;
		this.searchQueryController = searchQueryController;
		this.model = new FileDownloadModel(GuiModel.of(this));
	}

	public ObjectProperty<FileDownload> getLastSelectedDownload() {
		return this.model.lastSelectedDownload;
	}

	public ListProperty<FileDownload> getSelectedDownloads() {
		return this.model.selectedDownloads;
	}

	public BooleanProperty getShowOnlyEmptySources() {
		return this.model.showOnlyEmptySources;
	}

	public ActionListener getCancelFileAction() {
		return e -> {
			if (getSelectedDownloads().getValue() != null) {
				getSelectedDownloads().getValue().forEach(this.gui::cancelDownload);
			}
		};
	}

	public ActionListener getPreviewFileAction() {
		return e -> this.model.lastSelectedDownload.optional().ifPresent(v -> this.gui.preview(v, -1));
	}

	public ActionListener getBlackListFileAction() {
		return e -> this.model.selectedDownloads.getValue().forEach(download -> {
			this.gui.blackList(download);
			this.gui.cancelDownload(download);
		});
	}

	public ActionListener asQueryAction() {
		return e -> this.model.lastSelectedDownload.optional().ifPresent(v -> this.searchQueryController
				.getSearchPanelModel().getSearchTextProperty().setValue(this, v.getName()));
	}

	public BooleanProperty getShowOnlyDownloading() {
		return this.model.showOnlyDownloading;
	}

	public ActionListener getViewFileAction() {
		return e -> this.model.lastSelectedDownload.optional().ifPresent(this.gui::view);
	}
}
