/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.gui;

import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlString;

public class SetOption extends AbstractMlTypeContainer implements ISentMessage {
	private final MlString option = new MlString();
	private final MlString value = new MlString();

	public SetOption(String option, String value) {
		this.option.set(option);
		this.value.set(value);
	}

	@Override
	public short opCode() {
		return 28;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.option, this.value };
	}
}
