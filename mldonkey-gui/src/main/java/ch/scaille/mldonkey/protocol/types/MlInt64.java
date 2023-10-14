/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlInt64 implements IMlType {
	private long value;

	@Override
	public int messageLength() {
		return 8;
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		this.value = buffer.getLong();
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putLong(this.value);
	}

	public void set(final long value) {
		this.value = value;
	}

	public long value() {
		return this.value;
	}

	@Override
	public String toString() {
		return Long.toString(this.value);
	}
}
