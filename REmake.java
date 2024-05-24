/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

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
        compile(regexpInput);
    }

    //Need to set values/meanings of symbols
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