/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
 import java.util.List;
 import java.util.NoSuchElementException;
 import java.util.LinkedList;

 
 public class REfind {
 
     public static void main(String[] args) {
         // Check if the correct number of arguments is provided
         if (args.length != 1) {
            // cat make.txt | java REfind textfile.txt > found.txt
            System.err.println("Usage: java REfind <text file>");
            System.exit(1);
        }
         
        // Text file to search
        String textFile = args[0];
        // Output of the first program
        List<State> fsm = new ArrayList<>();
 
            // Read FSM from standard input
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("s") || line.startsWith("_")) {
                    continue;  // Skip header lines
                }
                String[] parts = line.split(",");
                int stateNumber = Integer.parseInt(parts[0]);
                String type = parts[1].replace("\"", "");
                int nextState1 = Integer.parseInt(parts[2]);
                int nextState2 = Integer.parseInt(parts[3]);
                fsm.add(new State(stateNumber, type, nextState1, nextState2));
            }
        } catch (IOException e) {
            System.err.println("Error reading FSM: " + e.getMessage());
            System.exit(1);
        }
 
        // Check for matches in the text file
         try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
             String line;
             boolean matchFound = false;
             while ((line = reader.readLine()) != null) {
                 if (matches(line, fsm)) {
                     System.out.println(line);
                     matchFound = true;
                 }
             }
             if(!matchFound){
                System.out.println("No matches found");
             }
         } catch (IOException e) {
             System.err.println("Error reading text file: " + e.getMessage());
             System.exit(1);
         }
     }
 
    /**
      * Checks if a substring of the given line matches the FSM
      */
        static boolean matches(String line, List<State> fsm) {
        // Loop through each character position in the input string
        for (int mark = 0; mark < line.length(); mark++) {
            // Start matching from the current mark position
            int point = mark; 
            Deque deque = new Deque();
            List<Integer> visited = new LinkedList<>();
            
            deque.addFirst(0); // Start from the initial state
            deque.addFirst(-1); // Add a scan marker

            boolean mismatch = false; // Track mismatches

            // Process the FSM transitions
            while (!deque.isEmpty()) {
                // Pop the next element
                int currentIndex = deque.removeFirst();

                // Handle scan marker
                if (deque.isScan(currentIndex)) {
                    if (!deque.isEmpty()) {
                        visited.clear();
                        deque.addLast(-1);
                        // Advance
                        continue;
                    } else {
                        break;
                    }
                }

                // Get the current state from the current index
                State currentState = fsm.get(currentIndex);
                visited.add(currentIndex);

                // Check if the FSM has reached a final state
                if (currentState.nextState1 == 0) {
                    // A match is found
                    // return true - end program early
                    return true; 
                }

                // Handle branch states
                if (currentState.type.equals("BR")) {
                    if (currentState.nextState1 == currentState.nextState2) {
                        deque.addFirst(currentState.nextState1);
                    } else {
                        deque.addFirst(currentState.nextState2);
                        deque.addFirst(currentState.nextState1);
                    }
                } else {
                    // Handle character match
                    if (point < line.length() && currentState.type.equals(String.valueOf(line.charAt(point)))) {    
                        if (currentState.nextState1 == currentState.nextState2) {
                            deque.addLast(currentState.nextState1);
                        } else {
                            if (!visited.contains(currentState.nextState1)) {
                                deque.addLast(currentState.nextState1);
                            }
                            if (!visited.contains(currentState.nextState2)) {
                                deque.addLast(currentState.nextState2);
                            }
                        }
                        if (point < line.length() - 1) {
                            point++;
                        } else {
                            // End of line without full match
                            mismatch = true;
                            break;
                        }
                    } else {
                        mismatch = true;
                        break;
                    }
                }
            }

            // If a mismatch occurs, continue to the next mark
            if (mismatch) {
                continue;
            }
        }
        return false; // No match found
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
 
 /**
  * This is a class representing a node in the Deque
  */
 class Node{
     int value;
     Node next;
     Node previous;
 
     // Constructor
     public Node(int state){
         value = state;
     }
 }
 
 /**
  * This is a class for the Deque
  */
 class Deque{
     Node head;
     Node tail;
     Node scan;
 
     // Constructor
     public Deque(){
        head = null;
        tail = null;
        addFirst(-1);
     }
 
     /*
      * A method that checks if the deque is empty, or only has the scan node
      */
     public boolean isEmpty(){
         return head == null && tail == null;
     }
 
     /*
      * A method to add a node to the start of the stack
      */
     public void addFirst(int value){
         Node newNode = new Node(value);
         if (isEmpty()){
             head = tail = newNode;
         }
         else {
             newNode.next = head;
             head.previous = newNode;
             head = newNode;
         }
     }
 
     /*
      * A method to add a node to the end of the queue
      */
     public void addLast(int value){
         Node newNode = new Node(value);
         if (isEmpty()) {
             tail = head = newNode;
         }
         else {
             newNode.previous = tail;
             tail.next = newNode;
             tail = newNode;
         }
     }
 
     /*
      * A method to remove the first node in the stack (returns the value of that node)
      */
     public int removeFirst(){
         if (head == null){
             throw new NoSuchElementException("Deque is Empty");
         }
         int value = head.value;
         if (head == tail){
             head = tail = null;
         }
         else {
             head = head.next;
             head.previous = null;
         }
         return value;
     }
 
     /*
      * A method to check if the current node is SCAN
      */
     public boolean isScan(int value){
         if (value == -1){
             return true;
         }
         return false;
     }

     /*
     * A method to print all the items in the deque
     */
    public void printDeque(){
        System.out.println("Deque: ");
        Node current = head;
        while(current != null){
            System.out.println(current.value);
            current = current.next;
        }
    }
 }