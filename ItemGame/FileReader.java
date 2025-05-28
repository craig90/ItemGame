import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class FileReader {

    Scanner scanner;
    String fileName;

    FileReader() {
        this.fileName = "ItemGame/character1.txt";
    }

    public void openFile() {

        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
                    
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public String getLine() {
        
        if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // System.out.println(line);
            return line;
        } else {
            scanner.close();
            return "EndOfFile";
        }
    }

}
