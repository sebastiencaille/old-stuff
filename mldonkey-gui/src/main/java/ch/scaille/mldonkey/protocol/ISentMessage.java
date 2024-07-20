/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public interface ISentMessage extends IMessage {
	int messageLength();

	void encodeInto(ByteBuffer var1);

	void log(Supplier<String> message);
}
