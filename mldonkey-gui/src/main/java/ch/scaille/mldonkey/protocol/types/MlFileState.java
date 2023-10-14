/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileDownload;

public class MlFileState extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 state = new MlInt8();
	private final MlString abortionReason = new MlString();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.state, this.abortionReason };
	}

	@Override
	protected boolean hasValue(final IMlType type) {
		if (type == this.abortionReason) {
			return this.value() == FileState.ABORTED;
		}
		return super.hasValue(type);
	}

	@Override
	public String toString() {
		return this.state.toString() + "(" + this.abortionReason.toString() + ")";
	}

	public void fill(final FileDownload download) {
		download.setState(this.value());
	}

	public FileState value() {
		return FileState.values()[this.state.value()];
	}
}
