/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints input.txt
 *  Dependencies: edu.princeton.cs.algs4.StdDraw
 *                edu.princeton.cs.algs4.In
 *                edu.princeton.cs.algs4.StdOut
 *                java.util.Collections
 *                java.util.ArrayList
 *                java.util.Arrays
 *
 *  A faster solution than Brute Force to find 4 or more collinear points.
 *
 *  Author: AlvinZSJ
 *************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    // LineSegment ArrayList to store the line segments
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    /**
     * Finds all line segments containing 4 or more points
     * @param points input array of points
     */
    public FastCollinearPoints(Point[] points) {

        // check if the input is null
        if (points == null)
            throw new IllegalArgumentException("Input points is null!");

        // copy points to solve the immutable data type problem
        Point[] points1 = Arrays.copyOf(points, points.length);

        // check if any point is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("Input points is null!");
        }

        // sort the points array
        Arrays.sort(points1);

        // check duplicated points
        for (int i = 0; i < points1.length - 1; i++) {
            if (points1[i].compareTo(points1[i + 1]) == 0)
                throw new IllegalArgumentException("Find duplicated points!");
        }

        int n = points1.length;

        // copy points array to set each point to be the invoking point
        Point[] points2 = Arrays.copyOf(points1, points1.length);

        for (int i = 0; i < n; i++) {
            // sort array according to the slope order
            Arrays.sort(points1, points2[i].slopeOrder());

            // store the possible collinear points
            ArrayList<Point> collinearPoints = new ArrayList<>();
            for (int j = 0; j < n; j++) {

                if (collinearPoints.isEmpty()) {
                    collinearPoints.add(points1[j]);
                    continue;
                }

                // slope of invoking point between two adjacent points
                double slopeA = points1[0].slopeTo(points1[j - 1]);
                double slopeB = points1[0].slopeTo(points1[j]);

                // if the two adjacent slope is equal, store the point
                if (Double.compare(slopeA, slopeB) == 0) {
                    collinearPoints.add(points1[j]);
                    if (j != n - 1) continue;
                }

                // See if the possible collinear points can from a line segment
                if (collinearPoints.size() > 2) {

                    collinearPoints.add(points1[0]);
                    // find the two end points
                    Collections.sort(collinearPoints);

                    // avoid duplicated line segments
                    if (points1[0].compareTo(Collections.min(collinearPoints)) == 0) {

                        LineSegment line = new LineSegment(Collections.min(collinearPoints),
                                Collections.max(collinearPoints));
                        lineSegments.add(line);

                    }
                }
                // clear collinearPoints to find rest possible collinear points
                collinearPoints.clear();
                collinearPoints.add(points1[j]);
            }

        }
    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * Convert LineSegment ArrayList to LineSegment Array
     * @return LineSegment array
     */
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[lineSegments.size()];
        for (int i = 0; i < lineSegments.size(); i++) {
            lines[i] = lineSegments.get(i);
        }
        return lines;
    }

    /**
     * Find all collinear points
     * @param args command line input input.txt
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
