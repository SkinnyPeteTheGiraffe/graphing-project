package com.notorious.visualization.graphing.algorithm.tree.spanning.kruskal; /******************************************************************************
 *  Compilation:  javac KruskalMST.java
 *  Execution:    java  KruskalMST filename.txt
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java
 *                UF.java In.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/43mst/tinyEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/mediumEWG.txt
 *                http://algs4.cs.princeton.edu/43mst/largeEWG.txt
 *
 *  Compute a minimum spanning forest using Kruskal's algorithm.
 *
 *  %  java KruskalMST tinyEWG.txt 
 *  0-7 0.16000
 *  2-3 0.17000
 *  1-7 0.19000
 *  0-2 0.26000
 *  5-7 0.28000
 *  4-5 0.35000
 *  6-2 0.40000
 *  1.81000
 *
 *  % java KruskalMST mediumEWG.txt
 *  168-231 0.00268
 *  151-208 0.00391
 *  7-157   0.00516
 *  122-205 0.00647
 *  8-152   0.00702
 *  156-219 0.00745
 *  28-198  0.00775
 *  38-126  0.00845
 *  10-123  0.00886
 *  ...
 *  10.46351
 *
 ******************************************************************************/

import com.notorious.visualization.graphing.algorithm.graph.Edge;
import com.notorious.visualization.graphing.algorithm.graph.WeightedEdgeGraph;
import com.notorious.visualization.graphing.collection.cache.Cache;
import com.notorious.visualization.graphing.collection.queue.Queue;
import com.notorious.visualization.graphing.util.In;
import com.notorious.visualization.graphing.util.MinPQ;
import com.notorious.visualization.graphing.util.StdDraw;
import com.notorious.visualization.graphing.util.union.UF;

import java.awt.*;
import java.util.Iterator;

/**
 *  The {@code KruskalMST} class represents a data type for computing a
 *  <em>minimum spanning tree</em> in an edge-weighted graph.
 *  The edge weights can be positive, zero, or negative and need not
 *  be distinct. If the graph is not connected, it computes a <em>minimum
 *  spanning forest</em>, which is the union of minimum spanning trees
 *  in each connected component. The {@code weight()} method returns the 
 *  weight of a minimum spanning tree and the {@code edges()} method
 *  returns its edges.
 *  <p>
 *  This implementation uses <em>Krusal's algorithm</em> and the
 *  union-find data type.
 *  The constructor takes time proportional to <em>E</em> log <em>E</em>
 *  and extra space (not including the graph) proportional to <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the {@code weight()} method takes constant time
 *  and the {@code edges()} method takes time proportional to <em>V</em>.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For alternate implementations, see {@link LazyPrimMST}, {@link PrimMST},
 *  and {@link BoruvkaMST}.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class KruskalMST {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private final int vertices;
    private double weight;                        // weight of MST
    private Queue<Edge> mst;// edges in MST

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param edgeGraph the edge-weighted graph
     */
    public KruskalMST(WeightedEdgeGraph edgeGraph) {
        mst = new Queue<>();
        this.vertices = edgeGraph.getVerticesCount();
        // more efficient to build heap by passing array of edges
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : edgeGraph.getEdges()) {
            pq.insert(e);
        }

        // run greedy algorithm
        UF uf = new UF(edgeGraph.getVerticesCount());
        while (!pq.isEmpty() && mst.size() < edgeGraph.getVerticesCount() - 1) {
            Edge e = pq.delMin();
            int v = e.getEndpointA();
            int w = e.getOtherEndpoint(v);
            if (!uf.connected(v, w)) { // v-w does not create a cycle
                uf.union(v, w);  // merge v and w components
                mst.enqueue(e);  // add edge e to mst
                weight += e.getWeight();
            }
        }
        // check optimality conditions
        assert check(edgeGraph);
    }

    public void render_() {

    }

    public void render(){
        int range = (int)Math.ceil(Math.sqrt(vertices));
        System.out.println(range);
        StdDraw.setXscale(-1, range);
        StdDraw.setYscale(-1, range);

        Iterable<Edge> mst_edges = getEdges();
        Iterator<Edge> it = mst_edges.iterator();
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.005);
        while(it.hasNext()){
            Edge e = it.next();
            int v = e.getEndpointA();
            int w = e.getOtherEndpoint(v);
            StdDraw.textLeft((w % range + w % range) / 2, (v % range + v % range) / 2, e.getWeight() + "");
            StdDraw.line(v % range, v / range,w % range, w / range);
        }
        StdDraw.setPenRadius(0.01);
        for(int i = 0; i < vertices; ++i) {
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.point(i % range, i / range);
            StdDraw.setPenColor(Color.RED);
            StdDraw.textLeft(i % range, i / range, i + " ");
        }
    }

    public Point getMidPoint(int a, int b, int v, int w) {
        return new Point((a + v) / 2, (b + w) / 2);
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Edge> getEdges() {
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double getWeight() {
        return weight;
    }
    
    // check optimality conditions (takes time proportional to E V lg* V)
    private boolean check(WeightedEdgeGraph edgeGraph) {

        // check total weight
        double total = 0.0;
        for (Edge e : getEdges()) {
            total += e.getWeight();
        }
        if (Math.abs(total - getWeight()) > FLOATING_POINT_EPSILON) {
            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, getWeight());
            return false;
        }

        // check that it is acyclic
        UF uf = new UF(edgeGraph.getVerticesCount());
        for (Edge e : getEdges()) {
            int v = e.getEndpointA(), w = e.getOtherEndpoint(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }

        // check that it is a spanning forest
        for (Edge e : edgeGraph.getEdges()) {
            int v = e.getEndpointA(), w = e.getOtherEndpoint(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }

        // check that it is a minimal spanning forest (cut optimality conditions)
        for (Edge e : getEdges()) {

            // all edges in MST except e
            uf = new UF(edgeGraph.getVerticesCount());
            for (Edge f : mst) {
                int x = f.getEndpointA(), y = f.getOtherEndpoint(x);
                if (f != e) uf.union(x, y);
            }
            
            // check that e is min weight edge in crossing cut
            for (Edge f : edgeGraph.getEdges()) {
                int x = f.getEndpointA(), y = f.getOtherEndpoint(x);
                if (!uf.connected(x, y)) {
                    if (f.getWeight() < e.getWeight()) {
                        System.err.println("Edge " + f + " violates cut optimality conditions");
                        return false;
                    }
                }
            }

        }

        return true;
    }


    private static final String TEST_DATA_REFERENCE_ROOT = "org/notorious/visualization/graphing/collection/algorithm/tree/spanning/";
    private static final String TEST_DATA_MEDIUM = "tinyEWG.txt";
    private static final String TEST_DATA_LARGE = "largeEWG.txt";
    /**
     * Unit tests the {@code KruskalMST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        StdDraw.setCanvasSize();
        StdDraw.setCanvasSize(1024, 1024);
        In in = new In(Cache.class.getClassLoader().getResource(TEST_DATA_REFERENCE_ROOT + TEST_DATA_MEDIUM));
        WeightedEdgeGraph edgeGraph = new WeightedEdgeGraph(in);
        KruskalMST mst = new KruskalMST(edgeGraph);
        mst.render();
        StdDraw.show();
    }

}
