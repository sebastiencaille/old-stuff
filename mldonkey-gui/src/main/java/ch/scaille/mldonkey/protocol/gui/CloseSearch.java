/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class CloseSearch extends AbstractMlTypeContainer implements ISentMessage {
	private final MlInt32 searchId = new MlInt32();
	private final MlBool forget = new MlBool();

	@Override
	public short opCode() {
		return 53;
	}

	public CloseSearch(FileQuery lastQuery, boolean forget) {
		this.searchId.set(lastQuery.getId());
		this.forget.set(forget);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.searchId, this.forget };
	}
}
