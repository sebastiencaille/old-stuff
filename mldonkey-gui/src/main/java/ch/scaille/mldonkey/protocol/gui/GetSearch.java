/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class GetSearch extends AbstractMlTypeContainer implements ISentMessage {
	private final MlInt32 search = new MlInt32();

	public GetSearch(int id) {
		this.search.set(id);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.search };
	}

	@Override
	public short opCode() {
		return 60;
	}
}
