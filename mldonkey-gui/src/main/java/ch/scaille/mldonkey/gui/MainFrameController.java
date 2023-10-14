/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ch.scaille.mldonkey.MLDonkeyGui;

public class MainFrameController {
	private final MLDonkeyGui gui;
	private final JFrame mainFrame;

	public MainFrameController(final MLDonkeyGui gui, final JFrame mainFrame) {
		this.gui = gui;
		this.mainFrame = mainFrame;
	}

	public ActionListener getKillPreviewAction() {
		return e -> MainFrameController.this.gui.killPreview();
	}

	public ActionListener getSaveBlackListAction() {
		return e -> MainFrameController.this.gui.saveBlackList();
	}

	public ActionListener getUndoBlackListAction() {
		return e -> MainFrameController.this.gui.undoBlackList();
	}

	public ActionListener getKillCoreAction() {
		return e -> {
			final int killCore = JOptionPane.showConfirmDialog(MainFrameController.this.mainFrame,
					"Are you sure that you want to kill the core ?", "Kill Core", 0);
			if (killCore == 0) {
				MainFrameController.this.gui.killCore();
			}
		};
	}

	public ActionListener getTestAction() {
		return e -> MainFrameController.this.gui.test();
	}

}
