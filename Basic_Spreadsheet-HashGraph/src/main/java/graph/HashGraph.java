package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Vertex<K, T>> getAllVertices () {
        List<Vertex<K, T>> vertices = new ArrayList<>(Vertices.size());

        vertices.addAll(Vertices.values());
        return vertices;
    }

    public void replaceVertexValue(K key, T value){
        Vertices.get(key).clear();
        Vertices.get(key).setValue(value);
    }

}