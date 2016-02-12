import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Main {
	
	public static void main(String[] args) {
		
		// Set will contain all unique words from the dataset
		HashMap<String, Integer> positiveVocabulary = new HashMap<String,Integer>();
		HashMap<String, Integer> negativeVocabulary = new HashMap<String, Integer>();
		
		// Acccess all files in the dataset and store the words in the set
		loadFilesFromDirectory("pos", positiveVocabulary);
		loadFilesFromDirectory("neg", negativeVocabulary);
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Please enter directory: ");
		String path = scanner.nextLine();

		examine(path,positiveVocabulary,negativeVocabulary);
		
		/* USE THIS CODE FOR SINGLE TEXT FILES

		double probPos = calculateProbabilty(positiveVocabularly,path);
		double probNeg = calculateProbabilty(negativeVocabularly,path);

		if(probPos>probNeg)
		{
			System.out.println("Review is: Positive");
		}
		else
		{
			//System.out.println("Review is: Negative");
			System.out.println("Review is: Negative");
		}
		
		System.out.println("Pos: " + probPos);
		System.out.println("Neg: " + probNeg);
		
		// END OF SINGLE CODE
		*/
	
	}

	private static void loadFilesFromDirectory(String dirName, HashMap<String, Integer> vocabularly) {
		
		
		String path = ".\\"+dirName+"\\"; 
				 
		String fileName;
		File folder = new File(path);
		
		// Creates an array of File from the specified path and directory
		File[] listOfFiles = folder.listFiles(); 
		 
		// Iterates through all the files in the directory
		for (int i = 0; i < listOfFiles.length; i++) 
		{		 
		   if (listOfFiles[i].isFile()) 
		   {
				fileName = listOfFiles[i].getName();
				
				// Open the specified file and adds it's contents into the set 
		        captureFileContents(path+fileName, vocabularly);
		   }
		}		
	}
	
	private static void examine(String dirName,HashMap<String,Integer> pos,HashMap<String,Integer> neg){
		
		
		String path = ".\\"+dirName+"\\"; 
				 
		String fileName;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles(); 
		
		
		double totalPositiveReviews = 0;	// Number of positive reviews in the given folder.
		double totalNegativeReviews = 0; 	// Number of negative reviews in the given folder.
		double totalReviews = 0;			// Total number of files (reviews) in the folder.
		 
		// Iterates through all the files in the directory
		for (int i = 0; i < listOfFiles.length; i++) 
		{		 
		   if (listOfFiles[i].isFile()) 
		   {
				fileName = listOfFiles[i].getName();
				String file = "" + path + fileName;
				
				// Probability of it being positive
				double probPos = calculateProbabilty(pos,file);
				// Probability of it being negative
				double probNeg = calculateProbabilty(neg,file);
				
				if(probPos>probNeg) // If probPos is closer to 0
					totalPositiveReviews ++; // Review is positive
		
				else
					totalNegativeReviews++; // Review is negative
	
				totalReviews++;
		   }
		}
		
		double percentPositive = (totalPositiveReviews/totalReviews)*100;
		double percentNegative = (totalNegativeReviews/totalReviews)*100;
		
		System.out.println("");
		System.out.println("Total reviews: " + Math.round(totalReviews));
		System.out.println("Total Positive: " + Math.round(percentPositive) + "% " + Math.round(totalPositiveReviews));
		System.out.println("Total negative: " + Math.round(percentNegative) + "% " + Math.round(totalNegativeReviews));
	}

	private static void captureFileContents(String fileName, HashMap<String, Integer> vocabularly) {

		try {
			Scanner reader = new Scanner(new File(fileName) );
			
			// Read each string from the file and add to the set
			while (reader.hasNext())
			{
				String word = reader.next().toUpperCase();		// Get the next word and set to upper case
				String filtered = word.replaceAll("\\W", "");	// Regex to replace all punctuation	        
		        if(notMeaningless(filtered))					// Check if the word is meaningless 
		        {
			        Integer count = vocabularly.get(filtered);	// Get the current word's frequency
			        if(vocabularly.containsKey(filtered))		// If the word already exists, increase the frequency value by one.
			        	vocabularly.put(filtered, count + 1);
			        else
			        	vocabularly.put(filtered,1);			// Else add the new word to the hash map with a frequency of 1.
		        }	
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error Capturing: " + fileName);
		}	
	}
	
	private static int countWords(HashMap<String, Integer> map){
		
		int total = 0;
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
	     
				String word = entry.getKey();
		        Integer count = map.get(word);
		        
		        total += count;
	    }

		return total;
	}
	
	private static boolean notMeaningless(String word){
		
		String MEANINGLESS_WORDS[] = {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
		"u", "v", "w", "x", "y", "z", "the", "be", "will", "he", "she", "it", "his", "her", "hers", "how", "name","an", "or",
		" ", "!","if","is"};
		
		boolean notmeaningless = true;
	
		for(int i=0; i<MEANINGLESS_WORDS.length; i++)
		{
			if(word.equalsIgnoreCase(MEANINGLESS_WORDS[i]))
				notmeaningless = false;
		}
		
		return notmeaningless;
	}
	
	private static double calculateProbabilty(HashMap<String,Integer> map,String path){
				
		HashMap<String, Integer> reviewVocabulary = new HashMap<String, Integer>();		
		captureFileContents(path,reviewVocabulary); // Capture document vocabulary
		
		// The reviewVocabulary contents each word in the file to be classified.
		
		int sizeOfVocab = countWords(map); // Size of the given vocabulary		
		int sizeOfDoc = countWords(reviewVocabulary); // Size of file vocabulary
		
		double probability = 0;
		
		for(Map.Entry<String, Integer> entry : reviewVocabulary.entrySet())
		{
			String word = entry.getKey(); // Get current word
			double value = 0;			// If word doesn't exist 0 is frequency
			
			if(map.get(word)!= null)
	        	value = map.get(word); // If the word exists get frequency
			
			probability += Math.log((value+1)/(sizeOfVocab+sizeOfDoc));
		}

		return probability + Math.log(.5); // .5 is the probability of class
		
	}
}
