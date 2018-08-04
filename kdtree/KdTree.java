/******************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    none
 *  Dependencies:  edu.princeton.cs.algs4.Point2D;
 *                 edu.princeton.cs.algs4.Queue;
 *                 edu.princeton.cs.algs4.RectHV;
 *                 edu.princeton.cs.algs4.StdDraw;
 *
 *  A mutable data type that uses a 2d-tree as a generalization of
 *  a BST to two-dimensional keys.
 *
 *  The idea is to build a BST with points in the nodes, using the x- and y-coordinates
 *  of the points as keys in strictly alternating sequence.
 *
 *  Author: AlvinZSJ
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;
    private int n;

    /**
     * Node for KdTree
     */
    private class Node {

        // point of the current node
        private final Point2D currKey;
        // rectangle corresponding to the node
        private final RectHV currRect;
        // left and right node of current node
        private Node leftNode, rightNode;

        public Node(Node left, Node right, Point2D key, RectHV rect) {
            leftNode = left;
            rightNode = right;
            currKey = key;
            currRect = rect;
        }
    }

    /**
     * construct an empty KdTree
     */
    public KdTree() {
        root = null;
        n = 0;
    }

    /**
     * @return true if no points in the KdTree, otherwise false
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * @return number of points in the KdTree
     */
    public int size() {
        return n;
    }

    /**
     * add the point to the KdTree (if it is not already in the set)
     * @param p point to be added
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    /**
     * Recursively search position for new point to insert,
     * then insert the new point
     *
     * @param node current node
     * @param key point of current node
     * @param x0 min x for current node rectangle
     * @param y0 min y for current node rectangle
     * @param x1 max x for current node rectangle
     * @param y1 max y for current node rectangle
     * @param isVertical flag to indicate the point should go left or right
     * @return node with the input point and corresponding rectangle
     */
    private Node insert(Node node, Point2D key, double x0, double y0,
                        double x1, double y1, boolean isVertical) {

        if (node == null) {
            n++;
            RectHV rect = new RectHV(x0, y0, x1, y1);
            return new Node(null, null, key, rect);
        }
        else if (Double.compare(key.x(), node.currKey.x()) == 0 &&
                 Double.compare(key.y(), node.currKey.y()) == 0) {
            return node;
        }

        if (isVertical) {
            if (key.x() < node.currKey.x())
                node.leftNode = insert(node.leftNode, key, x0, y0, node.currKey.x(), y1, !isVertical);
            else
                node.rightNode = insert(node.rightNode, key, node.currKey.x(), y0, x1, y1, !isVertical);
        }
        else {
            if (key.y() < node.currKey.y())
                node.leftNode = insert(node.leftNode, key, x0, y0, x1, node.currKey.y(), !isVertical);
            else
                node.rightNode = insert(node.rightNode, key, x0, node.currKey.y(), x1, y1, !isVertical);
        }

        return node;
    }

    /**
     * Does the KdTree contain point p?
     *
     * @param p point to search
     * @return true if KdTree contain point p, otherwise false
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p, true);
    }

    /**
     * Recursively search for the input point
     *
     * @param node current node of the KdTree
     * @param p input point
     * @param isVertical flag to indicate next search should go left or right
     * @return true if the KdTree contains point p, otherwise false
     */
    private boolean contains(Node node, Point2D p, boolean isVertical) {
        if (node == null)
            return false;

        if (Double.compare(p.x(), node.currKey.x()) == 0 &&
                Double.compare(p.y(), node.currKey.y()) == 0)
            return true;

        if (isVertical) {
            if (p.x() < node.currKey.x())
                return contains(node.leftNode, p, !isVertical);
            else
                return contains(node.rightNode, p, !isVertical);
        }
        else {
            if (p.y() < node.currKey.y())
                return contains(node.leftNode, p, !isVertical);
            else
                return contains(node.rightNode, p, !isVertical);
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root, Double.NaN, true, true);
    }

    /**
     * Recursively draw all lines of KdTree
     *
     * @param node current node
     * @param splitValue the x- or y-coordinate of previous point
     * @param isVertical flag to indicate the line of current node is vertical or not
     * @param isLeft flag to indicate if the next node is the left node
     */
    private void draw(Node node, double splitValue, boolean isVertical, boolean isLeft) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.currKey.draw();

        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();

        if (Double.isNaN(splitValue)) {
            StdDraw.line(node.currKey.x(), 0, node.currKey.x(), 1);
            splitValue = node.currKey.x();
        }
        else if (isVertical) {
            if (isLeft)
                StdDraw.line(node.currKey.x(), splitValue, node.currKey.x(), 0);
            else
                StdDraw.line(node.currKey.x(), splitValue, node.currKey.x(), 1);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            if (isLeft)
                StdDraw.line(splitValue, node.currKey.y(), 0, node.currKey.y());
            else
                StdDraw.line(splitValue, node.currKey.y(), 1, node.currKey.y());
        }

        draw(node.leftNode, splitValue, !isVertical, true);
        draw(node.rightNode, splitValue, !isVertical, false);
    }


    /**
     * Find all points that are inside the rectangle (or on the boundary)
     *
     * @param rect input rectangle
     * @return a queue contains all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> intersection = new Queue<>();
        range(root, intersection, rect);
        return intersection;
    }

    /**
     * Recursively find the points in the input rectangle
     * @param node current node
     * @param intersection queue contains all points that are inside the rectangle (or on the boundary)
     * @param rect the input rectangle
     */
    private void range(Node node, Queue<Point2D> intersection, RectHV rect) {
        if (node == null) return;

        if (node.currRect.intersects(rect)) {

            if (rect.contains(node.currKey))
                intersection.enqueue(node.currKey);

            range(node.leftNode, intersection, rect);
            range(node.rightNode, intersection, rect);
        }
    }
    /**
     * Find a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p input point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;

        Point2D nearestPoint = root.currKey;
        nearestPoint = nearest(root, nearestPoint, p, true);
        return nearestPoint;
    }

    /**
     * Recursively find the nearest point to point p in the KdTree
     *
     * @param node current node
     * @param nearestPoint nearest point to point p
     * @param p input point
     * @param isVertical flag to indicate current line is vertical or not
     * @return the nearest point to point p in the KdTree
     */
    private Point2D nearest(Node node, Point2D nearestPoint, Point2D p, boolean isVertical) {
        if (node == null)
            return nearestPoint;

        Node close;
        Node far;
        if (node.currRect.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {

            if (node.currKey.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p))
                nearestPoint = node.currKey;

            if ((isVertical && p.x() < node.currKey.x()) || (!isVertical && p.y() < node.currKey.y())) {
                close = node.leftNode;
                far = node.rightNode;
            }
            else {
                close = node.rightNode;
                far = node.leftNode;
            }

            nearestPoint = nearest(close, nearestPoint, p, !isVertical);
            nearestPoint = nearest(far, nearestPoint, p, !isVertical);
        }
        return nearestPoint;
    }
}