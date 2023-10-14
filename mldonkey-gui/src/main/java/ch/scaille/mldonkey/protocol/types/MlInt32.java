/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlInt32 implements IMlType {
	private int value;

	public MlInt32(final int i) {
		this.set(i);
	}

	public MlInt32() {
	}

	@Override
	public int messageLength() {
		return 4;
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		this.value = buffer.getInt();
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putInt(this.value);
	}

	public void set(final int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}

	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
}
