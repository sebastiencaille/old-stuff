/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

public class Client implements Comparable<Client> {
	private final int id;
	private final Map<FileDownload, FileDownload> files = new WeakHashMap<>();
	private String name;
	private int geoIp;
	private String software;
	private String release;
	private HostState state;

	public Client(final int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void addFile(final FileDownload download) {
		this.files.remove(download);
		this.files.put(download, download);
	}

	public void removeFile(final FileDownload download) {
		this.files.remove(download);
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getGeoIp() {
		return this.geoIp;
	}

	public void setGeoIp(final int geoIp) {
		this.geoIp = geoIp;
	}

	public Collection<FileDownload> getDownloads() {
		return this.files.values();
	}

	public String getSoftware() {
		return this.software;
	}

	public void setSoftware(final String value) {
		this.software = value;
	}

	public String getRelease() {
		return this.release;
	}

	public void setRelease(final String value) {
		this.release = value;
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

	public HostState getState() {
		return this.state;
	}

	public void setState(final HostState state) {
		this.state = state;
	}

	public int getFileCount() {
		return this.files.size();
	}

	@Override
	public String toString() {
		return "Client " + this.getId() + ": " + (this.state);
	}
}
