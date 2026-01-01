/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import static ch.scaille.gui.model.IListModelListener.editionStopped;
import static ch.scaille.gui.model.IListModelListener.editionStopping;
import static ch.scaille.gui.swing.factories.BindingDependencies.preserveOnUpdateOf;
import static ch.scaille.gui.swing.factories.SwingBindings.multipleSelection;
import static ch.scaille.gui.swing.factories.SwingBindings.selected;
import static ch.scaille.gui.swing.factories.SwingBindings.selection;
import static ch.scaille.gui.swing.factories.SwingBindings.values;
import static ch.scaille.javabeans.converters.Converters.listen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.JTableHelper;
import ch.scaille.gui.swing.jtable.PolicyTableColumnModel;
import ch.scaille.gui.swing.jtable.TableColumnWithPolicy;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.gui.swing.renderers.PercentRenderer;
import ch.scaille.gui.swing.renderers.RatioRenderer;
import ch.scaille.gui.swing.renderers.SpeedRenderer;
import ch.scaille.javabeans.properties.ObjectProperty;
import ch.scaille.mldonkey.GeoIp;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.components.ChunksBar;
import ch.scaille.mldonkey.gui.renderers.DownloadStateRenderer;
import ch.scaille.mldonkey.gui.renderers.MlSizeRenderer;
import ch.scaille.mldonkey.gui.renderers.WarningRenderer;
import ch.scaille.mldonkey.gui.search.SearchQueryController;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.model.WarningLevel;

public class FileDownloadPanel extends JPanel {

    private final FileDownloadHmiController downloadController;

