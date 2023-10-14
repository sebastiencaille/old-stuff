/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlString;

public class ConsoleMessage extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlString msg = new MlString();

	@Override
	public short opCode() {
		return 19;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.msg };
	}

	@Override
	public void handle(MLDonkeyGui gui) {
		gui.logConsole(this.msg.value());
	}
}
