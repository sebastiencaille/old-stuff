/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.util.Optional;

import ch.scaille.mldonkey.protocol.IReceivedMessage;
import ch.scaille.mldonkey.protocol.ISentMessage;
import ch.scaille.mldonkey.protocol.core.MessageFactory;

public class CoreConnection {

	private static final GuiLogger LOGGER = new GuiLogger(CoreConnection.class);

	private final MLDonkeyGui gui;
	private SocketChannel socket;

	public CoreConnection(final MLDonkeyGui mlDonkeyGui) {
		this.gui = mlDonkeyGui;
	}

	public boolean connect() {
		try {
			this.socket = SocketChannel.open();
			this.socket.socket().connect(new InetSocketAddress("localhost", 4001));
		} catch (final Exception e) {
			LOGGER.log("Unable to connect", e);
			return false;
		}
		return true;
	}

	public void read() {
		SocketChannel current = this.socket;
		while (current != null) {
			try {
				this.createMessage(current).ifPresent(m -> EventQueue.invokeLater(() -> m.handle(this.gui)));
			} catch (final Exception e) {
				LOGGER.log("Unable to read message", e);
			}
			current = this.socket;
		}
		this.notifyDisconnected();
	}

	public synchronized boolean sendMessage(final ISentMessage message) {
		final var currentSocket = this.socket;
		if (currentSocket == null || !currentSocket.isConnected()) {
			return false;
		}
		message.log(() -> "gui> sending " + message.getClass().getSimpleName());
		final var msgSize = 2 + message.messageLength();
		final var buffer = ByteBuffer.allocate(4 + msgSize);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(msgSize);
		buffer.putShort(message.opCode());
		message.encodeInto(buffer);
		buffer.flip();
		try {
			currentSocket.write(buffer);
			return true;
		} catch (final Exception e) {
			LOGGER.log("Sending failed", e);
			return false;
		}
	}

	private void readBuffer(final String name, final SocketChannel input, final ByteBuffer buffer) throws IOException {
		int lastRead;
		for (var read = 0; read < buffer.capacity(); read += lastRead) {
			lastRead = input.read(buffer);
			if (lastRead > 0) {
				continue;
			}
			throw new IOException("Unable to read data: " + name);
		}
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.flip();
	}

	private Optional<IReceivedMessage> createMessage(final SocketChannel input) throws IOException {
		int size;
		try {
			final var sizeBuffer = ByteBuffer.allocate(4);
			this.readBuffer("size", input, sizeBuffer);
			size = sizeBuffer.getInt();
		} catch (final BufferUnderflowException _) {
			throw new IOException("Disconnection detected");
		}
		if (size == 0) {
			LOGGER.log("Received zero length message");
			return Optional.empty();
		}
		final var msgBuffer = ByteBuffer.allocate(size);
		this.readBuffer("message", input, msgBuffer);
		final var opCode = msgBuffer.getShort();
		return MessageFactory.createMessage(opCode).map(message -> {
			message.log(() -> "gui> received " + message.getClass().getSimpleName() + "/" + size);
			try {
				message.decodeFrom(msgBuffer);
			} catch (final BufferUnderflowException e) {
				LOGGER.log("Unable to read message, index=" + message.getErrorIndex(), e);
			}
			if (msgBuffer.hasRemaining()) {
				LOGGER.log("Remains " + (msgBuffer.limit() - msgBuffer.position()) + "/" + msgBuffer.capacity());
			}
			return message;
		}).or(() -> {
			LOGGER.log("Unhandled opcode: " + opCode);
			return Optional.empty();
		});
	}

	private void notifyDisconnected() {
		this.socket = null;
		this.gui.notifyDisconnected();
	}

	public void disconnect() {
		final var currentSocket = this.socket;
		if (currentSocket == null) {
			return;
		}
		try {
			currentSocket.close();
		} catch (final IOException e) {
			LOGGER.log(e);
		}
	}
}
