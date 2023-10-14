/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MDLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class CoreProtocol extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final MDLogger LOGGER = new MDLogger(CoreProtocol.class);
	
	private final MlInt32 maxCoreProtocol = new MlInt32();
	private final MlInt32 maxOpCodeGui = new MlInt32();
	private final MlInt32 maxOpCodeCore = new MlInt32();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.maxCoreProtocol, this.maxOpCodeGui, this.maxOpCodeCore };
	}

	@Override
	public short opCode() {
		return 0;
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		LOGGER.log(this.maxCoreProtocol.toString());
		LOGGER.log(this.maxOpCodeGui.toString());
		LOGGER.log(this.maxOpCodeCore.toString());
		gui.handShake();
	}
}
