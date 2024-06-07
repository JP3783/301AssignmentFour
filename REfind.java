/**
 * Student 1: William Malone ID: 1604564 
 * Student 2: Justin Poutoa ID: 1620107
 */

 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.NoSuchElementException;
 import java.util.LinkedList;

 
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
                 // System.out.println("Current string to process:");
                 // System.out.println(line);
                 // System.out.println("Matches Found: ");
                 // Add logic here 
                 // Each found pattern should be outputted as a line
                 if (matches(line, fsm)) {
                     // System.out.println(line);
                 }
             }
         } catch (IOException e) {
             System.err.println("Error reading text file: " + e.getMessage());
             System.exit(1);
         }
     }
 
     // Checks if a substring of the given line matches the FSM
     static boolean matches(String line, List<State> fsm) {
         Deque deque = new Deque();
         List<Integer> visited = new LinkedList<>();
 
         deque.addFirst(0);
 
         //While stack not empty 
         while (!deque.isEmpty()){
             int currentStateIndex = deque.removeFirst(); 
             State currentState = fsm.get(currentStateIndex);
 
             System.out.println(visited.toString() + " " + currentState.stateNumber);
             if(visited.contains(currentState.stateNumber)){
                 System.out.println("Visited");
                 continue;
             }
 
             if (currentState.type.equals("BR")){
                 if(currentState.nextState1 == currentState.nextState2){
                     deque.addFirst(currentState.nextState1);
                 }else{
                     deque.addFirst(currentState.nextState1);
                     deque.addFirst(currentState.nextState2);
                 }  
             }else{
 
             }
 
 
             
             System.out.println("SCAN");
             // for (Integer integer : nextStates) {
             //     System.out.println(integer);
             // }
             // System.out.println("____________ ");
 
 
             visited.add(currentStateIndex);
         }
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
     // SCAN - sepperate the two stacks
     Node scan;
 
     // Constructor
     public Deque(){
         head = null;
         tail = null;
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
 }