/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    none
 *  Dependencies:  edu.princeton.cs.algs4.Point2D;
 *                 edu.princeton.cs.algs4.SET;
 *                 edu.princeton.cs.algs4.RectHV;
 *
 *  A mutable data type PointSET.java that represents a set of points in the unit square
 *
 *  Author: AlvinZSJ
 ******************************************************************************/

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

    private SET<Point2D> pointSet;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        pointSet = new SET<>();
    }

    /**
     * @return true if the set is empty, otherwise false
     */
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return pointSet.size();
    }

    /**
     * Add the point to the set (if it is not already in the set)
     * @param p input point
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        pointSet.add(p);
    }

    /**
     * @param p input point
     * @return true if the set contains point p, otherwise false
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return pointSet.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    /**
     * Find all points that are inside the rectangle (or on the boundary)
     * @param rect input rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        SET<Point2D> rectPoints = new SET<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point))
                rectPoints.add(point);
        }
        return rectPoints;
    }

    /**
     * Find a nearest neighbor in the set to point p; null if the set is empty
     * @param p input point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (pointSet.isEmpty())
            return null;

        Point2D nearestPoint = null;
        for (Point2D point : pointSet) {
            if (nearestPoint == null || point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p))
                nearestPoint = point;
        }
        return nearestPoint;
    }
}