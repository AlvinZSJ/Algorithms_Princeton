/*************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: java.util.Iterator
 *                edu.princeton.cs.algs4.StdRandom
 *                java.util.NoSuchElementException
 *
 *  A data type which is similar to a stack or queue,
 *  except that the item removed is chosen uniformly at random
 *  from items in the data structure
 *
 *  Author: AlvinZSJ
 *
 *************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // generic array
    private Item[] queue;
    // the size of generic array
    private int n = 0;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    /**
     * See if the randomized queue empty
     * @return empty: 1, otherwise: 0
     */
    public boolean isEmpty() {
        return  n == 0;
    }

    /**
     * @return the number of items on the randomized queue
     */
    public int size() {
        return n;
    }

    /**
     * Resize the item array
     * @param capacity the size of new array
     */
    private void resize(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    /**
     * Add the item
     * Double the array size if it is full
     * @param item the item to be added
     */
    public void enqueue(Item item) {

        if (item == null)
            throw new IllegalArgumentException("The item added is null!");

        if (n == queue.length)
            resize(2 * queue.length);

        queue[n++] = item;
    }

    /**
     * Remove and return a random item
     * Set the size of array to 1/2 of the original array if 1/4 items left
     * @return a random item
     */
    public Item dequeue() {

        if (n == 0)
            throw new NoSuchElementException("Randomized queue is empty!");

        int index = StdRandom.uniform(0, n);

        Item item = queue[index];
        queue[index] = queue[n - 1];
        queue[--n] = null;

        if (n > 0 && n == queue.length/4)
            resize(queue.length/2);

        return item;
    }

    /**
     * @return a random item (but do not remove it)
     */
    public Item sample() {

        if (n == 0)
            throw new NoSuchElementException("Randomized queue is empty!");

        int index = StdRandom.uniform(0, n);

        return queue[index];
    }

    /**
     * @return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Nested class to implement RandomizedQueue iterator
     */
    private class RandomizedQueueIterator implements Iterator<Item> {

        // copy of generic array in the iterator
        private Item[] iterRandQueue;
        // copy size of the array
        private int k = n;

        /**
         * Initialize an array of all items in queue in random order
         */
        public RandomizedQueueIterator() {

            iterRandQueue = (Item[]) new Object[k];

            for (int i = 0; i < k; i++) {
                iterRandQueue[i] = queue[i];
            }

            // set the copy to random order
            StdRandom.shuffle(iterRandQueue);
        }

        /**
         * See if any more items to return
         * @return if any: 1, otherwise: 0
         */
        @Override
        public boolean hasNext() { return k != 0; }

        /**
         * @return next item
         */
        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException("No more items!");
            return iterRandQueue[--k];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("No remove operation!"); }
    }

    /**
     * Test for the nested iterators
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        String[] df = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};
        for (int i = 0; i < 13; i++) {
            queue.enqueue(df[i]);
        }

        for (Iterator<String> iterator1 = queue.iterator(); iterator1.hasNext();) {
            System.out.printf("\n%s\n", iterator1.next());
            for (Iterator<String> iterator2 = queue.iterator(); iterator2.hasNext();) {
                System.out.printf("%s", iterator2.next());
            }
        }


    }
}