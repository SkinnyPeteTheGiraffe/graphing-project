package com.notorious.visualization.graphing.algorithm.graph;

/**
 * A modern adaptation of the Edge.java written by Robert Sedgewick and Kevin Wayne.
 * Following a much stricter OOP structure, adding encapsulation, removing literal null
 * values, and introducing some of the features given by Java 8.
 *
 * <p>
 * ORIGINAL DOCUMENTATION:
 * -------------------------------------------------------------------------------
 *  The {@code Edge} class represents a weighted edge in an
 *  {@link WeightedEdgeGraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the edge and
 *  the weight. The natural order for this data type is by
 *  ascending order of weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * -------------------------------------------------------------------------------
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3 /12/2017
 */
public class Edge implements Comparable<Edge> {

    private static final String VERTEX_ERROR_MESSAGE = "Vertex index must be a non-negative integer value!";
    private static final String WEIGHT_ERROR_MESSAGE = "Weight value is NaN!";
    private static final String ENDPOINT_ERROR_MESSAGE = "Illegal endpoint!";

    private final int a;
    private final int b;
    private final double weight;

    /**
     * Instantiates a new Edge.
     *
     * @param a      the endpoint A of this edge
     * @param b      the endpoint B of this edge
     * @param weight the weight of this edge
     */
    public Edge(int a, int b, double weight) {
        if (a < 0) throw new IllegalArgumentException(VERTEX_ERROR_MESSAGE);
        if (b < 0) throw new IllegalArgumentException(VERTEX_ERROR_MESSAGE);
        if (Double.isNaN(weight)) throw new IllegalArgumentException(WEIGHT_ERROR_MESSAGE);
        this.a = a;
        this.b = b;
        this.weight = weight;
    }

    /**
     * Gets the weight of this edge.
     *
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Gets the endpoint A of this edge.
     *
     * @return the endpoint A
     */
    public int getEndpointA() {
        return a;
    }

    /**
     * Gets the endpoint B of this edge.
     *
     * @return the endpoint B
     */
    public int getEndpointB() {
        return b;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the endpoints of this edge
     */
    public int getOtherEndpoint(int vertex) {
        if (vertex == a) {
            return b;
        } else if (vertex == b) {
            return a;
        } else {
            throw new IllegalArgumentException(ENDPOINT_ERROR_MESSAGE);
        }
    }


    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  edge the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    @Override
    public int compareTo(Edge edge) {
        return Double.compare(this.weight, edge.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d <-> %d |%.5f|", a, b, weight);
    }
}
