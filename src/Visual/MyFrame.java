import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MyFrame extends JFrame{
	private DrawPanel drawingPanel;
	private static final int DEFAULT_WIDTH = 500;	//
	private static final int DEFAULT_HEIGHT = 500;	//
	private static int weight = 0;
//	ArrayList<Ellipse2D> verts;
//	ArrayList<Line2D> edges;
	VertexVisual current;
	VertexVisual temp;
	ArrayList<VertexVisual> verts;
	
	MyFrame(){
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);			//
		verts = new ArrayList<VertexVisual>();
//		edges = new ArrayList<Line2D>();
		current = null;
		drawingPanel = new DrawPanel();
		drawingPanel.addMouseListener(new mL());
		drawingPanel.setLayout(null);
		add(drawingPanel);		
			
	}
	
	class DrawPanel extends JPanel{
		private int chosen;
		
		public void setChosen(int index_chosen){
			chosen=index_chosen;
		}
		
		protected void paintComponent(Graphics g){
			Graphics2D g2 = (Graphics2D)g;
			int id=1;
			
			g2.clearRect(0, 0, getWidth(), getHeight());
			
		//	System.out.println("chosen = "+chosen);
			g2.setFont(new Font("Calibri", Font.PLAIN, 12));
			for (VertexVisual r : verts){
				System.out.println("id = "+id);
		//		g2.drawString(""+id++,(int)(r.getX()+r.getWidth()/3),(int)(r.getY()+r.getWidth()/3*2));
				g2.drawString(""+id++,(int)(r.vertex.getX()),(int)(r.vertex.getY()));
		//		if (chosen==id-1) g2.setColor(Color.RED);
				g2.draw(r.vertex);
		//		if (chosen==id-1) g2.setColor(Color.BLACK);
				
				g2.setFont(new Font("Calibri", Font.BOLD, 18));
				for (WeightEdge z : r.edges){
					int x1 =(int)(r.vertex.getX()+r.vertex.getWidth()/2);
					int x2 =(int)(z.vert.getX()+z.vert.getWidth()/2);
					int y1 =(int)(r.vertex.getY()+r.vertex.getWidth()/2);
					int y2 =(int)(z.vert.getY()+z.vert.getWidth()/2);
					g2.draw(new Line2D.Double(x1,y1,x2,y2));
					int maxX = x1>=x2 ? x1 : x2;
					int minX = x1<x2 ? x1 : x2;
					int maxY = y1>=y2 ? y1 : y2;
					int minY = y1<y2 ? y1 : y2;
					
			
					
					g2.drawString(""+z.weight,
					(int)(2+minX + (maxX-minX)/2),
					(int)(10+minY + (maxY-minY)/2));
					
				}
				g2.setFont(new Font("Calibri", Font.PLAIN, 12));
				
			}
			
		}
		
	}
	
	public VertexVisual find(int x,int y){
		for (VertexVisual r : verts){
/*
//			if (r.getX()-r.getWidth()/2<=x & r.getX()+r.getWidth()*3/2 >= x)
//				if (r.getY()-r.getHeight()/2<=y & r.getY()+r.getHeight()*3/2 >= y)
//					return r;
*/
			if (r.vertex.contains(x,y)) return r;
		}
		return null;
	}
	
	
	class mL extends MouseAdapter{	
		@Override
		public void mouseClicked(MouseEvent e){
				temp = null;
				
				System.out.println("\nClicked!");
				System.out.println(""+verts.size());
				System.out.println(""+e.getX()+" " + e.getY());
				//ЛКМ
				if(e.getButton()==MouseEvent.BUTTON1){
					if (current != null){
						drawingPanel.setChosen(0);
						drawingPanel.repaint();
						current = null;
					}
					//выход, если нельзя поставить точку
					temp=find(e.getX(),e.getY());
					if(temp != null){
						System.out.println("clicks = "+e.getClickCount());
						if (e.getClickCount()>1){
							for (VertexVisual r : verts){
								for (WeightEdge z : r.edges){
									if (temp.vertex == z.vert)
										r.edges.remove(z);
								}
							}
							verts.remove(temp);
							drawingPanel.repaint();
							
						}
						return;
					}
					
				
					verts.add(new VertexVisual(new Ellipse2D.Double(e.getX()-20/2 ,e.getY()-20/2,20,20)));
					System.out.println("size = "+verts.size());
					drawingPanel.repaint();
					
				}
				//ПКМ

				if (e.getButton() == MouseEvent.BUTTON3){
					if (current==null){
						current = find(e.getX(),e.getY());
					//	System.out.println("index = "+(1+verts.indexOf(current)));
						drawingPanel.setChosen(1+verts.indexOf(current));
						drawingPanel.repaint();
					} else{
						temp = find(e.getX(),e.getY());
						
						if (temp != null){
							

							JTextField tF = new JTextField();
							tF.setSize(50,20);
							tF.setLocation(e.getX(),e.getY());
							drawingPanel.add(tF);
							tF.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
								// Отображение введенного текста
							//	int 
								weight=Integer.parseInt(tF.getText());
								
								System.out.println(""+ weight);
								drawingPanel.remove(tF);
								
								current.edges.add(new WeightEdge(temp.vertex,weight));
//								temp.edges.add(current.vertex);
								
								drawingPanel.repaint();
								current=null;
								
								}
							});
							drawingPanel.setChosen(0);
							drawingPanel.revalidate();
							tF.requestFocusInWindow();
							drawingPanel.repaint();
							
							
						}
					}
				}
		}
	}

}