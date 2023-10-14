/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MDLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.FileState;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlFileInfo;

public class FileInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final MDLogger LOGGER = new MDLogger(FileInfo.class);
	
	private final MlFileInfo info = new MlFileInfo();

	@Override
	public short opCode() {
		return 52;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.info };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		if (this.info.getState() == FileState.CANCELLED || this.info.getState() == FileState.ABORTED) {
			LOGGER.log("Download cancelled");
			gui.getDownloads().remove(this.asSample());
			return;
		}
		gui.getDownloads().findOrCreateAndEdit(this.asSample(), this.info::fill);
	}

	private FileDownload asSample() {
		return new FileDownload(this.info.getId());
	}
}
