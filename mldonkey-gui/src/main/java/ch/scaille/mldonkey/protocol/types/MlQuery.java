/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.protocol.types;

import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.protocol.Consts;

public class MlQuery extends AbstractMlTypeContainer implements IMlType {
	private final MlInt8 nodeType = new MlInt8((byte) -1);
	private final MlList subQueries = new MlList(MlQuery.class);
	private final MlRecursive<MlQuery> andNot1 = new MlRecursive<>(MlQuery.class);
	private final MlRecursive<MlQuery> andNot2 = new MlRecursive<>(MlQuery.class);
	private final MlString module = new MlString();
	private final MlRecursive<MlQuery> moduleQuery = new MlRecursive<>(MlQuery.class);
	private final MlString comment = new MlString();
	private final MlString defaultValue = new MlString();

	@Override
	protected boolean hasValue(final IMlType attribute) {
		final byte node = this.nodeType.value();
		if (attribute == this.subQueries) {
			return node == Consts.Q_AND || node == Consts.Q_OR || node == Consts.Q_HIDDEN;
		}
		if (attribute == this.andNot1 || attribute == this.andNot2) {
			return node == Consts.Q_ANDNOT;
		}
		if (attribute == this.module || attribute == this.moduleQuery) {
			return node == Consts.Q_MODULE;
		}
		if (attribute == this.comment || attribute == this.defaultValue) {
			return node > Consts.Q_MODULE && node < Consts.Q_HIDDEN;
		}
		return super.hasValue(attribute);
	}

	@Override
	protected IMlType[] createMessageContent() {
		return new IMlType[] { this.nodeType, this.subQueries, this.andNot1, this.andNot2, this.module,
				this.moduleQuery, this.comment, this.defaultValue };
	}

	@Override
	public String toString() {
		return Byte.toString(this.nodeType.value());
	}

	public void set(final FileQuery criteria) {
		final var searchText = criteria.getSearchText();
		final var parts = searchText.split(" -");
		if (parts.length == 1) {
			this.nodeType.set(Consts.Q_AND);
			this.subQueries.addValue(textQuery(parts[0]));
			this.subQueries.addValue(hidden());
		} else {
			this.nodeType.set(Consts.Q_ANDNOT);
			andNot1.set(textQuery(parts[0]));
			if (parts.length > 2) {
				final var subQuery = orQuery();
				for (var i = 1; i < parts.length; i++) {
					subQuery.subQueries.addValue(textQuery(parts[i]));
				}
				andNot2.set(subQuery);
			} else {
				andNot2.set(textQuery(parts[1]));
			}
		}
	}

	public static MlQuery orQuery() {
		final var query = new MlQuery();
		query.nodeType.set(Consts.Q_OR);
		return query;
	}

	public static MlQuery textQuery(final String content) {
		final var query = new MlQuery();
		query.nodeType.set(Consts.Q_KEYWORDS);
		query.defaultValue.set(content);
		return query;
	}

	public static MlQuery hidden() {
		final var query = new MlQuery();
		query.nodeType.set(Consts.Q_HIDDEN);
		return query;
	}
}
