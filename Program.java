
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main entry to the entire program.
 * @author Dennis Yiaile
 */
public class Program {

    /**
     * This is the entry method for this program.
     * @param args - command line arguments inputs
     * @throws FileNotFoundException - exception to be thrown
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
            System.out.println("No files to read.");
        } else {
            ArrayHeap<PollingData> tree = new ArrayHeap<>();
            String[] sortedFiles = sortedFiles(getFiles(args));
            for (String fileName : sortedFiles) {
                if (fileName != null) {
                    File file = new File(fileName);
                    Scanner input = new Scanner(file);
                    input.nextLine();
                    while (input.hasNext()) {
                        String line = input.nextLine();
                        String[] parts = line.split(",");
                        String lastName = parts[0].replace("\"", "");
                        String firstName = parts[1];
                        Double percent = Double.parseDouble(parts[2]);
                        PollingData data = new PollingData(
                            lastName, firstName, percent);
                        tree.insert(data);
                    }
                    input.close();
                    System.out.println(tree.toString());
                } else {
                    break;
                }
            }

            int topN = 0;
            String[] removeNames = new String[args.length];
            int i = 0;
            int names = 0;
            while (i < args.length) {
                if (args[i].equals("-n")) {
                    topN = topN + Integer.parseInt(args[i + 1]);
                    i += 2;
                } else if (args[i].equals("-r")) {
                    i++;
                    while (isLastName(args, i)) {
                        removeNames[names] = args[i];
                        names++;
                        i++;
                    }
                } else {
                    i++;
                }
            }

            tree = removeElements(tree, removeNames);
            printTopN(tree, topN);
        }
    }

    /**
     * Contains a list of sorted filenames based on the date.
     * @param args - an inputed list of filenames from the command line
     * @return String[] - a list of filenames in a sorted order
     */
    public static String[] sortedFiles(String[] args) {
        int i = 0;
        while (i < args.length && args[i] != null) {
            int j = i + 1;
            while (j < args.length && args[j] != null) {
                int date = extractDate(args[i]);
                int date2 = extractDate(args[j]);
                if (date > date2) {
                    String temp = args[i];
                    args[i] = args[j];
                    args[j] = temp;  
                }
                j++;
            }
            i++;
        }
        return args;
    }

    /**
     * Method extracts the date and the number of the poll for that poll. 
     * @param filename - name of the file containing poll data
     * @return  int - date and number of poll for that date
     */
    public static int extractDate(String filename) {
        String date = "";
        for (int i = 0; i < filename.length(); i++) {
            char item = filename.charAt(i);
            if (Character.isDigit(item)) {
                date += Character.toString(item);
            }
        }
        return Integer.parseInt(date);
    }

    /**
     * Get an array of files names from the command line arguments.
     * @param args - an array of command line arguments
     * @return String[] - array containing file names
     */
    public static String[] getFiles(String[] args) {
        String[] fileNames = new String[args.length]; 
        int index = 0;
        for (String string : args) {
            if (string.length() > 4 && 
                string.substring(string.length() - 4, 
                string.length()).equals(".csv")) {
                fileNames[index] = string;
                index += 1;
            }
        }
        return fileNames;
    }

    /**
     * Check whether the next argument is a lastName.
     * @param args - array containing command line arguments
     * @param i - position of an index in the array
     * @return - true if it's a last name else false
     */
    public static boolean isLastName(String[] args, int i) {
        Boolean isFile = args[i].length() > 4 && 
            args[i].substring(args[i].length() - 4, 
            args[i].length()).equals(".csv");
        return i < args.length && !args[i].equals("-n") && !isFile;
    }

    /**
     * Print the top N elements of a tree after removal.
     * @param tree - tree to find the elements
     * @param topN - number of elements to get
     */
    public static void printTopN(ArrayHeap<PollingData> tree, int topN) {
        if (topN > 0) {
            System.out.println("Top " + 
                Integer.toString(topN) + " Candidates:");
            ArrayList<PollingData> topNPolls = tree.peekTopN(topN);
            for (PollingData poll : topNPolls) {
                System.out.println(poll.toString());
            }
        }
    }

    /**
     * Remove inputed polling data from command line.
     * @param tree - where to remove data from
     * @param removeNames - array of last names to remove
     * @return - a tree with given data removed
     */
    public static ArrayHeap<PollingData> removeElements(
        ArrayHeap<PollingData> tree, String[] removeNames) {
        for (String string : removeNames) {
            if (string != null) {
                PollingData toRemove = new PollingData(string, "Test", 0);
                tree.remove(toRemove);
            } else {
                break;
            }
        }
        return tree;
    }
    
}
