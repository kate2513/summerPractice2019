import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

class WeightEdge{
	Ellipse2D vert;
	int weight;
	WeightEdge(Ellipse2D ellipse, int w){
		vert=ellipse;
		weight=w;
	}
}