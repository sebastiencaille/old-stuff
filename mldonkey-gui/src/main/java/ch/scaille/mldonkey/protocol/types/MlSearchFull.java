/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileQuery;

public class MlSearchFull extends AbstractMlTypeContainer implements IMlType {
	private final MlInt32 searchNum = new MlInt32();
	private final MlQuery criteria = new MlQuery();
	private final MlInt32 maxHits = new MlInt32();
	private final MlInt8 searchType = new MlInt8();
	private final MlInt32 networkId = new MlInt32();

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.searchNum, this.criteria, this.maxHits, this.searchType, this.networkId };
	}

	@Override
	public String toString() {
		return this.searchNum + ": " + this.criteria + " " + this.maxHits;
	}

	public void set(final FileQuery query) {
		this.searchNum.set(1);
		this.criteria.set(query);
		this.searchType.set(1);
		this.maxHits.set(1000);
		this.networkId.set(0);
	}
}
