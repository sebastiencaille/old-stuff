/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlBool;
import ch.scaille.mldonkey.protocol.types.MlInt32;
import ch.scaille.mldonkey.protocol.types.MlList;

public class GuiExtension extends AbstractMlTypeContainer implements ISentMessage {
	private final MlList list = new MlList(MlInt32.class, MlBool.class);

	public GuiExtension() {
		this.list.addValues(new MlInt32(1), MlBool.TRUE);
	}

	@Override
	public short opCode() {
		return 47;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.list };
	}
}
