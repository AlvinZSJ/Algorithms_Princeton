import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        RandomizedQueue<String> randQueue = new RandomizedQueue<>();

        int k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }

        int i = 0;
        for (Iterator<String> iterator = randQueue.iterator(); iterator.hasNext() && i != k;) {
            System.out.printf("%s\n", iterator.next());
            ++i;
        }

    }
}
