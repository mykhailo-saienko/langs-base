package ms.utils;

import static javax.swing.JOptionPane.showConfirmDialog;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
		File dirFile = new File(dir);
		if (!dirFile.exists() && !dirFile.mkdir()) {
			throw new IOException(
					dirFile.getAbsolutePath() + " cannot create directory '" + dirFile.getAbsolutePath() + "'");
		}
		return dirFile.getAbsolutePath();
	}

	public static File[] listFiles(String dir, String... extensions) throws IOException {
		File lib = new File(dir);
		if (lib.exists() && lib.isDirectory()) {
			return lib.listFiles((f, s) -> hasExtension(s, extensions) && f.equals(lib));
		}
		throw new IOException(
				"Library folder " + lib.getAbsolutePath() + " does not exist or is not a directory. Ignoring...");

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
	 * Deletes file or directory, even if it is non-empty. Returns false only if
	 * the object is not found or can not be deleted due to lacking permissions.
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
		Files.walk(Paths.get(path)).map(Path::toFile).sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
	}

	public static File promptFile(String curDir, Container parent, int type, boolean promptIfExists, String filterDesc,
			String... filter) {
		List<String> filters = Arrays.asList(filter);
		if (filters.isEmpty()) {
			throw new IllegalArgumentException("At least one filter must be specified for the file chooser");
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
				int result = showConfirmDialog(parent, "This file exists. Are you sure you want to overwrite?", "LiveX",
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
		return loadFromFile(path, StandardCharsets.UTF_8.name());
	}

	/**
	 * Loads a file's contents into a string with a given encoding and returns
	 * it.
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
		File file = new File(path);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))) {
			// read and ignore the very first line
			if (ignoreHeader) {
				in.readLine();
			}
			String line = null;
			while ((line = in.readLine()) != null) {
				lineProcessor.accept(line);
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

			String lastLine = sb.reverse().toString();
			return lastLine;
		} finally {
			if (fileHandler != null)
				try {
					fileHandler.close();
				} catch (IOException e) {
					/* ignore */
				}
		}
	}
}
