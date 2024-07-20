/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import java.util.function.Consumer;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.Client;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlString;

public class FileUpdateAvailability extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 fileNumber = new MlInt32();
	private final MlInt32 clientNumber = new MlInt32();
	private final MlString availability = new MlString();

	@Override
	public short opCode() {
		return 9;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.fileNumber, this.clientNumber, this.availability };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getClients().findAndEdit(new Client(this.clientNumber.value()), c -> {
			final var avail = this.availability.value();
			var isAvailable = false;
			for (final var availChunk : avail.toCharArray()) {
				if (availChunk == '1') {
					isAvailable = true;
					break;
				}
			}

			final Consumer<FileDownload> action;
			if (isAvailable) {
				action = e -> {
					c.addFile(e);
					e.addAvailability(c, avail);
				};
			} else {
				action = e -> {
					c.removeFile(e);
					e.removeAvailability(c);
				};
			}
			gui.getDownloads().findAndEdit(new FileDownload(this.fileNumber.value()), action);
		});

	}
}
