/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Server implements Comparable<Server> {
	private final int id;
	@Setter
    private String name;
	@Setter
    private String version;
	@Setter
    private long fileCount;
	@Setter
    private long userCount;
	@Setter
    private int geoIp;
	@Setter
    private HostState state;

	public Server(final int id) {
		this.id = id;
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

}
