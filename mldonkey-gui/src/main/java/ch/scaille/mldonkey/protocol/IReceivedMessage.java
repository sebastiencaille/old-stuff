/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

import ch.scaille.mldonkey.MLDonkeyGui;

public interface IReceivedMessage extends IMessage {
	public void decodeFrom(ByteBuffer var1);

	public void handle(MLDonkeyGui var1);

	public int getErrorIndex();

	public void log(Supplier<String> log);
}
