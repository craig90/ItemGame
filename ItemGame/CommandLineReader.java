import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineReader {

    Scanner scanner;

    public CommandLineReader() {
        scanner = new Scanner(System.in);

    }

    public int getInteger() {
        
        int input = -1;
        
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please type an integer value");
            scanner.next();
        }
        
        return input;
    }

}
