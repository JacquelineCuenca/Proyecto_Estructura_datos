package Modelo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.*;

public class GraphAL<V, E> {
    private LinkedList<Vertex<V, E>> vertices;
    private boolean isDirected;
    private Comparator<V> cmp;

    //constructor
    public GraphAL(boolean isDirected, Comparator<V> cmp) {
        this.isDirected = isDirected;
        this.cmp = cmp;
        this.vertices = new LinkedList<>();
    }

    // añadir vertice
    public boolean addVertex(V content) {
        if (content == null || findVertex(content) != null) {
            return false;
        }
        Vertex<V, E> newVertex = new Vertex<>(content);
        this.vertices.add(newVertex);
        return true;
    }

    // metodo auxiliar para encontrar un vertice por su contenido
    private Vertex<V, E> findVertex(V content) {
        for (Vertex<V, E> v : vertices) {
            V c = v.getContent();
            if (this.cmp.compare(c, content) == 0) {
                return v;
            }
        }
        return null;
    }

    // metodo para conectar dos vertices
    public boolean connect(V content1, V content2, int weight, E data) {
        // Caso 1: Contenidos son nulos
        if (content1 == null || content2 == null) {
            return false;
        }
        // Búsqueda de vertices por contenido
        Vertex<V, E> v1 = findVertex(content1);
        Vertex<V, E> v2 = findVertex(content2);

        // Vértices no existen, no se pueden conectar
        if (v1 == null || v2 == null) {
            return false;
        }

        // Crear arco y añadir a la lista de adyacencia de v1 source
        Edge<E, V> newEdge = new Edge<>(v1, v2, weight, data);
        v1.getEdges().add(newEdge);

        // También añadir a la lista de adyacencia de v2, si el grafo no es dirigido
        if (!this.isDirected) {
            Edge<E, V> reverseEdge = new Edge<>(v2, v1, weight, data);
            v2.getEdges().add(reverseEdge);
        }
        return true;
    }

    // imprimir el grafo
    public void printGraph() {
        for (Vertex<V, E> v : vertices) {
            System.out.print(v.getContent() + " -> ");
            for (Edge<E, V> e : v.getEdges()) {
                System.out.print(e.getTarget().getContent() + "(" + e.getWeight() + ") ");
            }
            System.out.println();
        }
    }

    // Este método encuentra la ruta más corta entre dos aeropuertos usando el peso de las aristas (duración del vuelo).
    public List<V> dijkstra(V origen, V destino) {
        Map<V, Integer> dist = new HashMap<>();
        Map<V, V> prev = new HashMap<>();
        Set<V> visitados = new HashSet<>();
        PriorityQueue<V> cola = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (Vertex<V, E> v : vertices) {
            dist.put(v.getContent(), Integer.MAX_VALUE);
            prev.put(v.getContent(), null);
        }
        dist.put(origen, 0);
        cola.add(origen);

        while (!cola.isEmpty()) {
            V actual = cola.poll();
            if (visitados.contains(actual)) continue;
            visitados.add(actual);

            Vertex<V, E> vActual = findVertex(actual);
            if (vActual == null) continue;

            for (Edge<E, V> e : vActual.getEdges()) {
                V vecino = e.getTarget().getContent();
                int peso = e.getWeight();
                int nuevaDist = dist.get(actual) + peso;
                if (nuevaDist < dist.get(vecino)) {
                    dist.put(vecino, nuevaDist);
                    prev.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }

        // Reconstruir el camino
        List<V> camino = new LinkedList<>();
        for (V at = destino; at != null; at = prev.get(at)) {
            camino.add(0, at);
        }
        if (camino.isEmpty() || !camino.get(0).equals(origen)) {
            return Collections.emptyList(); // No hay camino
        }
        return camino;
    }


    // Método para obtener para obtener la lista de contenidos de los vértices
    public List<V> getVerticesContents() {
        List<V> contents = new ArrayList<>();
        for (Vertex<V, E> v : vertices) {
            contents.add(v.getContent());
        }
        return contents;
    }

}

