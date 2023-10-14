/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class ProtocolVersion extends AbstractMlTypeContainer implements ISentMessage {
	private final MlInt32 version = new MlInt32();

	public ProtocolVersion(int version) {
		this.version.set(version);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.version };
	}

	@Override
	public short opCode() {
		return 0;
	}
}
