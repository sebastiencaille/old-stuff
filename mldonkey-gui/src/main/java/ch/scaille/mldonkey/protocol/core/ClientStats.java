/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Stats;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlList;

public class ClientStats extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt64 uploadedSize = new MlInt64();
	private final MlInt64 downloadedSize = new MlInt64();
	private final MlInt64 sharedSize = new MlInt64();
	private final MlInt32 sharedCount = new MlInt32();
	private final MlInt32 tcpUploadRate = new MlInt32();
	private final MlInt32 tcpDownloadRate = new MlInt32();
	private final MlInt32 udpUploadRate = new MlInt32();
	private final MlInt32 udpDownloadRate = new MlInt32();
	private final MlInt32 currentDownloads = new MlInt32();
	private final MlInt32 currentFinishedDownloads = new MlInt32();
	private final MlList serverCounts = new MlList(MlInt32.class, MlInt32.class);

	@Override
	public short opCode() {
		return 49;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.uploadedSize, this.downloadedSize, this.sharedSize, this.sharedCount,
				this.tcpUploadRate, this.tcpDownloadRate, this.udpUploadRate, this.udpDownloadRate,
				this.currentDownloads, this.currentFinishedDownloads, this.serverCounts };
	}

	@Override
	public void handle(MLDonkeyGui gui) {
		var stats = new Stats();
		stats.setDownloadCount(this.currentDownloads.value());
		gui.statsUpdated(stats);
	}
}
