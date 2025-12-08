package uni.aed.tda.graphTDA.shortestpath;

import java.util.List;
import uni.aed.tda.graphTDA.Edge;

/*Clase costo de ruta */
public class CostPathPair<T extends Comparable<T>> {
    private int cost=0;
    private List<Edge<T>> path=null;//aristas que comprenden la ruta
    //constructor

    public CostPathPair(int cost,List<Edge<T>> path) {
        if(path==null)
            throw (new NullPointerException("El path no puede ser Nulo"));
        this.cost=cost;
        this.path=path;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPath(List<Edge<T>> path) {
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public List<Edge<T>> getPath() {
        return path;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Costo= ").append(cost).append("\n");
        for(Edge<T> e: path)
            builder.append("\t").append(e);
        return builder.toString();
    }
    
    
    
    
}
