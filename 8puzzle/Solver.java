/******************************************************************************
 *  Compilation:  javac-algs4 Solver.java
 *  Execution:    none
 *  Dependencies: edu.princeton.cs.algs4.MinPQ
 *                edu.princeton.cs.algs4.Stack
 *
 *  An immutable data type to implement A* algorithm
 *  to find the best solution of 8-puzzle problem
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    // See if the input board is solvable
    private boolean solvable;
    // Final search node
    private SearchNode goalNode;

    /**
     * Nested class SearchNode
     * Construct the game tree
     */
    private class SearchNode implements Comparable<SearchNode> {

        // board of current search node
        private final Board board;

        // steps moving from initial board to current board
        private final int moves;

        // previous search node
        private final SearchNode predecessor;

        // manhattan distance of current search node
        // used for the second optimization
        // (operations of manhattan distance calculation)
        private final int manhattanDistance;

        /**
         * Initialize SearchNode
         * @param board current board
         * @param moves steps moving from initial board to current board
         * @param predecessor previous search node
         */
        public SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.predecessor = predecessor;
            this.manhattanDistance = this.board.manhattan();
        }

        /**
         * Compare two search node
         * @param that the other search node
         * @return compare result
         */
        public int compareTo(SearchNode that) {
            int thisManhattanPriority = this.manhattanDistance + this.moves;
            int thatManhattanPriority = that.manhattanDistance + that.moves;
            return Integer.compare(thisManhattanPriority, thatManhattanPriority);
        }

    }


    /**
     * Find a solution to the initial board (using the A* algorithm)
     * @param initial initial board
     */
    public Solver(Board initial) {

        if (initial == null)
            throw new IllegalArgumentException("The input is null!");

        // Priority queue used for the next search node selection
        // initPQ: PQ for initial search node
        // twinPQ: PQ for the twin search node
        MinPQ<SearchNode> initPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        // Priority queue initialization
        initPQ.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        // reference to the next search node
        SearchNode initSearchNode;
        SearchNode twinSearchNode;

        while (true) {

            // Choose the minimum priority search node
            initSearchNode = initPQ.delMin();
            twinSearchNode = twinPQ.delMin();

            if (initSearchNode.board.isGoal()) {
                goalNode = initSearchNode;
                solvable = true;
                break;
            }

            if (twinSearchNode.board.isGoal()) {
                goalNode = twinSearchNode;
                solvable = false;
                break;
            }

            // Enqueue all neighbors for initial board
            // except the board which is the same as the predecessor board
            for (Board neighbor : initSearchNode.board.neighbors()) {
                if (initSearchNode.predecessor == null
                        || !neighbor.equals(initSearchNode.predecessor.board)) {
                    initPQ.insert(new SearchNode(neighbor, initSearchNode.moves + 1, initSearchNode));
                }
            }

            // Enqueue all neighbors for the twin board
            // except the board which is the same as the predecessor board
            for (Board neighbor : twinSearchNode.board.neighbors()) {
                if (twinSearchNode.predecessor == null
                        || !neighbor.equals(twinSearchNode.predecessor.board)) {
                    twinPQ.insert(new SearchNode(neighbor, twinSearchNode.moves + 1, twinSearchNode));
                }
            }
        }
    }

    /**
     * @return Whether the initial board solvable or not
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (solvable) return goalNode.moves;
        else          return -1;
    }

    /**
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!solvable) return null;

        Stack<Board> solutionStack = new Stack<>();
        SearchNode currentNode = goalNode;
        for (int i = 0; i < goalNode.moves + 1; i++) {
            solutionStack.push(currentNode.board);
            currentNode = currentNode.predecessor;
        }
        return solutionStack;
    }

}