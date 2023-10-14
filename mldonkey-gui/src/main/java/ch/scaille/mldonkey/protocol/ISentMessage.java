/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public interface ISentMessage extends IMessage {
	public int messageLength();

	public void encodeInto(ByteBuffer var1);

	public void log(Supplier<String> message);
}
