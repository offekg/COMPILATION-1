package UTILS;

import TEMP.*;

import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RA_optimization {
	static int tempsCount = TEMP_FACTORY.getCounter();
	static String inputFilePath = "./FOLDER_5_OUTPUT/MIPS.txt";
	static Pattern tempsRegex = Pattern.compile("Temp_(\\d+)");

	static String mipsFileContent = "";

	private static TempContext[] livenessMarking() throws IOException {
		TempContext tempContexts[] = new TempContext[tempsCount];
		BufferedReader mipsFileReader = new BufferedReader(new FileReader(inputFilePath));

		String lineContent;
		int lineNumber = 0;

		while ((lineContent = mipsFileReader.readLine()) != null) {
			mipsFileContent += lineContent + "\n";

			Matcher matchedRegex = tempsRegex.matcher(lineContent);

			while (matchedRegex.find()) {
				int index = Integer.parseInt(matchedRegex.group(1));

				if (tempContexts[index] != null) {
					tempContexts[index].end = lineNumber;
				} else {
					TempContext newTemp = new TempContext(index, lineNumber, lineNumber);
					tempContexts[index] = newTemp;
				}
			}
			
			lineNumber++;
		}

		return tempContexts;
	}

	private static void buildInterferenceGraph(GraphManager graph) throws IOException {
		TempContext tempContexts[] = livenessMarking();

		for (int i = 0; i < tempContexts.length; i++) {
			TempContext temp1 = tempContexts[i];

			for (int j = i + 1; j < tempContexts.length; j++) {
				TempContext temp2 = tempContexts[j];
				
				if (temp1.isTempsIntersecting(temp2)) {
					temp1.intersections.add(temp2.index);
					temp2.intersections.add(temp1.index);

					graph.addEdge(temp1.index, temp2.index);
				}
			}
		}
	}

	public static void optimize(String outputFilePath) throws IOException {
		GraphManager graph = new GraphManager(tempsCount);
		buildInterferenceGraph(graph);
		HashMap<Integer, Integer> colored = graph.greedyColoring();

		for (int i = tempsCount - 1; i >= 0; i--) {
			mipsFileContent = mipsFileContent.replaceAll("Temp_" + String.valueOf(i), "\\$t" + colored.get(i));
		}
		
		FileWriter outputWriter = new FileWriter(outputFilePath);
		outputWriter.write(mipsFileContent);
		outputWriter.close();

	}
}
