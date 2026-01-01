/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.clients;

import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@Getter
public class ClientsGuiModel extends GuiModel {
	private final ObjectProperty<@Nullable Client> lastSelectedClient = new ObjectProperty<>("LastSelectedClient", this, null);
	private final ClientsTableModel tableModel;

	public ClientsGuiModel(final ModelConfiguration.ModelConfigurationBuilder config, final MLDonkeyGui gui) {
		super(config);
		final var filtered = gui.getClients()
				.child(ListViews.filtered(v -> v.getState() != null && v.getState().isConnected()));
		this.tableModel = new ClientsTableModel(filtered);
	}

}
