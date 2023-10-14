/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlBool implements IMlType {
	public static final MlBool TRUE = new MlBool(true);
	public static final MlBool FALSE = new MlBool(false);
	private boolean value;

	public MlBool(final boolean b) {
		this.set(b);
	}

	public MlBool() {
	}

	@Override
	public int messageLength() {
		return 1;
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		this.value = buffer.get() > 0;
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.put((byte) (this.value ? 1 : 0));
	}

	public void set(final boolean value) {
		this.value = value;
	}

	public boolean value() {
		return this.value;
	}

	@Override
	public String toString() {
		return Boolean.toString(this.value);
	}
}
