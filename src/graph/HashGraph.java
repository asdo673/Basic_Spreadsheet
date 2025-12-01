package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashGraph<E, T extends Comparable<T> > {

    private Map<E, Vertex<T>> Vertices = new HashMap<>();   // lista de vertices

    public HashGraph(List<E> keys, List<T> values){
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Se asigna el contenido de contents para cada vertice.
        int i = 0;
        for(E key : keys){
            Vertices.put(
                    key,
                    new Vertex<>(i, values.get(i++))
            );
        if(i == 159)
            System.out.println();
        }
    }

    public T getVertexValue(E key){
        //if(Vertices.get(key) == null) throw new NullPointerException("Value with key " + key + "does not exists.");
        return Vertices.get(key).getValue();
    }

    public void setVertexValue(E key, T value){
        Vertices.get(key).setValue(value);
    }

    public void replaceVertexValue(E key, T value){
        Vertices.get(key).clear();
        Vertices.get(key).setValue(value);
    }

}