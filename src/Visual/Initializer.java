import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Initializer{
	private JFrame mainFrame;
	private JLabel hello;
	private JPanel buttonPanel;
	private JButton buttonDraw;
	private JButton buttonFile;
	private static int way;
	private boolean ready;

	
	Initializer(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	//1 - from window, 2 - from file
	public void findOutWhatWay(){
		ready=false;
		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
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
	
	int getWay(){
		return way;
	}
	
	public boolean isReady(){
		return ready;
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