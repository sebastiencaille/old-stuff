/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Collection;

public interface IBlackListData {
	Collection<String> getBlackListData();

	long getBlackListSize();

	WarningLevel getWarnings();

	void setWarnings(WarningLevel var1);
}
