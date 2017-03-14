package com.notorious.visualization.graphing.algorithm.shorest.path;

import com.notorious.visualization.graphing.algorithm.graph.AdjMatrixEdgeWeightedDigraph;
import com.notorious.visualization.graphing.algorithm.graph.DirectedEdge;
import com.notorious.visualization.graphing.algorithm.graph.Edge;
import com.notorious.visualization.graphing.algorithm.graph.WeightedEdgeGraph;
import com.notorious.visualization.graphing.algorithm.tree.spanning.kruskal.RenderableKruskalMST;
import com.notorious.visualization.graphing.util.StdDraw;
import com.notorious.visualization.graphing.util.StdRandom;

import static com.notorious.visualization.graphing.util.StdDraw.show;

/**
 * ....
 *
 * @author Notorious
 * @version 0.0.1
 * @since 3/13/2017
 */
public class RenderFloydWarshall {

    AdjMatrixEdgeWeightedDigraph g;
    public static final int n = 100;
    double[][] coords = new double[n][2];

    public RenderFloydWarshall() {
        g = new AdjMatrixEdgeWeightedDigraph(n);
        for (int i=0; i<n; i++) {
            coords[i][0] = StdRandom.uniform();
            coords[i][1] = StdRandom.uniform();
        }
        double x1, y1, x2, y2, distance;
        for (int i=0; i<n; i++) {
            x1 = coords[i][0];
            y1 = coords[i][1];
            for(int j=0; j<n; j++) {
                x2 = coords[j][0];
                y2 = coords[j][1];
                distance = distance(x1, y1, x2, y2);
                if (distance < .15) {
                    g.addEdge(new DirectedEdge(i, j, distance));
                }
            }
        }
        draw(); //Render the initial map
        animate(); //Animate Prim's Algorithm
        show(5000); //Pause for 5s when animation is complete
    }

    private void animate() {
        RenderableFloydWarshall p = new RenderableFloydWarshall(g, coords);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }

    // Render the initial graph
    public void draw() {
        StdDraw.clear();
        StdDraw.show(1); //Turn on animation mode
        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i=0; i<coords.length; i++) {
            for (DirectedEdge j : g.adj(i)) {
                StdDraw.line(coords[j.from()][0], coords[j.from()][1],coords[j.to()][0], coords[j.to()][1]);
            }
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i=0; i<coords.length; i++) {
            StdDraw.filledCircle(coords[i][0], coords[i][1], .005);
        }
    }

    public static void main(String[] args) {
        new RenderFloydWarshall();
    }
}
