/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Server;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlAddr;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlHostState;
import ch.scaille.mldonkey.protocol.types.MlInt16;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;
import ch.scaille.mldonkey.protocol.types.MlTag;

public class ServerInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 id = new MlInt32();
	private final MlInt32 networkIdentifier = new MlInt32();
	private final MlAddr internetAddress = new MlAddr();
	private final MlInt16 serverPort = new MlInt16();
	private final MlInt32 serverScore = new MlInt32();
	private final MlList metadata = new MlList(MlTag.class);
	private final MlInt64 numberOfUser = new MlInt64();
	private final MlInt64 numberOfFiles = new MlInt64();
	private final MlHostState connectionState = new MlHostState();
	private final MlString serverName = new MlString();
	private final MlString serverDescription = new MlString();
	private final MlBool preferred = new MlBool();
	private final MlString serverVersion = new MlString();
	private final MlInt64 maxUsers = new MlInt64();
	private final MlInt64 lowIDUsers = new MlInt64();
	private final MlInt64 softLimit = new MlInt64();
	private final MlInt64 hardLimit = new MlInt64();
	private final MlInt32 ping = new MlInt32();

	@Override
	public short opCode() {
		return 26;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.id, this.networkIdentifier, this.internetAddress, this.serverPort, this.serverScore,
				this.metadata, this.numberOfUser, this.numberOfFiles, this.connectionState, this.serverName,
				this.serverDescription, this.preferred, this.serverVersion, this.maxUsers, this.lowIDUsers,
				this.softLimit, this.hardLimit, this.ping };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		if (this.connectionState.isRemoved()) {
			gui.getServers().remove(this.asSample());
			return;
		}
		gui.getServers().findOrCreateAndEdit(this.asSample(), s -> {
			s.setName(this.serverName.value());
			s.setVersion(this.serverVersion.value());
			s.setFileCount(this.numberOfFiles.value());
			s.setUserCount(this.numberOfUser.value());
			s.setGeoIp(this.internetAddress.getGeoIp());
			s.setState(this.connectionState.getState());
		});
	}

	private Server asSample() {
		return new Server(this.id.value());
	}
}
