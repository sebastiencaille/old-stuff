/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.config;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ch.scaille.mldonkey.MLDonkeyGui;

public class OptionsPanel extends JPanel {
	public OptionsPanel(final MLDonkeyGui gui) {
		final var options = gui.getOptions();
		final var model = new OptionTableModel(options);
		final var table = new JTable(model);
		this.add(new JScrollPane(table));
	}
}
