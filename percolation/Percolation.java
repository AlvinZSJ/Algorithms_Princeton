/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: edu.princeton.cs.algs4.WeightedQuickUnionUF
 *                java.lang.IllegalArgumentException
 *
 *  A data type with n-by-n grid of sites simulating the percolation process
 *
 *  Author: AlvinZSJ
 *  Date: 2018-07-13 23:33:54
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // number of sites
    private final int gridCount;
    private final int n;

    // WeightedUnionUF Object grids
    private WeightedQuickUnionUF grids;

    /* If only one (WeightedQuickUnionUF) "grids" is used,
    * isFull() will return a wrong value when the lattice percolates.
    * At that time all sites which connected to the bottom virtual site
    * will also connect to the top virtual site,
    * since the top and bottom virtual sites are connected.
    * Therefore, a "supervisor grids" without bottom virtual site is used
     * to check if the current site is full. (connected to the top virtual site)
    */
    private WeightedQuickUnionUF supervisorGrids;

    // Open or close state of the sites
    private boolean[] state;

    // Record the number of opened sites
    private int numOpenSites = 0;

    /**
     * Create n-by-n grid, with all sites blocked
     * @param n size of grids
     */
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException("n is out of boundary!");
        else       this.n = n;

        gridCount = n * n;

        grids = new WeightedQuickUnionUF(gridCount + 2);
        supervisorGrids = new WeightedQuickUnionUF(gridCount + 1);

        state = new boolean[gridCount + 2];
        state[0] = true;
        state[gridCount + 1] = true;

        for (int i = 1; i <= gridCount; i++) {
            state[i] = false;
        }
    }

    /**
     * Helper method, to map from 2D to 1D indices
     * @param row row number
     * @param col column number
     * @return index in grids
     */
    private int rcToIndex(int row, int col) {
        if (row <= 0 || row > n)
            throw new IllegalArgumentException("Row is out of boundary!");

        if (col <= 0 || col > n)
            throw new IllegalArgumentException("Col is out of boundary!");

        return (row - 1) * n + col;
    }

    /**
     * Open site (row, col) if it is not open already
     * @param row row of site
     * @param col column of site
     */
    public void open(int row, int col) {

        if (!isOpen(row, col)) {

            int index = rcToIndex(row, col);

            if (row > 1 && isOpen(row - 1, col)) {
                grids.union(rcToIndex(row - 1, col), index);
                supervisorGrids.union(rcToIndex(row - 1, col), index);
            }

            if (row < n && isOpen(row + 1, col)) {
                grids.union(rcToIndex(row + 1, col), index);
                supervisorGrids.union(rcToIndex(row + 1, col), index);
            }


            if (col > 1 && isOpen(row, col - 1)) {
                grids.union(rcToIndex(row, col - 1), index);
                supervisorGrids.union(rcToIndex(row, col - 1), index);
            }


            if (col < n && isOpen(row, col + 1)) {
                grids.union(rcToIndex(row, col + 1), index);
                supervisorGrids.union(rcToIndex(row, col + 1), index);
            }


            if (row == 1) {
                grids.union(0, index);
                supervisorGrids.union(0, index);
            }

            if (row == n)
                grids.union(gridCount + 1, index);

            state[index] = true;
            ++numOpenSites;
        }
    }

    /**
     * @param row row of site
     * @param col column of site
     * @return Whether site (row, col) is open
     */
    public boolean isOpen(int row, int col) {
        return state[rcToIndex(row, col)];
    }

    /**
     * @param row row of site
     * @param col column of site
     * @return Whether site (row, col) is full
     */
    public boolean isFull(int row, int col) {
        return supervisorGrids.connected(0, rcToIndex(row, col));
    }

    /**
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * @return Whether the system is percolated
     */
    public boolean percolates() {
        return grids.connected(0, gridCount + 1);
    }
}
