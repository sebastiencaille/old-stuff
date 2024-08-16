/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey;

import static ch.scaille.gui.model.IListModelListener.editionStopped;
import static ch.scaille.gui.model.IListModelListener.editionStopping;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.LogManager;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.scaille.gui.model.IListModelListener;
import ch.scaille.gui.model.ListEvent;
import ch.scaille.gui.model.ListModel;
import ch.scaille.gui.model.views.ListViews;
import ch.scaille.mldonkey.crypto.Crypto;
import ch.scaille.mldonkey.gui.MainFrame;
import ch.scaille.mldonkey.model.Client;
import ch.scaille.mldonkey.model.ConfigInjection;
import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.mldonkey.model.FileQuery;
import ch.scaille.mldonkey.model.FileQueryResult;
import ch.scaille.mldonkey.model.IBlackListData;
import ch.scaille.mldonkey.model.IWarnedData;
import ch.scaille.mldonkey.model.Option;
import ch.scaille.mldonkey.model.Server;
import ch.scaille.mldonkey.model.SharedFile;
import ch.scaille.mldonkey.model.Stats;
import ch.scaille.mldonkey.model.WarningLevel;
import ch.scaille.mldonkey.preview.MPlayerRunner;
import ch.scaille.mldonkey.preview.PreviewFactory;
import ch.scaille.mldonkey.protocol.gui.CloseSearch;
import ch.scaille.mldonkey.protocol.gui.ConnectMore;
import ch.scaille.mldonkey.protocol.gui.Download;
import ch.scaille.mldonkey.protocol.gui.ExtendedSearch;
import ch.scaille.mldonkey.protocol.gui.GetDownloadedFiles;
import ch.scaille.mldonkey.protocol.gui.GetDownloadingFiles;
import ch.scaille.mldonkey.protocol.gui.GetFileInfo;
import ch.scaille.mldonkey.protocol.gui.GetSearch;
import ch.scaille.mldonkey.protocol.gui.GetSearches;
import ch.scaille.mldonkey.protocol.gui.GuiPassword;
import ch.scaille.mldonkey.protocol.gui.KillServer;
import ch.scaille.mldonkey.protocol.gui.ProtocolVersion;
import ch.scaille.mldonkey.protocol.gui.RemoveDownload;
import ch.scaille.mldonkey.protocol.gui.RemoveServer;
import ch.scaille.mldonkey.protocol.gui.Reshare;
import ch.scaille.mldonkey.protocol.gui.SearchQuery;
import ch.scaille.mldonkey.protocol.gui.SetOption;
import ch.scaille.util.helpers.Logs;
import ch.scaille.util.helpers.TupleStream;

public class MLDonkeyGui {

	private static final GuiLogger LOGGER = new GuiLogger(MLDonkeyGui.class);

	public static final long CHUNK_SIZE = 9728000;

	private final Timer timer = new Timer();
	private final CoreConnection connection;
	private Stats stats = new Stats();
	private FileQuery currentQuery = null;
	private final ListModel<FileQuery> queries = new ListModel<>(ListViews.<FileQuery>sorted());
	private final ListModel<FileQueryResult> queryResults = new ListModel<>(ListViews.<FileQueryResult>sorted());
	private final ListModel<FileDownload> downloads = new ListModel<>(ListViews.<FileDownload>sorted());
	private final ListModel<Option> options = new ListModel<>(ListViews.<Option>sorted());
	private final ListModel<Client> clients = new ListModel<>(ListViews.<Client>sorted());
	private final ListModel<Server> servers = new ListModel<>(ListViews.<Server>sorted());
	private final ListModel<SharedFile> sharedFiles = new ListModel<>(ListViews.<SharedFile>sorted());
	private final Set<String> downloadedIdentifiers = new HashSet<>();
	private final WarnListManager warningManager;
	private final RefreshManager<Integer> downloadsRefresh = new RefreshManager<>(this.timer, 10000, 5000,
			this::refreshDownloads);
	private final Set<File> shared = new HashSet<>();
	private final RefreshManager<Integer> sharedFileRefresh = new RefreshManager<>(this.timer, 10000, 1000,
			id -> this.refreshSharedFiles());
	private final File temp;
	private final List<Process> previews = new ArrayList<>();
	private final BlackListManager blackListManager;
	private final File incoming;
    private Consumer<String> console;
	private JFrame mainFrame;
	private int lastQueryId = 0;

