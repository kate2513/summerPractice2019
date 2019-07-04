package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Представление в виде списка смежности
public class AdjList extends AbstractGraph {

    private HashMap<Integer, Vertex> adjList = new HashMap<Integer, Vertex>();

    // кривовато
    public void readGraph()
    {
	Integer v1=0, v2=0, weight=0;

	Scanner scan = new Scanner(System.in).useDelimiter("\\s");

	while (scan.hasNextInt())
	{
		if (scan.hasNextInt())
			v1 = scan.nextInt();
		if (scan.hasNextInt())
			v2 = scan.nextInt();
		if (scan.hasNextInt())
			weight = scan.nextInt();

		addEdge(new Edge(v1, v2, weight));
	}
    }

    public void showGraph()
    {

	for (Vertex vert : adjList.values())
	{
		System.out.println("vertex: " + vert.v);
		for (Integer neighbour : vert.way.keySet())
		{
			System.out.println("[neighbour: " + neighbour + ", edge: " + vert.way.get(neighbour) + "]");
		}
	}
    }

    @Override
    public boolean addVertex(int v) {
        if (adjList.containsKey(v)) return false;

        adjList.put(v, new Vertex(v));
        return true;
    }

    @Override
    public boolean addEdge(Edge e) {

        if ( !adjList.containsKey(e.v1) )
	{
		addVertex(e.v1);
	}

        if ( !adjList.containsKey(e.v2) )
	{
		addVertex(e.v2);
	}

        if ( adjList.get(e.v1).way.containsKey(e.v2)) return false;

        adjList.get(e.v1).way.put(e.v2, e.weight);
        adjList.get(e.v2).way.put(e.v1, e.weight);

        return true;
    }

    @Override
    public boolean removeVertex(Vertex v) {
        if (!adjList.containsKey(v.v)) return false;

        adjList.remove(v.v);
        return true;
    }

    @Override
    public boolean removeEdge(Edge e) {

        if ( !adjList.containsKey(e.v1) ) return false;
        if ( !adjList.containsKey(e.v2) ) return false;
        if ( !adjList.get(e.v1).way.containsKey(e.v2)) return false;

        adjList.get(e.v1).way.remove(e.v2);
        adjList.get(e.v2).way.remove(e.v1);

        return true;
    }

    @Override
    public Vertex isExistV(int v) {
        return adjList.get(v);
    }

    @Override
    public Edge isExistE(int v1, int v2) {
        if (isExistV(v1)!=null && isExistV(v2)!=null) {
            Integer i = adjList.get(v1).way.get(v2);

            return i==null ? null : new Edge(v1,v2,i.intValue());
        }
        return null;
    }


    @Override
    public void clear() {
        adjList = new HashMap<Integer, Vertex>();
    }

    @Override
    public ArrayList<Integer> getVertexes() {
        ArrayList<Integer> verts = new ArrayList<Integer>();

        for(Integer vert: adjList.keySet()) {
            verts.add(vert);
        }

        return verts;
    }

    @Override
    public ArrayList<Edge> getEdges() {
	ArrayList<Edge> edges = new ArrayList<Edge>();

	for(Vertex vert : adjList.values())
	{
		for (Map.Entry edge : vert.way.entrySet())
		{
			Edge isDuple = new Edge((int)edge.getKey(), vert.v, (int)edge.getValue());

			if (!edges.contains(isDuple))
			{
				edges.add(new Edge(vert.v, (int)edge.getKey(), (int)edge.getValue()));
			}
		}
	}

	return edges;

    }
}
