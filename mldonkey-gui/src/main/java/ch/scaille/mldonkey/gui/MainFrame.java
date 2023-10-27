/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ch.scaille.gui.model.IListModelListener;
import ch.scaille.javabeans.Converters;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.clients.ClientsView;
import ch.scaille.mldonkey.gui.config.OptionsPanel;
import ch.scaille.mldonkey.gui.console.ConsolePanel;
import ch.scaille.mldonkey.gui.download.FileDownloadPanel;
import ch.scaille.mldonkey.gui.search.SearchPanel;
import ch.scaille.mldonkey.gui.servers.ServersPanel;
import ch.scaille.mldonkey.gui.shared.SharedFilesPanel;
import ch.scaille.mldonkey.protocol.types.FileState;

public class MainFrame extends JFrame {
	private final MLDonkeyGui gui;
	private final MainFrameController controller;

	public MainFrame(final MLDonkeyGui gui) {
		this.gui = gui;
		this.controller = new MainFrameController(gui, this);
		this.setDefaultCloseOperation(3);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.createToolbar(), BorderLayout.NORTH);
		this.getContentPane().add(this.createMainPane(), BorderLayout.CENTER);
		this.validate();
		this.pack();
	}

	private Component createToolbar() {
		final var panel = new JPanel(new FlowLayout());
		
		final var killPreviewBtn = new JButton("Kill preview");
		killPreviewBtn.addActionListener(this.controller.getKillPreviewAction());
		panel.add(killPreviewBtn);
		
		final var undoBlackListBtn = new JButton("Undo BlackList");
		undoBlackListBtn.addActionListener(this.controller.getUndoBlackListAction());
		panel.add(undoBlackListBtn);
		
		final var saveBlackListBtn = new JButton("Save BlackList");
		saveBlackListBtn.addActionListener(this.controller.getSaveBlackListAction());
		panel.add(saveBlackListBtn);
		
		final var testBtn = new JButton("Test");
		testBtn.addActionListener(this.controller.getTestAction());
		panel.add(testBtn);
		
		final var killCoreBtn = new JButton("Kill Core");
		killCoreBtn.addActionListener(this.controller.getKillCoreAction());
		panel.add(killCoreBtn);
		return panel;
	}

	private Component createMainPane() {
		final var tabbedPane = new JTabbedPane();
		final var searchPanel = new SearchPanel(this.gui);
		tabbedPane.add(searchPanel, "Search");
		tabbedPane.add(new FileDownloadPanel(this.gui, searchPanel.getController()), "Downloads");
		final var sharedFilesPanel = new SharedFilesPanel(this.gui);
		tabbedPane.add(sharedFilesPanel, "Downloaded");
		final var sharedFilesPos = tabbedPane.indexOfComponent(sharedFilesPanel);
		final var originalBgColor = tabbedPane.getBackgroundAt(0);

		final var newDownload = sharedFilesPanel.getController().getModel().getNewDownload();
		newDownload.bind(Converters.booleanConverter(b -> b ? Color.ORANGE : originalBgColor, c -> null))
				.listen(color -> tabbedPane.setBackgroundAt(sharedFilesPos, color));

		this.gui.getDownloads().addListener(IListModelListener.editionStopped(event -> {
			final var download = event.getObject();
			if (download.getState() == FileState.DOWNLOADED && !download.isDownloadNotified()) {
				download.setDownloadNotified(true);
				newDownload.setValue(this, true);
			}
		}));
		tabbedPane.addChangeListener(e -> {
			if (tabbedPane.getSelectedComponent() == sharedFilesPanel) {
				newDownload.setValue(this, false);
			}
		});
		tabbedPane.add(new ClientsView(this.gui), "Clients");
		tabbedPane.add(new ServersPanel(this.gui), "Servers");
		tabbedPane.add(new OptionsPanel(this.gui), "Options");
		tabbedPane.add(new ConsolePanel(this.gui), "Console");
		return tabbedPane;
	}

}
