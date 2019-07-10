package Showing;

import Graph.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class ShowPanel extends JPanel{
	
	private JLabel label;
	private JButton backButton;
	private JButton nextButton;
	private ArrayList<AbstractGraph> states;
	private HashMap<Integer,Point> coords;
	private AbstractGraph current;
	private int step = 1;
	
	ShowPanel(ArrayList<AbstractGraph> states, HashMap<Integer,Point> coords){
		//null чтобы рисовать в любой точке панели
		setLayout(null);
		//указание требуемого размера панели
		setPreferredSize(new Dimension(400, 500));
		
		label = new JLabel("Steps of the Boruvki Algorithm:");
		label.setBounds(120,10,200,15);
		add(label);
		
		backButton = new JButton("Back");
		backButton.setBounds(5,420,70,30);
		backButton.addActionListener(new ButtonBackListener());
//		System.out.println("add button");
		add(backButton);
		
		nextButton = new JButton("Next");
		nextButton.setBounds(320,420,70,30);
		nextButton.addActionListener(new ButtonNextListener());
		add(nextButton);
		
		this.states = new ArrayList<AbstractGraph>();
		this.coords = new HashMap<Integer,Point>();
		
		this.states = states;
		this.coords = coords;
		
		current = states.get(step);
		
	}
	
	
	protected void paintComponent(Graphics g){
		System.out.println("Painting");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.clearRect(0, 0, getWidth(), getHeight());
		g2.setFont(new Font("Calibri", Font.PLAIN, 12));
		Point point = new Point();
		System.out.println(current.getVertexes().toString());
		System.out.println(coords.toString());
		for (Integer r : current.getVertexes()){
			point = coords.get(new Integer(r.intValue()));
			if (point == null) System.out.println("Null pointer");
			System.out.println(""+point.x+" "+point.y);
			g2.drawString(""+(r.intValue()),coords.get(r).x,coords.get(r).y);
			g2.draw(new Ellipse2D.Double(coords.get(r).x,coords.get(r).y,20,20));
		}
		g2.setFont(new Font("Calibri", Font.BOLD, 14));
		g2.drawString("Step "+step,getWidth()/2-80,40);
		g2.setFont(new Font("Calibri", Font.BOLD, 18));
		for (Edge r : current.getEdges()){
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
	
	class ButtonBackListener implements ActionListener {
        public synchronized void actionPerformed(ActionEvent e) {
			if (step>1) step--;
			else step=1;
			current = states.get(step);
			repaint();
        }
    }
	class ButtonNextListener implements ActionListener {
        public synchronized void actionPerformed(ActionEvent e) {
			if (step<states.size()-2) step++;
			else step=states.size()-1;
			current = states.get(step);
			repaint();
        }
    }
}