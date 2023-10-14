/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import static ch.scaille.gui.swing.factories.SwingBindings.selected;
import static ch.scaille.gui.swing.factories.SwingBindings.value;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.scaille.mldonkey.MLDonkeyGui;

public class SearchPanel extends JPanel {
	private final SearchQueryController controller;

	public SearchPanel(final MLDonkeyGui gui) {
		this.controller = new SearchQueryController(gui);
		this.setLayout(new BorderLayout());
		
		this.add(this.createQuerySearchPanel(), BorderLayout.NORTH);
		
		final var results = new SearchResultPane(gui, this.controller);
		this.add(results, BorderLayout.CENTER);
		this.controller.activate();
	}

	public SearchQueryController getController() {
		return this.controller;
	}

	private JPanel createQuerySearchPanel() {
		final var panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		final var searchPanelModel = this.controller.getSearchPanelModel();
		
		final var searchText = new JTextField();
		searchPanelModel.getSearchTextProperty().bind(value(searchText));
		searchText.addActionListener(this.controller.getPerformSearchAction());
		panel.add(searchText, BorderLayout.CENTER);
		
		final var buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		
		final var searchBtn = new JButton("Search");
		searchBtn.addActionListener(this.controller.getPerformSearchAction());
		buttons.add(searchBtn);
		
		final var moreBtn = new JButton("More");
		moreBtn.addActionListener(this.controller.getSearchMoreAction());
		buttons.add(moreBtn);
		
		final var warningsCB = new JCheckBox("Warnings");
		searchPanelModel.getShowWarnings().bind(selected(warningsCB));
		buttons.add(warningsCB);
		
		final var incompleteCB = new JCheckBox("Incomplete");
		searchPanelModel.getShowNotComplete().bind(selected(incompleteCB));
		buttons.add(incompleteCB);
		
		final var scamCB = new JCheckBox("Scam");
		searchPanelModel.getShowScam().bind(selected(scamCB));
		buttons.add(scamCB);
		
		panel.add(buttons, BorderLayout.EAST);
		return panel;
	}
}
