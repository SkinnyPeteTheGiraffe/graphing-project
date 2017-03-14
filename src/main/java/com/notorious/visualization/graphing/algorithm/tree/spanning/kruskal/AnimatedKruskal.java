package com.notorious.visualization.graphing.algorithm.tree.spanning.kruskal;

import com.notorious.visualization.graphing.algorithm.graph.Edge;
import com.notorious.visualization.graphing.algorithm.graph.WeightedEdgeGraph;
import com.notorious.visualization.graphing.util.StdDraw;
import com.notorious.visualization.graphing.util.StdRandom;

import static com.notorious.visualization.graphing.util.StdDraw.show;

public class AnimatedKruskal {
    WeightedEdgeGraph g;
	public static final int n = 100;
	double[][] coords = new double[n][2];

	public AnimatedKruskal() {
		g = new WeightedEdgeGraph(n);
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
					g.addEdge(new Edge(i, j, distance));
				}
			}
		}
		draw(); //Render the initial map
		animate(); //Animate Prim's Algorithm
		show(5000); //Pause for 5s when animation is complete
	}
	
	private void animate() {
		RenderableKruskalMST p = new RenderableKruskalMST(g, coords);
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
			for (Edge j : g.getAdjacent(i)) {
				StdDraw.line(coords[i][0], coords[i][1],coords[j.getOtherEndpoint(i)][0], coords[j.getOtherEndpoint(i)][1]);
			}
		}
		StdDraw.setPenColor(StdDraw.BLACK);
		for (int i=0; i<coords.length; i++) {
			StdDraw.filledCircle(coords[i][0], coords[i][1], .01);
		}
		
	}

	public static void main(String[] args) {
		new AnimatedKruskal();
	}

}