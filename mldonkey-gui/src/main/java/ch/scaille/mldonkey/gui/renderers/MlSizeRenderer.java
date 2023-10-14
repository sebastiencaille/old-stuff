package ch.scaille.mldonkey.gui.renderers;

import java.awt.Color;
import java.awt.Component;

public class MlSizeRenderer extends ch.scaille.gui.swing.renderers.SizeRenderer {

	public MlSizeRenderer() {
		super(MlSizeRenderer::setColor);
	}

	private static void setColor(long size, Component comp) {
		long sizeM = size / (1024 * 1024);
		Color fg = Color.LIGHT_GRAY;
		Color bg;
		if (sizeM < 2) {
			bg = Color.CYAN;
			fg = Color.BLACK;
		} else if (sizeM < 50) {
			bg = Color.CYAN.darker();
			fg = Color.BLACK;
		} else if (sizeM < 200) {
			bg = Color.BLUE.brighter();
		} else {
			bg = Color.BLUE.darker();
		}
		comp.setBackground(bg);
		comp.setForeground(fg);
	}

}
