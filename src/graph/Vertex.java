package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex <E, T extends Comparable<T>>{
    private static final int ADJACENTS_NUMBER = 8;

    private E key;      // clave del vertice
    private T value;     // valor del vertice
    private List<Vertex<E, T>> adjacents;  // lista de vertices adyacentes

    protected Vertex (E key){
        this(key, null);
    }
    protected Vertex(E key, T value) {
        this.value = value;
        this.key = key;
        adjacents = new ArrayList<>(ADJACENTS_NUMBER);
    }

    public T getValue() {
        return value;
    }

    public E getKey(){
        return key;
    }

    public List<Vertex<E, T>> getAdjacents() {
        return adjacents;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setAdjacents(List<Vertex<E, T>> adjacents) {
        this.adjacents = adjacents;
    }

    public void setAdjacent(Vertex<E, T> to){
        adjacents.add(to);
    }

    public void clear (){
        this.value = null;
        this.adjacents.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(key)
                .append("(").append(value).append(")")
                .append("\nadjacents: ");
        for(Vertex<E, T> v : adjacents)
            builder.append(v.key)
                    .append("(").append(value).append(")")
                    .append(" ");
        return builder.toString();
    }
}