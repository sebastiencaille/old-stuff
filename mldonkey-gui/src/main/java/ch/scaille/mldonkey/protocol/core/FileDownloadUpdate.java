/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlFloat;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;

public class FileDownloadUpdate extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 identifier = new MlInt32();
	private final MlInt64 downloadedSize = new MlInt64();
	private final MlFloat downloadRate = new MlFloat();
	private final MlInt32 lastSeen = new MlInt32();

	@Override
	public short opCode() {
		return 46;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.identifier, this.downloadedSize, this.downloadRate, this.lastSeen };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getDownloads().findAndEdit(new FileDownload(this.identifier.value()), e -> {
			e.setDownloadedSize(this.downloadedSize.value());
			e.setDownloadRate(this.downloadRate.value());
			e.setLastSeen(this.lastSeen.value());
		});
	}
}
