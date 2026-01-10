/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.console;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.ObjectProperty;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class ConsoleModel extends GuiModel {
	@Getter
    private final ObjectProperty<String> console = new ObjectProperty<>("Console", this, "");
	private int lineCount;

    public ConsoleModel(final ModelConfiguration.ModelConfigurationBuilder config) {
		super(config);
		this.lineCount = 0;
	}

	public void addLine(final String line) {
		++this.lineCount;
		var newText = this.console.getValue() + "\n" + line;
		if (this.lineCount > 110) {
			int next;
			for (var i = 0; i < 10 && (next = newText.indexOf(10)) > 0; ++i) {
				newText = newText.substring(0, next + 1);
			}
		}
		this.console.setValue(this, newText);
	}
}
