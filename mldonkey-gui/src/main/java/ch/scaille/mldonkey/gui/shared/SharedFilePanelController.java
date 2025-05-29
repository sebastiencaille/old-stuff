/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.mldonkey.MLDonkeyGui;

public class SharedFilePanelController extends GuiController {
	private final MLDonkeyGui gui;
	private final SharedFilePanelModel model;

	public SharedFilePanelController(final MLDonkeyGui gui) {
		this.model = new SharedFilePanelModel(of(this));
		this.gui = gui;
	}

	public SharedFilePanelModel getModel() {
		return this.model;
	}

	public ActionListener getCancelFileAction() {
		return _ -> model.getSelectedShares().getValue().forEach(gui::deleteSharedFile);
	}

	public ActionListener getPreviewFileAction() {
		return _ -> gui.preview(model.getLastSelectedShare().getValue());
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
