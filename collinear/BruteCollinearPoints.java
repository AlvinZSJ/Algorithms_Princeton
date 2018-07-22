/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    javac BruteCollinearPoints.java input.txt
 *  Dependencies: edu.princeton.cs.algs4.StdDraw
 *                edu.princeton.cs.algs4.In
 *                edu.princeton.cs.algs4.StdOut
 *                java.util.ArrayList
 *                java.util.Arrays
 *
 *  Use Brute Force to examines 4 points at a time and
 *  checks whether they all lie on the same line segment,
 *  then returning all such line segments.
 *
 *  Author: AlvinZSJ
 *************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    /**
     * Initializes BruteCollinearPoints to find 4 collinear points
     * @param points input array of points
     */
    public BruteCollinearPoints(Point[] points) {

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

        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                // the first slope
                double slopePQ = points1[i].slopeTo(points1[j]);

                for (int k = j + 1; k < n - 1; k++) {
                    // the second slope
                    double slopePR = points1[i].slopeTo(points1[k]);

                    if (Double.compare(slopePQ, slopePR) != 0)
                        continue;

                    for (int m = k + 1; m < n; m++) {
                        // the third slope
                        double slopePS = points1[i].slopeTo(points1[m]);

                        if (Double.compare(slopePQ, slopePS) != 0)
                            continue;
                        // If all slope are equal, set the line segment
                        LineSegment line = new LineSegment(points1[i], points1[m]);
                        lineSegments.add(line);

                        break;
                    }
                }

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
     * Find collinear points with brute force
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}