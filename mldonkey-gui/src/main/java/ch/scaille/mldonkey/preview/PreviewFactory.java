/*
 * Decompiled with CFR 0_122.
 */
package ch.scaille.mldonkey.preview;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ch.scaille.mldonkey.model.FileDownload;
import ch.scaille.util.helpers.JavaExt;

public class PreviewFactory {

	public static AbstractPreview create(final FileDownload download, final File tmp) {
		FileType typeByContent;
		String contentIdentification;
		if (download.getChunks().isEmpty()) {
			return null;
		}
		final boolean hasStart = download.getChunks().charAt(0) != '0';
		try {
			if (hasStart) {
				contentIdentification = PreviewFactory.getTypeIdentification(tmp);
				typeByContent = PreviewFactory.identifyTypeByContent(contentIdentification);
			} else {
				typeByContent = null;
				contentIdentification = null;
			}
		} catch (final IOException e) {
			return null;
		}
		if (typeByContent == null) {
			return new TailToMpvRunner(tmp, download, contentIdentification, download.getName());
		}
		return switch (typeByContent) {
		case DIVX -> new DivFixRunner(tmp, download.getName());
		case RAR -> new RarRunner(tmp, download.getName());
		case ZIP -> new ZipRunner(tmp, download.getName());
		default -> new MPlayerRunner(tmp, download.getName());
		};
	}

	private static String getTypeIdentification(final File tmp) throws IOException {
		final var pb = new ProcessBuilder("file", "-b", tmp.getAbsolutePath());
		pb.redirectErrorStream();
		final var process = pb.start();
		try (InputStream in= process.getInputStream()) {
			return JavaExt.readUTF8Stream(in);
		}
	}

	private static FileType identifyTypeByContent(final String line) {
		if (line == null || "data".equals(line)) {
			return null;
		}
		if (line.contains("video: XviD")) {
			return FileType.DIVX;
		}
		if (line.contains("AVI")) {
			return FileType.DIVX;
		}
		if (line.contains("Matroska data")) {
			return FileType.MKV;
		}
		if (line.contains("Apple QuickTime movie")) {
			return FileType.QT;
		}
		if (line.contains("Microsoft ASF")) {
			return FileType.WMV;
		}
		if (line.contains("RAR archive data")) {
			return FileType.RAR;
		}
		if (line.contains("MPEG")) {
			return FileType.MPG;
		}
		if (line.contains("Macromedia Flash Video")) {
			return FileType.FLV;
		}
		if (line.contains("RealMedia")) {
			return FileType.RM;
		}
		if (line.contains("Zip archive data")) {
			return FileType.ZIP;
		}
		return null;
	}

	public static AbstractPreview createPreview(final FileDownload download, final File tmp) {
		return new TailToMpvRunner(tmp, download, "Not tested", download.getName());
	}

	private enum FileType {
		DIVX, MKV, MPG, E3GP, WMV, QT, RAR, FLV, RM, ZIP;

		FileType() {
		}
	}

}
