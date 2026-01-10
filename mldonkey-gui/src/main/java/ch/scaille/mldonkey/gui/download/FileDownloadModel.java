/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import ch.scaille.gui.model.views.IView;
import ch.scaille.javabeans.properties.BooleanProperty;
import ch.scaille.javabeans.properties.ListProperty;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.javabeans.properties.PropertiesAggregator;
import ch.scaille.mldonkey.gui.model.FileDownloadGuiModel;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.protocol.types.FileState;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
class FileDownloadModel extends FileDownloadGuiModel {

    public record DownloadView(boolean showOnlyEmptySources,
                               boolean showOnlyDownloading) implements IView<FileDownload> {

        @Override
        public boolean test(FileDownload value) {
            return !(value.getState() == FileState.DOWNLOADED || value.getState() == FileState.SHARED
                    || this.showOnlyEmptySources && value.getNumberOfSources() != 0
                    || this.showOnlyDownloading && value.getDownloadRate() <= 0.0f && !value.isHasFirstByte());
        }
    }

    final ObjectProperty<@Nullable FileDownload> lastSelectedDownload;
    final ListProperty<FileDownload> selectedDownloads;
    final BooleanProperty showOnlyEmptySources;
    final BooleanProperty showOnlyDownloading;
    final PropertiesAggregator<DownloadView> fileDownloadView;

    public FileDownloadModel(final ModelConfiguration.ModelConfigurationBuilder config) {
        super("DownloadPanel", config);
        this.lastSelectedDownload = new ObjectProperty<>("LastSelectedFileDownload", this.getPropertySupport(), null);
        this.selectedDownloads = new ListProperty<>("SelectedFileDownloads", this.getPropertySupport());
        this.showOnlyEmptySources = new BooleanProperty("OnlyEmptySources", this.getPropertySupport());
        this.showOnlyDownloading = new BooleanProperty("OnlyDownloading", this.getPropertySupport());
        this.fileDownloadView = new PropertiesAggregator<DownloadView>("fileDownloadView", this.getPropertySupport())
                .add(showOnlyEmptySources, showOnlyDownloading,
                        (showOnlyEmptySourcesVal, showOnlyDownloadingVal) -> new DownloadView(showOnlyEmptySourcesVal.get(), showOnlyDownloadingVal.get()));
    }
}
