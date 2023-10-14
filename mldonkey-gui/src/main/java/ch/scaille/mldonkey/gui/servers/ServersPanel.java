/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.servers;

import static ch.scaille.gui.swing.factories.SwingBindings.selection;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.factories.SwingBindings;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Server;

public class ServersPanel extends JPanel {
	public ServersPanel(final MLDonkeyGui gui) {

		this.setLayout(new BorderLayout());

		final var controller = new ServerPanelController(gui);
		final var controlPanel = new JPanel();

		final var connectMore = new JButton("More");
		connectMore.addActionListener(controller.getConnectMoreAction());
		controlPanel.add(connectMore);

		final var showAll = new JCheckBox("Show all");
		controlPanel.add(showAll);
		controller.getModel().getShowAll().bind(SwingBindings.selected(showAll));
		this.add(controlPanel, BorderLayout.NORTH);

		final var serversModel = new ServersTableModel(controller.getFilteredServers());
		final var serversTable = new JTable(serversModel);
		controller.getModel().getLastSelectedServer().bind(selection(serversTable, serversModel));
		serversTable.addMouseListener(new AbstractJTablePopup<>(serversTable, serversModel,
				controller.getModel().getLastSelectedServer(), null) {

			@Override
			protected void buildPopup(final JPopupMenu popup, final Server clicked) {
				popup.removeAll();
				final var disconnect = new JMenuItem("Disconnect");
				disconnect.addActionListener(controller.getDisconnectAction());
				popup.add(disconnect);
			}
		});
		serversTable.setAutoCreateColumnsFromModel(false);
		controller.activate();

		this.add(new JScrollPane(serversTable), BorderLayout.CENTER);
	}

}
