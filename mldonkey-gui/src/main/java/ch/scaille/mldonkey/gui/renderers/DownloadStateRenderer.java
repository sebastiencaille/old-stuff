/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.types.FileState;

public class DownloadStateRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		this.setText("");
		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else {
			this.setBackground(table.getBackground());
		}
		switch (value) {
		case FileDownload download when download.getChunks().isEmpty() -> this.setText("Zero len");
		case FileDownload download when download.getState() == FileState.QUEUED -> this.setText("Queued");
		case FileDownload download when !download.isHasFirstByte() && download.getDownloadedSize() > 0 -> {
			this.setText("Missing start");
			this.setBackground(Color.ORANGE);
		}
		case FileDownload download when download.isHasFirstByte() -> {
			this.setText("Has start");
			this.setBackground(Color.GREEN.darker());
		}
		default -> { 
			// noop
		}
		}
		return this;
	}
	

}
