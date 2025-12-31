/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.gui.model.FileDownloadGuiModel;
import ch.scaille.mldonkey.model.FileDownload;
import org.jspecify.annotations.NullMarked;

@NullMarked
class FileDownloadModel extends FileDownloadGuiModel {
	final ObjectProperty<FileDownload> lastSelectedDownload;
	final ListProperty<FileDownload> selectedDownloads;
	final BooleanProperty showOnlyEmptySources;
	final BooleanProperty showOnlyDownloading;

	public FileDownloadModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super("DownloadPanel", config);
		this.lastSelectedDownload = new ObjectProperty<>("LastSelectedFileDownload", this.getPropertySupport());
		this.selectedDownloads = new ListProperty<>("SelectedFileDownloads", this.getPropertySupport());
		this.showOnlyEmptySources = new BooleanProperty("OnlyEmptySources", this.getPropertySupport());
		this.showOnlyDownloading = new BooleanProperty("OnlyDownloading", this.getPropertySupport());
	}
}
