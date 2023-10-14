/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

public enum FileState {
	DOWNLOADING, PAUSED, DOWNLOADED, SHARED, CANCELLED, NEW, ABORTED, QUEUED;

	private FileState() {
	}
}
