/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlSearch;

public class Search extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlSearch mlSearch = new MlSearch();

	@Override
	public short opCode() {
		return 57;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.mlSearch };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		gui.getQueries().findOrCreateAndEdit(new FileQuery(this.mlSearch.getId()), mlSearch::fill);
	}
}
