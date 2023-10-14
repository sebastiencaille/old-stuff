/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileQuery;

public class MlSearch extends AbstractMlTypeContainer implements IMlType {
	private final MlInt32 searchNum = new MlInt32();
	private final MlString criteria = new MlString();
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

	public int getId() {
		return this.searchNum.value();
	}

	public void fill(final FileQuery query) {
		query.setSearchText(this.criteria.value());
	}
}
