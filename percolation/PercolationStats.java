/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats n trials
 *  Dependencies: Percolation.java
 *                edu.princeton.cs.algs4.StdRandom
 *                edu.princeton.cs.algs4.StdStats
 *                java.lang.IllegalArgumentException
 *
 *  This program try T times to estimate the percolation probability
 *  of a n-by-n lattice. The input parameters are "n" and "trials",
 *  which set the size of lattice and the number of tests
 *
 *  Author: AlvinZSJ
 *  Date: 2018-07-13 23:33:54
 ******************************************************************************/


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    // array of threshold for each test
    private final double[] threshold;

    // T times test
    private final int t;

    /* stddev and mean value of threshold,
    *  which are used to reduce the calculation
    */
    private final double stddevThreshold;
    private final double meanThreshold;

    /**
     * Perform trials independent experiments on an n-by-n grid
     * @param n size of grids
     * @param trials times of trials
     */
    public PercolationStats(int n, int trials) {

        if (n <= 0)
            throw new IllegalArgumentException("n is out of boundary!");

        if (trials <= 0)
            throw new IllegalArgumentException("number of trails is out of boundary!");
        else
            this.t = trials;
        
        threshold = new double[t];
        int openCount = 0;

        // Take T times trials to find p
        for (int i = 0; i < t; i++) {
            
            // Percolation Object used for percolation test
            Percolation grid = new Percolation(n);

            // Randomly open sites until the lattice is percolated
            while (!grid.percolates()) {

                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);

                if (!grid.isOpen(randomRow, randomCol)) {
                    grid.open(randomRow, randomCol);
                    openCount += 1;
                }

            }

            threshold[i] = (double) openCount / (n * n);
            openCount = 0;
        }

        stddevThreshold = StdStats.stddev(threshold);
        meanThreshold = StdStats.mean(threshold);
    }

    /**
     * @return sample mean of percolation threshold
     */
    public double mean() {
        return meanThreshold;
    }

    /**
     * @return sample standard deviation of percolation threshold
     */
    public double stddev() {
        return stddevThreshold;
    }

    /**
     * @return partial interval
     */
    private double partialInterval() {
        return CONFIDENCE_95 * stddevThreshold / (Math.sqrt(t));
    }

    /**
     * @return low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return meanThreshold - partialInterval();
    }

    /**
     * @return high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return meanThreshold + partialInterval();
    }

    /**
     * test client (described below)
     * @param args times of trials and grids to open
     */
    public static void main(String[] args) {
        PercolationStats lattice = new PercolationStats(Integer.parseInt(args[0]),
                                                        Integer.parseInt(args[1]));

        System.out.printf("mean:        %f\n", lattice.meanThreshold);
        System.out.printf("stddev:      %f\n", lattice.stddevThreshold);
        System.out.printf("95%% confidence interval: [%f,%f]\n",
                          lattice.confidenceLo(),
                          lattice.confidenceHi());
    }
}