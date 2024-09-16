package ie.atu.sw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SimilarityCalculator {
	public static void similaritySearch(String input_word, Map<String, double[]> words_map, int most_similar,
			int num_words, String outfile) {
		/* 
		 * O(n log n) ->	for loop (O(n)), TreeMap (O(log n)) 
		 */
		String result = null;
		double[] targetValues = words_map.get(input_word);

		if (targetValues == null) {
			System.out.println("\n\'" + input_word + "\' not found in embeddings . . .");
			return;
		} // if word not in list of embeddings

		Map<Double, String> similarityTracker = new TreeMap<>(Comparator.reverseOrder());

		for (Map.Entry<String, double[]> entry : words_map.entrySet()) {
			String currentWord = entry.getKey().trim().toLowerCase();
			if (!currentWord.equals(input_word)) {
				double[] currentValues = entry.getValue();

				double similarityScore = getScore(targetValues, currentValues);

				similarityTracker.put(similarityScore, currentWord);
			} // if current word != input word
		} // for each entry in map entryset
		//System.out.println(ConsoleColour.WHITE_BOLD);
		if (most_similar == 1) {
			System.out.println("\nTop " + num_words + " Similar Words to \'" + input_word + "\': \n");
			int counter = 0;
			result = "\'" + input_word + "\': \n";
			for (Map.Entry<Double, String> entry : similarityTracker.entrySet()) {
				if (counter < num_words) {
					System.out.printf("%s, Similarity Score: %.2f \n", entry.getValue(), entry.getKey());
					result += entry.getValue() + ", " + entry.getKey() + "\n";
					counter++;
				}
				else
					break;
			} // for each entry in similarityTracker map
		} // if finding most similar words
		else if (most_similar == 2) {
			List<Map.Entry<Double, String>> entryList = new ArrayList<>(similarityTracker.entrySet());

			int start = Math.max(0, entryList.size() - num_words);

			System.out.println("\n" + num_words + " Least Similar Words to \'" + input_word + "\': \n");
			int counter = 0;
			result = "\'" + input_word + "\': \n";

			for (int i = start; i < entryList.size(); i++) {
				Map.Entry<Double, String> entry = entryList.get(i);
				System.out.printf("%s, Similarity Score: %.2f \n", entry.getValue(), entry.getKey());
				result += entry.getValue() + ", " + entry.getKey() + "\n";
			} // for each entry in entryList
		}//else finding least similar words

		System.out.println();
		System.out.println("Printing result to file . . .");
		OutputWriter.printToFile(result, outfile);
		System.out.println();
	}// similaritySearch()

	private static double getScore(double[] target_values, double[] current_values) {
		/* Method allowing use of dot product and cosine similarity calculations to compare words 
		 * O(n) ->	n = length of target_values & current_values
		 */
		double score = 0.0;
		double dotProduct = 0.0;
		double m1 = 0.0;
		double m2 = 0.0;

		// first get dot product
		// then get cosine
		for (int i = 0; i < target_values.length; i++) {
			dotProduct += target_values[i] * current_values[i];
			m1 += Math.pow(target_values[i], 2);
			m2 += Math.pow(current_values[i], 2);
		}

		if (m1 == 0 || m2 == 0) { // avoid dividing by 0
			return 0.0;
		}
		score = dotProduct / (Math.sqrt(m1) * Math.sqrt(m2));
		return score;
	}// getScore()
}// class SimilarityCalculator
