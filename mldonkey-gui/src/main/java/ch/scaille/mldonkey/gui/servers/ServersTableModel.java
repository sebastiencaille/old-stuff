/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.servers;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.GeoIp;
import ch.scaille.mldonkey.model.Server;

public class ServersTableModel extends ListModelTableModel<Server, ServersTableModel.Columns> {
	public ServersTableModel(final ListModel<Server> model) {
		super(model, Columns.class);
	}

	@Override
	protected Object getValueAtColumn(final Server object, final Columns column) {
		return switch (column) {
		case NAME -> object.getName();
		case STATE -> object.getState();
		case GEO -> GeoIp.getCounty(object.getGeoIp());
		case SOFTWARE -> object.getVersion();
		case FILES -> object.getFileCount();
		case USERS -> object.getUserCount();
        };
	}

	@Override
	protected void setValueAtColumn(final Server object, final Columns column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	public enum Columns {
		NAME, GEO, STATE, SOFTWARE, FILES, USERS;

		Columns() {
		}
	}

}
