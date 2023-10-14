/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.HostState;

public class MlHostState extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 state = new MlInt8();
	private final MlInt32 rank = new MlInt32();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.state, this.rank };
	}

	@Override
	protected boolean hasValue(final IMlType type) {
		if (type == this.rank) {
			final byte iState = this.state.value();
			return iState == 3 || iState == 5 || iState == 9;
		}
		return super.hasValue(type);
	}

	public boolean isRemoved() {
		return this.getState() == HostState.REMOVED_HOST;
	}

	public HostState getState() {
		return HostState.values()[this.state.value()];
	}

	@Override
	public String toString() {
		return this.getState().toString();
	}
}
