package VisualGraph;

import java.awt.geom.*;

public class WeightEdge{
	public Ellipse2D vert;
	public int weight;
	public WeightEdge(Ellipse2D ellipse, int w){
		vert=ellipse;
		weight=w;
	}
}