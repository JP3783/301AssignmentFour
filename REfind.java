/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class REfind {
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 1) {
            System.err.println("Usage: java REfind <asdasd> <text file>");
            System.exit(1);
        }
        
        // Output of the first program
        String fsmDescriptionFile = args[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(fsmDescriptionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Add Logic
            }
        } catch (IOException e) {
            System.err.println("Error reading FSM description file: " + e.getMessage());
            System.exit(1);
        }

        // Read text file from command-line argument
        String textFile = args[1];
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Search for patterns using the FSM
            }
        } catch (IOException e) {
            System.err.println("Error reading text file: " + e.getMessage());
            System.exit(1);
        } 
    }
}
