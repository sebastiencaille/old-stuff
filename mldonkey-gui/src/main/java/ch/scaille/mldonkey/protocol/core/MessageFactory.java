/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import ch.scaille.mldonkey.protocol.IReceivedMessage;

public class MessageFactory {

	private MessageFactory() {
	}

	private static final Map<Short, IReceivedMessage> messages = new HashMap<>();

	public static Optional<IReceivedMessage> createMessage(final short opcode) {
		final var message = messages.get(opcode);
		if (message == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(message.getClass().getDeclaredConstructor().newInstance());
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot create message " + message.getClass(), e);
		}
	}

	private static void addMessage(IReceivedMessage msg) {
		messages.put(msg.opCode(), msg);
	}

	static {
		addMessage(new CoreProtocol());
		addMessage(new BadPassword());
		addMessage(new NetworkInfo());
		addMessage(new ClientStats());
		addMessage(new ClientInfo());
		addMessage(new ClientState());
		addMessage(new ConsoleMessage());
		addMessage(new DownloadingFiles());
		addMessage(new DownloadedFiles());
		addMessage(new FileInfo());
		addMessage(new FileDownloadUpdate());
		addMessage(new FileAddSource());
		addMessage(new FileRemoveSource());
		addMessage(new FileUpdateAvailability());
		addMessage(new OptionsInfo());
		addMessage(new Search());
		addMessage(new SearchResult());
		addMessage(new ResultInfo());
		addMessage(new SearchWaiting());
		addMessage(new ServerInfo());
		addMessage(new ServerState());
		addMessage(new SharedFileInfo());
		addMessage(new SharedFileUnshared());
		addMessage(new DefineSearches());
	}
}
