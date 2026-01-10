/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FileQueryResult implements Comparable<FileQueryResult>, IWarnedData, IBlackListData {
	@Getter
    private final int id;
	private final Collection<Integer> searchId = new HashSet<>();
	@Getter
    private final List<String> fileNames = new ArrayList<>();
	@Getter
    private final List<String> fileIdentifiers = new ArrayList<>();
	@Setter
    private long size;
	@Setter
    @Getter
    private String format;
	@Setter
    @Getter
    private boolean downloaded;
	@Setter
    private int completeSources;
	@Setter
    @Getter
    private int availability;
	private WarningLevel warnings;

	public FileQueryResult(final int value) {
		this.id = value;
	}

    public void addSearchId(final int sid) {
		this.searchId.add(sid);
	}

	public void removeSearchId(final int sid) {
		this.searchId.remove(sid);
	}

	public boolean hasSearchId(final int sid) {
		return this.searchId.contains(sid);
	}

	public void addFileName(final String value) {
		this.fileNames.add(value);
	}

	public void addFileIdentifier(final String value) {
		this.fileIdentifiers.add(value);
	}

    public long getFileSize() {
		return this.size;
	}

	@Override
	public int compareTo(@Nullable final FileQueryResult o) {
		if (o == null) {
			return -1;
		}
		return Integer.compare(this.id, o.id);
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof FileQueryResult fileQueryResult && this.id == fileQueryResult.id;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

    public int completeSources() {
		return this.completeSources;
	}

    @Override
	public List<String> getWarnData() {
		return this.fileNames;
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
		return this.getFileIdentifiers();
	}

	@Override
	public long getBlackListSize() {
		return this.getFileSize();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":[id=" + this.id + ", queries=" + this.searchId + "] "
				+ fileNames.getFirst();
	}
}
