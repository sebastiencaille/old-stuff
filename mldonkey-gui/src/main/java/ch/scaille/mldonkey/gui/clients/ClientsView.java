/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.clients;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ch.scaille.gui.swing.factories.SwingBindings;
import ch.scaille.mldonkey.MLDonkeyGui;

public class ClientsView extends JPanel {
	public ClientsView(final MLDonkeyGui gui) {
		this.setLayout(new BorderLayout());
		final var controller = new ClientsGuiController(gui);
		final var model = controller.getModel();

		final var clientsTable = new JTable(model.getTableModel());
		clientsTable.setAutoCreateColumnsFromModel(false);
		this.add(new JScrollPane(clientsTable), BorderLayout.CENTER);

		model.getLastSelectedClient().bind(SwingBindings.selection(clientsTable, model.getTableModel()));

		controller.activate();
	}
}
