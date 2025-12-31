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
import java.util.Objects;

import javax.swing.JComponent;

import ch.scaille.gui.mvc.ComponentBindingAdapter;
import ch.scaille.gui.mvc.factories.ComponentBindings;
import ch.scaille.javabeans.IComponentBinding;
import ch.scaille.javabeans.IComponentChangeSource;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ChunksBar extends JComponent {
	private String value = "0";
	private long size = 0;

    public ChunksBar() {
		this.setPreferredSize(new Dimension(100, 16));
		this.setOpaque(true);
		final var mouseListener = new MouseListener();
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}

	protected void setSelectedOffset(final long mb) {
        this.firePropertyChange("SelectedOffset", -1, mb);
	}

	public void addSelectedOffsetListener(final PropertyChangeListener listener) {
		this.addPropertyChangeListener("SelectedOffset", listener);
	}

	public IComponentBinding<String> valueBinding() {
		return ComponentBindings.listen((_, v) -> {
			this.value = v;
			this.repaint();
		});
	}

	public IComponentBinding<Long> sizeBinding() {
		return new ComponentBindingAdapter<>() {

			@Override
			public void setComponentValue(final IComponentChangeSource source, @Nullable final Long value) {
				ChunksBar.this.size = Objects.requireNonNullElse(value, 0L);
				ChunksBar.this.repaint();
			}
		};
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final var chunkSize = 9728000.0f * this.getWidth() / this.size;
		if (this.value.isEmpty()) {
			return;
		}
		var last = this.value.charAt(0);
		var start = 0;
		for (var pos = 1; pos < this.value.length(); ++pos) {
			final var chunk = this.value.charAt(pos);
			if (chunk != last) {
				start = this.drawChunks(g, chunkSize, last, start, pos);
			}
			last = chunk;
		}
		this.drawChunks(g, chunkSize, last, start, this.value.length());
	}

	private int drawChunks(final Graphics g, final float chunkSize, final char last, final int start,
			final int endChunk) {
		switch (last) {
		case '0' -> g.setColor(Color.RED);
		case '1' -> g.setColor(Color.CYAN);
		case '2' -> g.setColor(Color.GREEN.darker());
		case '3' -> g.setColor(Color.GREEN);
		default -> g.setColor(Color.BLACK);
		}
		final int height = this.getHeight();
		int end = (int) (chunkSize * endChunk);
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
		@Nullable
		private Point oldPos;

		private MouseListener() {
			this.oldPos = null;
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (this.oldPos == null || Math.abs(this.oldPos.x - e.getPoint().x) > 5) {
				final var percent = this.asPercent(e);
				final var mb = this.asOffset(percent);
				ChunksBar.this.setToolTipText(Long.toString((long) percent) + '/' + mb + "Mb/"
						+ (ChunksBar.this.size / 0x100000 - mb) + "Mb");
				this.oldPos = e.getPoint();
			}
		}

		private long asOffset(final double percent) {
			return (long) (percent * ChunksBar.this.size) / 104857600;
		}

		private double asPercent(final MouseEvent e) {
			return 100.0 * e.getPoint().x / ChunksBar.this.getWidth();
		}

		@Override
		public void mouseExited(final MouseEvent e) {
			this.oldPos = null;
		}

		@Override
		public void mouseClicked(final MouseEvent e) {
			final var percent = this.asPercent(e);
			final var mb = this.asOffset(percent);
			ChunksBar.this.setSelectedOffset(mb);
		}
	}

}
