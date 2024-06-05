/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class REfind {
    public static void main(String[] args) {
        // Check if the correct number of arguments is provided
        if (args.length != 2) {
            System.err.println("Usage: java REfind <REmake Output> <text file>");
            System.exit(1);
        }
        
        // Output of the first program
        String fsmDescriptionFile = args[0];
        List<State> fsm = new ArrayList<>();

        // Read FSM description from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(fsmDescriptionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("s") || line.startsWith("_")) {
                    continue;  // Skip header lines
                }
                String[] parts = line.split(",");
                int stateNumber = Integer.parseInt(parts[0]);
                String type = parts[1];
                // Remove the quotes
                type = type.replace("\"", "");
                int nextState1 = Integer.parseInt(parts[2]);
                int nextState2 = Integer.parseInt(parts[3]);
                fsm.add(new State(stateNumber, type, nextState1, nextState2));
                //System.out.println(stateNumber + " " + type + " " + nextState1 + " " + nextState2);
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
                System.out.println("Current string to process:");
                System.out.println(line);
                System.out.println("Matches Found: ");
                // Add logic here 
                // Each found pattern should be outputted as a line
                if (matches(line, fsm)) {
                    //System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading text file: " + e.getMessage());
            System.exit(1);
        }
    }

    // Checks if a substring of the given line matches the FSM
    static boolean matches(String line, List<State> fsm) {
        // Outer loop to go through each character 
        for (int start = 0; start < line.length(); start++) {
            System.out.println(start + " " + line.charAt(start));
        }
        // Initialization :
        // Dequeue initialized (new deque is created for each starting position in the input string)
        Deque<Integer> deque = new ArrayDeque<>();
        // The starting state is added to the front of the deque
        deque.addFirst(0);
        
        // Current state is obtained by removing the first element from the deque
        
        

        return false;
    }



    /**
     * This is a class to initialise the variables for the state of the FSM
     */
    static class State{
        int stateNumber;
        String type;
        int nextState1;
        int nextState2;

        /**
         * The constructor to initialise the variables
         */
        State(int StateNumber, String Type, int NextState1, int NextState2){
            this.stateNumber = StateNumber;
            this.type = Type;
            this.nextState1 = NextState1;
            this.nextState2 = NextState2;
        }

        /**
         * To return the correct format of the FSM
         */
        @Override
        public String toString(){
            return stateNumber + "," + type + "," + nextState1 + "," + nextState2;
        }
    }
}
