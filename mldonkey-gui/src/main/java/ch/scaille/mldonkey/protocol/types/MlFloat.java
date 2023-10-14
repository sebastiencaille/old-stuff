/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlFloat implements IMlType {
	private String value = "";

	public MlFloat(final float value) {
		this.set(value);
	}

	public MlFloat() {
		this.set(0.0f);
	}

	@Override
	public int messageLength() {
		return 2 + this.value.length();
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		final var len = buffer.getShort();
		final var data = new byte[len];
		buffer.get(data);
		this.value = new String(data);
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putShort((short) this.value.length());
		buffer.put(this.value.getBytes());
	}

	public void set(final float value) {
		final var intPart = (int) value;
		final var floatPart = (int) ((value - intPart) * 100.0f);
		this.value = Integer.toString(intPart) + "." + Integer.toString(floatPart);
	}

	public float value() {
		final var intPart = Integer.parseInt(this.value.substring(0, this.value.indexOf(46)));
		final var floatPart = Integer.parseInt(this.value.substring(this.value.indexOf(46) + 1, this.value.length()));
		return intPart + floatPart / 100.0f;
	}

	@Override
	public String toString() {
		return Float.toString(this.value());
	}
}
