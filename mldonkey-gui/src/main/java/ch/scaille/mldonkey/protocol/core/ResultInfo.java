/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import java.util.ArrayList;

import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlInt64;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;
import ch.scaille.mldonkey.protocol.types.MlTag;

public class ResultInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final GuiLogger LOGGER = new GuiLogger(ResultInfo.class);
	
	private final MlInt32 identifier = new MlInt32();
	private final MlInt32 networkIdentifier = new MlInt32();
	private final MlList fileNames = new MlList(MlString.class);
	private final MlList fileIdentifiers = new MlList(MlString.class);
	private final MlInt64 size = new MlInt64();
	private final MlString format = new MlString();
	private final MlString type = new MlString();
	private final MlList metadata = new MlList(MlTag.class);
	private final MlString comment = new MlString();
	private final MlBool alreadyDownloaded = new MlBool();
	private final MlInt32 time = new MlInt32();

	@Override
	public short opCode() {
		return 4;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.identifier, this.networkIdentifier, this.fileNames, this.fileIdentifiers, this.size,
				this.format, this.type, this.metadata, this.comment, this.alreadyDownloaded, this.time };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getQueryResults().findOrCreateAndEdit(new FileQueryResult(this.identifier.value()), this::fill);
	}

	private void fill(final FileQueryResult result) {
		int i;
		for (i = 0; i < this.fileNames.size(); ++i) {
			result.addFileName(this.fileNames.valueAt(i, MlString.class).value());
		}
		for (i = 0; i < this.fileIdentifiers.size(); ++i) {
			result.addFileIdentifier(this.fileIdentifiers.valueAt(i, MlString.class).value());
		}
		result.setSize(this.size.value());
		result.setFormat(this.type.value());
		result.setDownloaded(this.alreadyDownloaded.value());
		result.setAvailability(this.getIntTag("availability"));
		result.setCompleteSources(this.getIntTag("completesources"));
	}

	private int getIntTag(final String tagName) {
		final var names = new ArrayList<>();
		for (var i = 0; i < this.metadata.size(); ++i) {
			final var tag = this.metadata.valueAt(i, MlTag.class);
			final var name = tag.getName();
			names.add(name);
			if (!tagName.equals(name)) {
				continue;
			}
			return tag.getIntTagValue();
		}
		LOGGER.log("Tag " + tagName + " not found in " + names);
		return -1;
	}
}
