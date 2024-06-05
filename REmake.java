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

    private static List<State> states;
    private static int currentState;

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

    private static void buildFSM(String regexp){
        //Declare variables
        Stack<Integer> startStates = new Stack<>();
        Stack<Integer> endStates = new Stack<>();
        int prevState = 1;
        int startState;
        char lastChar = 0;

        for(int i = 0; i < regexp.length(); i++){
            char c = regexp.charAt(i);

            if(c == '\\'){
                i++;
                char nextChar = regexp.charAt(i);
                states.add(new State(currentState, Character.toString(nextChar), currentState + 1, currentState + 1));
                prevState = currentState;
                currentState++;
            } else if(c == '*'){
                if(lastChar == ')'){
                    startState = startStates.peek();
                } else{
                    startState = prevState - 1;
                }
                states.get(startState).nextState1 = currentState;
                states.add(new State(currentState, "BR", startState, currentState + 1));
                currentState++;
                prevState = currentState;
            } else if(c == '?') {
                if(lastChar == ')') {
                    startState = startStates.peek();
                } else{
                    startState = prevState - 1;
                }
                states.get(startState).nextState2 = currentState;
                states.add(new State(currentState, "BR", startState, currentState + 1));
                currentState++;
                prevState = currentState;
            } else if(c == '|') {
                startStates.add(currentState);
                states.add(new State(currentState, "BR", prevState, -1));
                currentState++;
                prevState = currentState;
            } else if(c == '(') {
                startStates.add(currentState);
            } else if(c == ')') {
                endStates.add(prevState);
                startState = startStates.pop();
                states.add(new State(currentState, "BR", startState, prevState + 1));
                currentState++;
                prevState = currentState;
            } else{
                states.add(new State(currentState, "\"" + Character.toString(c) + "\"", currentState + 1, currentState + 1));
                prevState = currentState;
                currentState++;
            }
            lastChar = c;
        }

        while (!endStates.isEmpty()){
            int endState = endStates.pop();
            states.add(new State(currentState, "BR", endState, currentState + 1));
            currentState++;
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
            writer.write("___________");
            writer.newLine();

            // Write FSM states
            for (State state : states) {
                writer.write(state.toString());
                writer.newLine();
            }
            System.out.println("Successfully written to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}