import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Sender{
	private static final int DEFAULT_WIDTH = 800;	//
	private static final int DEFAULT_HEIGHT = 500;	//
	private JFrame mainFrame;
	
	private static Initializer init;
	
	public static void main(String[] args){
		Sender send = new Sender();
		send.running();
		
	}

	
	public void running(){
		//содание окна
		mainFrame = new JFrame();
		mainFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);	
		mainFrame.setTitle("New Window");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		
		
		init = new Initializer(mainFrame);
		init.findOutWhatWay();
		while(!init.isReady()){
			System.out.print("");
			if (init.isReady()){
				break;
			}
		}
//to be continued
//		init.getData(init.getWay());
		int way = init.getWay();
				System.out.println("way = "+way);
	}
}
