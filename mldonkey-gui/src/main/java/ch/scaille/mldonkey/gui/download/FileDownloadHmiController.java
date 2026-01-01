/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.search.SearchQueryController;

public class FileDownloadHmiController extends GuiController {
    private final MLDonkeyGui gui;
    final FileDownloadModel model;
    private final SearchQueryController searchQueryController;

    public FileDownloadHmiController(final MLDonkeyGui gui, final SearchQueryController searchQueryController) {
        this.gui = gui;
        this.searchQueryController = searchQueryController;
        this.model = new FileDownloadModel(GuiModel.of(this));
    }

    public ActionListener getCancelFileAction() {
        return _ -> model.selectedDownloads.getValue().forEach(this.gui::cancelDownload);
    }

    public ActionListener getPreviewFileAction() {
        return _ -> this.model.lastSelectedDownload.optional().ifPresent(v -> this.gui.preview(v, -1));
    }

    public ActionListener getBlackListFileAction() {
        return _ -> this.model.selectedDownloads.getValue().forEach(download -> {
            this.gui.blackList(download);
            this.gui.cancelDownload(download);
        });
    }

    public ActionListener asQueryAction() {
        return _ -> this.model.lastSelectedDownload.optional().ifPresent(v -> this.searchQueryController
                .getSearchPanelModel().getSearchTextProperty().setValue(this, v.getName()));
    }


    public ActionListener getViewFileAction() {
        return _ -> this.model.lastSelectedDownload.optional().ifPresent(this.gui::view);
    }
}
