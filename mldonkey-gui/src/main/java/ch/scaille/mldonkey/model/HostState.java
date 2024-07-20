/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

public enum HostState {
	NOT_CONNECTED, CONNECTING, CONNECTION_INITIATING, DOWNLOADING, CONNECTED, CONNECTED_QUEUED, NEW_HOST, REMOVED_HOST,
	BLACKLISTED, NOT_CONNECTED_QUEUED, CONNECTED_AND_UNK;

	HostState() {
	}

	public boolean isConnected() {
		return this == CONNECTED || this == CONNECTED_QUEUED || this == CONNECTED_AND_UNK || this == DOWNLOADING
				|| this == CONNECTION_INITIATING;
	}
}
