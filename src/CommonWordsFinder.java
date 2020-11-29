/**
 * The CommonWordsFinder class. Designed for static use.
 *
 * @author cbitting
 */

import java.util.ArrayList;

public class CommonWordsFinder {
	
	/**
	 * prints out the usage statement in order to properly use this class
	 */
	private static void usageStatement() {
		System.out.println("USAGE: java CommonWordsFinder <N> <WC file 1> <...> ");
		System.out.println("N: the number of words to report out");
		System.out.println("WC file {1, 2 ...} is the name of the file to analyze.");
	}
	
	public static void main(String[] args) {
		// parse command-line arguments
		int numWords;
		if (args.length < 2) {
			CommonWordsFinder.usageStatement();
			return;
		}
		try {
			numWords = Integer.parseInt(args[0]);
		} catch (Exception e) {
			CommonWordsFinder.usageStatement();
			return;
		}
		WordCounter2 counter = new WordCounter2("bst");
		// for each provided document
		for (int i = 1; i < args.length - 2; i++) {
			// read file
			ArrayList<String> words = WordCounter2.readWords(args[i]);
			counter.buildMap(words);
			
			// put into heap
			ArrayList<KeyValuePair<String, Integer>> maps = counter.getMap().entrySet();
			PQHeap<KeyValuePair<String, Integer>> heap = new PQHeap<KeyValuePair<String, Integer>>(250000);
			for (KeyValuePair<String, Integer> kvp : maps) {
				heap.add(kvp);
			}
			
			// get the top ten as frequencies
			ArrayList<KeyValuePair<String, Double>> top = new ArrayList<KeyValuePair<String, Double>>();
			double totalWords = counter.totalWordCount();
			for (int j = 0; j < numWords; j++) {
				KeyValuePair<String, Integer> foo = heap.remove();
				double freq = (double) foo.getValue()/totalWords;
				top.add(new KeyValuePair<String, Double>(foo.getKey(), freq));
			}
			
			// print
			System.out.println("For the file: " + args[i]);
			for (int j = 0; j < numWords; j++) {
				System.out.println("Word #" + j + ": " + top.get(j));
			}
			
			// reset for next file
			System.out.println();
			counter.clearMap();
	
		}
		
	}
	
}
