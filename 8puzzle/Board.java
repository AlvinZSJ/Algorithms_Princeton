/******************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    none
 *  Dependencies: edu.princeton.cs.algs4.Queue
 *
 *  An immutable data type which constructs a n-by-n integer board.
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.Queue;

public class Board {

    // blocks for the board
    private int[][] blocks;

    // board size (n-by-n)
    private final int n;

    // row of the empty block
    private int emptyRow;

    // column of the empty block
    private int emptyCol;

    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * @param blocks input blocks
     */
    public Board(int[][] blocks) {

        if (blocks == null)
            throw new IllegalArgumentException("Input block is null!");

        n = blocks.length;
        this.blocks = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // copy the blocks to make the object immutable
                this.blocks[i][j] = blocks[i][j];

                // find the empty block
                if (blocks[i][j] == 0) {
                    emptyRow = i;
                    emptyCol = j;
                }
            }
        }

    }

    /**
     * @return board dimension n
     */
    public int dimension() {
        return blocks.length;
    }

    /**
     * @return number of blocks out of place
     */
    public int hamming() {

        int distance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (blocks[i][j] == 0) continue;

                if (blocks[i][j] != ((i) * n + j + 1)) distance++;
            }
        }
        return distance;
    }

    /**
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {

        int distance = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (blocks[i][j] == 0) continue;

                distance += manhattanDistance(blocks[i][j], i, j);
            }
        }
        return distance;
    }

    /**
     * Calculate the manhattan distances for the input block in the board
     * sum of the vertical and horizontal distance from the block to their goal position
     * @param number the number in current block
     * @param currentRow the row index in blocks[][]
     * @param currentCol the col index in blocks[][]
     * @return manhattan distance
     */
    private int manhattanDistance(int number, int currentRow, int currentCol) {
        return Math.abs((number - 1) / n - currentRow)
                + Math.abs((number - 1) % n - currentCol);
    }

    /**
     * @return Whether this board is the goal board
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * @return a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        Board twinBoard = new Board(this.copy());
        if (emptyRow != 0) twinBoard.swap(0, 0, 0, 1);
        if (emptyRow == 0) twinBoard.swap(1, 0, 1, 1);
        return twinBoard;
    }

    /**
     * Swap the number in blocks of the input row and column
     * update the position of empty block
     * @param r1 row index of the block
     * @param c1 column index of the block
     * @param r2 row index of the other block
     * @param c2 column index of the other block
     */
    private void swap(int r1, int c1, int r2, int c2) {
        int tmp = blocks[r1][c1];
        blocks[r1][c1] = blocks[r2][c2];
        blocks[r2][c2] = tmp;

        if (blocks[r1][c1] == 0) {
            emptyRow = r1;
            emptyCol = c1;
        }

        if (blocks[r2][c2] == 0) {
            emptyRow = r2;
            emptyCol = c2;
        }
    }

    /**
     * See if this block equals to y
     * @param y the other object
     * @return equal: true, otherwise: false
     */
    public boolean equals(Object y) {

        if (this == y) return true;

        if (y == null || this.getClass() != y.getClass()) return false;

        Board that = (Board) y;
        if (this.n != that.n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    /**
     * Find all neighboring blocks
     * @return a queue of neighboring blocks
     */
    public Iterable<Board> neighbors() {
        Queue<Board> queueBoard = new Queue<>();

        if (emptyRow > 0) {
            Board neighborUp = new Board(this.copy());
            neighborUp.swap(emptyRow, emptyCol, emptyRow - 1, emptyCol);
            queueBoard.enqueue(neighborUp);
        }

        if (emptyRow < n - 1) {
            Board neighborDown = new Board(this.copy());
            neighborDown.swap(emptyRow, emptyCol, emptyRow + 1, emptyCol);
            queueBoard.enqueue(neighborDown);
        }

        if (emptyCol > 0) {
            Board neighborLeft = new Board(this.copy());
            neighborLeft.swap(emptyRow, emptyCol, emptyRow, emptyCol - 1);
            queueBoard.enqueue(neighborLeft);
        }

        if (emptyCol < n - 1) {
            Board neighborRight = new Board(this.copy());
            neighborRight.swap(emptyRow, emptyCol, emptyRow, emptyCol + 1);
            queueBoard.enqueue(neighborRight);
        }

        return queueBoard;
    }

    /**
     * Copy the current blocks
     * @return the copied blocks
     */
    private int[][] copy() {
        int[][] cpblocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cpblocks[i][j] = blocks[i][j];
            }
        }
        return cpblocks;
    }

    /**
     * @return string representation of this board
     *         (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}