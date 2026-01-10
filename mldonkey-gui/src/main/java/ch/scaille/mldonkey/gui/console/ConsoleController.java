/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.console;

import static ch.scaille.gui.mvc.GuiModel.of;

import java.awt.event.ActionListener;

import ch.scaille.gui.mvc.GuiController;
import lombok.Getter;

@Getter
public class ConsoleController extends GuiController {
	private final ConsoleModel model;

	public ConsoleController() {
		this.model = new ConsoleModel(of(this));
	}

    public ActionListener getClearAction() {
		return e -> this.model.getConsole().setValue(e.getSource(), "");
	}
}
