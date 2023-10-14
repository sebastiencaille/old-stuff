/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public interface IMlType {
	public int messageLength();

	public void encodeInto(ByteBuffer var1);

	public void decodeFrom(ByteBuffer var1);
}
