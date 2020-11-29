import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  The class to actually count words. Made for Project07
 *
 * @author cbitting
 */

public class WordCounter2 {
	
	MapSet<String, Integer> map;
	
	public WordCounter2(String structure) {
		map = new BSTMap<String, Integer>();
	}
	
	public MapSet<String, Integer> getMap() {
		return map;
	}
	
	/**
	 * @param input is a string to strip character from
	 * @param strip is a string of characters to strip
	 * @return input stripped of characters in strip
	 */
	private static String stripChars(String input, String strip) {
	    StringBuilder result = new StringBuilder();
	    for (char c : input.toCharArray()) {
	        if (strip.indexOf(c) == -1) {
	            result.append(c);
	        }
	    }
	    return result.toString();
	}
	
	/**
	 * @param word to check against the 20 most popular words
	 * @return boolean representing if the word passed is among the most popular or not
	 */
	private static boolean noNoWord(String word) {
		boolean toReturn = false;
		String[] noNoWords = new String[] {"the", "be", "to", "of", "and", "a", "in", "that", "have", "i", "it", "for", "not", "on", "with", "he", "as", "you", "do", "at"};
		for (String s : noNoWords) {
			if (s.equals(word)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	/**
	 * @param filename to read
	 * @return an ArrayList of all of the words
	 */
	public static ArrayList<String> readWords(String filename) {
		ArrayList<String> toReturn = new ArrayList<String>();
		try {
			// set up objects
	    	FileReader reader = new FileReader(filename);
	    	BufferedReader bufferedReader = new BufferedReader(reader);

	    	// go through each line
	    	String line = bufferedReader.readLine();
	    	while (line != null) {
	    		// split by any non letter character
	    		String[] words = line.split("[^a-zA-Z0-9']");
	    		// remove spaces
	    	    for (int i = 0; i < words.length; i++) {
	    	        String word = words[i].trim().toLowerCase();
	    	        // only consider actual letters and not the 20 most popular words
	    	        if (word.length() > 0 && !WordCounter2.noNoWord(word) && !Character.isDigit(word.charAt(0))) {
	    	        	toReturn.add(word);
	    	        }
	    	    }
	    		line = bufferedReader.readLine();
	    	}
	    	// close reader
	    	bufferedReader.close();
			} catch (IOException e) {
				;
			}
		return toReturn;
	}
	
	/**
	 * @param an ArrayList of words to build into a mapset
	 * @return a double representing how long it took to build
	 */
	public double buildMap(ArrayList<String> words) {
		long start = System.nanoTime();
		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			// if word already present
        	if (map.containsKey(word)) {
        		map.put(word, map.get(word) + 1);
        	} else {
    		// if new word 
        		map.put(word, 1);
        	}
		}
		long end = System.nanoTime();
		return (end - start);
	}
	
	/**
	 * clears the internal map
	 */
	public void clearMap() {
		map = new BSTMap<String, Integer>();
	}
	
	/**
	 * @return the total number of words
	 */
	public int totalWordCount() {
		ArrayList<Integer> values = map.values();
		int total = 0;
		for (int i : values) {
			total += i;
		}
		return total;
	}
	
	/**
	 * @return the number of different words
	 */
	public int uniqueWordCount() {
		ArrayList<String> words = map.keySet();
		return words.size();
	}
	
	/**
	 * @param word to check for
	 * @return the number of times it occurs
	 */
	public int getCount(String word) {
		return map.get(word);
	}
	
	/**
	 * @param word to check for
	 * @return how often it occurs
	 */
	public double getFrequency(String word) {
		double size = this.totalWordCount();
		double wordCount = this.getCount(word);
		return wordCount/size;
	}
	
	/**
	 * @param filename to write to
	 * @return whether or not the writing was successful
	 */
	public boolean writeWordCount(String filename) {
		try {	      
		    // creates a FileWriter Object
	        FileWriter writer = new FileWriter(filename); 
		    
		    // Writes the content to the file
		    writer.write(map.entrySet().toString()); 
		    writer.close();
		    return true;
		} catch (IOException e){
			return false;
		}
	}
	
	/**
	 * @param filename to read from
	 * @return whether or not the read was successul
	 */
	public boolean readWordCount(String filename) {
		try {
			FileReader reader = new FileReader(filename);
	    	BufferedReader bufferedReader = new BufferedReader(reader);
	
	    	// read line
	    	String line = bufferedReader.readLine();
    		// split by commas
    		String[] words = line.split(",");
    		// remove all characters that aren't what we care about
    		for (int i = 0; i < words.length; i++) {
    			words[i] = WordCounter2.stripChars(words[i], "[< >]"); 
    		}
    		// separate into words and counts
    		ArrayList<String> actualWords = new ArrayList<String>();
    		ArrayList<String> counts =  new ArrayList<String>();
    		for (int i = 0; i < words.length; i++) {	    			
    			if (i%2 == 0) {
    				actualWords.add(words[i]);
    			} else {
    				counts.add(words[i]);
    			}
    		}
    		// close reader
    		bufferedReader.close();
    		// iterate over both ArrayLists adding the nodes with their count
    		for (int i = 0; i < actualWords.size(); i++) {
    			String word = actualWords.get(i);
    			Integer count = Integer.parseInt(counts.get(i));
				map.put(word, count);
    		}
    		return true;
		} catch (IOException e) {
			return false;
		}
	}

}
