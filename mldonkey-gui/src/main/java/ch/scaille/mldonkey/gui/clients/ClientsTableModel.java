/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.gui.clients;

import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.swing.model.ListModelTableModel;
import ch.scaille.mldonkey.GeoIp;
import ch.scaille.mldonkey.model.Client;

public class ClientsTableModel extends ListModelTableModel<Client, ClientsTableModel.Columns> {

	public ClientsTableModel(final ListModel<Client> model) {
		super(model, Columns.class);
	}

	@Override
	protected Object getValueAtColumn(final Client object, final Columns column) {
		return switch (column) {
		case NAME -> object.getName();
		case GEO -> GeoIp.getCounty(object.getGeoIp());
		case SOFTWARE -> "%s %s".formatted(object.getSoftware(), object.getRelease());
		case STATE -> object.getState();
		case FILES -> object.getFileCount();
        };
	}

	@Override
	protected void setValueAtColumn(final Client object, final Columns column, final Object value) {
		throw new UnsupportedOperationException("Read-only");
	}

	public enum Columns {
		NAME, GEO, FILES, SOFTWARE, STATE;

		Columns() {
		}
	}

}
