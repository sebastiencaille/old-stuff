/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.clients;

import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.gui.mvc.properties.ObjectProperty;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;

public class ClientsGuiModel extends GuiModel {
	private final ObjectProperty<Client> lastSelectedClient = new ObjectProperty<>("LastSelectedClient", this);
	private final ClientsTableModel tableModel;

	public ClientsGuiModel(final ModelConfiguration config, final MLDonkeyGui gui) {
		super(config);
		final var filtered = gui.getClients()
				.child(ListViews.filtered(v -> v.getState() != null && v.getState().isConnected()));
		this.tableModel = new ClientsTableModel(filtered);
	}

	public ObjectProperty<Client> getLastSelectedClient() {
		return this.lastSelectedClient;
	}

	public ClientsTableModel getTableModel() {
		return this.tableModel;
	}
}
