/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileDownload;

public class MlFileInfo extends AbstractMlTypeContainer implements IMlType {
	private final MlInt32 fileId = new MlInt32();
	private final MlInt32 fileNetwork = new MlInt32();
	private final MlList fileNames = new MlList(MlString.class);
	private final MlMd4 md4 = new MlMd4();
	private final MlInt64 fileSize = new MlInt64();
	private final MlInt64 downloaded = new MlInt64();
	private final MlInt32 sourceCount = new MlInt32();
	private final MlInt32 clientCount = new MlInt32();
	private final MlFileState state = new MlFileState();
	private final MlString chunks = new MlString();
	private final MlList availability = new MlList(MlInt32.class, MlString.class);
	private final MlFloat downloadRate = new MlFloat();
	private final MlList chunkAges = new MlList(MlInt32.class);
	private final MlInt32 age = new MlInt32();
	private final MlFileFormat format = new MlFileFormat();
	private final MlString preferredName = new MlString();
	private final MlInt32 lastSeenCompleteSeconds = new MlInt32();
	private final MlInt32 filePriority = new MlInt32();
	private final MlString comment = new MlString();
	private final MlList links = new MlList(MlString.class);
	private final MlList subFiles = new MlList(MlString.class, MlInt64.class, MlString.class);
	private final MlString fileFormat = new MlString();
	private final MlList geo = new MlList(MlInt32.class, MlInt8.class, MlString.class, MlInt8.class, MlString.class);
	private final MlString user = new MlString();
	private final MlString group = new MlString();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.fileId, this.fileNetwork, this.fileNames, this.md4, this.fileSize, this.downloaded,
				this.sourceCount, this.clientCount, this.state, this.chunks, this.availability, this.downloadRate,
				this.chunkAges, this.age, this.format, this.preferredName, this.lastSeenCompleteSeconds,
				this.filePriority, this.comment, this.links, this.subFiles, this.fileFormat, this.geo, this.user,
				this.group };
	}

	@Override
	public String toString() {
		return this.preferredName.value();
	}

	public void fill(final FileDownload download) {
		int i;
		download.setName(this.preferredName.value());
		this.state.fill(download);
		download.setFileSize(this.fileSize.value());
		download.setDownloadedSize(this.downloaded.value());
		download.setDownloadRate(this.downloadRate.value());
		for (i = 0; i < this.fileNames.size(); ++i) {
			download.addName(this.fileNames.valueAt(i, MlString.class).value());
		}
		download.setNumberOfSources(this.sourceCount.value());
		download.setChunks(this.chunks.value());
		download.setMd4(this.md4.value());
		for (i = 0; i < this.links.size(); ++i) {
			download.addLink(this.links.valueAt(i, MlString.class).value());
		}
		this.format.fill(download);
	}

	public int getId() {
		return this.fileId.value();
	}

	public FileState getState() {
		return this.state.value();
	}

}
