/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

public class RefreshManager<T> {
	private final Set<T> dirty = new HashSet<>();
	private final Timer timer;
	private final long period;
	private final long refreshDelay;
	private final Consumer<T> onRefresh;

	public RefreshManager(final Timer timer, final long period, final long refreshDelay, final Consumer<T> onRefresh) {
		this.timer = timer;
		this.period = period;
		this.refreshDelay = refreshDelay;
		this.onRefresh = onRefresh;
	}

	public void start() {
		this.timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					final var set = RefreshManager.this.dirty;
					synchronized (set) {
						RefreshManager.this.dirty.clear();
					}
					RefreshManager.this.onRefresh.accept(null);
				} catch (final RuntimeException e) {
					e.printStackTrace();
				}
			}
		}, 1000, this.period);
	}

	public void force(final T resourceId) {
		synchronized (this.dirty) {
			this.dirty.add(resourceId);
		}
		this.timer.schedule(new TimerTask() {

			/*
			 * WARNING - Removed try catching itself - possible behaviour change.
			 */
			@Override
			public void run() {
				synchronized (RefreshManager.this.dirty) {
					if (!RefreshManager.this.dirty.contains(resourceId)) {
						return;
					}
					RefreshManager.this.dirty.remove(resourceId);
				}
				RefreshManager.this.onRefresh.accept(resourceId);
			}
		}, this.refreshDelay);
	}

}
