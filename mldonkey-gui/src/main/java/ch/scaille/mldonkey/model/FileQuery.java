/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import ch.scaille.annotations.GuiObject;
import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.ListViews;
import lombok.Getter;
import lombok.Setter;

@Getter
@GuiObject
public class FileQuery implements Comparable<FileQuery> {
	@Setter
    private int id;
	@Setter
    private String searchText;
	private ListModel<FileQueryResult> results;

	public FileQuery(final int searchId) {
		this.id = searchId;
	}

	public void attachTo(final ListModel<FileQueryResult> allResults) {
		this.results = allResults
				.child(ListViews.filtered(v -> v != null && !v.isDownloaded() && v.hasSearchId(this.id)))
				.withName("FileQueryResult of " + FileQuery.this.id + "(" + System.identityHashCode(this) + ")");
	}

    public void dispose() {
		this.results.dispose();
	}

    @Override
	public int compareTo(final FileQuery o) {
		return this.id - o.id;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof FileQuery fileQuery && this.id == fileQuery.id;
	}

	@Override
	public int hashCode() {
		return this.id;
	}

}
