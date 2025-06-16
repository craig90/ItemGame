import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandLineReader {

    Scanner scanner;

    public CommandLineReader() {
        scanner = new Scanner(System.in);
    }


    public int getInteger() {
        
        int input = -1;
        
        try {
            input = this.scanner.nextInt();
            this.scanner.nextLine(); // consume the \n so any future calls to nextLine don't "appear skipped"
        } catch (InputMismatchException e) {
            System.out.println("Please type an integer value");
            this.scanner.next();
        }

        return input;
    }


    public String getLine() {

        String line = "";

        try {
            line = this.scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Please enter a string");
            this.scanner.nextLine();
        }

        return line;

    }


}
