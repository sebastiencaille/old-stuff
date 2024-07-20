/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlCharArray implements IMlType {
	private final byte[] value;

	public MlCharArray(final int size) {
		this.value = new byte[size];
	}

	@Override
	public int messageLength() {
		return this.value.length;
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.put(this.value);
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		buffer.get(this.value);
	}

	public void set(final byte[] bytes) {
		System.arraycopy(bytes, 0, this.value, 0, this.value.length);
	}

	public byte[] value() {
		return this.value;
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder();
        for (final byte bint : this.value) {
            builder.append(Integer.toHexString(bint & 255));
        }
		return builder.toString();
	}
}
