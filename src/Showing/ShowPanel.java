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
	private JButton finishButton;
	private JTextArea logs;
	private JScrollPane scroll;
	private ArrayList<AbstractGraph> states;
	private ArrayList<String> stringLogs;
	private HashMap<Integer,Point> coords;
	private AbstractGraph current;
	private StringBuilder string;
	private int step = 1;
	
	ShowPanel(ArrayList<AbstractGraph> states, HashMap<Integer,Point> coords,int frameWidth,int frameHeight,ArrayList<String> stringLogs){
		//null чтобы рисовать в любой точке панели
		setLayout(null);
		//указание требуемого размера панели
		setPreferredSize(new Dimension(frameWidth/2 - 10, frameHeight));
		
		this.stringLogs = stringLogs;
//		
	//	string = new StringBuilder();

		if (states != null)
		{
			label = new JLabel("Steps of the Boruvki Algorithm:");
			label.setBounds(getPreferredSize().width/2-100,10,250,15);
			add(label);
		}
		else
		{
			label = new JLabel("Wrong input!");
			label.setBounds(getPreferredSize().width/2-100,10,250,15);
			add(label);
			return;
		}
//		
		
		backButton = new JButton("Back");
		backButton.setBounds(5,getPreferredSize().height-80,70,30);
		backButton.addActionListener(new ButtonBackListener());
		add(backButton);
		
		nextButton = new JButton("Next");
		nextButton.setBounds(getPreferredSize().width-180,getPreferredSize().height-80,70,30);
		nextButton.addActionListener(new ButtonNextListener());
		add(nextButton);
		
		nextButton = new JButton("Finish");
		nextButton.setBounds(getPreferredSize().width-100,getPreferredSize().height-80,70,30);
		nextButton.addActionListener(new ButtonFinishListener());
		add(nextButton);
		
		this.states = new ArrayList<AbstractGraph>();
		this.coords = new HashMap<Integer,Point>();
		
		this.states = states;
		this.coords = coords;
		
		current = states.get(step);
		
		logs = new JTextArea(15,10);
		logs.setLineWrap(true);
        logs.setWrapStyleWord(true);
		logs.setEditable(false);
		logs.setText("hello");
		logs.setCaretPosition(0);
		
		scroll = new JScrollPane(logs);
		scroll.setBounds(100,getPreferredSize().height-80,getPreferredSize().width-180-100-10,40);
		add(scroll);
		
	}
	
	
	protected void paintComponent(Graphics g){
		System.out.println("Painting");
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if (current == null)
		{
			return;
		}
		
		
		g2.clearRect(0, 0, getWidth(), getHeight());
/*		
		if (step == 1){
			string = new StringBuilder("Шаг №"+step+". ");
			string.append("Каждая вершина - отдельная компонента связности. ");
			string.append("На данном шаге добавлены вершины: ");
			for (Integer r : current.getVertexes()){
				string.append("("+r.intValue()+") ");
			}
			logs.setText(string.toString());
		}
		else if (step==2){
			string = new StringBuilder("Шаг №"+step+". ");
			string.append("Добавлены ребра:\n");
			for (Edge r : current.getEdges()){
				string.append("("+r.v1+")--"+r.weight+"--("+r.v2+")  ");
			}
			logs.setText(string.toString());
		}
		else if (step>2){
			string = new StringBuilder("Шаг №"+step+". ");
			string.append("Добавлены ребра: ");
			logs.setText(string.toString());
		}
*/		
		logs.setText(stringLogs.get(step-1));
		logs.setCaretPosition(0);
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
	class ButtonFinishListener implements ActionListener {
        public synchronized void actionPerformed(ActionEvent e) {
			step=states.size()-1;
			current = states.get(step);
			repaint();
        }
    }
}