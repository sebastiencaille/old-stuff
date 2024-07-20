/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.renderers;

import java.awt.Component;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import ch.scaille.gui.swing.SwingExt;
import ch.scaille.mldonkey.model.WarningLevel;

public class WarningRenderer extends JLabel implements TableCellRenderer {
	private static final ImageIcon warnIcon;
	private static final ImageIcon tickIcon;
	private static final ImageIcon forbiddenIcon;
	private static final ImageIcon questionMarkIcon;

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
			final boolean hasFocus, final int row, final int column) {
		final WarningLevel warn = (WarningLevel) (value);
		if (warn == null) {
			setIcon(null);
			return this;
		}
		final ImageIcon icon = switch (warn) {
		case BLACK_LISTED -> forbiddenIcon;
		case WARN -> warnIcon;
		case ACK -> tickIcon;
		case UNK -> questionMarkIcon;
		default -> null;
		};
		setIcon(icon);
		return this;
	}

	static {
		try {
			final ClassLoader classLoader = WarningRenderer.class.getClassLoader();
			warnIcon = SwingExt.iconFromStream(() -> classLoader.getResourceAsStream("warning-icon.png"));
			tickIcon = SwingExt.iconFromStream(() -> classLoader.getResourceAsStream("tick-icon.png"));
			forbiddenIcon = SwingExt.iconFromStream(() -> classLoader.getResourceAsStream("forbidden-icon.png"));
			questionMarkIcon = SwingExt
					.iconFromStream(() -> classLoader.getResourceAsStream("question-mark-icon.png"));
		} catch (final IOException e) {
			throw new IllegalStateException("Cannot load icon");
		}
	}
}
