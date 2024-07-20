/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Collection;

public interface IWarnedData {
	Collection<String> getWarnData();

	WarningLevel getWarnings();

	void setWarnings(WarningLevel var1);
}
