/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.File;

public class MPlayerRunner extends AbstractPreview {
	public MPlayerRunner(final File tmp, final String info) {
		super(tmp, info);
	}

	@Override
	protected ProcessBuilder createProcessBuilder() {
		return new ProcessBuilder("mplayer", this.getFileName());
	}
}
