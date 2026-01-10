/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.mldonkey.MLDonkeyGui;
import lombok.Getter;

public class SharedFilePanelController extends GuiController {
    private final MLDonkeyGui gui;
    @Getter
    private final SharedFilePanelModel model;

    public SharedFilePanelController(final MLDonkeyGui gui) {
        this.model = new SharedFilePanelModel(of(this));
        this.gui = gui;
    }

    public ActionListener getCancelFileAction() {
        return _ -> model.getSelectedShares().getValue().forEach(gui::deleteSharedFile);
    }

    public ActionListener getPreviewFileAction() {
        return _ -> {
            final var share = model.getLastSelectedShare().getValue();
            if (share != null) {
                gui.preview(share);
            }
        };
    }

    public ActionListener getKillPreviewAction() {
        return _ -> gui.killPreview();
    }

    public ActionListener getBlackListFileAction() {
        return _ -> model.getSelectedShares().getValue().forEach(file -> {
            gui.blackList(file);
            gui.deleteSharedFile(file);
        });
    }

}
