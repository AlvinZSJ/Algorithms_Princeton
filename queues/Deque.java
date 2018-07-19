import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n = 0;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // inner class Node
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("The added item is null!");
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;

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

    // add the item to the end
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("The item added is null!");
        }

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

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

    // remove and return the item from the front
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

    // remove and return the item from the end
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

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        // current node
        private Node current = first;

        // any more items to return?
        @Override
        public boolean hasNext() { return current != null; }

        // return next item
        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException("No more items!");

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("No remove operation!"); }
    }

}