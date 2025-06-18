import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;


public class FileReader {

    public static final String directoryPath = "ItemGame/CharacterFiles/";

    Scanner scanner;
    // String fileName;

    FileReader() {
        this.scanner = null;
        // this.fileName = "";
    }

    public void openFile(String fileName) {

        try {
            // System.out.println(fileName);
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred locating the file");
            e.printStackTrace();
            System.exit(0);
        }

    }

    public String getLine() {
        
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            return line;
        } else {
            scanner.close();
            return FileKeywords.END_OF_FILE;
        }
    }

    public void closeFile() {
        scanner.close();
    }

    /**
     * returns the names of the files in the specified directory as an ArrayList of Strings
     * @return false if no files are found
     */
    public static ArrayList<String> readFileNames() throws FileNotFoundException {
        
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();

        if (files != null) {
            for (File file : files) {
                // System.out.println(file.getName());
                fileNames.add(directoryPath + file.getName());
            }
            return fileNames;
        } else {
            throw new FileNotFoundException("No files found in " + directoryPath);
        }

    }


     /**
     * this main method is purely for testing file IO class
     * @param args
     */
    public static void main(String args[]){

        ArrayList<String> fileNames = new ArrayList<String>();
        //FileReader fileReader = new FileReader();
        try {
            fileNames = readFileNames();
        } catch (FileNotFoundException e) {
            System.out.println("Ahhh, No files found");
        }

        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println(fileNames.get(i));
        }

    }    


}
