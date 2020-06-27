package ms.utils;

import static javax.swing.JOptionPane.showConfirmDialog;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IOHelper {
	public static final String UTF_8 = "UTF-8";

	public static String LINE_SEPARATOR;

	static {
		try {
			LINE_SEPARATOR = System.getProperty("line.separator");
		} catch (Exception lse) {
			LINE_SEPARATOR = "\n";
		}
	}

	/**
	 * 
	 * @param dir
	 * @return
	 */
	public static String getDirPath(String dir) throws IOException {
		return getDirPath(dir, false);
	}

	public static String getDirPath(String dir, boolean force) throws IOException {
		File dirFile = new File(dir).getAbsoluteFile();
		if (force && !dirFile.exists()) {
			getDirPath(dirFile.getParent(), true);
		}
		if (!dirFile.exists() && !dirFile.mkdir()) {
			throw new IOException("Cannot create directory '" + dirFile.getPath() + "'");
		}
		return dirFile.getPath();
	}

	public static void ensureDirExists(String dir) throws IOException {
		File file = new File(dir).getAbsoluteFile();
		if (file.exists() && !file.isDirectory()) {
			throw new IOException("Path '" + dir + "' is a file, not a directory");
		} else if (!file.exists()) {
			getDirPath(dir, true);
		}
	}

	public static String getParentDir(String file) {
		return new File(file).getAbsoluteFile().getParent();
	}

	public static boolean isEmpty(String path) {
		File f = new File(path);
		// non-existent files are deemed empty
		if (!f.exists()) {
			return true;
		} else {
			// An empty UTF-8 file may still contains 2 or 3 bytes.
			// As our files normally contain more than 3 bytes per line, this is safe in our
			// case.
			// https://stackoverflow.com/questions/7190618/most-efficient-way-to-check-if-a-file-is-empty-in-java-on-windows
			// https://stackoverflow.com/questions/18516343/why-empty-text-file-contains-3-bytes
			return f.length() <= 3;
		}
	}

	public static File[] listFiles(String dir, String... extensions) throws IOException {
		File lib = new File(dir);
		if (lib.exists() && lib.isDirectory()) {
			return lib.listFiles((f, s) -> hasExtension(s, extensions) && f.equals(lib));
		}
		throw new IOException("Library folder " + lib.getAbsolutePath()
				+ " does not exist or is not a directory. Ignoring...");

	}

	public static String getFilestem(String path) {
		File file = new File(path);
		String filename = file.getName();
		int dotIndex = filename.lastIndexOf('.');
		return dotIndex == -1 ? filename : filename.substring(0, dotIndex);
	}

	public static String getExtension(String path) {
		File file = new File(path);
		String filename = file.getName();
		int dotIndex = filename.lastIndexOf('.');
		return dotIndex == -1 ? "" : filename.substring(dotIndex + 1);
	}

	public static boolean hasExtension(String path, String... extensions) {
		for (String s : extensions) {
			if (path.endsWith(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes file or directory, even if it is non-empty. Returns false only if the
	 * object is not found or can not be deleted due to lacking permissions.
	 * 
	 * @param path
	 * @return
	 */
	public static boolean forceDelete(File file) {
		if (!file.exists()) {
			return false;
		}

		if (file.isFile()) {
			return file.delete();
		}

		File[] files = file.listFiles();
		boolean success = true;
		for (File subfile : files) {
			success &= forceDelete(subfile);
		}
		// delete the directory (which is now empty)
		success &= file.delete();
		return success;
	}

	public static void deleteAll(String path) throws IOException {
		Files.walk(Paths.get(path)).map(Path::toFile).sorted((o1, o2) -> -o1.compareTo(o2))
				.forEach(File::delete);
	}

	public static File promptFile(String curDir, Container parent, int type, boolean promptIfExists,
			String filterDesc, String... filter) {
		List<String> filters = Arrays.asList(filter);
		if (filters.isEmpty()) {
			throw new IllegalArgumentException(
					"At least one filter must be specified for the file chooser");
		}
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(new FileNameExtensionFilter(filterDesc, filter));
		fc.setFileFilter(fc.getChoosableFileFilters()[1]);

		File f = new File(curDir);
		if (f.exists()) {
			fc.setCurrentDirectory(f);
		} else {
			fc.setCurrentDirectory(new File("."));
		}

		fc.setDialogType(type);
		if (fc.showDialog(parent, null) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String path = file.getPath();
			if (filters.size() == 1 && path != null && !path.endsWith("." + filters.get(0))) {
				file = new File(path + "." + filters.get(0));
			}
			if (file.exists() && promptIfExists) {
				int result = showConfirmDialog(parent,
						"This file exists. Are you sure you want to overwrite?", "LiveX",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.NO_OPTION) {
					file = null;
				}
			}
			return file;
		} else {
			return null;
		}
	}

	public static String loadFromFile(String path) throws IOException {
		return loadFromFile(path, UTF_8);
	}

	/**
	 * Loads a file's contents into a string with a given encoding and returns it.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String loadFromFile(String path, String encoding) throws IOException {
		File file = new File(path);
		byte[] data = new byte[(int) file.length()];

		try (FileInputStream stream = new FileInputStream(file)) {
			// We don't use Readers in order to avoid a line-by-line reading
			stream.read(data);
		}

		return new String(data, encoding);
	}

	public static void processFromFile(String path, String encoding, boolean ignoreHeader,
			Consumer<String> lineProcessor) throws IOException {
		// if ignore header -> the first line will be read and ignored
		// otherwise -> don't skip the first line (i.e., set header proc to null)
		processFromFile(path, encoding, lineProcessor, ignoreHeader ? s -> {
		} : null);
	}

	/**
	 * Closes the stream passed!
	 * 
	 * @param stream
	 * @param encoding
	 * @param lineProcessor
	 * @param headerProcessor
	 * @throws IOException
	 */
	public static void processFromStream(InputStream stream, String encoding,
			Consumer<String> lineProcessor, Consumer<String> headerProcessor) throws IOException {

		try (BufferedReader in = new BufferedReader(new InputStreamReader(stream, encoding))) {
			if (headerProcessor != null) {
				headerProcessor.accept(in.readLine()); // the first line is treated as header
			}
			String line = null;
			while ((line = in.readLine()) != null) {
				lineProcessor.accept(line);
			}
		}
	}

	public static void processFromFile(String path, String encoding, Consumer<String> lineProcessor,
			Consumer<String> headerProcessor) throws IOException {
		File file = new File(path);
		FileInputStream fileStream = new FileInputStream(file);
		processFromStream(fileStream, encoding, lineProcessor, headerProcessor);
	}

	public static enum ZipAction {
		USE, // Use current entry
		SKIP, // Skip current entry but keep scanning the remaining ones
		END // Don't use current entry and don't scan the remaining ones
	}

	public static void processFromZip(String zipPath, String encoding,
			Function<String, ZipAction> decider, Consumer<String> lineProcessor,
			Consumer<String> headerProcessor) throws IOException {
		// NOTE: The implementation using FileSystem, although much more convenient to
		// write, is MUCH slower! We have decided to keep this one
		// NOTE: This implementation is ~2.5 times slower than processing files directly
		try (ZipFile zipFile = new ZipFile(zipPath)) {
			final Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry ze = entries.nextElement();
				ZipAction a = decider.apply(ze.getName());
				if (a == ZipAction.USE) {
					InputStream zinput = zipFile.getInputStream(ze);
					processFromStream(zinput, encoding, lineProcessor, headerProcessor);
				} else if (a == ZipAction.END) {
					break;
				}
			}
		}
	}

	public static void processFromZip(String zipPath, String encoding, Predicate<String> useDecider,
			boolean returnOnSkip, Consumer<String> lineProcessor, Consumer<String> headerProcessor)
			throws IOException {
		Function<String, ZipAction> d = s -> useDecider.test(s) ? ZipAction.USE
				: returnOnSkip ? ZipAction.END : ZipAction.SKIP;
		processFromZip(zipPath, encoding, d, lineProcessor, headerProcessor);
	}

	public static void addFilesToZip(Iterable<String> sourceFiles,
			Function<String, String> pathInZip, String encoding, String targetZip)
			throws IOException {
		Map<String, String> env = Map.of("create", "true", "encoding", encoding);
		Path zipfile = Paths.get(targetZip);
		URI uri = URI.create("jar:" + zipfile.toUri());
		try (FileSystem fs = FileSystems.newFileSystem(uri, env, null)) {
			for (String source : sourceFiles) {
				Path externalTxtFile = Paths.get(source);
				Path pathInZipfile = fs.getPath(pathInZip.apply(source));
				// make sure parent directory exists
				if (Files.notExists(pathInZipfile.getParent())) {
					Files.createDirectories(pathInZipfile.getParent());
				}

				// copy a file into the zip file
				Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	public static URI toUri(String path) {
		try {
			return new URI(null, null, path, null);
		} catch (URISyntaxException e) {
			throw new RuntimeException("exception parsing uri", e);
		}
	}

	public static String tail(File file) throws IOException {
		RandomAccessFile fileHandler = null;
		try {
			fileHandler = new RandomAccessFile(file, "r");
			long fileLength = fileHandler.length() - 1;
			StringBuilder sb = new StringBuilder();

			for (long filePointer = fileLength; filePointer != -1; filePointer--) {
				fileHandler.seek(filePointer);
				int readByte = fileHandler.readByte();

				if (readByte == 0xA) {
					if (filePointer == fileLength) {
						continue;
					}
					break;

				} else if (readByte == 0xD) {
					if (filePointer == fileLength - 1) {
						continue;
					}
					break;
				}

				sb.append((char) readByte);
			}

			return sb.reverse().toString();
		} finally {
			if (fileHandler != null)
				try {
					fileHandler.close();
				} catch (IOException e) {
					/* ignore */
				}
		}
	}

	public static boolean fileExists(String fileName) {
		return new File(fileName).isFile();
	}

	public static String currentDir() {
		return Paths.get("").toAbsolutePath().toString();
	}
}
