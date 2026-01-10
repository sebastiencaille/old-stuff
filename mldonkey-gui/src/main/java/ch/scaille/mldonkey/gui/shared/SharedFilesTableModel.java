/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.model.SharedFile;

public class SharedFilesTableModel extends ListModelTableModel<SharedFile, SharedFilesTableModel.Columns> {
	public SharedFilesTableModel(final ListModel<SharedFile> model) {
		super(model, Columns.class);
	}

	@Override
	protected Object getValueAtColumn(final SharedFile object, final Columns column) {
		return switch (column) {
		case DATE -> object.getTimestamp();
		case FILENAME -> object.getName();
		case SIZE -> object.getSize();
        };
	}

	@Override
	protected void setValueAtColumn(final SharedFile object, final Columns column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	@Override
	protected boolean verbose() {
		return true;
	}

	public enum Columns {
		DATE, FILENAME, SIZE;

		Columns() {
		}
	}

}
