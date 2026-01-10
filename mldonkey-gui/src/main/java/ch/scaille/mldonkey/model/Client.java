/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

public class Client implements Comparable<Client> {
	@Getter
    private final int id;
	private final Map<FileDownload, FileDownload> files = new WeakHashMap<>();
	@Setter
    @Getter
    private String name;
	@Setter
    @Getter
    private int geoIp;
	@Setter
    @Getter
    private String software;
	@Setter
    @Getter
    private String release;
	@Setter
    @Getter
    private HostState state;

	public Client(final int id) {
		this.id = id;
	}

    public void addFile(final FileDownload download) {
		this.files.remove(download);
		this.files.put(download, download);
	}

	public void removeFile(final FileDownload download) {
		this.files.remove(download);
	}

    public Collection<FileDownload> getDownloads() {
		return this.files.values();
	}

    @Override
	public int compareTo(final Client o) {
		return this.id - o.id;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Client client && this.id == client.id;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

    public int getFileCount() {
		return this.files.size();
	}

	@Override
	public String toString() {
		return "Client " + this.getId() + ": " + (this.state);
	}
}
