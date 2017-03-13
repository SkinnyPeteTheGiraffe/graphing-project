package org.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.collection.cache.Cache;
import com.notorious.visualization.graphing.collection.stack.Stack;
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
public class StackTesting {

    private static final String TEST_DATA_REFERENCE_LOCATION = "org/notorious/visualization/graphing/collection/tobe.txt";

    @Test
    public void testStack() {
        StdOut.println("Starting Stack Test...");
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        Stack<String> stack = new Stack<String>();
        while (!in.isEmpty()) {
            String item = in.readString();
            if (!item.equals("-")) {
                stack.push(item);
            } else if (!stack.isEmpty()) {
                StdOut.print(stack.pop() + " ");
            }
        }
        StdOut.println("(" + stack.size() + " left on stack)");
        StdOut.println("\nCompleted Stack Test!");
    }
}