	public MLDonkeyGui() {
		final var home = new File(System.getProperty("user.home"));
		final var mldonkeyDir = new File(home, ".mldonkey");
		final var crypto = new Crypto(mldonkeyDir);
		mldonkeyDir.mkdirs();
		this.temp = new File(home, ".mldonkey/temp");
		this.incoming = new File(home, ".mldonkey/incoming/files");
		this.warningManager = new WarnListManager(new File(mldonkeyDir, "warnlist.txt"));
		this.warningManager.load();
		this.blackListManager = new BlackListManager(new File(mldonkeyDir, "blacklist.txt"), crypto, this.timer);
		this.blackListManager.load();
		this.connection = new CoreConnection(this);
		this.downloadsRefresh.start();
		this.queries.addListener(new IListModelListener<>() {

			@Override
			public void valuesAdded(final ListEvent<FileQuery> event) {
				for (final FileQuery query : event.getObjects()) {
					MLDonkeyGui.this.lastQueryId = Math.max(query.getId(), MLDonkeyGui.this.lastQueryId);
					if (query.getId() == MLDonkeyGui.this.lastQueryId) {
						MLDonkeyGui.this.currentQuery = query;
					}
					query.attachTo(MLDonkeyGui.this.queryResults);
					MLDonkeyGui.this.connection.sendMessage(new GetSearch(query.getId()));
				}
			}

			@Override
			public void valuesRemoved(final ListEvent<FileQuery> event) {
				event.getObjects().forEach(FileQuery::dispose);
			}
		});
		this.queryResults
				.addListener(editionStopping(event -> event.getObjects().forEach(MLDonkeyGui.this::checkResult)));

		this.clients.addListener(new IListModelListener<>() {

			@Override
			public void editionStopping(final ListEvent<Client> event) {
				event.getObjects()
						.stream()
						.flatMap(client -> client.getDownloads().stream())
						.forEach(FileDownload::updateAvailability);
			}

			@Override
			public void valuesRemoved(final ListEvent<Client> event) {
				event.getObjects().stream().collect(TupleStream.streamOfList(Client::getDownloads)).//
						forEach((client, download) -> MLDonkeyGui.this.downloads.editValue(download,
								d -> d.removeAvailability(client)));
			}
		});
		this.downloads.addListener(editionStopped(event -> event.getObjects()
				.stream()
				.filter(download -> download.getWarnings() == null
						&& MLDonkeyGui.this.warningManager.matches(download.getNames()))
				.forEach(this::setWarning)));
		this.sharedFileRefresh.start();

	}

	protected void setWarning(final FileDownload download) {
		EventQueue.invokeLater(() -> downloads.editValue(download, d -> d.setWarnings(WarningLevel.WARN)));
	}

	protected void show() {
		EventQueue.invokeLater(() -> {
			this.mainFrame = new MainFrame(this);
			this.mainFrame.setVisible(true);
		});
	}

	public void connect() {
		new Thread(this::performConnection).start();
	}

