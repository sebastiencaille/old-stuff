/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileDownload;

public class MlFileFormat extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 format = new MlInt8();
	private final MlString extension = new MlString();
	private final MlString kind = new MlString();
	private final MlString codec = new MlString();
	private final MlInt32 vidWidth = new MlInt32();
	private final MlInt32 vidHeight = new MlInt32();
	private final MlInt32 vidFPS = new MlInt32();
	private final MlInt32 vidRate = new MlInt32();
	private final MlString mp3title = new MlString();
	private final MlString mp3artist = new MlString();
	private final MlString mp3album = new MlString();
	private final MlString mp3year = new MlString();
	private final MlString mp3comment = new MlString();
	private final MlInt32 mp3Track = new MlInt32();
	private final MlInt32 mp3Genre = new MlInt32();
	private final MlList oggInfo = new MlList(MlOggStream.class);

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.format, this.extension, this.kind, this.codec, this.vidWidth, this.vidHeight,
				this.vidFPS, this.vidRate, this.mp3title, this.mp3artist, this.mp3album, this.mp3year, this.mp3comment,
				this.mp3Track, this.mp3Genre, this.oggInfo };
	}

	@Override
	protected boolean hasValue(final IMlType type) {
		if (type == this.extension || type == this.kind) {
			return this.format.value() == Format.GENERIC.ordinal();
		}
		if (type == this.vidWidth || type == this.vidHeight || type == this.vidFPS || type == this.vidRate
				|| type == this.codec) {
			return this.format.value() == Format.AVI.ordinal();
		}
		if (type == this.mp3title || type == this.mp3artist || type == this.mp3album || type == this.mp3year
				|| type == this.mp3comment || type == this.mp3Track || type == this.mp3Genre) {
			return this.format.value() == Format.MP3.ordinal();
		}
		if (type == this.oggInfo) {
			return this.format.value() == Format.OGG.ordinal();
		}
		return super.hasValue(type);
	}

	@Override
	public String toString() {
		return Format.values()[this.format.value()].name();
	}

	public void fill(final FileDownload download) {
		download.setFormat(Format.values()[this.format.value()].name());
	}

	public enum Format {
		UNKNOWN, GENERIC, AVI, MP3, OGG;

		private Format() {
		}
	}

}
