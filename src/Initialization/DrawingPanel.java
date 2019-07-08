package Initialization;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import Graph.*;
import VisualGraph.*;

public class DrawingPanel extends JPanel{
	
	private static int weight = 0;
	private JLabel label;
	private JLabel error;
	private JLabel help;
	private JButton readyButton;
	private VertexVisual current;
	private VertexVisual temp;
	private ArrayList<VertexVisual> verts;
	private boolean ready; 
	private int paintVertex;
	
	
	DrawingPanel(){
		//null чтобы рисовать в любой точке панели
		setLayout(null);
		//указание требуемого размера панели
		setPreferredSize(new Dimension(400, 500));
		//нарисовать границу
		setBorder(BorderFactory.createEtchedBorder());
		verts = new ArrayList<VertexVisual>();
		current = null;
		addMouseListener(new GraphMouseListener());
		label = new JLabel("Draw your graph here:");
		label.setBounds(120,10,200,15);
		add(label);
		error = new JLabel("");
		error.setBounds(100,25,300,15);
		add(error);
		
        help = new JLabel("<html>Mouse clicks:\nLeft = new vertex.\nDouble left = delete vertex.\nRight click on two vertexes = edge.\n</html>");
		help.setBounds(30,400,200,60);
		add(help);
		
		readyButton = new JButton("OK");
		readyButton.setBounds(320,420,70,30);
		readyButton.addActionListener(new ButtonReadyListener());
		add(readyButton);
		
	}
		
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		int id=1;
			
		g2.clearRect(0, 0, getWidth(), getHeight());
			
		g2.setFont(new Font("Calibri", Font.PLAIN, 12));
		for (VertexVisual r : verts){
	//		System.out.println("id = "+id);
			g2.drawString(""+id++,(int)(r.vertex.getX()),(int)(r.vertex.getY()));
	//		System.out.println("id = "+(id-1)+", index = "+paintVertex);
			if (id-1==paintVertex) g2.setColor(Color.RED);
			g2.draw(r.vertex);
			if (id-1==paintVertex) g2.setColor(Color.BLACK);
			
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
	
	private int getIndex(Ellipse2D vertex){
		int i=0;
		for (VertexVisual r : verts){
			if (r.vertex == vertex)
				return i;
			i++;
		}
		return -1;
	}
	
	public AbstractGraph getGraph(){
		int from=0;
		int to=0;
		AbstractGraph graph = new AdjList();
		for (VertexVisual r : verts){
			for (WeightEdge z : r.edges){
					to = getIndex(z.vert);
					graph.addEdge(new Edge(from,to,z.weight));
			}
			from++;
		}
		return graph;
		
	}
	
	public ArrayList<Point> getCoordinates(){
		ArrayList<Point> coords = new ArrayList<Point>();
		for (VertexVisual r : verts){
			coords.add(new Point((int)(r.vertex.getX()),(int)(r.vertex.getY())));
		}
		return coords;
	}
	
	public boolean isReady(){
		return ready;
	}
		
	public VertexVisual find(int x,int y){
	//	if (label.contains(x,y)) return verts.get(0);
		for (VertexVisual r : verts){
			if (r.vertex.contains(x,y)) return r;
		}
		return null;
	}
	
	class ButtonReadyListener implements ActionListener {
        public synchronized void actionPerformed(ActionEvent e) {
			ready = true;
        }
    }
	
	class GraphMouseListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
				temp = null;
				
				//ЛКМ
				if(e.getButton()==MouseEvent.BUTTON1){
					error.setText("");
					revalidate();
					if (current != null){
//						setChosen(0);
//						repaint();
						current = null;
					}
					//выход, если нельзя поставить точку
					temp=find(e.getX(),e.getY());
					if(temp != null){
						if (e.getClickCount()>1){
							try{
								for (VertexVisual r : verts){
									for (WeightEdge z : r.edges){
										if (temp.vertex == z.vert)
											r.edges.remove(z);
									}
								}
								verts.remove(temp);
								repaint();
							} catch(java.util.ConcurrentModificationException ex){
									error.setText("Please, try again.");
									revalidate();
							}
						}
						return;
					}
					
				
					verts.add(new VertexVisual(new Ellipse2D.Double(e.getX()-20/2 ,e.getY()-20/2,20,20)));
//					System.out.println("size = "+verts.size());
					repaint();
					
				}
				//ПКМ
				if (e.getButton() == MouseEvent.BUTTON3){
					if (current==null){
						current = find(e.getX(),e.getY());
						//окрасить выбранную вершину
						paintVertex = 1+getIndex(current.vertex);
//						System.out.println("paintIndex = "+(1+getIndex(current.vertex)));
						repaint();
					} else{
						temp = find(e.getX(),e.getY());
						//если ребро в саму себя - не строить
						if (current == temp){
							current=null;
							temp=null;
							paintVertex=0;
							repaint();
							return;
						}
						if (temp != null){
							JTextField textField = new JTextField();
							textField.setSize(50,20);
							textField.setLocation(e.getX(),e.getY());
							add(textField);
							textField.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									try{
										weight=Integer.parseInt(textField.getText());
									} catch (NumberFormatException exception){
										temp = null;
										error.setText("ERROR! WEIGHT = NUMBER!");
										remove(textField);
										revalidate();
										repaint();
										return;
									}
									remove(textField);
									current.edges.add(new WeightEdge(temp.vertex,weight));
									//убрать окраску вершины
									paintVertex=0;
									repaint();
									current=null;
								}
							});
							revalidate();
							textField.requestFocusInWindow();
							repaint();
							
						}
					}
				}
		}
	}

}
