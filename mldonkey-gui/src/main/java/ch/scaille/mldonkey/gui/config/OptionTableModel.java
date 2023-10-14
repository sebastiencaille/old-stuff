/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.config;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.model.Option;

public class OptionTableModel extends ListModelTableModel<Option, OptionTableModel.Column> {
	public OptionTableModel(final ListModel<Option> model) {
		super(model, Column.class);
	}

	@Override
	protected Object getValueAtColumn(final Option object, final Column column) {
		return switch (column) {
		case NAME -> object.getName();
		case VALUE -> object.getValue();
		default -> throw new IllegalStateException("Unknown column " + column);
		};

	}

	@Override
	protected void setValueAtColumn(final Option object, final Column column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	public enum Column {
		NAME, VALUE;

		private Column() {
		}
	}

}
