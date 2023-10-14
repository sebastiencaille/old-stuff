/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Collection;

public interface IWarnedData {
	public Collection<String> getWarnData();

	public WarningLevel getWarnings();

	public void setWarnings(WarningLevel var1);
}
