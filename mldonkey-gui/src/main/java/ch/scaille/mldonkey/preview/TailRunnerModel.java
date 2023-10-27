/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import ch.scaille.gui.mvc.GuiModel;
import ch.scaille.javabeans.properties.LongProperty;

public class TailRunnerModel extends GuiModel {
	protected final LongProperty pos = new LongProperty("Pos", this);

	public TailRunnerModel(final ModelConfiguration config) {
		super(config);
	}

	public LongProperty getPos() {
		return this.pos;
	}
}
