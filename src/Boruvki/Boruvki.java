package Boruvki;

import Graph.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;


public class Boruvki {
    private AbstractGraph adjList = new AdjList();
    private AbstractGraph MST = new AdjList();
    private ArrayList < Component > components = new ArrayList < Component > ();
    private ArrayList < AbstractGraph.Edge > edges = new ArrayList < AbstractGraph.Edge > ();

    public void makeGraphAndInit() {
        adjList.readGraph();

        ArrayList < Integer > verts = adjList.getVertexes();

        edges = adjList.getEdges();

        for (int i = 0; i < verts.size(); i++) {
            MST.addVertex(verts.get(i));
            components.add(new Component(verts.get(i)));
        }
    }

    public void boruvki() {
        findComponents();
        int step = 1;

        while (!isMSTReady()) {
            oneStep();
            System.out.println("\nstep " + step + "\n");
            MST.showGraph();
            step++;

        }
    }


    public void oneStep() {
        for (int i = 0; i < components.size(); i++) {
            components.get(i).minEdge = new AbstractGraph.Edge(0, 0, 999999);
        }

        for (int i = 0; i < edges.size(); i++) {
            Component vert1 = components.get(components.indexOf(new Component(edges.get(i).v1)));
            Component vert2 = components.get(components.indexOf(new Component(edges.get(i).v2)));

            if (vert1.comp != vert2.comp) {
                if (vert1.minEdge.weight > edges.get(i).weight) {
                    setMinEdge(vert1.comp, edges.get(i).v1, edges.get(i).v2, edges.get(i).weight);
                }

                if (vert2.minEdge.weight > edges.get(i).weight) {
                    setMinEdge(vert2.comp, edges.get(i).v1, edges.get(i).v2, edges.get(i).weight);
                }
            }
        }

        for (int i = 0; i < components.size(); i++) {
            MST.addEdge(components.get(i).minEdge);
        }

        findComponents();

    }

    public boolean isMSTReady() {
        for (int i = 0; i < components.size() - 1; i++) {
            if (components.get(i).comp != components.get(i + 1).comp)
                return false;
        }

        return true;
    }

    public void setMinEdge(int comp, int v1, int v2, int edge) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).comp == comp)
                components.get(i).minEdge = new AbstractGraph.Edge(v1, v2, edge);
        }
    }

    public void search(int vert, int comp, ArrayList < Integer > passed) {
        passed.add(vert);
        components.get(components.indexOf(new Component(vert))).comp = comp;

        for (Integer neighbour: MST.isExistV(vert).way.keySet()) {
            if (!passed.contains(neighbour)) {
                search(neighbour, comp, passed);
            }
        }
    }

    public void findComponents() {
        ArrayList < Integer > passed = new ArrayList < Integer > ();

        for (int i = 0; i < components.size(); i++) {
            if (!passed.contains(components.get(i).vert)) {
                search(components.get(i).vert, i, passed);
            }
        }

        //for (int i = 0; i < components.size(); i++)
        //{
        //System.out.println(components.get(i).vert + "   comp:  " + components.get(i).comp);
        //}

    }

    public static class Component {
        public int vert;
        public int comp = 0;
        public AbstractGraph.Edge minEdge = new AbstractGraph.Edge(0, 0, 999999); // sorry not sorry
        public Component(int v) {
            vert = v;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Component))
                return false;

            Component comp = (Component) other;

            if (comp.vert == this.vert)
                return true;
            else
                return false;
        }
    }

}
