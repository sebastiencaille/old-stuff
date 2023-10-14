/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;

public class Stub extends AbstractMlTypeContainer implements IReceivedMessage {
	@Override
	public short opCode() {
		return 0;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[0];
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		// not used
	}
}
