package Modelo;

import java.util.Comparator;
import java.util.LinkedList;

public class GraphAL<V, E> {
    private LinkedList<Vertex<V, E>> vertices;
    private boolean isDirected;
    private Comparator<V> cmp;

    public GraphAL(boolean isDirected, Comparator<V> cmp) {
        this.isDirected = isDirected;
        this.cmp = cmp;
        this.vertices = new LinkedList<>();
    }

    public boolean addVertex(V content) {
        if (content == null || findVertex(content) != null) {
            return false;
        }
        Vertex<V, E> newVertex = new Vertex<>(content);
        this.vertices.add(newVertex);
        return true;
    }

    private Vertex<V, E> findVertex(V content) {
        for (Vertex<V, E> v : vertices) {
            V c = v.getContent();
            if (this.cmp.compare(c, content) == 0) {
                return v;
            }
        }
        return null;
    }

    public boolean connect(V content1, V content2, int weight, E data) {
        if (content1 == null || content2 == null) {
            return false;
        }

        Vertex<V, E> v1 = findVertex(content1);
        Vertex<V, E> v2 = findVertex(content2);

        if (v1 == null || v2 == null) {
            return false;
        }

        Edge<E, V> newEdge = new Edge<>(v1, v2, weight, data);
        v1.getEdges().add(newEdge);

        if (!this.isDirected) {
            Edge<E, V> reverseEdge = new Edge<>(v2, v1, weight, data);
            v2.getEdges().add(reverseEdge);
        }

        return true;
    }

    public void printGraph() {
        for (Vertex<V, E> v : vertices) {
            System.out.print(v.getContent() + " -> ");
            for (Edge<E, V> e : v.getEdges()) {
                System.out.print(e.getDestination().getContent() + "(" + e.getWeight() + ") ");
            }
            System.out.println();
        }
    }
}

