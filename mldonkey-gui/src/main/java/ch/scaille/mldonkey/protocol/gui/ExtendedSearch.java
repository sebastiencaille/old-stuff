/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;

public class ExtendedSearch extends AbstractMlTypeContainer implements ISentMessage {
	@Override
	public short opCode() {
		return 4;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[0];
	}
}
