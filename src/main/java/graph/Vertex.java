package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex <K, T extends Comparable<T>>{

    private K key;      // clave del vertice
    private T value;     // valor del vertice
    private List<Vertex<K, T>> adjacents;  // lista de vertices adyacentes
    private boolean circuit;    // especifica si el vertice forma parte de un ciclo euleriano

    protected Vertex (K key){
        this(key, null);
    }
    protected Vertex(K key, T value) {
        this.value = value;
        this.key = key;
        adjacents = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public K getKey(){
        return key;
    }

    public List<Vertex<K, T>> getAdjacents() {
        return adjacents;
    }

    public boolean isCircuit(){
        return circuit;
    }

    public Vertex<K, T> getFirstAdjacent() {
        adjacents.getFirst();
        return adjacents.getFirst();
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setAdjacents(List<Vertex<K, T>> adjacents) {
        this.adjacents = adjacents;
    }

    public void setAdjacent(Vertex<K, T> to){
        adjacents.add(to);
    }

    public void setCircuit(boolean iscircuit){
        circuit = iscircuit;
    }

    public void clear (){
        this.value = null;
        this.adjacents.clear();
        this.circuit = false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(key)
                .append("(").append(value).append(")")
                .append("\nadjacents: ");
        for(Vertex<K, T> v : adjacents)
            builder.append(v.key)
                    .append("(").append(value).append(")")
                    .append(" ");
        return builder.toString();
    }
}