/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.SharedFile;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class SharedFileUnshared extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 fileIdentifier = new MlInt32();

	@Override
	public short opCode() {
		return 35;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.fileIdentifier };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getSharedFiles().remove(new SharedFile(this.fileIdentifier.value()));
	}
}
