package ie.atu.sw;

import java.io.*;

public class OutputWriter {
	public static void printToFile(String text, String output_file) {
		/*
		 * Method to print similarity results to specified (or default) file 
		 * O(1) -> 	no loops, only constant time operations (O(1))
		 */
		try (FileWriter fw = new FileWriter(new File(output_file), true)) {
			fw.write(text);
		} catch (Exception e) {
			System.out.println("Error writing to file: " + e.getMessage());
			return;
		}
		System.out.println("Successfully written to file. (" + output_file + ")");
	}// printToFile()
}// class OutputWriter
