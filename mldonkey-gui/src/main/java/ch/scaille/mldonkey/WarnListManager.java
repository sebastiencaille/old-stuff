/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WarnListManager {
	private static final String SPLIT = "[\\s\\p{Punct}-]+";
	private final Set<String> keywords = new HashSet<>();
	private final Set<Character> singleChars = new HashSet<>();
	private final File file;

	public WarnListManager(final File file) {
		this.file = file;
	}

	public void load() {
		try (var reader = new BufferedReader(new FileReader(this.file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				final var word = line.trim().toLowerCase();
				if (word.isEmpty()) {
					continue;
				}
				if (word.length() == 1) {
					this.singleChars.add(word.charAt(0));
				} else {
					this.keywords.add(word);
				}
			}
		} catch (final FileNotFoundException e) {
			// ignore
		} catch (final IOException e) {
			throw new IllegalStateException("Cannot read file", e);
		}
	}

	public boolean match(final String toCheck) {
		final var checkParts = toCheck.toLowerCase().split(SPLIT);
		String last = null;
		for (final var akeyword : checkParts) {
			if (akeyword.isEmpty()) {
				continue;
			}
			if (this.keywords.contains(akeyword)) {
				return true;
			}
			if (last != null && this.keywords.contains(last + akeyword)) {
				return true;
			}
			last = akeyword;
		}
		for (final var c : toCheck.toCharArray()) {
			if (this.singleChars.contains(c)) {
				return true;
			}
		}
		return false;
	}

	public boolean matches(final Set<String> names) {
		return names.stream().anyMatch(this::match);
	}
}
