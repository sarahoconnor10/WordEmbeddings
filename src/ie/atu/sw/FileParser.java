package ie.atu.sw;

import java.io.*;
import java.util.*;

public class FileParser {
	public static Map<String, double[]> parseFile(String fileName) {
		/*
		 * Method to read in data from file and return as Map<String, double[]> 
		 * O(n) ->	while loop (O(n)), for loop within while loop is O(n), as num of values to be parsed is fixed
		 */

		Map<String, double[]> map = new HashMap<>();
		String word;
		String line = null;

		try (var br = new BufferedReader(new FileReader(fileName))) {
			while ((line = br.readLine()) != null) {
				String[] allValues = line.split(",\\s");
				word = allValues[0].trim();
				double[] values = new double[50];

				for (int i = 0; i < values.length; i++) {
					values[i] = Double.parseDouble(allValues[i + 1].trim());
				} // for length of values[]

				map.put(word, values);
			} // while not end of file

			br.close();
			if (!map.isEmpty())
				System.out.println("Successfully parsed file.");

		} catch (Exception e) {
			System.out.println("Error parsing file: " + e.getMessage());
			System.out.println("Please try again.");
		}

		return map;
	}// parseFile()
}// class FileParser
