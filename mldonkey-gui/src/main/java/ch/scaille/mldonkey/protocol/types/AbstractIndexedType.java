/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public abstract class AbstractIndexedType extends AbstractMlTypeContainer {
	private MlInt8 dataIndex;

	@Override
	public void decodeFrom(final ByteBuffer buffer) {
		final IMlType[] loadContent = this.loadContent();
		this.dataIndex = new MlInt8();
		this.dataIndex.decodeFrom(buffer);
		if (this.dataIndex.value() < loadContent.length) {
			final IMlType type = loadContent[this.dataIndex.value()];
			if (type == null) {
				return;
			}
			log(() -> "Loading index " + this.dataIndex.value() + ": " + type.getClass().getSimpleName() + "=");
			type.decodeFrom(buffer);
			log(type::toString);
		} else {
			log(() -> "bad index: " + this.dataIndex.value());
		}
	}

	@Override
	public String toString() {
		return "Tag " + this.dataIndex;
	}
}
