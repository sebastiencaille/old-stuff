package ch.scaille.mldonkey.model;

import java.io.IOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.scaille.mldonkey.MLDonkeyGui;
import ch.scaille.mldonkey.protocol.gui.SetOption;

public class ConfigInjection {

	private Integer natPort;

	public void setNatPort(final Integer natPort) {
		this.natPort = natPort;
	}

	public static ConfigInjection read(final Path file) throws IOException {
		return new ObjectMapper().readValue(file.toFile(), ConfigInjection.class);
	}

	public void inject(final MLDonkeyGui gui) {

		gui.sendOption(new SetOption("max_opened_connections", "200"));
		gui.sendOption(new SetOption("max_hard_download_rate", "400"));
		gui.sendOption(new SetOption("max_concurrent_downloads", "200"));
		gui.sendOption(new SetOption("ip_blocking_countries", "US CH"));
		gui.sendOption(new SetOption("ED2K-port", natPort.toString()));
		gui.sendOption(new SetOption("geoip_dat", "/home/scaille/dbip.dat.gz"));
	}

}
