/*************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation k input.txt
 *  Dependencies: java.util.Iterator
 *                edu.princeton.cs.algs4.StdIn
 *                RandomizedQueue.java
 *
 *  Client program
 *
 *  Author: AlvinZSJ
 *
 *************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    /**
     * Take an integer k as a command-line argument
     * reads in a sequence of strings from standard input using StdIn.readString()
     * Prints exactly k of them, uniformly at random
     *
     * @param args k: number of output strings   input.txt: input strings
     */
    public static void main(String[] args) {
        // RandomizedQueue object declaration
        RandomizedQueue<String> randQueue = new RandomizedQueue<>();

        // the number of output string
        int k = Integer.parseInt(args[0]);

        // read the string from input txt file
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
