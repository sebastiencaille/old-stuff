/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import ch.scaille.mldonkey.MDLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.types.AbstractMlTypeContainer;
import ch.scaille.mldonkey.protocol.types.IMlType;
import ch.scaille.mldonkey.protocol.types.MlList;
import ch.scaille.mldonkey.protocol.types.MlQuery;
import ch.scaille.mldonkey.protocol.types.MlString;

public class DefineSearches extends AbstractMlTypeContainer implements IReceivedMessage {
	
	private static final MDLogger LOGGER = new MDLogger(DefineSearches.class);
	
	private final MlList searches = new MlList(MlString.class, MlQuery.class);

	@Override
	public short opCode() {
		return 3;
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.searches };
	}

	@Override
	public void handle(MLDonkeyGui gui) {
		for (var i = 0; i < this.searches.size(); ++i) {
			LOGGER.log(
					this.searches.valueAt(i, 0, MlString.class) + ":" + this.searches.valueAt(i, 1, MlQuery.class));
		}
	}
}
