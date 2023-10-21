/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Server;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlHostState;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class ServerState extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(ServerState.class);
	
	private final MlInt32 identifier = new MlInt32();
	private final MlHostState hostState = new MlHostState();

	@Override
	public short opCode() {
		return 13;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.identifier, this.hostState };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		if (this.hostState.isRemoved()) {
			LOGGER.log("Removing server(2) " + this.identifier);
			gui.getServers().remove(this.asSample());
			return;
		}
		gui.getServers().findAndEdit(this.asSample(), e -> e.setState(this.hostState.getState()));
	}

	private Server asSample() {
		return new Server(this.identifier.value());
	}
}
