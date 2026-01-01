/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.model.SharedFile;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@Getter
public class SharedFilePanelModel extends GuiModel {
	private final ObjectProperty<@Nullable SharedFile> lastSelectedShare;
	private final ListProperty<SharedFile> selectedShares;
	private final BooleanProperty newDownload;

	public SharedFilePanelModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super(config);
		this.lastSelectedShare = new ObjectProperty<>("LastSelectedSharedFile", this.getPropertySupport(), null);
		this.selectedShares = new ListProperty<>("SelectedSharedFiles", this.getPropertySupport());
		this.newDownload = new BooleanProperty("NewDownload", getPropertySupport(), false);
	}

}
