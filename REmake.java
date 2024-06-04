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

        ArrayList<ArrayList<String>> table = new ArrayList<>();
        createTable(table);

        //Initialise state that branches to the start of the actual FSM
        states.add(new State(0, "BR", 1, 1));

        //Output the FSM
        writeToFile(table, FSM);
    }

    /**
     * This creates the heading of the table, where you'll find "s,ch,1,2"
     * @param table the structure for the FSM outputs
     * @return the table with an informative first row
     */
    private static ArrayList<ArrayList<String>> createTable(ArrayList<ArrayList<String>> table){
        //Declare variables
        ArrayList<String> patternRow = new ArrayList<>();
        ArrayList<String> line = new ArrayList<>();
        //Populate table
        patternRow.add("s");
        patternRow.add("ch");
        patternRow.add("1");
        patternRow.add("2");
        line.add("________");
        table.add(patternRow);
        table.add(line);
        //Return the output
        return table;
    }

    /**
     * This method writes the FSM to a file
     * @param table the structure for the FSM output
     * @param fSM the FSM output from the regexp
     */
    private static void writeToFile(ArrayList<ArrayList<String>> table, String fileName) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            for(ArrayList<String> row : table){
                for (int i = 0; i < row.size(); i++) {
                    writer.write(row.get(i));
                    if (i < row.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }
            for(State state : states){
                writer.write(state.toString());
                writer.newLine();
            }
            System.out.println("Successfully written to " + fileName);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}