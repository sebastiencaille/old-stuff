/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;

public class BadPassword extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(BadPassword.class);
	
	@Override
	public short opCode() {
		return 47;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[0];
	}

	@Override
	public void handle(MLDonkeyGui gui) {
		LOGGER.log("Bad password");
	}
}
