package Showing;

import Graph.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Presenter{
	
	private JFrame mainFrame;
	private JPanel showPanel;
	
	public Presenter(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void showSteps(ArrayList<AbstractGraph> states, HashMap<Integer,Point> coords){
		System.out.println(coords.toString());
		showPanel = new ShowPanel(states,coords);
		mainFrame.add(showPanel, BorderLayout.EAST);
		showPanel.repaint();
		mainFrame.revalidate();
		
	}
	
}