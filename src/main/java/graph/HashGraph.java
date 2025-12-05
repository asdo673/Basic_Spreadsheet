package graph;

import java.util.*;

public class HashGraph<K, T extends Comparable<T> > {

    private Map<K, Vertex<K, T>> Vertices = new HashMap<>();   // lista de llaves con vertices

    public HashGraph(List<K> keys){
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Value de cada vertice es null.
        int i = 0;
        for(K key : keys){
            Vertices.put(
                    key,
                    new Vertex<>(key)
            );
        }
    }
    public HashGraph(List<K> keys, List<T> values){
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Se asigna el contenido de values para cada vertice.
        int i = 0;
        for(K key : keys){
            Vertices.put(
                    key,
                    new Vertex<>(key, values.get(i++))
            );
        }
    }

    public Vertex<K, T> getVertex(K key){
        if(!Vertices.containsKey(key))
            throw new NullPointerException("Not such key in the graph.");
        return Vertices.get(key);
    }

    public void setVertex(K key, Vertex<K, T> vertex){
        Vertices.replace(key, vertex);
    }

    public boolean isVertexCircuit(K key){
        return getVertex(key).isCircuit();
    }

    public void setVertexCircuit(K key, boolean circuit){
        getVertex(key).setCircuit(circuit);
    }

    public List<Vertex<K, T>> getAllVertices () {
        List<Vertex<K, T>> vertices = new ArrayList<>(Vertices.size());

        vertices.addAll(Vertices.values());
        return vertices;
    }

    // Retorna camino desde vertice start hasta vertice end. Si no es posible determinar un camino
    // retorna null.
    public List<K> getPath(K start, K end){
        // Clase local para instanciar vertices con un campo que especifique si fueron visitados.
        // Hace falta usar el metodo setAdjacents para obtener los adyacentes.
        class vertex {
            public K node;
            public boolean visited; // false por default
            public List<vertex> adjacents;

            public vertex(K node){
                this.node = node;
            }
            public void setAdjacents(){
                adjacents = new ArrayList<>();
                for(Vertex<K, T> v : getVertex(node).getAdjacents()){
                    adjacents.add(new vertex(v.getKey()));
                }
            }
        }

        List<K> finalPath = null;   // Resultado final
        Stack<vertex> path = new Stack<>();     // Stack para obtener el camino

        // Se obtiene el camino (si existe) por medio de backtracking.
        vertex trav = new vertex(start);
        do {
            boolean allvisited = true;
            if(trav.adjacents == null) trav.setAdjacents();
            for(vertex v : trav.adjacents){
                if(!v.visited){
                    path.push(trav);
                    trav = v;
                    allvisited = false;
                    break;
                }
            }
            if(allvisited){
                if(path.isEmpty()) break;
                trav = path.pop();
            }
        } while (!trav.node.equals(end));

        // Si existe el camino, se guarda en finalPath
        if(!path.isEmpty()){
            finalPath = new LinkedList<>();
            for(vertex v : path){
                finalPath.add(v.node);
            }
        }

        return finalPath;
    }

    public void replaceVertexValue(K key, T value){
        Vertices.get(key).clear();
        Vertices.get(key).setValue(value);
    }

}