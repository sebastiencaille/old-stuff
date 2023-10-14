/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.converters;

import ch.scaille.gui.mvc.converters.IConverter;

public class MD4Converter implements IConverter<byte[], String> {
	@Override
	public byte[] convertComponentValueToPropertyValue(final String componentObject) {
		throw new IllegalStateException("Read only");
	}

	@Override
	public String convertPropertyValueToComponentValue(final byte[] value) {
		final var builder = new StringBuilder();
		for (final var b : value) {
			builder.append(Integer.toHexString(b & 255));
		}
		return builder.toString().toUpperCase();
	}
}
