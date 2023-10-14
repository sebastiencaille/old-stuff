/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

public class Server implements Comparable<Server> {
	private final int id;
	private String name;
	private String version;
	private long fileCount;
	private long userCount;
	private int geoIp;
	private HostState state;

	public Server(final int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String value) {
		this.name = value;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(final String value) {
		this.version = value;
	}

	public long getFileCount() {
		return this.fileCount;
	}

	public void setFileCount(final long value) {
		this.fileCount = value;
	}

	public long getUserCount() {
		return this.userCount;
	}

	public void setUserCount(final long value) {
		this.userCount = value;
	}

	public int getGeoIp() {
		return this.geoIp;
	}

	public void setGeoIp(final int geoIp) {
		this.geoIp = geoIp;
	}

	@Override
	public int compareTo(final Server o) {
		return this.id - o.id;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Server server && this.id == server.id;
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

	public int getId() {
		return this.id;
	}
}
