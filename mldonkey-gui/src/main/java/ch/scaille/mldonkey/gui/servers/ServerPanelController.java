/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.servers;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.DynamicListView;
import ch.scaille.gui.mvc.GuiController;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Server;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@NullMarked
@Getter
public class ServerPanelController extends GuiController {
	private final MLDonkeyGui gui;
	private final ServerPanelModel model;

	public ServerPanelController(final MLDonkeyGui gui) {
		this.model = new ServerPanelModel(of(this));
		this.gui = gui;
	}

	public ListModel<Server> getFilteredServers() {
		final var view = new DynamicListView<Server, Boolean>(false, null,
				(server, showAll) -> showAll || server.getState() != null && server.getState().isConnected());
		this.model.getShowAll().bind(view);
		return gui.getServers().child(view);
	}

	public ActionListener getDisconnectAction() {
		return _ -> {
			final var server = ServerPanelController.this.model.getLastSelectedServer().getValue();
			if (server != null) {
				gui.disconnectServer(server);
			}
		};
	}

	public ActionListener getConnectMoreAction() {
		return _ -> gui.connectMoreServers();
	}

}
