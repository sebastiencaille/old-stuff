/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

public class MlOggStream extends AbstractMlTypeContainer implements IMlType {
	private final MlInt32 streamNumber = new MlInt32();
	private final MlInt8 type = new MlInt8();
	private final MlList tag = new MlList(MlOggStreamTag.class);

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.streamNumber, this.type, this.tag };
	}
}
