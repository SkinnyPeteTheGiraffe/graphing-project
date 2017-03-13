package com.notorious.visualization.graphing.algorithm.graph;

import com.notorious.visualization.graphing.collection.cache.Cache;
import com.notorious.visualization.graphing.collection.stack.Stack;
import com.notorious.visualization.graphing.util.In;
import com.notorious.visualization.graphing.util.StdDraw;
import com.notorious.visualization.graphing.util.StdRandom;

import java.awt.*;
import java.util.Iterator;

/**
 * A modern adaptation of the EdgeWeightedGraph.java written by Robert Sedgewick and Kevin Wayne.
 * Following a much stricter OOP structure, adding encapsulation, removing literal null
 * values, and introducing some of the features given by Java 8.
 *
 * <p>
 * ORIGINAL DOCUMENTATION:
 * -------------------------------------------------------------------------------
 *  The {@code EdgeWeightedGraph} class represents an edge-weighted
 *  graph of vertices named 0 through <em>V</em> – 1, where each
 *  undirected edge is of type {@link Edge} and has a real-valued weight.
 *  It supports the following two primary operations: add an edge to the graph,
 *  iterate over all of the edges incident to a vertex. It also provides
 *  methods for returning the number of vertices <em>V</em> and the number
 *  of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *  By convention, a self-loop <em>v</em>-<em>v</em> appears in the
 *  adjacency list of <em>v</em> twice and contributes two to the degree
 *  of <em>v</em>.
 *  <p>
 *  This implementation uses an adjacency-lists representation, which
 *  is a vertex-indexed array of {@link Cache} objects.
 *  All operations take constant time (in the worst case) except
 *  iterating over the edges incident to a given vertex, which takes
 *  time proportional to the number of such edges.
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * -------------------------------------------------------------------------------
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3/12/2017
 */
public class WeightedEdgeGraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final int vertices;
    private int edges;
    private Cache<Edge>[] adjacent;

    /**
     * Initializes an empty edge-weighted graph with {@code V} vertices and 0 edges.
     *
     * @param  vertices the number of vertices
     * @throws IllegalArgumentException if {@code V < 0}
     */
    @SuppressWarnings("unchecked")
    public WeightedEdgeGraph(int vertices) {
        if (vertices < 0) throw new IllegalArgumentException("Number of vertices must be non-negative!");
        this.vertices = vertices;
        this.edges = 0;
        adjacent = (Cache<Edge>[]) new Cache[vertices];
        for (int v = 0; v < vertices; v++) {
            adjacent[v] = new Cache<>();
        }
    }

    /**
     * Initializes an edge-weighted graph from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices and edge weights,
     * with each entry separated by whitespace.
     *
     * @param  in the input stream
     * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public WeightedEdgeGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            validateVertex(v);
            validateVertex(w);
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    /**
     * Initializes a new edge-weighted graph that is a deep copy of {@code G}.
     *
     * @param  graph the edge-weighted graph to copy
     */
    public WeightedEdgeGraph(WeightedEdgeGraph graph) {
        this(graph.getVerticesCount());
        this.edges = graph.getEdgeCount();
        for (int v = 0; v < graph.getVerticesCount(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : graph.adjacent[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                adjacent[v].add(e);
            }
        }
    }


    /**
     * Initializes a random edge-weighted graph with {@code V} vertices and <em>E</em> edges.
     *
     * @param  vertices the number of vertices
     * @param  edges the number of edges
     * @throws IllegalArgumentException if {@code V < 0}
     * @throws IllegalArgumentException if {@code E < 0}
     */
    public WeightedEdgeGraph(int vertices, int edges) {
        this(vertices);
        if (edges < 0) throw new IllegalArgumentException("Number of getEdges must be non-negative!");
        for (int i = 0; i < edges; i++) {
            int v = StdRandom.uniform(vertices);
            int w = StdRandom.uniform(vertices);
            double weight = Math.round(100 * StdRandom.uniform()) / 100D;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    public void render(){
        int range = (int)Math.ceil(Math.sqrt(vertices));
        StdDraw.setXscale(-1, range);
        StdDraw.setYscale(-1, range);

        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.01);
        for(int i = 0; i < vertices; ++i) {
            for(Edge e : getAdjacent(i)) {
                int w = e.getOtherEndpoint(i);
                StdDraw.line(w % range, w / range,i % range, i / range);
            }
        }
        StdDraw.setPenRadius(0.05);
        for(int i = 0; i < vertices; ++i) {
            StdDraw.setPenColor(Color.GREEN);
            StdDraw.point(i % range, i / range);
            StdDraw.setPenColor(Color.RED);
            StdDraw.textLeft(i % range, i / range, i + " ");
        }
    }


    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this edge-weighted graph
     */
    public int getVerticesCount() {
        return vertices;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this edge-weighted graph
     */
    public int getEdgeCount() {
        return edges;
    }


    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param  e the edge
     * @throws IllegalArgumentException unless both endpoints are between {@code 0} and {@code V-1}
     */
    public void addEdge(Edge e) {
        int v = e.getEndpointA();
        int w = e.getOtherEndpoint(v);
        validateVertex(v);
        validateVertex(w);
        adjacent[v].add(e);
        adjacent[w].add(e);
        edges++;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int vertex) {
        if (vertex < 0 || vertex >= this.vertices)
            throw new IllegalArgumentException("Vertex \'" + vertex + "\' is not between 0 and " + (this.vertices -1));
    }

    /**
     * Returns the degree of vertex {@code v}.
     *
     * @param  vertex the vertex
     * @return the degree of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int getDegree(int vertex) {
        validateVertex(vertex);
        return adjacent[vertex].size();
    }

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  vertex the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Cache<Edge> getAdjacent(int vertex) {
        validateVertex(vertex);
        return adjacent[vertex];
    }

    /**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> getEdges() {
        Cache<Edge> list = new Cache<>();
        for (int v = 0; v < vertices; v++) {
            int selfLoops = 0;
            for (Edge e : getAdjacent(v)) {
                if (e.getOtherEndpoint(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop (self loops will be consecutive)
                else if (e.getOtherEndpoint(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

    /**
     * Returns a string representation of the edge-weighted graph.
     * This method takes time proportional to <em>E</em> + <em>V</em>.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(vertices).append(" ").append(edges).append(NEWLINE);
        for (int v = 0; v < vertices; v++) {
            s.append(v).append(": ");
            for (Edge e : adjacent[v]) {
                s.append(e).append('\t');
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}
