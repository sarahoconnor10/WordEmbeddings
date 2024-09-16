package ie.atu.sw;

import java.util.*;

public class Menu {
	private Scanner keyboard;
	Map<String, double[]> words = new HashMap<>();

	private int n = 5;
	private int optionsChoice = 0;
	private int mostSimilar = 1;
	private String outfile = null;
	private String defaultOutfile = "./out.txt";
	private String embedFile = null;
	private String defaultEmbedFile = "./word-embeddings.txt";
	private String inputString = null;

	public Menu(Scanner k) {
		keyboard = k;
	}// Menu constructor

	public void run() {
		/* 
		 * worst case scenario -> O(n log n), based on option 2 using SimilarityCalculator
		 */
		int choice = 0;
		displayIntro();
		do {
			displayMenu();
			choice = keyboard.nextInt();
			keyboard.nextLine();
			processChoice(choice);
		} while (choice != -1);
		System.out.println();
		System.out.println(ConsoleColour.RED_BRIGHT + "Quitting Similarity Search . . . \nGoodbye :)");
		System.out.println(ConsoleColour.WHITE);
	}// run()

	private void processChoice(int choice) {
		if (choice == 1) {
			/* 
			 * O(n) -> because FileParser is O(n)
			 */
			System.out.println();
			System.out.println("Please enter Word Embeddings File name, including Path (No Spaces): ");
			embedFile = keyboard.next();
			keyboard.nextLine();
			words = FileParser.parseFile(embedFile);
		} else if (choice == 2) {
			/* 
			 * O(1) -> constant time, just receiving input from user, no loops
			 */
			System.out.println();
			System.out.println("Please enter Output File name, including Path: ");
			outfile = keyboard.next();
			keyboard.nextLine();

			if (outfile.contains(".txt")) {
				System.out.println("Output file has been saved.");
			} else {
				System.out.println("Failed to save Output file. Please ensure path includes .txt, and try again.");
				outfile = null;
			}
		} else if (choice == 3) {
			/* 
			 * O(n log n) -> because SimilarityCalculator is O(n log n)
			 */
			System.out.println();
			if (embedFile == null) {
				System.out.println(
						"No word embedding file location has been specified. Attempting to use default file path (word-embeddings.txt) . . .");
				words = FileParser.parseFile(defaultEmbedFile);
			}
			if (outfile == null) {
				outfile = defaultOutfile;
			}
			if (words.isEmpty()) {
				System.out.println(
						"Unable to find Word Embeddings. Please choose Option 1 in the menu, and provide correct path.\n");
			} else {
				System.out.println("Would you like to find: ");
				System.out.println("(1) Most Similar Words");
				System.out.println("(2) Least Similar Words");
				mostSimilar = keyboard.nextInt();
				if (mostSimilar != 1 && mostSimilar != 2) {
					System.out.println("Invalid input. Finding Most Similar Words . . .");
					mostSimilar = 1;
				}
				keyboard.nextLine();
				System.out.println();
				System.out.println("Please input text you wish to compare: ");
				inputString = keyboard.nextLine().trim().toLowerCase();

				String[] inputWords = inputString.split("\\s+");

				for (String w : inputWords) {
					SimilarityCalculator.similaritySearch(w, words, mostSimilar, n, outfile);
				}
			} // else file has been parsed and map has been populated correctly
		} else if (choice == 4) {
			/* 
			 * O(n) -> mostly O(1), except for case 1, which uses FileParser (O(n))
			 */
			do {
				System.out.println();
				displayOptionsMenu();
				optionsChoice = keyboard.nextInt();
				keyboard.nextLine();
				System.out.println();
				switch (optionsChoice) {
				case 1:
					/* 
					 * O(n) -> Input O(1), and fileParser O(n)
					 */
					if (embedFile == null)
						System.out.println("Embedding File has not yet been specified.");
					else
						System.out.println("Current Embedding File: " + embedFile);

					System.out.println("Please enter new Word Embeddings File name, including Path (No Spaces): ");
					embedFile = keyboard.next();
					words = FileParser.parseFile(embedFile);
					break;
				case 2:
					/* 
					 * O(1) -> just receiving input from user - constant time operation
					 */
					String input;
					if (outfile == null)
						System.out.println("Output File has not yet been specified.");
					else
						System.out.println("Current Output File: " + outfile);

					System.out.println("Please enter new Output File name, including Path (No Spaces): ");
					input = keyboard.next();

					if (input.contains(".txt")) {
						outfile = input;
						System.out.println("Output file has been updated.");
					} else {
						System.out.println(
								"Failed to save Output file. Please ensure path includes .txt, and try again.");
					}
					break;
				case 3:
					/* 
					 * O(1) -> just receiving input from user - constant time operation
					 */
					System.out.println("Current number of words to output: " + n);
					System.out.println("Please input new number of words to output: ");
					n = keyboard.nextInt();
					System.out.println("Number of words to output has been updated.");
					break;
				case -1:
					/* 
					 * O(1) -> simply printing to screen
					 */
					System.out.println("Returning to main menu . . .");
					break;
				default:
					/* 
					 * O(1) -> simply printing to screen
					 */
					System.out.println("Invalid input . . .");
					break;
				}
			} while (optionsChoice != -1);
		} // choice == 4
		else if (choice > 4 || (choice <= 0 && choice != -1)) {
			System.out.println("Invalid input . . .");
		}
	}// processChoice()

	private void displayIntro() {
		/* 
		 * O(1) -> simply printing, no looping involved.
		 */
		System.out.println(ConsoleColour.WHITE_BRIGHT);
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("*                                                           *");
		System.out.println("*                    " + ConsoleColour.BLUE_BOLD + "S A R A H ' S"
				+ ConsoleColour.WHITE_BRIGHT + "                          *");
		System.out.println("*                   " + ConsoleColour.BLUE_BOLD_BRIGHT + " Similarity Search!"
				+ ConsoleColour.WHITE_BRIGHT + "                     *");
		System.out.println("*                                                           *");
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

	}// displayIntro()

	private void displayMenu() {
		/* 
		 * O(1) -> simply printing, no looping involved.
		 */
		System.out.println(ConsoleColour.WHITE_BRIGHT);
		System.out.print("* * * ");
		System.out.print(ConsoleColour.BLUE_BOLD_BRIGHT + "M E N U ");
		System.out.print(ConsoleColour.WHITE_BRIGHT + "* * * ");
		System.out.println(ConsoleColour.WHITE_BRIGHT);
		System.out.println("(1)  Specify Embedding File");
		System.out.println("(2)  Specify an Output File (default: ./out.txt)");
		System.out.println("(3)  Enter a Word or Text");
		System.out.println("(4)  Configure Options");
		System.out.println("(-1) Quit \n");

		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option: ");
		System.out.println();
	}// displayMenu()

	private void displayOptionsMenu() {
		/* 
		 * O(1) -> simply printing, no looping involved.
		 */
		System.out.print(ConsoleColour.WHITE_BRIGHT + "* * * ");
		System.out.print(ConsoleColour.BLUE_BOLD_BRIGHT + "OPTIONS ");
		System.out.print(ConsoleColour.WHITE_BRIGHT + "* * * \n");

		System.out.println("(1)  Edit Embedding File Path");
		System.out.println("(2)  Edit Output File Path");
		System.out.println("(3)  Specify Number of Similar Words to Output");
		System.out.println("(-1) Back \n");

		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option: ");
		System.out.println();
	}// displayOptionsMenu()

}// class Menu
