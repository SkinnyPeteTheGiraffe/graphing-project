package org.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.collection.Cache;
import com.notorious.visualization.graphing.collection.LinkedCache;
import com.notorious.visualization.graphing.collection.ResizableArrayCache;
import com.notorious.visualization.graphing.util.In;
import com.notorious.visualization.graphing.util.StdOut;
import org.junit.Test;


/**
 * ....
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3/12/2017
 */
public class CacheTesting {

    private static final String TEST_DATA_REFERENCE_LOCATION = "org/notorious/visualization/graphing/collection/tobe.txt";

    @Test
    public void testCache() {
        StdOut.println("Starting Cache Test...");
        In binaryIn = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        Cache<String> bag = new Cache<>();
        while (!binaryIn.isEmpty()) {
            String item = binaryIn.readString();
            bag.add(item);
        }
        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nCompleted Cache Test!");
    }

    @Test
    public void testLinkedCache() {
        StdOut.println("Starting Linked Cache Test...");
        In binaryIn = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        LinkedCache<String> bag = new LinkedCache<>();
        while (!binaryIn.isEmpty()) {
            String item = binaryIn.readString();
            bag.add(item);
        }
        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nCompleted Linked Cache Test!");
    }

    @Test
    public void testResizableArrayCache() {
        StdOut.println("Starting Resizable Array Cache Test...");
        In binaryIn = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        ResizableArrayCache<String> bag = new ResizableArrayCache<>();
        while (!binaryIn.isEmpty()) {
            String item = binaryIn.readString();
            bag.add(item);
        }
        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.print(s + " ");
        }
        StdOut.println("\nCompleted Resizable Array Cache Test!");
    }
}
