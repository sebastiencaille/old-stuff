/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.model.SharedFile;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SharedFilePanelModel extends GuiModel {
	private final ObjectProperty<SharedFile> lastSelectedShare;
	private final ListProperty<SharedFile> selectedShares;
	private final BooleanProperty newDownload;

	public SharedFilePanelModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super(config);
		this.lastSelectedShare = new ObjectProperty<>("LastSelectedSharedFile", this.getPropertySupport());
		this.selectedShares = new ListProperty<>("SelectedSharedFiles", this.getPropertySupport());
		this.newDownload = new BooleanProperty("NewDownload", getPropertySupport(), false);
	}

	public ObjectProperty<SharedFile> getLastSelectedShare() {
		return this.lastSelectedShare;
	}

	public ListProperty<SharedFile> getSelectedShares() {
		return this.selectedShares;
	}

	public BooleanProperty getNewDownload() {
		return newDownload;
	}
}
