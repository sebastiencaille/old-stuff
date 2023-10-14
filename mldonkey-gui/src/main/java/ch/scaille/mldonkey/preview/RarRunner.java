/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.File;

public class RarRunner extends AbstractPreview {
	public RarRunner(final File tmp, final String info) {
		super(tmp, info);
	}

	@Override
	protected ProcessBuilder createProcessBuilder() {
		final var pb = new ProcessBuilder("unrar", "x", "-kb", this.getFileName());
		final var tempfolder = new File(new File(this.getFileName()).getParentFile(), "tmp");
		tempfolder.mkdirs();
		pb.directory(tempfolder);
		return pb;
	}
}
