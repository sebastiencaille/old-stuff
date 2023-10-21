/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class SearchWaiting extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(SearchWaiting.class);
	
	private final MlInt32 id1 = new MlInt32();
	private final MlInt32 id2 = new MlInt32();

	@Override
	public short opCode() {
		return 6;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.id1, this.id2 };
	}

	@Override
	public void handle(MLDonkeyGui gui) {
		LOGGER.log("Query Waiting " + this.id1 + "/" + this.id2);
	}
}
