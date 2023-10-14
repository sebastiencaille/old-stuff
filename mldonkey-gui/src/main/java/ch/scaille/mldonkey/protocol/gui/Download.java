/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;

public class Download extends AbstractMlTypeContainer implements ISentMessage {
	private final MlList names = new MlList(MlString.class);
	private final MlInt32 resultId = new MlInt32();
	private final MlBool force = new MlBool();

	public Download(FileQueryResult result) {
		this.resultId.set(result.getId());
	}

	@Override
	public short opCode() {
		return 50;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.names, this.resultId, this.force };
	}
}
