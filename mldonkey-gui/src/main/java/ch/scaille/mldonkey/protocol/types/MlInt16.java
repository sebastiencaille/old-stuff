/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlInt16 implements IMlType {
	private short value;

	public MlInt16(final short i) {
		this.set(i);
	}

	public MlInt16() {
	}

	@Override
	public int messageLength() {
		return 2;
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		this.value = buffer.getShort();
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putShort(this.value);
	}

	public void set(final short value) {
		this.value = value;
	}

	public short value() {
		return this.value;
	}

	@Override
	public String toString() {
		return Short.toString(this.value);
	}
}
