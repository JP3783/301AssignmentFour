/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            return stateNumber + ", " + type + ", " + nextState1 + ", " + nextState2;
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
        int startState;
        int prevState = 1;

        for(int i = 0; i < regexp.length(); i++){
            char c = regexp.charAt(i);

            if(c == '*'){
                states.get(prevState - 1).nextState1 = currentState;
                states.add(new State(currentState++, "BR", prevState, currentState + 1));
                prevState = currentState++;
            } else if(c == '?'){
                states.get(prevState - 1).nextState2 = currentState;
                states.add(new State(currentState++, "BR", prevState, currentState + 1));
                prevState = currentState++;
            } else if (c == '|') {
                startState = states.size();
                states.add(new State(currentState++, "BR", prevState, -1));
                prevState = currentState++;
            } else if (c == '(') {
                startState = currentState;
            } else if (c == ')') {
                // Find the corresponding startState for '('
                // This part is simplified for the sake of example
                startState = prevState - 1;
                states.get(startState).nextState2 = prevState;
            } else {
                states.add(new State(currentState++, "\"" + c + "\"", currentState, currentState));
                prevState = currentState++;
            }
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
            writer.write("s, ch, 1, 2");
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