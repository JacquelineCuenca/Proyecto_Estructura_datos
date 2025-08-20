package Modelo;

import java.util.LinkedList;

public class Vertex<V, E> {
    private V content;
    private LinkedList<Edge<E, V>> edges; // cada vértice tiene una lista de arcos

    // constructor
    public Vertex(V content) {
        this.content = content;
        this.edges = new LinkedList<>();
    }

    // getters y setters
    public V getContent() {
        return content;
    }

    public void setContent(V content) {
        this.content = content;
    }

    public LinkedList<Edge<E, V>> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge<E, V>> edges) {
        this.edges = edges;
    }
}

