/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import java.nio.ByteBuffer;

public interface IMlType {
	int messageLength();

	void encodeInto(ByteBuffer var1);

	void decodeFrom(ByteBuffer var1);
}