    public FileDownloadPanel(final MLDonkeyGui gui, final SearchQueryController searchQueryController) {
        this.setLayout(new BorderLayout());
        this.downloadController = new FileDownloadHmiController(gui, searchQueryController);
        final var model = this.downloadController.model;

        final var downloadView = ListViews.dynamic(new FileDownloadModel.DownloadView(false, false));
        model.fileDownloadView.bind(downloadView);

        final var filteredDownloads = gui.getDownloads().child(downloadView).withName("Downloads");
        filteredDownloads.addListener(editionStopped(event -> {
            for (final var download : event.getObjects()) {
                if (model.lastSelectedDownload.getValue() != download) {
                    continue;
                }
                model.setCurrentObject(download);
                model.load();
            }
        }));
        final var tableModel = new FileDownloadTableModel(filteredDownloads);
        final var downloadsTable = new JTable(tableModel);
        final var columnModel = new PolicyTableColumnModel<FileDownloadTableModel.Columns>(downloadsTable);
        downloadsTable.setColumnModel(columnModel);
        columnModel.configureColumn(
                TableColumnWithPolicy.percentOfAvailableSpace(FileDownloadTableModel.Columns.FILENAME, 100));
        columnModel.configureColumn(TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.WARNING, 2)
                .with(new WarningRenderer()));
        columnModel.configureColumn(TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.AVAILABILITY, 8)
                .with(new PercentRenderer()));
        columnModel.configureColumn(
                TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.NOW, 8).with(new PercentRenderer()));
        columnModel.configureColumn(
                TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.RATIO, 8).with(new RatioRenderer()));
        columnModel.configureColumn(
                TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.SIZE, 10).with(new MlSizeRenderer()));
        columnModel.configureColumn(TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.SOURCES, 48));
        columnModel.configureColumn(
                TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.SPEED, 9).with(new SpeedRenderer()));
        columnModel.configureColumn(TableColumnWithPolicy.fixedTextLength(FileDownloadTableModel.Columns.STATE, 16)
                .with(new DownloadStateRenderer()));
        downloadsTable.setAutoCreateColumnsFromModel(false);
        model.lastSelectedDownload.bind(selection(downloadsTable, tableModel));
        model.selectedDownloads.bind(multipleSelection(downloadsTable, tableModel))
                .addDependency(preserveOnUpdateOf(filteredDownloads));
        downloadsTable.addMouseListener(new MouseClickListener(downloadsTable, gui, tableModel));
        downloadsTable.addMouseListener(new MousePopup(downloadsTable, tableModel,
                model.lastSelectedDownload, model.selectedDownloads));
        this.add(new JScrollPane(downloadsTable), BorderLayout.CENTER);
        gui.getDownloads().addListener(editionStopping(event -> {
            if (event.getObject() == model.lastSelectedDownload.getValue()) {
                model.setCurrentObject(event.getObject());
                model.load();
            }
        }));
        model.lastSelectedDownload.bind(model.loadBinding());

        final var filtersPanel = new JPanel(new FlowLayout());
        final var onlyZeroSources = new JCheckBox("No Source");
        model.showOnlyEmptySources.bind(selected(onlyZeroSources));
        filtersPanel.add(onlyZeroSources);

        final var onlyDownloading = new JCheckBox("Downloading");
        model.showOnlyDownloading.bind(selected(onlyDownloading));

        filtersPanel.add(onlyDownloading);
        this.add(filtersPanel, BorderLayout.NORTH);

        final var detailsPanel = new JPanel(new GridLayout(0, 1));
        final var chunks = new ChunksBar();
        model.getChunksProperty().bind(chunks.valueBinding());
        model.getFileSizeProperty().bind(chunks.sizeBinding());
        chunks.addSelectedOffsetListener(
                evt -> gui.preview(model.lastSelectedDownload.getValue(),
                        (Long) evt.getNewValue()));
        detailsPanel.add(chunks);

        final var urls = new JComboBox<String>();
        model.getLinksProperty().bind(values(urls));
        detailsPanel.add(urls);

        final var names = new JComboBox<String>();
        model.getNamesProperty().bind(values(names));
        detailsPanel.add(names);

        final var countries = new JLabel();
        detailsPanel.add(countries);
        model.getAvailabilitiesProperty().bind(listen(a -> a.keySet().stream()
                        .map(client -> GeoIp.getCounty(client.getGeoIp()))
                        .distinct()
                        .collect(Collectors.joining(", "))))
                .listen(countries::setText);
        this.add(detailsPanel, BorderLayout.SOUTH);
        this.downloadController.activate();
    }

    private final class MousePopup extends AbstractJTablePopup<FileDownload> {
        private MousePopup(final JTable table, final ListModelTableModel<FileDownload, ?> model,
                           final ObjectProperty<FileDownload> lastSelected,
                           final ObjectProperty<? extends Collection<FileDownload>> selections) {
            super(table, model, lastSelected, selections);
        }

        @Override
        protected void buildPopup(final JPopupMenu popupMenu, final FileDownload download) {
            popupMenu.removeAll();
            final var downloadName = download.getName();
            final var nameLabel = new JLabel(downloadName.substring(0, Math.min(downloadName.length(), 20)));
            popupMenu.add(nameLabel);
            popupMenu.add(new JSeparator());
            final var previewItem = new JMenuItem("Preview");
            previewItem.addActionListener(FileDownloadPanel.this.downloadController.getPreviewFileAction());
            popupMenu.add(previewItem);
            final var viewItem = new JMenuItem("View");
            viewItem.addActionListener(FileDownloadPanel.this.downloadController.getViewFileAction());
            popupMenu.add(viewItem);
            popupMenu.add(new JSeparator());
            final var asQueryItem = new JMenuItem("As query");
            asQueryItem.addActionListener(FileDownloadPanel.this.downloadController.asQueryAction());
            popupMenu.add(asQueryItem);
            final var cancelItem = new JMenuItem("Cancel");
            cancelItem.addActionListener(FileDownloadPanel.this.downloadController.getCancelFileAction());
            popupMenu.add(cancelItem);
            final var blackListItem = new JMenuItem("Blacklist");
            blackListItem.addActionListener(FileDownloadPanel.this.downloadController.getBlackListFileAction());
            popupMenu.add(blackListItem);
        }
    }

    private static final class MouseClickListener extends MouseAdapter {
        private final JTable downloadsTable;
        private final MLDonkeyGui gui;
        private final FileDownloadTableModel model;

        private MouseClickListener(final JTable downloadsTable, final MLDonkeyGui gui,
                                   final FileDownloadTableModel model) {
            this.downloadsTable = downloadsTable;
            this.gui = gui;
            this.model = model;
        }

        private FileDownload getDownload(final MouseEvent e) {
            return model.getObjectAtRow(downloadsTable.rowAtPoint(e.getPoint()));
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (e.getButton() == 1 && JTableHelper.columnAt(downloadsTable, e.getPoint(),
                    FileDownloadTableModel.Columns.class) == FileDownloadTableModel.Columns.WARNING) {
                gui.getDownloads().editValue(this.getDownload(e), this::setDownloadWarning);
            }
            if (e.getButton() == 1 && e.getClickCount() == 2) {
                final FileDownload download = this.getDownload(e);
                gui.preview(download, -1);
            }
        }

        private void setDownloadWarning(final FileDownload download) {
            if (download.getWarnings() == WarningLevel.ACK) {
                download.setWarnings(WarningLevel.UNK);
            } else if (download.getWarnings() == WarningLevel.UNK) {
                download.setWarnings(null);
            } else {
                download.setWarnings(WarningLevel.ACK);
            }
        }
    }

}
