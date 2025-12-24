/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FileQueryResult implements Comparable<FileQueryResult>, IWarnedData, IBlackListData {
	private final int id;
	private final Collection<Integer> searchId = new HashSet<>();
	private final List<String> fileNames = new ArrayList<>();
	private final List<String> fileIdentifiers = new ArrayList<>();
	private long size;
	private String format;
	private boolean downloaded;
	private int completeSources;
	private int availability;
	private WarningLevel warnings;

	public FileQueryResult(final int value) {
		this.id = value;
	}

	public int getId() {
		return this.id;
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

	public List<String> getFileIdentifiers() {
		return this.fileIdentifiers;
	}

	public void setSize(final long value) {
		this.size = value;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(final String value) {
		this.format = value;
	}

	public void setDownloaded(final boolean value) {
		this.downloaded = value;
	}

	public List<String> getFileNames() {
		return this.fileNames;
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

	public void setCompleteSources(final int completeSources) {
		this.completeSources = completeSources;
	}

	public int completeSources() {
		return this.completeSources;
	}

	public int getAvailability() {
		return this.availability;
	}

	public void setAvailability(final int value) {
		this.availability = value;
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

	public boolean isDownloaded() {
		return this.downloaded;
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
