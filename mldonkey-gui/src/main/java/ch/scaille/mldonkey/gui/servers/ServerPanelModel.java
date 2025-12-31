/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.servers;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.model.Server;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ServerPanelModel extends GuiModel {
	private final BooleanProperty showAll = new BooleanProperty("ShowAll", this);
	private final ObjectProperty<Server> lastSelectedServer = new ObjectProperty<>("LastSelected", this);

	public ServerPanelModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super(config);
	}

	public BooleanProperty getShowAll() {
		return this.showAll;
	}

	public ObjectProperty<Server> getLastSelectedServer() {
		return this.lastSelectedServer;
	}
}
