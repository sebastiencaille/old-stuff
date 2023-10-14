/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class SearchResult extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlInt32 searchId = new MlInt32();
	private final MlInt32 resultId = new MlInt32();

	@Override
	public short opCode() {
		return 5;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.searchId, this.resultId };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getQueryResults().findOrCreateAndEdit(new FileQueryResult(this.resultId.value()),
				e -> e.addSearchId(this.searchId.value()));
	}
}
