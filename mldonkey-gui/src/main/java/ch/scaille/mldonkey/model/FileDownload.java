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
import lombok.Getter;
import lombok.Setter;

@GuiObject
public class FileDownload implements Comparable<FileDownload>, IWarnedData, IBlackListData {
	@Getter
    private final int id;
	@Setter
    @Getter
    private String name;
	@Setter
    @Getter
    private FileState state;
	@Setter
    @Getter
    private long fileSize;
	@Setter
    @Getter
    private long downloadedSize;
	@Setter
    @Getter
    private float downloadRate;
	@Getter
    private final Set<String> names = new HashSet<>();
	@Setter
    @Getter
    private int numberOfSources;
	@Setter
    @Getter
    private String chunks;
	@Setter
    @Getter
    private byte[] md4;
	@Getter
    private final List<String> links = new ArrayList<>();
	@Setter
    @Getter
    private String format;
	private WarningLevel warnings = null;
	@Setter
    @Getter
    private int lastSeen;
	@Getter
    private final Map<Client, String> availabilities = new HashMap<>();
	@Getter
    private float availability;
	@Setter
    @Getter
    private boolean shareChecked;
	@Getter
    private float immediateAvailability;
	@Setter
    @Getter
    private boolean hasFirstByte;
	@Setter
    @Getter
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

    public void addName(final String aname) {
		this.names.add(aname);
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

    @Override
	public Collection<String> getBlackListData() {
		return this.links;
	}

	@Override
	public long getBlackListSize() {
		return this.getFileSize();
	}

    public void addLink(final String value) {
		if (!this.links.contains(value)) {
			this.links.add(value);
		}
	}

	public List<String> getIdentifiers() {
		return this.links;
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

}
