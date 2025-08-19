package Modelo;

public class Edge<E, V> {
    private Vertex<V, E> source;
    private Vertex<V, E> destination;
    private int weight;
    private E data;

    public Edge(Vertex<V, E> source, Vertex<V, E> destination, int weight, E data) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.data = data;
    }

    public Vertex<V, E> getSource() {
        return source;
    }

    public Vertex<V, E> getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public E getData() {
        return data;
    }
}

