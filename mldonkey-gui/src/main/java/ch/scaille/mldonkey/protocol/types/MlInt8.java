/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlInt8 implements IMlType {
	private byte value;

	public MlInt8(final byte value) {
		this.value = value;
	}

	public MlInt8() {
	}

	@Override
	public int messageLength() {
		return 1;
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.put(this.value);
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		this.value = buffer.get();
	}

	public void set(final byte value) {
		this.value = value;
	}

	public void set(final int value) {
		this.value = (byte) (value & 0xFF);
	}

	public byte value() {
		return this.value;
	}

	@Override
	public String toString() {
		return Byte.toString(this.value);
	}
}
