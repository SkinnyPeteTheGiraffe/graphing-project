package org.notorious.visualization.graphing.collection;

import com.notorious.visualization.graphing.algorithm.graph.Edge;
import com.notorious.visualization.graphing.algorithm.graph.WeightedEdgeGraph;
import com.notorious.visualization.graphing.algorithm.tree.spanning.KruskalMST;
import com.notorious.visualization.graphing.collection.cache.Cache;
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
public class MSTTesting {

    private static final String TEST_DATA_REFERENCE_ROOT = "org/notorious/visualization/graphing/collection/algorithm/tree/spanning/";
    private static final String TEST_DATA_MEDIUM = "mediumEWG.txt";

    @Test
    public void testStack() {
        StdOut.println("Starting Stack Test...");
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_ROOT + TEST_DATA_MEDIUM));
        WeightedEdgeGraph G = new WeightedEdgeGraph(in);
        KruskalMST mst = new KruskalMST(G);
        for (Edge e : mst.getEdges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.getWeight());
        StdOut.println("\nCompleted Stack Test!");
    }
}
