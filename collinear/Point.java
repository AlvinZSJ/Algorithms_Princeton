/*************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    none
 *  Dependencies: edu.princeton.cs.algs4.StdDraw
 *                java.util.Comparator
 *
 *  Data type for points with x and y coordinates
 *
 *  Author: AlvinZSJ
 *************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // x coordinate
    private final int x;
    // y coordinate
    private final int y;

     /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

     /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

     /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

     /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

     /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;

        if (this.x == that.x && this.y == that.y)
            return 0;

        return 1;
    }

     /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y != that.y) return Double.POSITIVE_INFINITY;
            else                  return Double.NEGATIVE_INFINITY;
        }
        if (this.y == that.y) return 0.0;
        else return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

     /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }

    /**
     * Nested class to implement slopOrder comparator
     */
    private class SlopeOrder implements Comparator<Point>  {

        /**
         * Use Double.compare(a, b) to compare two calculated slopes,
         * since floating-point calculations may involve rounding,
         * and the calculated values may be imprecise.
         * Do not use "==", "!=" to compare two slopes.
         * @param u point for slope comparison
         * @param v point for slope comparison
         * @return comparison result: eq : 0, > : 1, < : -1
         */
        public int compare(Point u, Point v) {

            Point tmp = new Point(x, y);
            double slopeU = tmp.slopeTo(u);
            double slopeV = tmp.slopeTo(v);
            return Double.compare(slopeU, slopeV);
        }
    }
}