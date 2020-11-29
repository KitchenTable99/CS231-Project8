/**
 * A class designed to find the frequency of words across different files. Designed to be used statically.
 *
 * @author cbitting
 */

import java.util.ArrayList;

public class WordTrendsFinder {
	
	/**
	 * Prints out the usage statement.
	 */
	public static void usageStatement() {
		System.out.println("USAGE: java WordTrendsFinder prefix start end <words>");
		System.out.println("prefix --> prefix of files");
		System.out.println("start --> starting number");
		System.out.println("end --> ending number (the last number to be included)");
		System.out.println("<words> --> target words separated by spaces");
	}
	
	public static void main(String[] args) {
		// make sure enough command-line arguments
		if (args.length < 4) {
			WordTrendsFinder.usageStatement();
			return;
		}
		
		// basic set-up
		WordCounter2 counter = new WordCounter2("bst");
		ArrayList<ArrayList<Double>> wordLists = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < args.length - 5; i++) {
			wordLists.add(new ArrayList<Double>());
		}
		
		// run over every file
		for (int i = Integer.parseInt(args[1]); i < Integer.parseInt(args[2]) + 1; i++) {
			// build map
			ArrayList<String> words = WordCounter2.readWords(args[0] + i + ".txt");
			counter.buildMap(words);
			// for each desired word add frequency to list
			for (int j = 3; j < args.length - 2; j++) {
				ArrayList<Double> wordList = wordLists.get(j - 3);
				wordList.add(counter.getFrequency(args[j]));
			}
			// reset map
			counter.clearMap();
		}
		
		// print results
		for (int i = 3; i < args.length - 2; i++) {
			String toPrint = args[i];
			toPrint += " ";
			for (Double d : wordLists.get(i - 3)) {
				toPrint += d;
				toPrint += ",";
			}
			System.out.println(toPrint);
		}
	}

}
