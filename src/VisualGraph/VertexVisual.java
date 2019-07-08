package VisualGraph;

import java.awt.geom.*;
import java.util.ArrayList;

public class VertexVisual{
	
	public Ellipse2D vertex;
	public ArrayList<WeightEdge> edges;
	
	public VertexVisual(Ellipse2D ellipse){
		edges = new ArrayList<WeightEdge>();
		vertex = ellipse;
	}
}
