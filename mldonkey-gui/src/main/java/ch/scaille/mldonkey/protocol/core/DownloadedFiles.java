/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.model.SharedFile;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlFileInfo;
import ch.scaille.mldonkey.protocol.types.MlList;

public class DownloadedFiles extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(DownloadedFiles.class);
	
	private final MlList files = new MlList(MlFileInfo.class);

	@Override
	public short opCode() {
		return 54;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.files };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		for (var i = 0; i < this.files.size(); ++i) {
			final var fileInfo = this.files.valueAt(i, MlFileInfo.class);
			gui.getDownloads().findOrCreateAndEdit(new FileDownload(fileInfo.getId()), d -> {
				fileInfo.fill(d);
				LOGGER.log("File downloaded " + fileInfo.getId() + " - "
						+ gui.getSharedFiles().getRowOf(new SharedFile(fileInfo.getId())));
				gui.getSharedFiles().findOrCreateAndEdit(new SharedFile(fileInfo.getId()), s -> {
					s.setName(d.getName());
					s.setSize(d.getFileSize());
					s.addIdentifiers(d.getIdentifiers());
				});
			});
		}
	}
}
