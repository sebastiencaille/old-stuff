/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.download;

import static ch.scaille.gui.model.IListModelListener.editionStopped;
import static ch.scaille.gui.model.IListModelListener.editionStopping;
import static ch.scaille.gui.mvc.factories.BindingDependencies.preserveOnUpdateOf;
import static ch.scaille.gui.mvc.factories.Converters.listen;
import static ch.scaille.gui.swing.factories.SwingBindings.multipleSelection;
import static ch.scaille.gui.swing.factories.SwingBindings.selected;
import static ch.scaille.gui.swing.factories.SwingBindings.selection;
import static ch.scaille.gui.swing.factories.SwingBindings.values;
import static java.util.stream.Collectors.joining;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.function.Predicate;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import ch.scaille.gui.model.views.AbstractDynamicView;
import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.mvc.IComponentBinding;
import ch.scaille.gui.mvc.properties.ObjectProperty;
import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.JTableHelper;
import ch.scaille.gui.swing.jtable.PolicyTableColumnModel;
import ch.scaille.gui.swing.jtable.TableColumnWithPolicy;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.gui.swing.renderers.PercentRenderer;
import ch.scaille.gui.swing.renderers.RatioRenderer;
import ch.scaille.gui.swing.renderers.SpeedRenderer;
import ch.scaille.mldonkey.GeoIp;
import ch.scaille.mldonkey.GuiLogger;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.components.ChuncksBar;
import ch.scaille.mldonkey.gui.renderers.DownloadStateRenderer;
import ch.scaille.mldonkey.gui.renderers.MlSizeRenderer;
import ch.scaille.mldonkey.gui.renderers.WarningRenderer;
import ch.scaille.mldonkey.gui.search.SearchQueryController;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.model.WarningLevel;
import ch.scaille.mldonkey.protocol.types.FileState;

public class FileDownloadPanel extends JPanel {
	
	private static final GuiLogger LOGGER = new GuiLogger(FileDownloadPanel.class);
	
	private final FileDownloadHmiController downloadController;

