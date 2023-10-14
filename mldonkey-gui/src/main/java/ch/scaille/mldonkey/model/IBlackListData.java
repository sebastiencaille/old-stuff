/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import java.util.Collection;

public interface IBlackListData {
	public Collection<String> getBlackListData();

	public long getBlackListSize();

	public WarningLevel getWarnings();

	public void setWarnings(WarningLevel var1);
}
