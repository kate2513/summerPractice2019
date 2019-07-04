import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main{
//	ArrayList<VertexComponent> mainVerts = new ArrayList<VertexComponent>();
	public static void main(String[] args){
		SwingUtilities.invokeLater(() ->
		{
				var frame = new MyFrame();
				frame.setTitle("New Window");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
//				if (frame.verts.size() >5){
//					mainVerts.add(frame.verts[0]);
//					System.out.println(""+mainVerts.size());
//				}
					
				
				
		});
/*		
		try{
					Thread.sleep(5000);
				}
				catch(InterruptedException ex){
					Thread.currentThread().interrupt();
				}
		SwingUtilities.invokeLater(() ->
		{
			System.out.println("lalala");
		});
*/		
	}
}