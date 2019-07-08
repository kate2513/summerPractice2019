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
import java.util.ArrayList;

public class Sender{
	private static final int DEFAULT_WIDTH = 820;	//
	private static final int DEFAULT_HEIGHT = 500;	//
	private JFrame mainFrame;
	private static Initializer init;
	
	private AbstractGraph graph;
	private ArrayList<Point> coords;
	
	private Boruvki algorithm;
	private ArrayList<AbstractGraph> states;
	
	private Presenter presenter;
	
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
		mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
		
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
		if (init.getWay() ==1){
			coords = new ArrayList<Point>();
			coords = init.getCoordinates();
		}
		
		graph.showGraph();
		
		//запуск алгоритма с сохранением состояний
		algorithm = new Boruvki(graph);
		states = new ArrayList<AbstractGraph>();
		states = algorithm.boruvki();
		
		//показ шагов алгоритма
		presenter = new Presenter(mainFrame);
		if (init.getWay() == 1){
			presenter.showSteps(states,coords);
		}
		
	}
}
