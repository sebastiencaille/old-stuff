/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlFileInfo;
import ch.scaille.mldonkey.protocol.types.MlList;

public class DownloadingFiles extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlList files = new MlList(MlFileInfo.class);

	@Override
	public short opCode() {
		return 53;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.files };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		for (var i = 0; i < this.files.size(); ++i) {
			final MlFileInfo info = this.files.valueAt(i, MlFileInfo.class);
			gui.getDownloads().findOrCreateAndEdit(new FileDownload(info.getId()), info::fill);
		}
	}
}
