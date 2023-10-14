/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.File;

public class ZipRunner extends AbstractPreview {
	public ZipRunner(final File tmp, final String info) {
		super(tmp, info);
	}

	@Override
	protected ProcessBuilder createProcessBuilder() {
		final var pb = new ProcessBuilder("zip", "-FF", "-out", "tmp.zip", this.getFileName());
		final var tempfolder = new File(new File(this.getFileName()).getParentFile(), "tmp");
		tempfolder.mkdirs();
		pb.directory(tempfolder);
		return pb;
	}

}
