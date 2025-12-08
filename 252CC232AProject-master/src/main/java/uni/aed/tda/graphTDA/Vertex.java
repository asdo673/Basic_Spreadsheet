package uni.aed.tda.graphTDA;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>>{
    private T value=null;   //valor del vertice    
    private int weigth=0;   //peso del vertice
    private List<Edge<T>> edges=new ArrayList<>();//lista de aristas del vertice

    public Vertex(T value) {
        this.value=value;
    }
    public Vertex(T value, int weigth ) {
        this.value=value;
        this.weigth=weigth;        
    }
    public Vertex(Vertex<T> vertex) {
        //invoca al 1° constructor
        this(vertex.value,vertex.weigth);
        //consignar todas las ariasta en la lista edges de la clase
        this.edges.addAll(vertex.edges);
    }

    public T getValue() {
        return value;
    }

    public int getWeigth() {
        return weigth;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void setWeigth(int weigth) {
        this.weigth = weigth;
    }
    //metodo para añadir una arista a un vertice
    public void addEdge(Edge<T> e){
        edges.add(e);
    }
    /*metodo que retorna una arista que tiene como destino el vertice
    indicado como parametro
    */
    public Edge<T> getEdge(Vertex<T> v){
        //recorrer la lista de aristas
        for(Edge<T> e: edges){
            //pendiente del implementar
        }
        return null;
    }    
    
    @Override
    public int compareTo(Vertex<T> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Value=")
                .append(value)
                .append(" weight=")
                .append(weigth)
                .append("\n");
        for(Edge<T> e: edges)
            builder.append("\t").append(e.toString());
        return builder.toString();
    }
}
