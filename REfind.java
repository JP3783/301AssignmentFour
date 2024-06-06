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
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

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
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading text file: " + e.getMessage());
            System.exit(1);
        }
    }

    // Checks if a substring of the given line matches the FSM
    static boolean matches(String line, List<State> fsm) {
       
        // Advance mark 
        // If mark cannot advance, total fail 

        // Reset point
        // Reset deque
        Stack<Integer> currentStates = new Stack<>();
        Queue<Integer> nextStates = new LinkedList<>();
        List<Integer> visited = new LinkedList<>();

        // Set current state to 0 to get started 
        currentStates.push(0); 
       
       //While stack not empty 
       while (!currentStates.isEmpty()) {
        // Remove the state number we are looking at 
        // Store it in the variable currentStateNumber
        int currentStateIndex = currentStates.pop(); // Pop 
        State currentState = fsm.get(currentStateIndex);
       
        if(currentState.nextState1 == 0){
            break;
        }

        // If its a branch state 
        if(currentState.type.equals("BR")){
            if(currentState.nextState1 == currentState.nextState2){
                if(!(visited.contains(currentState.nextState1))){
                    //System.out.println("Adding: " + currentState.nextState1);
                    nextStates.offer(currentState.nextState1);
                    visited.add(currentState.nextState1);
                }
            }
            else{ 
                if(!((visited.contains(currentState.nextState1)) | visited.contains(currentState.nextState2))){
                    //System.out.println("Adding: " + currentState.nextState1 + " " + currentState.nextState2);
                    nextStates.offer(currentState.nextState1);
                    nextStates.offer(currentState.nextState2);
                    visited.add(currentState.nextState1);
                    visited.add(currentState.nextState2);
                }
            }
        }
        else{
            if(!(visited.contains(currentState.nextState1))){
                //System.out.println("Adding: " + currentState.nextState1);
                nextStates.offer(currentState.nextState1);
                visited.add(currentState.nextState1);
            }
        }

        // Print DeQueue
        System.out.println(" ");
        System.out.println("Current Stack: ");
        System.out.println(currentState.stateNumber);
        System.out.println("SCAN");
        for (Integer integer : nextStates) {
            System.out.println(integer);
        }
        System.out.println(" ____ ");
        

        if (currentStates.isEmpty() && !nextStates.isEmpty()) {
            //System.out.println("SWAP");
            currentStates.push(nextStates.poll());
        }
    }
       
        //While stack not empty 
        // Pop 
        // Set state to visited <<
        // If its a branch state 
            //- push next1/2 (if not visited)
        // If it is a match (curr state char = text char)
            // move pointer and advance to next state
            // add nextstate (so if the char is a match then add the next states to the nextstate stack)

        // While more curr sattes 
        // Pop - mark visited 
        // // If its a branch state 
            //- push next1/2 (if not visited)

        return false;
    }

    // public static boolean isVisited(List<Integer> visited, int states) {
    //     boolean isvisited = false;
    //     for (Integer state : nextState1) {
    //         if (visited.contains(state)) {
    //             System.out.println(state + " " + nextState1);
    //             isvisited = true;
    //         }
    //     }
    //     return isvisited;
    // }



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
