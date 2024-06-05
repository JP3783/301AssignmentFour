/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This program takes a regular expression (regex) as input and outputs the corresponding FSM.
 */
public class REmake{

    //Declare some global variables
    private static List<State> states;
    private static int currentState;

    /**
     * This is a class to initialise the variables for the state of the FSM
     */
    static class State{

        //Declare variables
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

    /**
     * The entry point of the program
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs when writing to the output file
     */
    public static void main(String[] args) throws IOException {

        //Make sure the program is run correctly in the terminal
        if(args.length != 2){
            System.err.println("Usage: java REmake <regexp> <output_file_path>");
            System.exit(0);
        }

        //Declare variables for inputFile and outputFile
        String regexpInput = args[0];
        String FSM = args[1];
        
        //Initialise the states list
        states = new ArrayList<>();
        currentState = 1;

        //Initialise state that branches to the start of the actual FSM
        states.add(new State(0, "BR", 1, 1));

        //Build FSM from regexp input
        buildFSM(regexpInput);

        //Output the FSM
        writeToFile(FSM);
    }

    /**
     * Builds the FSM from the regular expression
     * @param regexp the input regexp from the command-line that will be processed
     */
    private static void buildFSM(String regexp) {

        //Using stacks to keep trackof startStates, endStates, and alternationStates, it's easier this way
        Stack<Integer> startStates = new Stack<>();
        Stack<Integer> endStates = new Stack<>();
        Stack<Integer> alterStates = new Stack<>();

        //Tracks the previous state
        int prevState = 1;

        //Tracks the last character processed
        char lastChar = 0;

        //Loop through each character in ther regexp
        for (int i = 0; i < regexp.length(); i++) {
            char c = regexp.charAt(i);

            if (c == '\\') {    //If an escape character is found
                //Increment i to point to the next character
                i++;
                char nextChar = regexp.charAt(i);
                //Add a state for the escaped character
                states.add(new State(currentState, "\"" + Character.toString(nextChar) + "\"", currentState + 1, currentState + 1));
                prevState = currentState;
                currentState++;
            } else if (c == '*') {      //If a "zero or more occurrences" symbol is found
                //Determine its start state
                int startState = (lastChar == ')' ? startStates.peek() : prevState - 1);
                states.get(startState).nextState1 = currentState;
                //Add a branch state to create the loop
                states.add(new State(currentState, "BR", startState, currentState + 1));
                currentState++;
                prevState = currentState;
            } else if (c == '?') {      //If a "zero or one occurrence" symbol is found
                //Determine its startState
                int startState = (lastChar == ')' ? startStates.peek() : prevState - 1);
                states.get(startState).nextState2 = currentState;
                //Add a branch state to handle the optional character
                states.add(new State(currentState, "BR", startState, currentState + 1));
                currentState++;
                prevState = currentState;
            } else if (c == '|') {      //If an "alternation" symbol is found
                //Push onto the alterStates stack
                alterStates.push(prevState);
                //Add a branch state to handle the alternation
                states.add(new State(currentState, "BR", prevState, -1));
                currentState++;
                prevState = currentState;
            } else if (c == '(') {      //If an opened bracket is found. i.e. start of a group
                //Push the current state onto the startStates stack
                startStates.push(currentState);
            } else if (c == ')') {      //If a closed bracket is found. i.e. end of a group
                //Push the previous state onto the endStates stack
                endStates.push(prevState);
                //Get the startState from the startStates stack
                int startState = startStates.pop();
                //Add a branch state to link the group
                states.add(new State(currentState, "BR", startState, prevState + 1));
                currentState++;
                prevState = currentState;
            } else {    //If a regular character or any other symbol is found
                //Treat as a literal
                //Add a state for the character
                states.add(new State(currentState, c == '.' ? "." : "\"" + Character.toString(c) + "\"", currentState + 1, currentState + 1));
                prevState = currentState;
                currentState++;
            }

            //Update the last character processed
            lastChar = c;
        }

        //Handle any remaining endStates
        while (!endStates.isEmpty()) {
            int endState = endStates.pop();
            states.add(new State(currentState, "BR", endState, currentState + 1));
            currentState++;
        }

        //Handle any remaining alternationStates
        while (!alterStates.isEmpty()) {
            int alterState = alterStates.pop();
            states.get(alterState).nextState2 = currentState;
        }
    }

    /**
     * This method writes the FSM to a file
     * 
     * @param fileName the FSM output from the regexp
     */
    private static void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            // Write table header
            writer.write("s,ch,1,2");
            writer.newLine();
            writer.write("_________");
            writer.newLine();

            // Write FSM states
            for (State state : states) {
                writer.write(state.toString());
                writer.newLine();
            }

            //Print a message saying the file has been written
            System.out.println("Successfully written to " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}