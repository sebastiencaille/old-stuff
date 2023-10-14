/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MlString implements IMlType {
	private static final Charset UTF_8 = StandardCharsets.UTF_8;
	private String value = "";

	public MlString(final String value) {
		this.set(value);
	}

	public MlString() {
	}

	@Override
	public int messageLength() {
		return 2 + this.value.getBytes(StandardCharsets.UTF_8).length;
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		final var len = buffer.getShort();
		final var data = new byte[len];
		buffer.get(data);
		this.value = new String(data, UTF_8);
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putShort((short) this.value.getBytes().length);
		buffer.put(this.value.getBytes(UTF_8));
	}

	public void set(final String value) {
		this.value = value == null ? "" : value;
	}

	public String value() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}
}
