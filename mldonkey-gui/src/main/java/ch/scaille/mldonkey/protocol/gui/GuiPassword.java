/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlString;

public class GuiPassword extends AbstractMlTypeContainer implements ISentMessage {
	private final MlString login = new MlString("admin");
	private final MlString password = new MlString();

	@Override
	public short opCode() {
		return 52;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.password, this.login };
	}
}
