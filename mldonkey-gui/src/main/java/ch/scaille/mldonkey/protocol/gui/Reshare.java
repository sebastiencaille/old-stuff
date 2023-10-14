/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlString;

public class Reshare extends AbstractMlTypeContainer implements ISentMessage {
	private final MlString command = new MlString();

	public Reshare() {
		this.command.set("reshare");
	}

	@Override
	public short opCode() {
		return 29;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.command };
	}
}
