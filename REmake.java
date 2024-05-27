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

        //Compile the regexp into FSM
        compile(regexpInput);

        //Output the FSM
        writeToFile(FSM);
    }

    /**
     * This method writes the FSM to a file
     * @param fSM the FSM output from the regexp
     * @throws IOException 
     */
    private static void writeToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(State state : states){
                writer.write(state.toString());
                writer.newLine();
            }
        }
    }

    public static void compile(String regexpString){
        //This is where the regexp input is checked for illegal characters
        if(regexpString.contains(".")){
            //Wildcard that matches any literal
        }

        if(regexpString.contains("*")){
            //Indicates closure (zero or more occurences) on the preceding regexp
        }

        if(regexpString.contains("?")){
            //Indicates that the preceding regexp can occur zero or one time
        }

        if(regexpString.contains("|")){
            //is an infix alternation operator such that if r and e are regexps, then r|e is a regexp that matches one of either r or e
        }

        //Didn't do:
        //1. any symbol that does not have a special meaning (as given below) is a literal that matches itself
        //3. adjacent regexps are concatenated to form a single regexp
        //7. ( and ) may enclose a regexp to raise its precedence in the usual manner; such that if e is a regexp, then (e) is a regexp and is equivalent to e. e cannot be empty.
        //8. \ is an escape character that matches nothing but indicates the symbol immediately following the backslash loses any special meaning and is to be interpretted as a literal symbol
        //9. operator precedence is as follows (from high to low):
        //      escaped characters (i.e. symbols preceded by \)
        //      parentheses (i.e. the most deeply nested regexps have the highest precedence)
        //      repetition/option operators (i.e. * and ?)
        //      concatenation
        //      alternation (i.e. | )
        //10. not required for this assignment, but a challenge to those who are interested, is how you might incorporate ! as a "do not match" operator, such that !e matches only a pattern that does not match the expression e.
    }
}