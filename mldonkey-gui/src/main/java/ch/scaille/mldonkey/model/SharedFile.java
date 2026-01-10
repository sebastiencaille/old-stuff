/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class SharedFile implements Comparable<SharedFile> {
	private final int id;
	@Setter
    private String name;
	@Setter
    private long size;
	private String identifier;
	@Setter
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

    public void addIdentifier(final String value) {
		this.identifier = value;
	}

	public void addIdentifiers(final List<String> identifiers2) {
		this.identifier = identifiers2.getFirst();
	}

    @Override
	public String toString() {
		return "SharedFile " + this.getId() + ", " + name;
	}

}
