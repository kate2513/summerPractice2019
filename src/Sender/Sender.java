package Sender;

import Graph.*;
import Boruvki.*;
import VisualGraph.*;
import Initialization.*;
import Showing.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Sender{
	private int DEFAULT_WIDTH = 1000;	//
	private int DEFAULT_HEIGHT = 700;	//
	private JFrame mainFrame;
	private static Initializer init;
	
	private AbstractGraph graph;
	private HashMap<Integer,Point> coords;
	
	private Boruvki algorithm;
	private ArrayList<AbstractGraph> states;
	private ArrayList<String> stringsLogs;
	
	private Presenter presenter;
	
	public static void main(String[] args){
		Sender send = new Sender();
		send.running();
		
	}

	
	public void running(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		DEFAULT_WIDTH = (int)screenSize.getWidth()-200;
		DEFAULT_HEIGHT = (int)screenSize.getHeight()-200;
		//содание окна
		mainFrame = new JFrame();
		mainFrame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		mainFrame.setMinimumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		mainFrame.setMaximumSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		mainFrame.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		mainFrame.setTitle("Boruvki Algorithm made by Golovina, Deryabina, Chigaleychik");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainFrame.setResizable(false);
		
		//инициализация
		init = new Initializer(mainFrame);
		init.findOutWhatWay();
		while(!init.isReady()){
			System.out.print("");
			if (init.isReady()){
				break;
			}
		}
		//создание графа
		graph = new AdjList();
		graph = init.getData();
		
		//получение координат
		coords = new HashMap<Integer,Point>();
		coords = init.getCoordinates();
		System.out.println(coords.toString());
		
		graph.showGraph();
		
		//запуск алгоритма с сохранением состояний
		algorithm = new Boruvki(graph);
		states = new ArrayList<AbstractGraph>();
		stringsLogs = new ArrayList <String>();
		states = algorithm.boruvki(stringsLogs);
		
		//показ шагов алгоритма
		presenter = new Presenter(mainFrame);
		presenter.showSteps(states,coords,stringsLogs);
		
	}
}
