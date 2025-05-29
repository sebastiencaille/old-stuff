/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.servers;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.BoundFilter;
import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.mvc.GuiController;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Server;

public class ServerPanelController extends GuiController {
	private final MLDonkeyGui gui;
	private final ServerPanelModel model;

	public ServerPanelController(final MLDonkeyGui gui) {
		this.model = new ServerPanelModel(of(this));
		this.gui = gui;
	}

	public ServerPanelModel getModel() {
		return this.model;
	}

	public ListModel<Server> getFilteredServers() {
		final var filter = BoundFilter.<Server, Boolean>filter(
				(server, showAll) -> showAll || server.getState() != null && server.getState().isConnected());
		final var filtered = gui.getServers().child(ListViews.filtered(filter));
		this.model.getShowAll().bind(filter);
		return filtered;
	}

	public ActionListener getDisconnectAction() {
		return _ -> gui.disconnectServer(ServerPanelController.this.model.getLastSelectedServer().getValue());
	}

	public ActionListener getConnectMoreAction() {
		return _ -> gui.connectMoreServers();
	}

}
