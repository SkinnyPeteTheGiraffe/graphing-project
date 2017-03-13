package org.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.collection.cache.Cache;
import com.notorious.visualization.graphing.collection.queue.LinkedQueue;
import com.notorious.visualization.graphing.collection.queue.Queue;
import com.notorious.visualization.graphing.collection.queue.ResizingArrayQueue;
import com.notorious.visualization.graphing.util.In;
import com.notorious.visualization.graphing.util.StdIn;
import com.notorious.visualization.graphing.util.StdOut;
import org.junit.Test;


/**
 * ....
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3/12/2017
 */
public class QueueTesting {

    private static final String TEST_DATA_REFERENCE_LOCATION = "org/notorious/visualization/graphing/collection/tobe.txt";

    @Test
    public void testQueue() {
        StdOut.println("Starting Queue Test...");
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        Queue<String> queue = new Queue<String>();
        while (!in.isEmpty()) {
            String item = in.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue)");
        StdOut.println("Completed Queue Test!");
    }

    @Test
    public void testLinkedQueue() {
        StdOut.println("Starting Linked Queue Test...");
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        LinkedQueue<String> queue = new LinkedQueue<>();
        while (!in.isEmpty()) {
            String item = in.readString();
            if (!item.equals("-"))
                queue.enqueue(item);
            else if (!queue.isEmpty())
                StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
        StdOut.println("Completed Linked Queue Test!");
    }

    @Test
    public void testResizableArrayQueue() {
        StdOut.println("Starting Resizable Array Queue Test...");
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_LOCATION));
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        while (!in.isEmpty()) {
            String item = in.readString();
            if (!item.equals("-")) queue.enqueue(item);
            else if (!queue.isEmpty()) StdOut.print(queue.dequeue() + " ");
        }
        StdOut.println("(" + queue.size() + " left on queue)");
        StdOut.println("Completed Resizable Array Queue Test!");
    }
}
