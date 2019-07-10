package Initialization;

import Graph.*;
import VisualGraph.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class Initializer{
	private JFrame mainFrame;
	private JLabel hello;
	private JPanel buttonPanel;
	private JButton buttonDraw;
	private JButton buttonFile;
	private JTextField textField;
	private String name;
	private static int way;
	private boolean ready;
	private HashMap<Integer,Point> coords;
	private AbstractGraph graph;

	
	public Initializer(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	//1 - from window, 2 - from file
	public void findOutWhatWay(){
		ready=false;
		
		hello = new JLabel("CHOOSE YOUR WAY TO DRAW GRAPH:");
		mainFrame.add(hello);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		mainFrame.add(buttonPanel);
		
		buttonDraw = new JButton("Draw");
		buttonDraw.addActionListener(new ButtonDrawListener());
		buttonPanel.add(buttonDraw);
		
		buttonFile = new JButton("File");
		buttonFile.addActionListener(new ButtonFileListener());
		buttonPanel.add(buttonFile);
		
		mainFrame.revalidate();
	
	}
//будет не void	
	public AbstractGraph getData(){
		//все убрать
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
		
		if (way == 1) return getDataFromDraw();
		else return getDataFromFile();
	}
	
	private AbstractGraph getDataFromDraw(){
		
		mainFrame.setLayout(new BorderLayout());
		DrawingPanel drawingPanel = new DrawingPanel();
		mainFrame.add(drawingPanel,BorderLayout.WEST);
/*		
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(400, 500));
		mainFrame.add(eastPanel, BorderLayout.EAST);
*/
		mainFrame.revalidate();
		
		while(!drawingPanel.isReady()){
			System.out.print("");
			if (drawingPanel.isReady()){
				break;
			}
		}
		drawingPanel.removeMouseListener(drawingPanel.getMouseListeners()[0]);
		
		coords = new HashMap<Integer,Point>();
		coords = drawingPanel.getCoordinates();
		return drawingPanel.getGraph();
	}
	
	private AbstractGraph getDataFromFile(){
		
		ready = false;
		mainFrame.setLayout(new FlowLayout());
		
		hello = new JLabel("Write the name of your file and push enter:");
		mainFrame.add(hello);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		mainFrame.add(buttonPanel);
		
		textField = new JTextField("input.txt");
		textField.setPreferredSize(new Dimension(200, 30));
		textField.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									name=textField.getText();
									ready=true;
								}
							});
		textField.requestFocusInWindow();
		buttonPanel.add(textField);
		mainFrame.revalidate();
		while(!ready){
			System.out.print("");
			if (ready){
				break;
			}
		}
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
		System.out.println(name);
		
		graph = new AdjList();
		graph.readFile(name);
		
		mainFrame.setLayout(new BorderLayout());
		SetAndDrawPanel drawingPanel = new SetAndDrawPanel(graph);
		mainFrame.add(drawingPanel,BorderLayout.WEST);
		
/*		
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(400, 500));
		mainFrame.add(eastPanel, BorderLayout.EAST);
*/
		mainFrame.revalidate();
		
		coords = drawingPanel.getCoordinates();
		return graph;
	}
	
	public int getWay(){
		return way;
	}
	
	public boolean isReady(){
		return ready;
	}
	
	public HashMap<Integer,Point> getCoordinates(){
		return coords;
	}

	
	class ButtonDrawListener implements ActionListener {
        public synchronized void actionPerformed(ActionEvent e) {
			way = 1;
			ready = true;
        }
    }
	class ButtonFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
			way = 2;
			ready = true;
        }
    }
	
	
}