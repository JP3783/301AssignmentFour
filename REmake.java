import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This program takes a regular expression (regex) as input and outputs the corresponding FSM.
 */
public class REmake{

    //Define state arrays
    protected static char[] ch;
    protected static int[] next1;
    protected static int[] next2;
    //Declare the start state
    protected static int startState = 0;

    /**
     * This method contains the functions for parsing different components of the regex ('E', 'T', 'F')
     * @param state represents the state during the process of pattern matching
     * @param c character accepted by each state
     * @param next1 next state to transition to
     * @param next2 alternative state for branching
     */
    public static void setState(int state, char c, int next1, int next2){
        ch[state] = c;
        REmake.next1[state] = next1;
        REmake.next2[state] = next2;
    }

    public static void main(String[] args) {
        //Make sure the program is run correctly in the terminal
        if(args.length != 2){
            System.err.println("Usage: java REmake <regexp> <output_file_path>");
            System.exit(0);
        }

        //Declare variables for inputFile and outputFile
        String regexpInput = args[0];
        String FSM = args[1];

        //Check if regexp input format is valid

    }

    //Need to set values/meanings of symbols
    
}