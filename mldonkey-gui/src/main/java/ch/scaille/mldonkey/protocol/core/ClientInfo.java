/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlClientKind;
import ch.scaille.mldonkey.protocol.types.MlHostState;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlInt8;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;
import ch.scaille.mldonkey.protocol.types.MlTag;

public class ClientInfo extends AbstractMlTypeContainer implements IReceivedMessage {

	private static final GuiLogger LOGGER = new GuiLogger(ClientInfo.class);
	
	private final MlInt32 identifier = new MlInt32();
	private final MlInt32 networkIdentifier = new MlInt32();
	private final MlClientKind kind = new MlClientKind();
	private final MlHostState connectionState = new MlHostState();
	private final MlInt8 type = new MlInt8();
	private final MlList metadata = new MlList(MlTag.class);
	private final MlString name = new MlString();
	private final MlInt32 rating = new MlInt32();
	private final MlString software = new MlString();
	private final MlInt64 downloaded = new MlInt64();
	private final MlInt64 uploaded = new MlInt64();
	private final MlString uploadFileName = new MlString();
	private final MlInt32 connectTime = new MlInt32();
	private final MlString emuleMod = new MlString();
	private final MlString release = new MlString();
	private final MlBool suiVerified = new MlBool();

	@Override
	public short opCode() {
		return 15;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.identifier, this.networkIdentifier, this.kind, this.connectionState, this.type,
				this.metadata, this.name, this.rating, this.software, this.downloaded, this.uploaded,
				this.uploadFileName, this.connectTime, this.emuleMod, this.release, this.suiVerified };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		if (this.connectionState.isRemoved()) {
			LOGGER.log("Removing client " + this.identifier);
			gui.getClients().remove(this.asSample());
			return;
		}
		gui.getClients().findOrCreateAndEdit(this.asSample(), c -> {
			c.setName(this.name.value());
			c.setGeoIp(this.kind.getGeoIp());
			c.setSoftware(this.software.value());
			c.setRelease(this.release.value());
			c.setState(this.connectionState.getState());
		});
	}

	private Client asSample() {
		return new Client(this.identifier.value());
	}
}
