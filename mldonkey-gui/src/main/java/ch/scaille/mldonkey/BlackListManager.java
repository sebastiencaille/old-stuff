/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import ch.scaille.mldonkey.crypto.Crypto;
import ch.scaille.util.helpers.JavaExt;

public class BlackListManager {
	
	private static final GuiLogger LOGGER = new GuiLogger(BlackListManager.class);
	
	
	private final Map<String, Date> allEntries = new ConcurrentHashMap<>();
	private final Map<String, Date> newEntries = new ConcurrentHashMap<>();
	private final File file;
	private final Crypto crypto;

	private boolean dirty = false;

	public BlackListManager(final File file, Crypto crypto, final Timer timer) {
		this.file = file;
		this.crypto = crypto;
		timer.schedule(JavaExt.timerTask(this::saveValidatedBlacklist), 60000, 60000);
	}

	protected void saveValidatedBlacklist() {
		final var all = BlackListManager.this.allEntries;
		synchronized (all) {
			if (!dirty) {
				return;
			}
			var oldEntries = new HashMap<>(BlackListManager.this.allEntries);
			oldEntries.keySet().removeAll(BlackListManager.this.newEntries.keySet());
			try {
				save(oldEntries, false);
			} catch (final IOException e) {
				LOGGER.log(e);
			}
			dirty = false;
		}
	}

	public void load() {
		if (!this.file.exists()) {
			return;
		}
		try (var reader = new BufferedReader(new FileReader(this.file))) {
			String line;
			var builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				final var split = line.split(" ");
				if (split.length == 1) {
					builder.append(line);
					continue;
				}
				builder.append(split[0]);
				final var word = builder.toString().trim().toLowerCase();
				if (word.isEmpty()) {
					return;
				}
				final var date = new Date(Long.parseLong(split[1]));
				if (System.currentTimeMillis() - date.getTime() > 15724800000L) {
					continue;
				}
				builder = new StringBuilder();
				this.allEntries.put(word, date);
			}
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot read file", e);
		}
	}

	private void save(final Map<String, Date> toSave, final boolean append) throws IOException {
		try (var writer = new BufferedWriter(new FileWriter(this.file, append))) {
			for (final Map.Entry<String, Date> entry : toSave.entrySet()) {
				writer.write(entry.getKey());
				writer.write(" ");
				writer.write(Long.toString(entry.getValue().getTime()));
				writer.newLine();
			}
		}
	}

	public void save() throws IOException {
		this.save(this.newEntries, true);
		this.newEntries.clear();
	}

	public boolean match(final Collection<String> links, final long size) {
		boolean found = false;
		for (final var link : links) {
			final var key = this.key(link, size);
			final var hashed = crypto.getHash(key);
			if (!this.allEntries.containsKey(hashed)) {
				continue;
			}
			found = true;
			break;
		}
		if (found && links.size() > 1) {
			addLinks(links, size);
		}
		return found;
	}

	public void addLinks(final Collection<String> fileLinks, final long size) {
		for (final var link : fileLinks) {
			final var key = this.key(link, size);
			final var hashed = crypto.getHash(key);
			if (this.allEntries.containsKey(hashed)) {
				continue;
			}
			this.newEntries.put(hashed, new Date());
			this.allEntries.put(hashed, new Date());
		}
	}

	private String key(final String link, final long size) {
		try {
			return link.toLowerCase() + size;
		} catch (final Exception e) {
			LOGGER.log(e);
			return null;
		}
	}

	public void clearUnsaved() {
		final var map = this.allEntries;
		synchronized (map) {
			this.newEntries.clear();
		}
	}

}
