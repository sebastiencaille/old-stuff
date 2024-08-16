/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PreviewProcess {

	protected Process currentProcess;
	private final ProcessBuilder processBuilder;
	private final JTextArea processLog;

	public PreviewProcess(final ProcessBuilder processBuilder, final JTextArea processLog) {
		this.processBuilder = processBuilder;
		this.processLog = processLog;
	}

	/** 
	 * Runs the process
     */
	public void run() throws IOException {
		this.processBuilder.redirectErrorStream(true);
		if (this.processLog != null) {
			SwingUtilities.invokeLater(() -> processLog.setText(""));
		}
		this.currentProcess = processBuilder.start();
		new Thread(() -> {
			try (var stream = currentProcess.getInputStream()) {
				handleOutput(stream);
			} catch (final IOException stream) {
				// empty catch block
			}
		}).start();
	}

	public void waitTerminated() {
		this.waitProcessTerminated();
	}

	protected void handleOutput(final InputStream stream) throws IOException {
		try (var reader = new BufferedReader(new InputStreamReader(stream))) {
			String line;
			String lastLine = null;
			var ignoreLine = false;
			while ((line = reader.readLine()) != null) {
				var myLine = line;
				final boolean sameAsPreviousLine = line.equals(lastLine);
				if (sameAsPreviousLine) {
					myLine = "<same>";
				} else {
					ignoreLine = false;
				}
				if (this.processLog != null && !ignoreLine) {
					this.addText(myLine);
				}
				// If same line, don't display it
				ignoreLine = sameAsPreviousLine;
				lastLine = line;
			}
		}
	}

	private void addText(final String myLine) {
		SwingUtilities.invokeLater(() -> processLog.setText(myLine));
	}

	public void kill() {
		if (this.currentProcess == null) {
			return;
		}
		this.currentProcess.destroyForcibly();
		while (!this.isTerminated()) {
			final Process myProcess = this.currentProcess;
			if (myProcess == null) {
				return;
			}
			myProcess.destroy();
		}
	}

	protected void waitProcessTerminated() {
		while (!this.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (final InterruptedException e) {
				Thread.interrupted();
				return;
			}
		}
	}

	public boolean isTerminated() {
		if (this.currentProcess == null) {
			return false;
		}
		try {
			Thread.sleep(100);
			this.currentProcess.exitValue();
			return true;
		} catch (final Exception exception) {
			Thread.interrupted();
			return false;
		}
	}

	public void injectIntoProcess(final byte[] buffer, final int off, final int len) throws IOException {
		if (this.currentProcess == null || this.currentProcess.isAlive()) {
			return;
		}
		this.currentProcess.getOutputStream().write(buffer, off, len);
	}

}
