import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return  n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resize the item array
    private void resize(int capacity) {
        Item[] newQueue = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null)
            throw new IllegalArgumentException("The item added is null!");

        if (n == queue.length)
            resize(2 * queue.length);

        queue[n++] = item;
    }

    // remove and return a random item
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

    // return a random item (but do not remove it)
    public Item sample() {

        if (n == 0)
            throw new NoSuchElementException("Randomized queue is empty!");

        int index = StdRandom.uniform(0, n);
        return queue[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private Item[] iterRandQueue;
        private int k = n;

        public RandomizedQueueIterator() {
            iterRandQueue = (Item[]) new Object[k];
            for (int i = 0; i < k; i++) {
                iterRandQueue[i] = queue[i];
            }
            StdRandom.shuffle(iterRandQueue);
        }

        // any more items to return?
        @Override
        public boolean hasNext() { return k != 0; }

        // return next item
        @Override
        public Item next() {

            if (!hasNext()) throw new NoSuchElementException("No more items!");
            return iterRandQueue[--k];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("No remove operation!"); }
    }

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