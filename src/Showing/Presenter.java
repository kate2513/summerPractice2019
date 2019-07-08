package Showing;

import Graph.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Presenter{
	
	private JFrame mainFrame;
	private JPanel showPanel;
	
	public Presenter(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void showSteps(ArrayList<AbstractGraph> states, ArrayList<Point> coords){
		showPanel = new ShowPanel(states,coords);
		mainFrame.add(showPanel, BorderLayout.EAST);
		showPanel.repaint();
		mainFrame.revalidate();
		
	}
	
	public void showSteps(ArrayList<AbstractGraph> states){
	}
	
}