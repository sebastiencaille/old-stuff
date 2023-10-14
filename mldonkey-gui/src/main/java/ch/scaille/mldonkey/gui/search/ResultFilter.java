/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.search;

import java.util.function.Predicate;

import ch.scaille.gui.model.views.AbstractDynamicView;
import ch.scaille.gui.mvc.IComponentBinding;
import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.model.WarningLevel;

public class ResultFilter extends AbstractDynamicView<FileQueryResult> implements Predicate<FileQueryResult> {
	private static final int SCAM_SIZE = 5939876;
	private boolean showWarning = false;
	private boolean showNotComplete = false;
	private boolean showScam = false;

	@Override
	public boolean test(final FileQueryResult value) {
		return value.getWarnings() != WarningLevel.BLACK_LISTED
				&& (value.getWarnings() != WarningLevel.WARN || this.showWarning)
				&& (value.completeSources() > 0 || this.showNotComplete)
				&& (value.getFileSize() != SCAM_SIZE || this.showScam);
	}

	public IComponentBinding<Boolean> showWarning() {
		return this.refreshWhenUpdated(v -> this.showWarning = v);
	}

	public IComponentBinding<Boolean> showNotComplete() {
		return this.refreshWhenUpdated(v -> this.showNotComplete = v);
	}

	public IComponentBinding<Boolean> showScam() {
		return this.refreshWhenUpdated(v -> this.showScam = v);
	}
}