	@SuppressWarnings("java:S2189")
	private void performConnection() {
		while (true) {
			var connected = this.connection.connect();
			if (connected) {
				try {
					ConfigInjection.read(Paths.get(System.getProperty("user.dir"), ".donkeygui")).inject(this);
				} catch (final IOException e) {
					System.err.println("Cannot inject values");
					e.printStackTrace();
				}
				this.connection.read();
			}
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				Thread.interrupted();
				LOGGER.log("Interrupted");
				return;
			}
		}
	}

	public void notifyDisconnected() {
		LOGGER.log("Disconnected");
	}

	public void start() {
		this.show();
		this.connect();
	}

	public static void main(final String[] args) throws IOException {
		LogManager.getLogManager().readConfiguration();
		new MLDonkeyGui().start();
	}

	public void setOptions(final Map<String, String> optionsMap) {
		optionsMap.forEach((name, value) -> this.options.insert(new Option(name, value)));
	}

	public void handShake() {
		try {
			this.queries.clear();
			this.downloads.clear();
			this.clients.clear();
			this.servers.clear();
			this.options.clear();
			this.connection.sendMessage(new ProtocolVersion(41));
			this.connection.sendMessage(new GuiPassword());
			EventQueue.invokeLater(() -> MLDonkeyGui.this.connection.sendMessage(new GetSearches()));
		} catch (final Exception e) {
			LOGGER.log("Handshake failed:" + e, e);
		}
	}

	public void statsUpdated(final Stats newStats) {
		if (this.stats.getDownloadCount() != newStats.getDownloadCount()) {
			this.updateDownloadedFiles();
		}
		this.stats = newStats;
	}

	private void updateDownloadedFiles() {
		this.connection.sendMessage(new GetDownloadingFiles());
		this.connection.sendMessage(new GetDownloadedFiles());
	}

	private void updateDownloadedFile(final Integer donkeyId) {
		this.connection.sendMessage(new GetFileInfo(donkeyId));
	}

	private void checkDownload(final FileDownload download) {
		if (this.mustWarn(download)) {
			download.setWarnings(WarningLevel.WARN);
		}
		if (this.mustMarkAsBlackListed(download)) {
			download.setWarnings(WarningLevel.BLACK_LISTED);
		}
	}

	public void cancelDownload(final FileDownload download) {
		this.connection.sendMessage(new RemoveDownload(download.getId()));
	}

	public ListModel<FileDownload> getDownloads() {
		return this.downloads;
	}

	public ListModel<Option> getOptions() {
		return this.options;
	}

	public ListModel<FileQueryResult> getQueryResults() {
		return this.queryResults;
	}

	public ListModel<Client> getClients() {
		return this.clients;
	}

	public ListModel<Server> getServers() {
		return this.servers;
	}

	public ListModel<SharedFile> getSharedFiles() {
		return this.sharedFiles;
	}

	public void preview(final SharedFile value) {
		this.preview(new File(this.incoming, value.getName()));
	}

	public void preview(final FileDownload download, final long offset) {
		if (download == null) {
			return;
		}
		if (download.getDownloadedSize() == 0) {
			return;
		}
		this.getTempFile(download).ifPresent(tempFile -> {
			if (offset >= 0) {
				PreviewFactory.createPreview(download, tempFile).preview(this.mainFrame, offset);
			} else {
				PreviewFactory.create(download, tempFile).preview(this.mainFrame, -1);
			}
		});
	}

	public void view(final FileDownload download) {
		if (download.getDownloadedSize() == 0) {
			return;
		}
		this.getTempFile(download).ifPresent(f -> new MPlayerRunner(f, download.getName()).preview(this.mainFrame, 0));
	}

	private void preview(final File file) {
		final var processBuilder = new ProcessBuilder("smplayer", file.getAbsolutePath());
		try {
			final var process = processBuilder.start();
			this.previews.add(process);
			new StreamReader(process::getInputStream, false).start();
			new StreamReader(process::getErrorStream, true).start();
		} catch (final IOException e) {
			LOGGER.log(e);
		}
	}

	private Optional<File> getTempFile(final FileDownload download) {
		if (download.getIdentifiers().isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new File(this.temp, download.getIdentifiers().getFirst().replace(':', '_')));
	}

	public void killPreview() {
		for (final Process process : this.previews) {
			process.destroy();
		}
		this.previews.clear();
	}

	public FileQuery createFileQuery() {
		return new FileQuery(-1);
	}

	public void executeQuery(final FileQuery query) {
		if (this.currentQuery != null) {
			this.connection.sendMessage(new CloseSearch(this.currentQuery, false));
		}
		query.setId(0);
		this.connection.sendMessage(new SearchQuery(query));
		this.connection.sendMessage(new GetSearches());
	}

	public void closeQuery(final FileQuery object) {
		this.connection.sendMessage(new CloseSearch(object, true));
		this.queries.remove(object);
		if (object == this.currentQuery) {
			this.currentQuery = null;
		}
	}

	public ListModel<FileQuery> getQueries() {
		return this.queries;
	}

	private void checkResult(final FileQueryResult result) {
		if (this.mustMarkAsBlackListed(result)) {
			result.setWarnings(WarningLevel.BLACK_LISTED);
		} else if (this.isDownloaded(result)) {
			result.setWarnings(WarningLevel.ACK);
		} else if (this.mustWarn(result)) {
			result.setWarnings(WarningLevel.WARN);
		}
	}

	protected void refreshDownloads(final Integer id) {
		if (id == null) {
			this.updateDownloadedFiles();
		} else {
			this.updateDownloadedFile(id);
		}
		EventQueue.invokeLater(this::refreshDownloads);
	}

	private void refreshDownloads() {
		for (final var download : this.downloads) {
			var file = this.getTempFile(download);
			if (download.getChunks().isEmpty() || download.getChunks().charAt(0) == '0' || download.isHasFirstByte()
					|| file.filter(File::exists).isEmpty()) {
				continue;
			}

			// Check if first bytes available
			try (var in = new FileInputStream(file.orElseThrow())) {
				final var buffer = new byte[10];
				final int len = in.read(buffer);
				for (int i = 0; i < len; i++) {
					if (buffer[i] != 0) {
						this.getDownloads().editValue(download, d -> d.setHasFirstByte(true));
						break;
					}
				}
			} catch (final Exception e) {
				LOGGER.log(e);
			}
		}

	}

	public void refreshDownload(final int downloadId) {
		this.downloadsRefresh.force(downloadId);
	}

	public void download(final FileQueryResult result) {
		this.queryResults.editValue(result, r -> r.setWarnings(WarningLevel.ACK));
		this.connection.sendMessage(new Download(result));
	}

	private boolean mustWarn(final IWarnedData data) {
		if (data.getWarnings() != null) {
			return false;
		}
		return data.getWarnData().stream().anyMatch(warningManager::match);
	}

	private boolean mustMarkAsBlackListed(final IBlackListData data) {
		return this.blackListManager.match(data.getBlackListData(), data.getBlackListSize());
	}

	public boolean isDownloaded(final FileQueryResult value) {
		return value.isDownloaded() || value.getFileIdentifiers().stream().anyMatch(downloadedIdentifiers::contains);
	}

	public void searchMore() {
		this.connection.sendMessage(new ExtendedSearch());
	}

	private void blackList(final Collection<String> links, final long size) {
		this.blackListManager.addLinks(links, size);
		this.recheckData();
	}

	public void blackList(final FileDownload value) {
		blackList(value.getBlackListData(), value.getBlackListSize());
	}

	public void blackList(final FileQueryResult result) {
		blackList(result.getFileIdentifiers(), result.getFileSize());
	}

	public void blackList(final SharedFile sharedFile) {
		blackList(Collections.singleton(sharedFile.getIdentifier()), sharedFile.getSize());
	}

	public void recheckData() {
		for (final var queryResult : this.queryResults) {
			if (this.mustMarkAsBlackListed(queryResult) == (queryResult.getWarnings() == WarningLevel.BLACK_LISTED)) {
				continue;
			}
			this.queryResults.editValue(queryResult, this::checkResult);
		}
		for (final var download : this.downloads) {
			if (download.getChunks().isEmpty()
					|| this.mustMarkAsBlackListed(download) == (download.getWarnings() == WarningLevel.BLACK_LISTED)) {
				continue;
			}
			this.downloads.editValue(download, this::checkDownload);
		}
	}

	public void saveBlackList() {
		try {
			this.blackListManager.save();
		} catch (final IOException e) {
			LOGGER.log(e);
		}
	}

	public void disconnectServer(final Server value) {
		this.connection.sendMessage(new RemoveServer(value.getId()));
	}

	public void killCore() {
		this.connection.sendMessage(new KillServer());
	}

	public Optional<File> getFile(final SharedFile value) {
		if (value.getName() == null) {
			return Optional.empty();
		}
		final var file = new File(this.incoming, value.getName());
		if (!file.exists()) {
			return Optional.empty();
		}
		return Optional.of(file);
	}

	public void test() {
		this.connection.sendMessage(new Reshare());
	}

	public void connectMoreServers() {
		this.connection.sendMessage(new ConnectMore());
	}

	public void deleteSharedFile(final SharedFile file) {
		final var sharedFile = new File(this.incoming, file.getName());
		try {
			Files.delete(sharedFile.toPath());
		} catch (final IOException e) {
			LOGGER.log(e);
		}
		this.sharedFileRefresh.force(file.getId());
	}

	public void undoBlackList() {
		this.blackListManager.clearUnsaved();
		this.recheckData();
	}

	public void addConsoleListener(final Consumer<String> listener) {
		this.console = listener;
	}

	public void logConsole(final String str) {
		if (this.console != null) {
			this.console.accept(str);
		} else {
			LOGGER.log(str);
		}
	}

	protected void refreshSharedFiles() {
		if (!this.incoming.isDirectory()) {
			return;
		}
		final var current = Set.of(this.incoming.listFiles());
		final var added = new HashSet<>(current);
		final var removed = new HashSet<>(this.shared);
		removed.removeAll(current);
		added.removeAll(this.shared);
		final boolean reshare = !removed.isEmpty();
		this.shared.clear();
		this.shared.addAll(current);
		if (reshare) {
			this.connection.sendMessage(new Reshare());
		}
		SwingUtilities.invokeLater(this::updateSharedFiles);
	}

	private void updateSharedFiles() {
		for (final var sharedFile : this.sharedFiles) {
			final var localFile = this.getFile(sharedFile);

			if (localFile.isPresent() && sharedFile.getTimestamp() == 0) {
				this.sharedFiles.editValue(sharedFile, f -> f.setTimestamp(localFile.get().lastModified()));

			} else if (localFile.isEmpty() && sharedFile.getTimestamp() > 0) {
				this.sharedFiles.remove(sharedFile);
			}
		}
	}

	private static class StreamReader extends Thread {
		private final Supplier<InputStream> stream;
		private final boolean show;

		public StreamReader(final Supplier<InputStream> stream, final boolean show) {
			this.stream = stream;
			this.show = show;
		}

		@Override
		public void run() {
			try (var in = this.stream.get()) {
				if (show) {
					Logs.of(MLDonkeyGui.class).info(new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n")));
				} else {
					in.transferTo(OutputStream.nullOutputStream());
				}
			} catch (final IOException e) {
				// ignore
			}
		}
	}

	public void sendOption(final SetOption setOption) {
		connection.sendMessage(setOption);
	}

}
