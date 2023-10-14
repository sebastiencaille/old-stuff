/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.File;

public class DivFixRunner extends AbstractPreview {
	public DivFixRunner(final File tmp, final String info) {
		super(tmp, info);
	}

	@Override
	protected ProcessBuilder createProcessBuilder() {
		return new ProcessBuilder("DivFix++", this.getFileName());
	}
}