	public FileDownloadPanel(final MLDonkeyGui gui, final SearchQueryController searchQueryController) {
		this.setLayout(new BorderLayout());
		this.downloadController = new FileDownloadHmiController(gui, searchQueryController);
		final var downloadFilter = new DownloadFilter();
		final var filteredDownloads = gui.getDownloads().child(ListViews.filtered(downloadFilter)).withName("Download");
		filteredDownloads.addListener(editionStopped(event -> {
			for (final var download : event.getObjects()) {
				if (FileDownloadPanel.this.downloadController.getLastSelectedDownload().getValue() != download) {
					continue;
				}
				downloadController.model.setCurrentObject(download);
				downloadController.model.load();
			}
		}));
		this.downloadController.getShowOnlyEmptySources().bind(downloadFilter.showOnlyEmptySources());
		this.downloadController.getShowOnlyDownloading().bind(downloadFilter.showOnlyDownloading());
		final var model = new FileDownloadTableModel(filteredDownloads);
		final var downloadsTable = new JTable(model);
		final var columnModel = new PolicyTableColumnModel<FileDownloadTableModel.Columns>(downloadsTable);
		downloadsTable.setColumnModel(columnModel);
		columnModel.configureColumn(
				TableColumnWithPolicy.percentOfAvailableSpace(FileDownloadTableModel.Columns.FILENAME, 100));
		columnModel.configureColumn(TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.WARNING, 2)
				.with(new WarningRenderer()));
		columnModel.configureColumn(TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.AVAILABILITY, 8)
				.with(new PercentRenderer()));
		columnModel.configureColumn(
				TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.NOW, 8).with(new PercentRenderer()));
		columnModel.configureColumn(
				TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.RATIO, 8).with(new RatioRenderer()));
		columnModel.configureColumn(
				TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.SIZE, 10).with(new MlSizeRenderer()));
		columnModel.configureColumn(TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.SOURCES, 48));
		columnModel.configureColumn(
				TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.SPEED, 9).with(new SpeedRenderer()));
		columnModel.configureColumn(TableColumnWithPolicy.fixedTextWidth(FileDownloadTableModel.Columns.STATE, 16)
				.with(new DownloadStateRenderer()));
		downloadsTable.setAutoCreateColumnsFromModel(false);
		this.downloadController.getLastSelectedDownload().bind(selection(downloadsTable, model));
		this.downloadController.getSelectedDownloads().bind(multipleSelection(downloadsTable, model))
				.addDependency(preserveOnUpdateOf(filteredDownloads));
		downloadsTable.addMouseListener(new MouseClickListener(downloadsTable, gui, model));
		downloadsTable.addMouseListener(new MousePopup(downloadsTable, model,
				this.downloadController.getLastSelectedDownload(), this.downloadController.getSelectedDownloads()));
		this.add(new JScrollPane(downloadsTable), BorderLayout.CENTER);
		gui.getDownloads().addListener(editionStopping(event -> {
			if (event.getObject() == FileDownloadPanel.this.downloadController.getLastSelectedDownload().getValue()) {
				downloadController.model.setCurrentObject(event.getObject());
				downloadController.model.load();
			}
		}));
		this.downloadController.getLastSelectedDownload().bind(this.downloadController.model.loadBinding());

		final var filtersPanel = new JPanel(new FlowLayout());
		final var onlyZeroSources = new JCheckBox("No Source");
		this.downloadController.getShowOnlyEmptySources().bind(selected(onlyZeroSources));
		filtersPanel.add(onlyZeroSources);

		final var onlyDownloading = new JCheckBox("Downloading");
		this.downloadController.getShowOnlyDownloading().bind(selected(onlyDownloading));

		filtersPanel.add(onlyDownloading);
		this.add(filtersPanel, BorderLayout.NORTH);

		final var detailsPanel = new JPanel(new GridLayout(0, 1));
		final var chunks = new ChuncksBar();
		this.downloadController.model.getChunksProperty().bind(chunks.valueBinding());
		this.downloadController.model.getFileSizeProperty().bind(chunks.sizeBinding());
		chunks.addSelectedOffsetListener(
				evt -> gui.preview(FileDownloadPanel.this.downloadController.getLastSelectedDownload().getValue(),
						(Long) evt.getNewValue()));
		detailsPanel.add(chunks);

		final var urls = new JComboBox<String>();
		this.downloadController.model.getLinksProperty().bind(values(urls));
		detailsPanel.add(urls);

		final var names = new JComboBox<String>();
		this.downloadController.model.getNamesProperty().bind(values(names));
		detailsPanel.add(names);

		final var countries = new JLabel();
		detailsPanel.add(countries);
		this.downloadController.model
				.getAvailabilitiesProperty().bind(listen(a -> a.keySet().stream()
						.map(client -> GeoIp.getCounty(client.getGeoIp())).distinct().collect(joining(", "))))
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

	private final class MouseClickListener extends MouseAdapter {
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

	private static class DownloadFilter extends AbstractDynamicView<FileDownload> implements Predicate<FileDownload> {
		private Boolean showOnlyEmptySources;
		private Boolean showOnlyDownloading;

		private DownloadFilter() {
		}

		@Override
		public boolean test(final FileDownload value) {
			if (!EventQueue.isDispatchThread()) {
				final var exc = new Exception();
				exc.setStackTrace(Thread.currentThread().getStackTrace());
				LOGGER.log(exc);
			}
			return !(value.getState() == FileState.DOWNLOADED || value.getState() == FileState.SHARED
					|| this.showOnlyEmptySources && value.getNumberOfSources() != 0
					|| this.showOnlyDownloading && value.getDownloadRate() <= 0.0f && !value.isHasFirstByte());
		}

		public IComponentBinding<Boolean> showOnlyEmptySources() {
			return this.refreshWhenUpdated(v -> this.showOnlyEmptySources = v);
		}

		public IComponentBinding<Boolean> showOnlyDownloading() {
			return this.refreshWhenUpdated(v -> this.showOnlyDownloading = v);
		}
	}

}
