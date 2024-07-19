/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.shared;

import static ch.scaille.gui.swing.factories.SwingBindings.multipleSelection;
import static ch.scaille.gui.swing.factories.SwingBindings.selection;
import static ch.scaille.gui.swing.jtable.TableColumnWithPolicy.fixedTextLength;
import static ch.scaille.gui.swing.factories.BindingDependencies.preserveOnUpdateOf;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import ch.scaille.gui.model.views.ListViews;
import ch.scaille.gui.swing.AbstractJTablePopup;
import ch.scaille.gui.swing.jtable.PolicyTableColumnModel;
import ch.scaille.gui.swing.renderers.DateRenderer;
import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.gui.renderers.MlSizeRenderer;
import ch.scaille.mldonkey.model.SharedFile;

public class SharedFilesPanel extends JPanel {

	private final SharedFilePanelController sharedController;

	public SharedFilesPanel(final MLDonkeyGui gui) {
		this.setLayout(new BorderLayout());
		this.sharedController = new SharedFilePanelController(gui);

		final var filtered = gui.getSharedFiles()
				.child(ListViews.sortedFiltered((o1, o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()),
						v -> v.getTimestamp() > 0));

		final var tableModel = new SharedFilesTableModel(filtered);
		final var table = new JTable(tableModel);

		final var columnModel = new PolicyTableColumnModel<SharedFilesTableModel.Columns>(table);
		table.setColumnModel(columnModel);
		columnModel.configureColumn(fixedTextLength(SharedFilesTableModel.Columns.DATE, 16).with(new DateRenderer()));
		columnModel.configureColumn(fixedTextLength(SharedFilesTableModel.Columns.SIZE, 10).with(new MlSizeRenderer()));
		table.setAutoCreateColumnsFromModel(false);
		
		this.sharedController.getModel().getLastSelectedShare().bind(selection(table, tableModel));
		this.sharedController.getModel()
				.getSelectedShares()
				.bind(multipleSelection(table, tableModel))
				.addDependency(preserveOnUpdateOf(tableModel.getBaseModel()));
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getButton() == 1 && e.getClickCount() == 2) {
					gui.preview(tableModel.getObjectAtRow(table.rowAtPoint(e.getPoint())));
				}
			}
		});
		table.addMouseListener(
				new AbstractJTablePopup<>(table, tableModel, this.sharedController.getModel().getLastSelectedShare(),
						this.sharedController.getModel().getSelectedShares()) {

					@Override
					protected void buildPopup(final JPopupMenu popupMenu, final SharedFile clicked) {
						popupMenu.removeAll();
						
						final var dName = clicked.getName();
						final var name = new JLabel(dName.substring(0, Math.min(dName.length(), 20)));
						popupMenu.add(name);
						popupMenu.add(new JSeparator());
						
						final var preview = new JMenuItem("Preview");
						preview.addActionListener(SharedFilesPanel.this.sharedController.getPreviewFileAction());
						popupMenu.add(preview);
						popupMenu.add(new JSeparator());
						
						final var cancel = new JMenuItem("Cancel");
						cancel.addActionListener(SharedFilesPanel.this.sharedController.getCancelFileAction());
						
						final var blackList = new JMenuItem("Blacklist");
						blackList.addActionListener(SharedFilesPanel.this.sharedController.getBlackListFileAction());
						popupMenu.add(blackList);
						popupMenu.add(cancel);
					}
				});
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		sharedController.activate();
	}

	public SharedFilePanelController getController() {
		return sharedController;
	}

}
