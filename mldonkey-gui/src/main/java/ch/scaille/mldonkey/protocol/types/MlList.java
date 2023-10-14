/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MlList implements IMlType {
	private final Class<?>[] types;
	private final List<IMlType> data = new ArrayList<>();

	public /* varargs */ MlList(final Class<?>... types) {
		for (final Class<?> clazz : types) {
			if (IMlType.class.isAssignableFrom(clazz)) {
				continue;
			}
			throw new IllegalArgumentException("Type " + clazz.getName() + " is not a " + IMlType.class.getName());
		}
		this.types = types;
	}

	@Override
	public int messageLength() {
		var length = 0;
		for (final var aData : this.data) {
			length += aData.messageLength();
		}
		return 2 + length;
	}

	@Override
	public void encodeInto(final ByteBuffer buffer) {
		buffer.putShort((short) (this.data.size() / this.types.length));
		for (final var adata : this.data) {
			adata.encodeInto(buffer);
		}
	}

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		final var len = buffer.getShort();
		for (var i = 0; i < len; ++i) {
			for (var type : this.types) {
				try {
					var newData = (IMlType) type.getDeclaredConstructor().newInstance();
					newData.decodeFrom(buffer);
					this.data.add(newData);
				} catch (final Exception e) {
					throw new IllegalStateException("Cannot create data " + type.getName(), e);
				}
			}
		}
	}

	public void addValue(final IMlType type) {
		final var expectedType = this.types[this.data.size() % this.types.length];
		if (!expectedType.equals(type.getClass())) {
			throw new IllegalArgumentException(
					"Expected " + expectedType.getClass().getName() + ", but was " + type.getClass().getName());
		}
		this.data.add(type);
	}

	public int size() {
		return this.data.size() / this.types.length;
	}

	public <T extends IMlType> T valueAt(final int index, final Class<T> clazz) {
		return this.valueAt(index, 0, clazz);
	}

	public <T extends IMlType> T valueAt(final int index, final int subindex, final Class<T> clazz) {
		return (clazz.cast(this.data.get(index * this.types.length + subindex)));
	}

	public /* varargs */ void addValues(final IMlType... values) {
		for (final IMlType value : values) {
			this.addValue(value);
		}
	}

	@Override
	public String toString() {
		final var builder = new StringBuilder("size=");
		builder.append(this.size());
		for (final var type : this.data) {
			builder.append(", ").append(type);
		}
		return builder.toString();
	}
}
