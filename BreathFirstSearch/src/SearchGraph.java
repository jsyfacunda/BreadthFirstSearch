//  SearchGraph class    Creed Jones    CBU    CSC512 SP18    Jan 28, 2018
//   this class supports solving the 8-puzzle problem

import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Iterator;

public class SearchGraph {

    public static final int TESTDATA = 1;

    public SGSolution breadthFirstSearch(SGProblem p) {  // as discussed on page 82
        LinkedList<SGNode> frontier = new LinkedList<>();
        LinkedList<SGState> explored = new LinkedList<SGState>();

        //YOUR CODE GOES HERE…
        SGState initialState = new SGState(TESTDATA);                               // set initial state to case 1
        SGNode rootNode = new SGNode(initialState, null, new SGAction(""), 0); //create root node

        explored.add(0, initialState);  // add initial state to list

        SGSolution sol = new SGSolution(rootNode);
        if (p.goalTest(explored.get(0))) {return sol;}  //checks if initial state is goal

        ArrayList<SGAction> moves = new ArrayList<>();
        moves = p.allowedActions(initialState);  //gives moves from initial state
        for(int i = 0; i < moves.size(); i++) {
            frontier.offerLast(rootNode.childNode(p, moves.get(i)));   //add moves from initial state to queue
        }



        SGNode temp;
        while(true) {
            temp = frontier.poll();      //poll frontier into temp
            moves = p.allowedActions(temp.getState());

            for(int i = 0; i < moves.size(); i++) {
                int x = 0;
                for( SGNode checkNode : frontier) {    //for each loop to not add same states to queue
                    if (temp.childNode(p, moves.get(i)).getState().equals(checkNode.getState())) {
                        x++;
                    }
                }
                if (x == 0) {
                    frontier.offerLast(temp.childNode(p, moves.get(i)));   //add moves from child node to queue
                }
            }

            //System.out.println(temp.getState().toString());

            if (temp.getState().checkDone()) {      //check if goal reached
                sol = new SGSolution(temp);  //poll frontier into sol
                return sol;
            }
        }
    }

    public SGSolution uniformCostSearch(SGProblem p) {     // as discussed on page 84
        final int INITIALSIZE = 1000;       // arbitrary - will resize if needed

        PriorityQueue<SGNode> frontier =
                new PriorityQueue<SGNode>(INITIALSIZE, new SGNode.SortByCost());
        LinkedList<SGState> explored = new LinkedList<SGState>();

        //YOUR CODE GOES HERE…
        SGState initialState = new SGState(TESTDATA);                               // set initial state to case 1
        SGNode rootNode = new SGNode(initialState, null, new SGAction(""), 0); //create root node

        explored.add(0, initialState);  // add initial state to list

        SGSolution sol = new SGSolution(rootNode);
        if (p.goalTest(explored.get(0))) {return sol;}  //checks if initial state is goal

        ArrayList<SGAction> moves = new ArrayList<>();
        moves = p.allowedActions(initialState);  //gives moves from initial state
        for(int i = 0; i < moves.size(); i++) {
            frontier.offer(rootNode.childNode(p, moves.get(i)));   //add moves from initial state to queue
        }



        SGNode temp;
        while(true) {
            temp = frontier.poll();      //poll frontier into temp
            moves = p.allowedActions(temp.getState());

            for(int i = 0; i < moves.size(); i++) {
                int x = 0;
                for( SGNode checkNode : frontier) {    //for each loop to not add same states to queue
                    if (temp.childNode(p, moves.get(i)).getState().equals(checkNode.getState())) {
                        x++;
                    }
                }
                if (x == 0) {
                    frontier.offer(temp.childNode(p, moves.get(i)));   //add moves from child node to queue
                }
            }

            //System.out.println(temp.getState().toString());

            if (temp.getState().checkDone()) {      //check if goal reached
                sol = new SGSolution(temp);  //poll frontier into sol
                return sol;
            }
        }
    }


    public static void main(String[] args) {
        SearchGraph sg = new SearchGraph();

        //SGSolution soln = sg.uniformCostSearch(new SGProblem());
        SGSolution soln = sg.breadthFirstSearch(new SGProblem());

        System.out.println("Solution is " + soln.getLength() + " long;");
        System.out.println(soln.toString());

    }
}