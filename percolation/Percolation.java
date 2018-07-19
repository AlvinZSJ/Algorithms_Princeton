/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: edu.princeton.cs.algs4.WeightedQuickUnionUF
 *                java.lang.IllegalArgumentException
 *
 *  This program is a class used for percolation probability test
 *
 *  Date: 2018-07-13 23:33:54     Author: ZHAnG Shenjia
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // number of sites
    private int gridCount;
    private int n;

    // WeightedUnionUF Object grid
    private WeightedQuickUnionUF grid;

    // Open or close state of the sites
    private boolean[] state;

    // Record the number of opened sites
    private int numOpenSites = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("n is out of boundary!");
        else       this.n = n;

        gridCount = n * n;
        grid = new WeightedQuickUnionUF(gridCount + 2);
        state = new boolean[gridCount + 2];
        state[0] = true;
        state[gridCount + 1] = true;

        for (int i = 1; i <= gridCount; i++) {
            state[i] = false;
        }
    }

    // helper method, to map from 2D to 1D indices
    private int rcToIndex(int row, int col) {
        if (row <= 0 || row > n)
            throw new IllegalArgumentException("Row is out of boundary!");

        if (col <= 0 || col > n)
            throw new IllegalArgumentException("Col is out of boundary!");

        return (row - 1) * n + col;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {

        if (!isOpen(row, col)) {
            if (row > 1 && isOpen(row - 1, col))
                grid.union(rcToIndex(row - 1, col), rcToIndex(row, col));

            if (row < n && isOpen(row + 1, col))
                grid.union(rcToIndex(row + 1, col), rcToIndex(row, col));

            if (col > 1 && isOpen(row, col - 1))
                grid.union(rcToIndex(row, col - 1), rcToIndex(row, col));

            if (col < n && isOpen(row, col + 1))
                grid.union(rcToIndex(row, col + 1), rcToIndex(row, col));

            if (row == 1)
                grid.union(0, rcToIndex(row, col));

            if (row == n)
                grid.union(gridCount + 1, rcToIndex(row, col));

            state[rcToIndex(row, col)] = true;
            ++numOpenSites;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return state[rcToIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return grid.connected(0, rcToIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, gridCount + 1);
    }
}
