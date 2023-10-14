/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlInt16;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;

public class NetworkInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 id = new MlInt32();
	private final MlString name = new MlString();
	private final MlBool enabled = new MlBool();
	private final MlString config = new MlString();
	private final MlInt64 uploaded = new MlInt64();
	private final MlInt64 downloaded = new MlInt64();
	private final MlInt32 serverCount = new MlInt32();
	private final MlList flags = new MlList(MlInt16.class);

	@Override
	public short opCode() {
		return 20;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.id, this.name, this.enabled, this.config, this.uploaded, this.downloaded,
				this.serverCount, this.flags };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		// not used
	}
}
