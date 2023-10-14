/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlInt32;

public class GuiGetFileLocations extends AbstractMlTypeContainer implements ISentMessage {
	private final MlInt32 id = new MlInt32();

	public GuiGetFileLocations(int id) {
		this.id.set(id);
	}

	@Override
	public short opCode() {
		return 34;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.id };
	}
}
