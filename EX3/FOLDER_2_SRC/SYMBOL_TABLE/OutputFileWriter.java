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
	
	public static void writeOk() {
		file_writer.println("OK");
		file_writer.close();
	}

	public static void writeError(int lineNumber, String errored_line) {
		file_writer.println(String.format("ERROR(%d)", lineNumber));
		System.out.println(">>Semantic Error at [" + lineNumber+ "]: " + errored_line);
		file_writer.close();
		System.exit(0);
	}
}
