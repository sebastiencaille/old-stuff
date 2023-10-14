/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public class MlRecursive<T extends IMlType> implements IMlType {
	private final Class<T> clazz;
	private T instance = null;

	public MlRecursive(final Class<T> clazz) {
		this.clazz = clazz;
	}

	public void set(final T instance) {
		this.instance = instance;
	}

	public T value() {
		return this.instance;
	}

	@Override
	public int messageLength() {
		if (this.instance == null) {
			return 0;
		}
		return this.instance.messageLength();
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		if (this.instance != null) {
			this.instance.encodeInto(buffer);
		}
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		try {
			this.instance = this.clazz.getDeclaredConstructor().newInstance();
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot instantiate " + this.clazz.getName());
		}
		this.instance.decodeFrom(buffer);
	}
}
