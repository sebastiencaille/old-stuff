/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.model.FileDownload;

public class FileDownloadTableModel extends ListModelTableModel<FileDownload, FileDownloadTableModel.Columns> {
	public FileDownloadTableModel(final ListModel<FileDownload> model) {
		super(model, Columns.class);
	}

	@Override
	protected Object getValueAtColumn(final FileDownload object, final Columns column) {
		return switch (column) {
		case FILENAME -> object.getName();
		case SOURCES -> object.getNumberOfSources();
		case SIZE -> object.getFileSize();
		case SPEED -> object.getDownloadRate();
		case RATIO -> (float) object.getDownloadedSize() / (float) object.getFileSize();
		case WARNING -> object.getWarnings();
		case STATE -> object;
		case AVAILABILITY -> object.getAvailability();
		case NOW -> object.getImmediateAvailability();
        };
	}

	@Override
	protected void setValueAtColumn(final FileDownload object, final Columns column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	public enum Columns {
		SOURCES, SIZE, NOW, AVAILABILITY, SPEED, STATE, RATIO, WARNING, FILENAME;

		Columns() {
		}
	}

}
