/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.function.LongUnaryOperator;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.PropertyChangeSupportController;
import ch.scaille.javabeans.IPropertiesGroup;
import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.model.FileDownload;

/**
 * Run tail and inject into mvp, hoping that may recover some parts of the corrupted stream
 */
public class TailToMpvRunner extends AbstractPreview {

	private static final GuiLogger LOGGER = new GuiLogger(TailToMpvRunner.class);
	
	private final Random random = new Random(System.currentTimeMillis());

	private final IPropertiesGroup support;
	private final TailRunnerModel model;
	private final FileDownload download;
	private Thread previewThread;
	private long stepInMb;
	private final String fileCmdOutput;
	private Thread tailThread;
	private static final long MB = 972_800;

	public TailToMpvRunner(final File tmp, final FileDownload download, final String fileCmdOutput, final String info) {
		super(tmp, info);
		this.support = new PropertyChangeSupportController(this).scoped(this);
		this.model = new TailRunnerModel(GuiModel.with(this.support));
		this.download = download;
		this.fileCmdOutput = fileCmdOutput;
	}

	@Override
	public void preview(final JFrame win, final long offset) {
		super.preview(win, offset);
		this.support.attachAll();
		if (offset >= 0) {
			this.stepInMb = MB;
			this.previewThread = new Thread(() -> {
				try {
					long pos = offset * 1024 * 1024;
					pos = MB * (pos / MB + 1);
					TailToMpvRunner.this.doPreview(pos, true);
				} finally {
					TailToMpvRunner.this.previewThread = null;
				}
			});
			this.previewThread.start();
		}
	}

	@Override
	protected ProcessBuilder createProcessBuilder() {
		return new ProcessBuilder("mpv", "-");
	}

	private boolean checkFile(final RandomAccessFile raf, final long pos) throws IOException {
		raf.seek(pos);
		var found = false;
		final var buffer = new byte[5242880];
		final var len = raf.read(buffer);
		for (var i = 0; i < Math.min(len, 100); ++i) {
			found = (buffer[i] != 0);
			if (found) {
				break;
			}
		}
		return found;
	}

