/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.List;

public class SharedFile implements Comparable<SharedFile> {
	private final int id;
	private String name;
	private long size;
	private String identifier;
	private long timestamp;

	public SharedFile(final int id) {
		this.id = id;
	}

	@Override
	public int compareTo(final SharedFile o) {
		return this.id - o.id;
	}

	@Override
	public boolean equals(final Object obj) {
		return (obj instanceof SharedFile sharedFile) && this.id == sharedFile.getId();
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String value) {
		this.name = value;
	}

	public long getSize() {
		return this.size;
	}

	public void setSize(final long value) {
		this.size = value;
	}

	public void addIdentifier(final String value) {
		this.identifier = value;
	}

	public void addIdentifiers(final List<String> identifiers2) {
		this.identifier = identifiers2.iterator().next();
	}

	public String getIdentifier() {
		return identifier;
	}

	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "SharedFile " + this.getId() + ", " + name;
	}

}
