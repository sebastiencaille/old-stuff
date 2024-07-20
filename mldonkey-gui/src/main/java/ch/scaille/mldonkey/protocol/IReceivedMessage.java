/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

import ch.scaille.mldonkey.MLDonkeyGui;

public interface IReceivedMessage extends IMessage {
	void decodeFrom(ByteBuffer var1);

	void handle(MLDonkeyGui var1);

	int getErrorIndex();

	void log(Supplier<String> log);
}
