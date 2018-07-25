/*************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    none
 *  Dependencies: java.util.Iterator
 *                java.util.NoSuchElementException
 *
 *  A generic data type, which is is a generalization of a stack and a queue
 *  that supports adding and removing items from either the front
 *  or the back of the data structure
 *
 *  Author: AlvinZSJ
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // first node of the double ended-queue
    private Node first;
    // last node of the double ended-queue
    private Node last;

    // size of item array
    private int n = 0;

    /**
     * Construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
    }

    /**
     *  inner class Node to implement linked list
     */
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    /**
     * See if the deque is empty
     * @return empty: 1, otherwise: 0
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * @return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * Add the item to the front
     * @param item input item
     */
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("The added item is null!");
        }

        // add first node
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

        // link the new first node
        // both next and prev should be specified
        if (isEmpty()) {
            first.next = null;
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        n++;
    }

    /**
     * Add the item to the end
     * @param item input item
     */
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("The item added is null!");
        }

        // add last node
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        // link the new last node
        // both next and prev should be specified
        if (isEmpty()) {
            last.prev = null;
            first = last;
        }
        else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        n++;
    }

    /**
     * Remove and return the item from the front
     * @return first item
     */
    public Item removeFirst() {

        if (n == 0)
            throw new NoSuchElementException("The Deque is empty!");

        Item item = first.item;

        if (n == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        n--;
        return item;
    }

    /**
     * Remove and return the item from the end
     * @return last item
     */
    public Item removeLast() {

        if (n == 0)
            throw new NoSuchElementException("The Deque is empty!");

        Item item = last.item;
        if (n == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        n--;
        return item;
    }

    /**
     * @return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Nested class ListIterator to implement iterator
     */
    private class ListIterator implements Iterator<Item> {

        // current node
        private Node current = first;

        /**
         *  See if any more items to return
         * @return if any: 1, otherwise: 0
         */
        @Override
        public boolean hasNext() { return current != null; }

        /**
         * @return next item
         */
        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException("No more items!");

            // move current reference to next node
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("No remove operation!"); }
    }
}