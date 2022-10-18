import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private boolean solvable;
    private int solveMoves;
    private List<Board> solutionPath = null;

    private class SearchNode implements Comparable<SearchNode> {
        private Board searchBoard;
        private int moves;
        private SearchNode prevNode;
        private int priority;

        public SearchNode(Board initialBoard , int moves ,SearchNode prevNode) {
            this.searchBoard = initialBoard;
            this.moves = moves;
            this.prevNode = prevNode;

            priority = moves + this.searchBoard.manhattan();
        }

        @Override
        public int compareTo(Solver.SearchNode o) {
            return this.priority - o.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if( initial == null ){
            throw new IllegalArgumentException();
        }
        
        MinPQ<SearchNode> queue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        queue.insert(new SearchNode(initial, 0, null) );
        twinQueue.insert(new SearchNode(initial.twin(), 0, null) );
        
        boolean queueSolved = false;
        boolean twinSolved = false;


        while( !queueSolved && !twinSolved ) {
            SearchNode queueMin = queue.delMin();
            SearchNode twinMin = twinQueue.delMin();

            if( queueMin.searchBoard.isGoal() ){
                queueSolved = true;
                solveMoves = queueMin.moves;
                solutionPath = new ArrayList<>();
                while( queueMin != null ){
                    solutionPath.add( queueMin.searchBoard );
                    queueMin = queueMin.prevNode;
                }
                Collections.reverse(solutionPath);
                break;
            }

            if( twinMin.searchBoard.isGoal() ){
                twinSolved = true;
                solveMoves = -1;
                solutionPath = null;
                break;
            }
                        
            for(Board cur : queueMin.searchBoard.neighbors() ) {
                if( queueMin.prevNode == null || !queueMin.prevNode.searchBoard.equals(cur) ){
                    queue.insert( new SearchNode(cur, queueMin.moves + 1, queueMin) ); 
                }   
            }

            for(Board cur : twinMin.searchBoard.neighbors() ) {
                if( twinMin.prevNode == null || !twinMin.prevNode.searchBoard.equals(cur) ){
                    twinQueue.insert( new SearchNode(cur, twinMin.moves + 1, twinMin) ); 
                }   
            }
        }
        
        solvable = !twinSolved;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        return solveMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        return solutionPath;
    }

    // test client (see below) 
    public static void main(String[] args) {
        
    }

}