/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.SharedFile;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;

public class SharedFileInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(SharedFileInfo.class);
	
	private final MlInt32 sharedFileIdentifier = new MlInt32();
	private final MlInt32 networkIdentifier = new MlInt32();
	private final MlString sharedFileName = new MlString();
	private final MlInt64 fileSize = new MlInt64();
	private final MlInt64 bytesUploaded = new MlInt64();
	private final MlInt32 requests = new MlInt32();
	private final MlList sharedUids = new MlList(MlString.class);
	private final MlList subFiles = new MlList(MlString.class, MlInt64.class, MlString.class);
	private final MlString magic = new MlString();

	@Override
	public short opCode() {
		return 48;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.sharedFileIdentifier, this.networkIdentifier, this.sharedFileName, this.fileSize,
				this.bytesUploaded, this.requests, this.sharedUids, this.subFiles, this.magic };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		LOGGER.log("Sharing " + this.sharedFileIdentifier.value());
		gui.getSharedFiles().findOrCreateAndEdit(new SharedFile(this.sharedFileIdentifier.value()), s -> {
			s.setName(this.sharedFileName.value());
			s.setSize(this.fileSize.value());
			for (var i = 0; i < this.sharedUids.size(); ++i) {
				s.addIdentifier(this.sharedUids.valueAt(i, MlString.class).value());
			}
		});
	}
}
