/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Arrays;

public class FileUUID implements Comparable<FileUUID> {
	private final byte[] md4;
	private final long size;

	public FileUUID(final byte[] md4, final long size) {
		this.md4 = md4;
		this.size = size;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof FileUUID)) {
			return false;
		}
		final var o = (FileUUID) obj;
		return this.size == o.size && Arrays.equals(this.md4, o.md4);
	}

	@Override
	public int hashCode() {
		return (int) this.size;
	}

	@Override
	public int compareTo(final FileUUID paramT) {
		if (this.size < paramT.size) {
			return -1;
		}
		if (this.size > paramT.size) {
			return 1;
		}
		for (var i = 0; i < this.md4.length; ++i) {
			if (this.md4[i] < paramT.md4[i]) {
				return -1;
			}
			if (this.md4[i] <= paramT.md4[i]) {
				continue;
			}
			return 1;
		}
		return 0;
	}
}
