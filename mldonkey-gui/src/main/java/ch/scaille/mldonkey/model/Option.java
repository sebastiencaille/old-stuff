/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

public class Option implements Comparable<Option> {
	private final String name;
	private String value;

	public Option(final String name, final String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Option option && this.name.equals(option.name);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public int compareTo(final Option option) {
		return this.name.compareTo(option.name);
	}
}
