/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import java.util.HashMap;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlString;

public class OptionsInfo extends AbstractMlTypeContainer implements IReceivedMessage {
	private final MlList options = new MlList(MlString.class, MlString.class);

	@Override
	public short opCode() {
		return 1;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.options };
	}

	@Override
	public void handle(final MLDonkeyGui gui) {
		final var optionsMap = new HashMap<String, String>();
		for (var i = 0; i < this.options.size(); ++i) {
			optionsMap.put(this.options.valueAt(i, 0, MlString.class).value(),
					this.options.valueAt(i, 1, MlString.class).value());
		}
		gui.setOptions(optionsMap);
	}
}
