/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ch.scaille.annotations.GuiObject;
import ch.scaille.mldonkey.protocol.types.FileState;

@GuiObject
public class FileDownload implements Comparable<FileDownload>, IWarnedData, IBlackListData {
	private final int id;
	private String name;
	private FileState state;
	private long fileSize;
	private long downloadedSize;
	private float downloadRate;
	private final Set<String> names = new HashSet<>();
	private int numberOfSources;
	private String chunks;
	private byte[] md4;
	private final List<String> links = new ArrayList<>();
	private String format;
	private WarningLevel warnings = null;
	private int lastSeen;
	private final Map<Client, String> availabilities = new HashMap<>();
	private float availability;
	private boolean shareChecked;
	private float immediateAvailability;
	private boolean hasFirstByte;
	private boolean downloadNotified;

	public FileDownload(final int id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof FileDownload fileDownload && this.id == fileDownload.id;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public int compareTo(final FileDownload obj) {
		return obj.id - this.id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setState(final FileState state) {
		this.state = state;
	}

	public void setFileSize(final long fileSize) {
		this.fileSize = fileSize;
	}

	public void setDownloadedSize(final long downloadedSize) {
		this.downloadedSize = downloadedSize;
	}

	public void setDownloadRate(final float speed) {
		this.downloadRate = speed;
	}

	public void addName(final String aname) {
		this.names.add(aname);
	}

	public void setNumberOfSources(final int numberOfSources) {
		this.numberOfSources = numberOfSources;
	}

	public void setChunks(final String chunks) {
		this.chunks = chunks;
	}

	public void setMd4(final byte[] md4) {
		this.md4 = md4;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public FileState getState() {
		return this.state;
	}

	public long getFileSize() {
		return this.fileSize;
	}

	public long getDownloadedSize() {
		return this.downloadedSize;
	}

	public float getDownloadRate() {
		return this.downloadRate;
	}

	public Set<String> getNames() {
		return this.names;
	}

	public int getNumberOfSources() {
		return this.numberOfSources;
	}

	public String getChunks() {
		return this.chunks;
	}

	public byte[] getMd4() {
		return this.md4;
	}

	public String getFormat() {
		return this.format;
	}

	@Override
	public Collection<String> getWarnData() {
		return this.names;
	}

	@Override
	public WarningLevel getWarnings() {
		return this.warnings;
	}

	@Override
	public void setWarnings(final WarningLevel warnings) {
		this.warnings = warnings;
	}

	public int getLastSeen() {
		return this.lastSeen;
	}

	public void setLastSeen(final int value) {
		this.lastSeen = value;
	}

	@Override
	public Collection<String> getBlackListData() {
		return this.links;
	}

	@Override
	public long getBlackListSize() {
		return this.getFileSize();
	}

	public List<String> getLinks() {
		return links;
	}

	public void addLink(final String value) {
		if (!this.links.contains(value)) {
			this.links.add(value);
		}
	}

	public List<String> getIdentifiers() {
		return this.links;
	}

	public Map<Client, String> getAvailabilities() {
		return availabilities;
	}

	public void addAvailability(final Client client, final String val) {
		this.availabilities.put(client, val);
		this.updateAvailability();
	}

	public void removeAvailability(final Client removedClient) {
		if (this.availabilities.remove(removedClient) != null) {
			this.updateAvailability();
		}
	}

	public void updateAvailability() {
		this.availability = this.computeAvail(false);
		this.immediateAvailability = this.computeAvail(true);
	}

	private float computeAvail(final boolean onlyConnected) {
		var result = 0.0f;
		boolean[] hasValue = null;
		for (final var availFromClient : this.availabilities.entrySet()) {
			if (onlyConnected && !availFromClient.getKey().getState().isConnected()) {
				continue;
			}
			if (hasValue == null) {
				hasValue = new boolean[availFromClient.getValue().length()];
			} else if (hasValue.length < availFromClient.getValue().length()) {
				final boolean[] old = hasValue;
				hasValue = new boolean[availFromClient.getValue().length()];
				System.arraycopy(old, 0, hasValue, 0, old.length);
			}
			for (var i = 0; i < availFromClient.getValue().length(); ++i) {
                final int n = i;
				hasValue[n] |= availFromClient.getValue().charAt(i) == '1';
			}
		}
		if (hasValue == null) {
			return 0.0f;
		}
        for (boolean b : hasValue) {
            if (!b) {
                continue;
            }
            result = (float) (result + 1.0);
        }
		return result / hasValue.length;
	}

	public float getAvailability() {
		return this.availability;
	}

	public float getImmediateAvailability() {
		return this.immediateAvailability;
	}

	public boolean isShareChecked() {
		return this.shareChecked;
	}

	public void setShareChecked(final boolean shareChecked) {
		this.shareChecked = shareChecked;
	}

	public boolean isHasFirstByte() {
		return this.hasFirstByte;
	}

	public void setHasFirstByte(final boolean b) {
		this.hasFirstByte = b;
	}

	public boolean isDownloadNotified() {
		return downloadNotified;
	}

	public void setDownloadNotified(final boolean downloadNotified) {
		this.downloadNotified = downloadNotified;
	}
}
