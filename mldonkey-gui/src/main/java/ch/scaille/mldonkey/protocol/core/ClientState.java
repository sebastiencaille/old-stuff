/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlHostState;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class ClientState extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 identifier = new MlInt32();
	private final MlHostState hostState = new MlHostState();

	@Override
	public short opCode() {
		return 16;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.identifier, this.hostState };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		if (this.hostState.isRemoved()) {
			gui.getClients().remove(this.asSample());
			return;
		}
		gui.getClients().findAndEdit(this.asSample(), c -> c.setState(this.hostState.getState()));
	}

	private Client asSample() {
		return new Client(this.identifier.value());
	}
}
