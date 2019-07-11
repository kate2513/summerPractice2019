package Initialization;

import Graph.*;

import java.awt.geom.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;

public class SetAndDrawPanel extends JPanel{
	
	private JLabel label;
	private AbstractGraph graph;
	private HashMap<Integer,Point> coords;
	
	public SetAndDrawPanel(AbstractGraph graph, int frameWidth, int frameHeight){
		this.graph = graph;
		setLayout(null);
		setPreferredSize(new Dimension(frameWidth/2 - 10, frameHeight));
		label = new JLabel("Your graph:");
		label.setBounds(getPreferredSize().width/2-100,10,200,15);
		add(label);
		setCoordinates();
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.clearRect(0, 0, getWidth(), getHeight());
		g2.setFont(new Font("Calibri", Font.PLAIN, 12));
		
		for (Integer r : graph.getVertexes()){
			g2.drawString(""+r.intValue(),coords.get(r).x,coords.get(r).y);
			g2.draw(new Ellipse2D.Double(coords.get(r).x,coords.get(r).y,20,20));
		}
		g2.setFont(new Font("Calibri", Font.BOLD, 18));
		for (Edge r : graph.getEdges()){
			int x1 =coords.get(r.v1).x+10;
			int x2 =coords.get(r.v2).x+10;
			int y1 =coords.get(r.v1).y+10;
			int y2 =coords.get(r.v2).y+10;
			g2.draw(new Line2D.Double(x1,y1,x2,y2));
			int maxX = x1>=x2 ? x1 : x2;
			int minX = x1<x2 ? x1 : x2;
			int maxY = y1>=y2 ? y1 : y2;
			int minY = y1<y2 ? y1 : y2;
			
			g2.drawString(""+r.weight,
				(int)(2+minX + (maxX-minX)/2),
				(int)(10+minY + (maxY-minY)/2));
		}
	}	
	
	public void setCoordinates(){
		UndirectedSparseGraph g = new UndirectedSparseGraph();
		int i=0;
		for (Integer r : graph.getVertexes()){
			g.addVertex(r.intValue());
		}
		for (Edge r : graph.getEdges()){
			g.addEdge(i++,r.v1,r.v2);
		}
		MyLayout layout = new MyLayout(g);
		VisualizationImageServer vs = new VisualizationImageServer(layout, new Dimension(getPreferredSize().width, getPreferredSize().height-150));
		com.google.common.cache.LoadingCache location = layout.getLocation();
		
		coords = new HashMap<Integer,Point>();
		Point2D point = new Point2D.Double();
		try{
			for (Integer r : graph.getVertexes()){
				point = (Point2D.Double)location.get(r.intValue());
				coords.put(new Integer(r.intValue()), new Point((int)(point.getX()),(int)(point.getY())+50));
			}
		} catch(ExecutionException e) {
		}
		repaint();
//		System.out.println(coords.toString());
	}
	
	public HashMap<Integer,Point> getCoordinates(){
		return coords;
	}
	
	public class MyLayout<V,E> extends CircleLayout<V,E>{
	  
	  public MyLayout(edu.uci.ics.jung.graph.Graph<V,E> g){
		super(g);
	  }
	  public com.google.common.cache.LoadingCache<V,Point2D> getLocation(){
		  return locations;
	  }
  }
}