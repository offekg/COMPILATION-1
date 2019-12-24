package SYMBOL_TABLE;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputFileWriter {
	private static PrintWriter file_writer = null;
	
	public static PrintWriter getInstance(String outputFilename) throws FileNotFoundException {
		if (file_writer == null) {
			file_writer = new PrintWriter(outputFilename);
		}
		return file_writer;
	}

	public static void write(String s) {
		file_writer.println(s);
	}
	
	public static void close() {
		file_writer.close();
	}
}
