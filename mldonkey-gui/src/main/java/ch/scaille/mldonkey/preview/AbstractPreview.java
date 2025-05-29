/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.scaille.mldonkey.GuiLogger;

public abstract class AbstractPreview {
	
	private static final GuiLogger LOGGER = new GuiLogger(AbstractPreview.class);

	
	protected final File tmp;
	protected PreviewProcess process;
	protected JTextArea processLog;
	protected boolean closed;
	private final String info;

	protected AbstractPreview(final File tmp, final String info) {
		this.tmp = tmp;
		this.info = info;
	}

	protected abstract ProcessBuilder createProcessBuilder();

	protected String getFileName() {
		return this.tmp.getAbsolutePath();
	}

	public void preview(final JFrame frame, final long offset) {
		final var dialogContent = this.createDialogContent();
		if (dialogContent != null) {
			// 
			final var win = new JDialog(frame, "Preview " + this.info);
			win.setDefaultCloseOperation(2);
			win.getContentPane().setLayout(new BorderLayout());
			win.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(final WindowEvent e) {
					AbstractPreview.this.kill();
					super.windowClosing(e);
					AbstractPreview.this.closed = true;
				}
			});
			
			final var kill = new JButton("Kill");
			kill.addActionListener(this.killAction());
			win.add(kill, BorderLayout.EAST);
			
			win.add(dialogContent, BorderLayout.CENTER);
			
			this.processLog = new JTextArea();
			final var pane = new JScrollPane(this.processLog);
			pane.setPreferredSize(new Dimension(300, 300));
			
			win.add(pane, BorderLayout.SOUTH);
			win.validate();
			win.pack();
			win.setLocation(frame.getLocation());
			win.setVisible(true);
		} else {
			this.executeInThread();
		}
	}

	protected void executeInThread() {
		new Thread(this::execute).start();
	}

	protected void execute() {
		try {
			this.process = new PreviewProcess(this.createProcessBuilder(), this.processLog);
			this.process.run();
			this.process.waitTerminated();
		} catch (final IOException e) {
			LOGGER.log(e);
		}
	}

	protected void kill() {
		if (this.process != null) {
			this.process.kill();
		}
	}

	protected JComponent createDialogContent() {
		return null;
	}

	private ActionListener killAction() {
		return _ -> kill();
	}

}
