import edu.duke.*;
import java.util.*;

/**
* The GladLibs class dynamically generates stories 
* by substituting placeholders within a template 
* with random words from specified categories, 
* offering a customizable and entertaining way 
* to create text-based content.
* 
* @author Vaishali Vyas
* @version 2024-02-12
*/

public class GladLibs {
    private HashMap<String, ArrayList<String>> wordCategoriesMap;
    private Random random;
    
    private ArrayList<String> wordsUsed;
    private ArrayList<String> categoriesUsed;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    /**
     * Constructs a GladLibs instance using the default data source directory.
     */
    public GladLibs() {
        wordCategoriesMap = new HashMap<>();
        wordsUsed = new ArrayList<>();
        categoriesUsed = new ArrayList<>();
        initializeFromSource(dataSourceDirectory);
        random = new Random();
    }
    
    /**
     * Constructs a GladLibs instance using a specified source directory or URL.
     *
     * @param source the path to the source directory or URL containing the word lists
     */
    public GladLibs(String source) {
        wordCategoriesMap = new HashMap<>();
        wordsUsed = new ArrayList<>();
        categoriesUsed = new ArrayList<>();
        initializeFromSource(source);
        random = new Random();
    }
    
    /**
     * Initializes word categories and their corresponding words from the specified source.
     *
     * @param source the path to the source directory or URL containing the word lists
     */
    private void initializeFromSource(String source) {
        String[] labels = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        
        for (String label : labels) {
            wordCategoriesMap.put(label, ResourceLoader.readResourceLines(source + "/" + label + ".txt"));
        }
        /*
        for (String label : labels) {
            wordCategoriesMap.put(label, readIt(source + "/" + label + ".txt"));
        }
        */
    }
    
    private ArrayList<String> readIt(String source){
    	ArrayList<String> list = new ArrayList<String>();
    	if (source.startsWith("http")) {
    	    URLResource resource = new URLResource(source);
    	    for(String line : resource.lines()) {
    	        list.add(line);
    	    }
    	}
    	else {
    	    FileResource resource = new FileResource(source);
    	    for(String line : resource.lines()) {
    	        list.add(line);
    	    }
    	}
    	return list;
    }
    
    /**
     * Returns a random word from the specified ArrayList of words.
     *
     * @param source the ArrayList of words to select from
     * @return a randomly selected word from the list
     */
    private String randomFrom(ArrayList<String> source) {
        int index = random.nextInt(source.size());
        return source.get(index);
    }
    
    /**
     * Retrieves a substitute word for a given label. If the label is "number", generates a random number instead.
     *
     * @param label the label for which to find a substitute word
     * @return a substitute word or a random number as a string
     */
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return "" + (random.nextInt(50) + 5);
        } else if (wordCategoriesMap.containsKey(label)) {
            if (!categoriesUsed.contains(label)) {
                categoriesUsed.add(label);
            }
            return randomFrom(wordCategoriesMap.get(label));
        } else {
            System.err.println("Label does not exist: " + label);
            return "**UNKNOWN**";
        }
    }
    
    /**
     * Processes a word by replacing any placeholders within it with a substitute word.
     *
     * @param w the word to process
     * @return the processed word with placeholders replaced by substitute words
     */
    public String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if (first == -1 || last == -1) {
            return w;
        }
        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String sub = "";
        do {
            sub = getSubstitute(w.substring(first + 1, last));
        } while (wordsUsed.contains(sub));
        wordsUsed.add(sub);
        return prefix + sub + suffix;
    }
    
    /**
     * Generates a story from a template, replacing placeholders with words from the corresponding categories.
     *
     * @param source the path or URL to the template
     * @return a story generated from the template
     */
    private String fromTemplate(String source) {
        List<String> words = new ArrayList<>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            resource.words().forEach(word -> words.add(processWord(word)));
        } else {
            FileResource resource = new FileResource(source);
            resource.words().forEach(word -> words.add(processWord(word)));
        }
        return String.join(" ", words);
    }
    
    /**
     * Prints the generated story to the console, with a specified maximum line width.
     *
     * @param s the story to print
     * @param lineWidth the maximum width of a line before wrapping to the next line
     */
    private void printOut(String s, int lineWidth) {
        int charsWritten = 0;
        for (String w : s.split("\\s+")) {
            if (charsWritten + w.length() > lineWidth) {
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
    }
    
    /**
     * This method returns the total number of words in all the Lists in the Map.
     */
    private int totalWordsInMap() {
        int totalWordsInMap = 0;
        for (String s : wordCategoriesMap.keySet()) {
            totalWordsInMap += wordCategoriesMap.get(s).size();
        }
        return totalWordsInMap;
    }
    
    /**
     * This method returns the total number of words in the ArrayLists of the categories that were used for a particular GladLib.
     */
    private int totalWordsConsidered() {
        int totalWordsConsidered = 0;
        for (String s : categoriesUsed) {
            ArrayList<String> words = wordCategoriesMap.get(s);
            if (words != null) {
                totalWordsConsidered += wordCategoriesMap.get(s).size();
            }
        }
        return totalWordsConsidered;
    }
    
    /**
     * Generates and prints a story to the console using the default template.
     */
    public void makeStory() {
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate.txt");
        printOut(story, 60);
        System.out.println("\n\nTotal number of words that were possible to pick from Words in Map are: " + totalWordsInMap());
        System.out.println("Total number of words that were possible to pick from Words Considered are: " + totalWordsConsidered());
    }    
}
