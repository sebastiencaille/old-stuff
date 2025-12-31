/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.model;

import ch.scaille.annotations.GuiObject;
import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.ListViews;

@GuiObject
public class FileQuery implements Comparable<FileQuery> {
	private int id;
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

	public ListModel<FileQueryResult> getResults() {
		return this.results;
	}

	public void dispose() {
		this.results.dispose();
	}

	public String getSearchText() {
		return this.searchText;
	}

	public void setSearchText(final String searchText) {
		this.searchText = searchText;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int value) {
		this.id = value;
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