	@Override
	protected void execute() {
		this.tailThread = null;
		final var file = new File(this.getFileName());
		try (var raf = new RandomAccessFile(file, "r")) {
			final long pos = this.model.getPos().getValue();
			LOGGER.log("Seeking to " + pos / 0x100000);
			if (!this.checkFile(raf, pos)) {
				return;
			}
			this.process = new PreviewProcess(this.createProcessBuilder(), this.processLog);
			this.process.run();
			this.tailThread = new Thread(() -> runPreview(file, pos), "Tail");
			this.tailThread.start();
		} catch (final IOException e) {
			LOGGER.log(e);
		}

		final var start = System.currentTimeMillis();
		while (!this.process.isTerminated()) {
			try {
				if (System.currentTimeMillis() - start > 10000) {
					break;
				}
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				// empty catch block
				Thread.interrupted();
				break;
			}
		}
		if (this.process != null) {
			this.process.kill();
		}
		if (this.tailThread != null) {
			this.tailThread.interrupt();
		}

	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 * Enabled aggressive exception aggregation
	 */
	private void runPreview(final File file, final long pos) {
		try (var raf2 = new RandomAccessFile(file, "r")) {
			raf2.seek(pos);
			final var buffer2332 = new byte[5242880];
			do {
				int len;
				if ((len = raf2.read(buffer2332)) < 0) {
					return;
				}
				TailToMpvRunner.this.process.injectIntoProcess(buffer2332, 0, len);
				break;
			} while (true);
		} catch (final IOException raf2) {
			// ignore
		} catch (final Exception e) {
			LOGGER.log(e);
		}
	}

	@Override
	protected JComponent createDialogContent() {
		final var main = new JPanel(new BorderLayout());
		final var buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(this.button(0.25f));
		buttonPanel.add(this.button(0.5f));
		buttonPanel.add(this.button(1.0f));
		buttonPanel.add(this.button(5.0f));
		buttonPanel.add(this.button(-2f));
		final var button = new JButton("1234");
		this.model.getPos().listenActive(pos -> {
			long value = pos;
			button.setEnabled(value >= 0);
			if (value < 0) {
				value = 0;
			}
			button.setText(Long.toString(value / 0x100000));
		});
		button.addActionListener(this.next());
		buttonPanel.add(button);
		main.add(buttonPanel, BorderLayout.NORTH);
		main.add(new JLabel(this.fileCmdOutput), BorderLayout.SOUTH);
		return main;

	}

	private ActionListener smartRun(final JButton button, final float step) {
		return e -> {

			if (step == -2) {
				if (previewThread != null) {
					previewThread.interrupt();
				}
				this.previewThread = new Thread(() -> {
					final var file = new File(this.getFileName());
					try (final var raf = new RandomAccessFile(file, "r")) {
						while (!closed) {
							final long pos = random.nextLong() % download.getFileSize();
							SwingUtilities.invokeLater(() -> model.pos.setValue(this, pos));
							if (checkFile(raf, pos)) {
								TailToMpvRunner.this.doPreview(pos, false);
							}
						}
					} catch (final IOException ex) {
						ex.printStackTrace();
					}
				});
				previewThread.start();
				return;
			}

			TailToMpvRunner.this.stepInMb = (int) (step * 1024.0f * 1024.0f);
			if (TailToMpvRunner.this.previewThread != null) {
				return;
			}
			TailToMpvRunner.this.previewThread = new Thread() {

				@Override
				public void run() {
					try {
						long pos = 0;
						if (TailToMpvRunner.this.download.getChunks().charAt(0) == '0') {
							pos = TailToMpvRunner.this.skipToNextChunckWithContent(pos);
						}
						TailToMpvRunner.this.doPreview(pos, true);
					} finally {
						button.setEnabled(true);
						TailToMpvRunner.this.previewThread = null;
					}
				}
			};
			TailToMpvRunner.this.previewThread.start();
		};
	}

	private ActionListener next() {
		return e -> {
			if (TailToMpvRunner.this.tailThread != null) {
				TailToMpvRunner.this.tailThread.interrupt();
			}
			TailToMpvRunner.super.kill();
		};
	}

	private void doPreview(final long startPos, final boolean tryMore) {
		this.preview(startPos, pos -> {
			if (pos < 0) {
				return -1L;
			}
			final long newPos = pos + this.stepInMb;
			return this.skipToNextChunckWithContent(newPos);
		}, tryMore);
	}

	protected long skipToNextChunckWithContent(final long pos) {
		final int chunckCount = (int) (pos / 9728000);
		float newPos2 = Math.max((long) chunckCount * 9728000, pos);
		var first = true;
		for (final char c : this.download.getChunks().substring(chunckCount).toCharArray()) {
			if (c == '0') {
				newPos2 = first ? (float) ((long) (chunckCount + 1) * 9728000) : (newPos2 += 9728000.0f);
			} else {
				return (long) newPos2;
			}
			first = false;
		}
		return -1;
	}

	@Override
	public void kill() {
		if (this.tailThread != null) {
			this.tailThread.interrupt();
		}
		this.model.getPos().setValue(this, -1);
		super.kill();
	}

	private Component button(final float tailGap) {
		final var button = new JButton(Float.toString(tailGap));
		button.addActionListener(this.smartRun(button, tailGap));
		return button;
	}

	private void preview(final long start, final LongUnaryOperator tailSizeProvider, final boolean tryMore) {
		long pos = start;
		this.setPos(pos);
		while (pos < this.tmp.length() && !this.closed && pos >= 0) {
			try {
				this.execute();
			} catch (final Exception e1) {
				LOGGER.log(e1);
				return;
			}
			pos = this.model.getPos().getValue();
			pos = tailSizeProvider.applyAsLong(pos);
			this.setPos(pos);
			if (!tryMore) {
				return;
			}
		}
		if (pos >= this.tmp.length()) {
			this.setPos(Long.MIN_VALUE);
		}
	}

	private void setPos(final long tail) {
		try {
			final long val = tail;
			EventQueue.invokeAndWait(() -> TailToMpvRunner.this.model.getPos().setValue(this, val));
		} catch (final InterruptedException e) {
			try {
				SwingUtilities.invokeAndWait(this::kill);
			} catch (InvocationTargetException | InterruptedException e1) {
				LOGGER.log(e1);
			}
		} catch (final Exception e) {
			LOGGER.log(e);
		}
	}

}
