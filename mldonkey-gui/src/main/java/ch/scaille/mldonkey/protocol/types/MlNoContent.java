/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlNoContent implements IMlType {
	@Override
	public int messageLength() {
		return 0;
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		// nope
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		// nope
	}
}
