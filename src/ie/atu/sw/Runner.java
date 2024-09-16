package ie.atu.sw;
import java.util.*;


public class Runner {

	public static void main(String[] args) throws Exception {
		/* 
		 * using worst case scenario -> 
		 * calling menu.run(), which is O(n log n) due to
		 * option 2 using SimilarityCalculator, which is O(n log n)
		 */
		Scanner keyboard = new Scanner(System.in);
		Menu menu = new Menu(keyboard);
		menu.run();
	}//main
}//class Runner