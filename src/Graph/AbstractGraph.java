package Graph;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractGraph {

    abstract public void readGraph();

    abstract public void showGraph();

    abstract public boolean addVertex(int v);

    abstract public boolean addEdge(Edge e);

    abstract public boolean removeVertex(Vertex v);

    abstract public boolean removeEdge(Edge e);

    abstract public Vertex isExistV(int v);

    abstract public Edge isExistE(int v1, int v2);

    abstract public void clear();

    abstract public ArrayList < Integer > getVertexes();

    abstract public ArrayList < Edge > getEdges();

    public static class Edge {
        public Edge(int v1, int v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Edge))
                return false;

            Edge edge = (Edge) other;

            if (edge.v1 == this.v1 && edge.v2 == this.v2 && edge.weight == this.weight)
                return true;
            else
                return false;
        }


        public int v1;
        public int v2;
        public int weight;
    }
    public static class Vertex {
        public int v;
        public HashMap < Integer, Integer > way = new HashMap < Integer, Integer > ();

        public Vertex(int v) {
            this.v = v;
        }
    }

}
