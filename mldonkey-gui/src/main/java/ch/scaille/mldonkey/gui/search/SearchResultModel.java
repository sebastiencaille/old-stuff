/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.model.FileQueryResult;

public class SearchResultModel extends ListModelTableModel<FileQueryResult, SearchResultModel.Columns> {
	
	public SearchResultModel(final ListModel<FileQueryResult> model) {
		super(model, Columns.class);
	}

	@Override
	protected Object getValueAtColumn(final FileQueryResult object, final Columns column) {
		return switch (column) {
		case NAME -> object.getFileNames().getFirst();
		case SIZE -> object.getFileSize();
		case TYPE -> {
			final String name = object.getFileNames().getFirst();
			final int lastDotIndex = name.lastIndexOf(46) + 1;
			if (lastDotIndex < 1 || name.length() - lastDotIndex > 5) {
				yield "";
			}
			yield name.substring(lastDotIndex);
		}
		case COMPLETE -> "%s / %s".formatted(object.getAvailability(), object.completeSources());
		case WARNING -> object.getWarnings();
        };
	}

	@Override
	protected void setValueAtColumn(final FileQueryResult object, final Columns column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	public enum Columns {
		TYPE, SIZE, COMPLETE, WARNING, NAME;

		Columns() {
		}
	}

}
