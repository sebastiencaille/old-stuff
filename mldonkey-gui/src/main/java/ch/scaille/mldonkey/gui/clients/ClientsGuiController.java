/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.clients;

import ch.scaille.gui.mvc.GuiController;
import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.mldonkey.MLDonkeyGui;

public class ClientsGuiController extends GuiController {
	private final ClientsGuiModel model;

	public ClientsGuiController(final MLDonkeyGui gui) {
		this.model = new ClientsGuiModel(GuiModel.of(this), gui);
	}

	public ClientsGuiModel getModel() {
		return this.model;
	}
}
