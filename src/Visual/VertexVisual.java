import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

class VertexVisual{
	
	Ellipse2D vertex;
	ArrayList<WeightEdge> edges;
	
	VertexVisual(Ellipse2D ellipse){
		edges = new ArrayList<WeightEdge>();
		vertex = ellipse;
//		System.out.println(""+vertex.getX()+" "+vertex.getY());
//		System.out.println(edges.size());
	}
}
