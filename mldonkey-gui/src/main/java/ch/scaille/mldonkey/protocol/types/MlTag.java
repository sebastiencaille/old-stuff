/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.GuiLogger;

public class MlTag extends AbstractMlTypeContainer implements IMlType {
	
	private static final GuiLogger LOGGER = new GuiLogger(MlTag.class);
	
	private final MlString tagName = new MlString();
	private final MlInt8 tagType = new MlInt8();
	private final MlInt32 int32Value = new MlInt32();
	private final MlString strValue = new MlString();
	private final MlInt16 int16Value = new MlInt16();
	private final MlInt8 int8Value = new MlInt8();
	private final MlInt32 int32Value2 = new MlInt32();

	@Override
	protected boolean hasValue(final IMlType type) {
		final byte tt = this.tagType.value();
		if (type == this.int32Value) {
			return tt == 0 || tt == 1 || tt == 3 || tt == 6;
		}
		if (type == this.strValue) {
			return tt == 2;
		}
		if (type == this.int16Value) {
			return tt == 4;
		}
		if (type == this.int8Value) {
			return tt == 5;
		}
		if (type == this.int32Value2) {
			return tt == 6;
		}
		return super.hasValue(type);
	}

	private Type getType() {
		return Type.values()[this.tagType.value()];
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.tagName, this.tagType, this.int32Value, this.strValue, this.int16Value,
				this.int8Value, this.int32Value2 };
	}

	@Override
	public String toString() {
		return "TagType=" + Integer.toString(this.tagType.value());
	}

	public String getName() {
		return this.tagName.value();
	}

	public int getIntTagValue() {
		return switch (this.getType()) {
		case UINT32, INT32 -> this.int32Value.value();
		case INT16 -> this.int16Value.value();
		case INT8 -> this.int8Value.value() & 255;
		default -> {
			LOGGER.log("Tag " + this.getName() + " cannot be used as int: " + (this.getType()));
			yield -1;
		}
		};
	}

	private enum Type {
		UINT32, INT32, STRING, IP, INT16, INT8, PAIR32
	}

}
