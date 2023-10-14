/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.console;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.scaille.gui.swing.bindings.JTextAreaBinding;
import ch.scaille.mldonkey.MLDonkeyGui;

public class ConsolePanel extends JPanel {
	public ConsolePanel(final MLDonkeyGui gui) {
		this.setLayout(new BorderLayout());
		
		final var controller = new ConsoleController();
		gui.addConsoleListener(line -> controller.getModel().addLine(line));
		
		final var buttons = new JPanel(new FlowLayout());
		
		final var clear = new JButton("Clear");
		clear.addActionListener(controller.getClearAction());
		buttons.add(clear);
		this.add(buttons, BorderLayout.NORTH);
		
		final var area = new JTextArea();
		controller.getModel().getConsole().bind(new JTextAreaBinding(area));
		this.add(new JScrollPane(area), BorderLayout.CENTER);
		controller.activate();
	}
}
