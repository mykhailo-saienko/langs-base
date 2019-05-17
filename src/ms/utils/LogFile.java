package ms.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.function.Function;

public class LogFile implements Closeable {
	public static final String UTF_8 = IOHelper.UTF_8;

	private BufferedWriter writer;
	// if true, the logger adds a new line before logging an item. This is used
	// to manage when new lines are added depending on whether a header is added
	private boolean addNewLine;

	public LogFile(String path, String encoding) throws IOException {
		this(path, encoding, false);
	}

	public LogFile(String path, String encoding, boolean append) throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(path, append); // re-write
			Writer out = new OutputStreamWriter(fos, encoding);
			writer = new BufferedWriter(out);
		} catch (IOException e) {
			close();
			throw e;
		}
		addNewLine = false;
	}

	public void line(String line) throws IOException {
		if (line == null) {
			return;
		}
		if (addNewLine) {
			writer.newLine();
		}

		writer.write(line);
		// after writing at least one line, separate next lines with a new line
		addNewLine = true;
	}

	@Override
	public void close() throws IOException {
		if (writer != null) {
			writer.close();
		}
	}

	public static <T> void log(String path, String encoding, Collection<T> items, Function<T, String> serializer,
			String header) throws IOException {
		if (items == null) {
			throw new IllegalArgumentException("Items cannot be null");
		}

		try (LogFile file = new LogFile(path, encoding)) {
			file.line(header);
			for (T item : items) {
				file.line(serializer.apply(item));
			}
		}
	}

	public static void save(String string, String path, String encoding) throws IOException {
		// re-write
		try (LogFile file = new LogFile(path, encoding)) {
			file.line(string);
		}
	}
}
