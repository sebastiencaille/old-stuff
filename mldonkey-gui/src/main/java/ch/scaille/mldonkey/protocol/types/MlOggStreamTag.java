/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

public class MlOggStreamTag extends AbstractIndexedType implements IMlType {
	private final MlString codec = new MlString();
	private final MlInt32 bitsPerSamples = new MlInt32();
	private final MlInt32 duration = new MlInt32();
	private final MlNoContent hasSubtitle = new MlNoContent();
	private final MlNoContent hasIndex = new MlNoContent();
	private final MlInt32 audioChannels = new MlInt32();
	private final MlFloat audioSampleRate = new MlFloat();
	private final MlInt32 audioBlockAlign = new MlInt32();
	private final MlFloat audioAverageBytesPerSecond = new MlFloat();
	private final MlFloat vorbisVersion = new MlFloat();
	private final MlFloat vorbisSampleRate = new MlFloat();
	private final MlList vorbisBitRates = new MlList(MlInt8.class, MlFloat.class);
	private final MlInt32 vorbisBlockSize0 = new MlInt32();
	private final MlInt32 vorbisBlockSize1 = new MlInt32();
	private final MlFloat videoWidth = new MlFloat();
	private final MlFloat videoHeight = new MlFloat();
	private final MlFloat videoSampleRate = new MlFloat();
	private final MlFloat videoAspectRatio = new MlFloat();
	private final MlInt8 theoraCS = new MlInt8();
	private final MlInt32 theoraQuality = new MlInt32();
	private final MlInt32 theoraAverageBytesPerSecond = new MlInt32();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.codec, this.bitsPerSamples, this.duration, this.hasSubtitle, this.hasIndex,
				this.audioChannels, this.audioSampleRate, this.audioBlockAlign, this.audioAverageBytesPerSecond,
				this.vorbisVersion, this.vorbisSampleRate, this.vorbisBitRates, this.vorbisBlockSize0,
				this.vorbisBlockSize1, this.videoWidth, this.videoHeight, this.videoSampleRate, this.videoAspectRatio,
				this.theoraCS, this.theoraQuality, this.theoraAverageBytesPerSecond };
	}
}
