/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.function.Supplier;

public abstract class AbstractMlTypeContainer {
	private IMlType[] content;
	private int decodingContentIndex = 0;

	protected abstract IMlType[] createMessageContent();

	private void validate() {
		for (var i = 0; i < this.content.length; ++i) {
			if (this.content[i] != null) {
				continue;
			}
			throw new IllegalStateException(this.getClass().getName() + " has null content at index " + i);
		}
	}

	protected IMlType[] loadContent() {
		if (this.content == null) {
			this.content = this.createMessageContent();
			this.validate();
		}
		return this.content;
	}

	public int messageLength() {
		this.loadContent();
		return Arrays.stream(this.content)
                .filter(this::hasValue).mapToInt(IMlType::messageLength).sum();
	}

	public void encodeInto(final ByteBuffer buffer) {
		this.loadContent();
		log(() -> "--> Encoding class " + this.getClass().getName());

		final int start = buffer.position();
		for (final var mlType : this.content) {
			
			final var pos = buffer.position();
			log(() -> "encoding " + mlType.getClass().getSimpleName() + ": ");
			
			if (this.hasValue(mlType)) {
				mlType.encodeInto(buffer);
			}
			log(() -> {
				final var len = buffer.position() - pos;
				final var result = new byte[len];
				System.arraycopy(buffer.array(), pos, result, 0, len);
				return Arrays.toString(result);
			});
		}
		if (buffer.position() - start != this.messageLength()) {
			log(() -> this.getClass().getName() + ": written length != expected length: " + (buffer.position() - start)
					+ " != " + this.messageLength());
		}
		log(() -> "<-- Encoded class " + this.getClass().getName());
	}

	public void decodeFrom(final ByteBuffer buffer) {
		this.loadContent();
		this.decodingContentIndex = 0;
		log(() -> "--> Decoding class " + this.getClass().getName());
		for (final IMlType type : this.content) {
			if (this.hasValue(type)) {
				log(() -> "decoding " + type.getClass().getSimpleName() + ": ");
				type.decodeFrom(buffer);
				log(type::toString);
			}
			++this.decodingContentIndex;
		}
		log(() -> "<-- Decoded class " + this.getClass().getName());
	}

	public int getErrorIndex() {
		return this.decodingContentIndex;
	}

	public void log(Supplier<String> log) {
		// log(() -> log.get());
	}

	/**
	 * Returns true if the field given in parameter is present according to the
	 * state of the current node
	 */
	protected boolean hasValue(final IMlType type) {
		return true;
	}
}
