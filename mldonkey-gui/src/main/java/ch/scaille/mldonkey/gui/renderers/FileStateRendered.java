/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ch.scaille.mldonkey.protocol.types.FileState;

public class FileStateRendered extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (isSelected) {
			this.setBackground(table.getSelectionBackground());
		} else if (value == FileState.SHARED) {
			this.setBackground(Color.orange.brighter());
		} else {
			this.setBackground(table.getBackground());
		}
		if (value == FileState.DOWNLOADING) {
			this.setText("");
		} else {
			this.setText(value.toString());
		}
		return this;
	}
}
