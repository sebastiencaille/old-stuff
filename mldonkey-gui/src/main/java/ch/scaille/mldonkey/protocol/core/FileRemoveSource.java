/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class FileRemoveSource extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 fileIdentifier = new MlInt32();
	private final MlInt32 clientIdentifier = new MlInt32();

	@Override
	public short opCode() {
		return 10;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.fileIdentifier, this.clientIdentifier };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getClients().findAndEdit(new Client(this.clientIdentifier.value()),
				c -> gui.getDownloads().findAndEdit(new FileDownload(this.fileIdentifier.value()), e -> {
					c.removeFile(e);
					e.removeAvailability(c);
				}));
	}
}
