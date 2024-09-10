import java.util.*;

public class Anagrams {
	public static void main(String[] args) {
		// Welcome message
		System.out.println("Welcome to the Anagrams finder!\n");

		// Check if the command-line argument is provided
		if (args.length != 1) {
			System.out.println("  usage: java Anagram some-text.txt\n");
			System.exit(0);
		}

		System.out.println("  reading file " + args[0] + "...");

		String filename = args[0];
		WordReader wr = new WordReader(filename);
		List<String> words = readWords(wr);

		// Check if the file contains words
		if (words.isEmpty()) {
			System.out.println("No words found in the file.");
			return;
		}

		// Start timer to determine efficiency
		RunTimer timer = new RunTimer();
		timer.start(); // Start the timer

		// Find anagram sets
		Map<String, List<String>> anagramSets = findAnagrams(words);

		// Stop timer
		timer.stop();

		// Display found anagram sets
		System.out.println("Found anagrams:");
		int totalAnagramSets = 0;
		for (List<String> anagrams : anagramSets.values()) {
			if (anagrams.size() > 1) {
				totalAnagramSets++;
				System.out.println("- " + String.join(" ", anagrams));
			}
		}

		// Display total number of anagram sets found
		System.out.println("There are " + totalAnagramSets + " sets of anagrams.");
		System.out.println("That took " + timer.getElapsedMillis() + "ms");

		// Find the word with the most anagrams
		String mostAnagramsWord = findWordWithMostAnagrams(anagramSets);
		System.out.println("Words with the most anagrams are:");
		for (String word : anagramSets.get(mostAnagramsWord)) {
			System.out.println("  " + word);
		}
	}

	// Read words from WordReader and return as a list
	private static List<String> readWords(WordReader wordReader) {
		List<String> words = new ArrayList<>();
		String word;
		while ((word = wordReader.nextWord()) != null) {
			words.add(word);
		}
		return words;
	}

	// Finds anagram sets from a list of words
	private static Map<String, List<String>> findAnagrams(List<String> words) {
		Map<String, List<String>> anagramSets = new HashMap<>();
		for (String word : words) {
			// Sort characters of word to find anagrams
			char[] charArray = word.toCharArray();
			Arrays.sort(charArray);
			String sortedWord = new String(charArray);
			List<String> anagrams = anagramSets.getOrDefault(sortedWord, new ArrayList<>());
			anagrams.add(word);
			anagramSets.put(sortedWord, anagrams);
		}
		return anagramSets;
	}

	// Finds the word with the most anagrams
	private static String findWordWithMostAnagrams(Map<String, List<String>> anagramSets) {
		String mostAnagramsWord = "";
		int maxAnagrams = 0;
		for (String word : anagramSets.keySet()) {
			int numAnagrams = anagramSets.get(word).size();
			if (numAnagrams > maxAnagrams) {
				maxAnagrams = numAnagrams;
				mostAnagramsWord = word;
			}
		}
		return mostAnagramsWord;
	}
}
