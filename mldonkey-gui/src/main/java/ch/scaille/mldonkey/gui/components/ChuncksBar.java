/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import ch.scaille.gui.mvc.ComponentBindingAdapter;
import ch.scaille.gui.mvc.factories.ComponentBindings;
import ch.scaille.javabeans.IComponentBinding;
import ch.scaille.javabeans.properties.AbstractProperty;

public class ChuncksBar extends JComponent {
	private String value = "0";
	private long size = 0;
	private long selectedOffset = 0;

	public ChuncksBar() {
		this.setPreferredSize(new Dimension(100, 16));
		this.setOpaque(true);
		final var mouseListener = new MouseListener();
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}

	protected void setSelectedOffset(final long mb) {
		this.selectedOffset = mb;
		this.firePropertyChange("SelectedOffset", -1, this.selectedOffset);
	}

	public void addSelectedOffsetListener(final PropertyChangeListener listener) {
		this.addPropertyChangeListener("SelectedOffset", listener);
	}

	public IComponentBinding<String> valueBinding() {
		return ComponentBindings.listen((p, v) -> {
			this.value = v;
			this.repaint();
		});
	}

	public IComponentBinding<Long> sizeBinding() {
		return new ComponentBindingAdapter<>() {

			@Override
			public void setComponentValue(final AbstractProperty source, final Long value) {
				ChuncksBar.this.size = value;
				ChuncksBar.this.repaint();
			}
		};
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final var chunckSize = 9728000.0f * this.getWidth() / this.size;
		if (this.value.length() == 0) {
			return;
		}
		var last = this.value.charAt(0);
		var start = 0;
		for (var pos = 1; pos < this.value.length(); ++pos) {
			final var chunk = this.value.charAt(pos);
			if (chunk != last) {
				start = this.drawChuncks(g, chunckSize, last, start, pos);
			}
			last = chunk;
		}
		this.drawChuncks(g, chunckSize, last, start, this.value.length());
	}

	private int drawChuncks(final Graphics g, final float chunckSize, final char last, final int start,
			final int endChunk) {
		switch (last) {
		case '0' -> g.setColor(Color.RED);
		case '1' -> g.setColor(Color.CYAN);
		case '2' -> g.setColor(Color.GREEN.darker());
		case '3' -> g.setColor(Color.GREEN);
		default -> g.setColor(Color.BLACK);
		}
		final int height = this.getHeight();
		int end = (int) (chunckSize * endChunk);
		if (end > this.getWidth()) {
			end = this.getWidth();
		}
		final int width = end - start;
		if (g.getClipBounds().intersects(start, 0.0, width, height)) {
			g.fillRect(start, 0, width, height);
		}
		return end;
	}

	private final class MouseListener extends MouseAdapter {
		Point oldPos;

		private MouseListener() {
			this.oldPos = null;
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (this.oldPos == null || Math.abs(this.oldPos.x - e.getPoint().x) > 5) {
				final var percent = this.asPercent(e);
				final var mb = this.asOffset(percent);
				ChuncksBar.this.setToolTipText(Long.toString((long) percent) + '/' + Long.toString(mb) + "Mb/"
						+ Long.toString(ChuncksBar.this.size / 0x100000 - mb) + "Mb");
				this.oldPos = e.getPoint();
			}
		}

		private long asOffset(final double percent) {
			return (long) (percent * ChuncksBar.this.size) / 104857600;
		}

		private double asPercent(final MouseEvent e) {
			return 100.0 * e.getPoint().x / ChuncksBar.this.getWidth();
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			this.oldPos = null;
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			final var percent = this.asPercent(e);
			final var mb = this.asOffset(percent);
			ChuncksBar.this.setSelectedOffset(mb);
		}
	}

}
