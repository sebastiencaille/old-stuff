/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlSearchFull;

public class SearchQuery extends AbstractMlTypeContainer implements ISentMessage {
	private final MlSearchFull search = new MlSearchFull();

	@Override
	public short opCode() {
		return 42;
	}

	public SearchQuery(final FileQuery query) {
		this.search.set(query);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.search };
	}
}
