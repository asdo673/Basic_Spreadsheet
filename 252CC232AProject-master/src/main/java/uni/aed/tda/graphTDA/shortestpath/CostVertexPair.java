package uni.aed.tda.graphTDA.shortestpath;
import uni.aed.tda.graphTDA.Vertex;
/** * Clase Costo del Vertice  */
public class CostVertexPair<T extends Comparable<T>> implements Comparable<CostVertexPair<T>>{
    private int cost=Integer.MAX_VALUE;
    private Vertex<T> vertex=null;
    //constructor

    public CostVertexPair(int cost,Vertex<T> vertex) {
        if(vertex==null)
            throw (new NullPointerException("El vertice no puede ser Nulo"));
        this.cost=cost;
        this.vertex=vertex;                
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setVertex(Vertex<T> vertex) {
        this.vertex = vertex;
    }

    public int getCost() {
        return cost;
    }

    public Vertex<T> getVertex() {
        return vertex;
    }

    @Override
    public int compareTo(CostVertexPair<T> o) {
        if(o==null)
            throw (new NullPointerException("CostVertexPair no puede ser Nulo"));
        if(this.cost<o.cost)
            return -1;
        if(this.cost>o.cost)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append(vertex.getValue()).append(" (")
                .append(vertex.getWeigth()).append(") ")
                .append(" cost=")
                .append(cost).append("\n");
        return builder.toString();
    }
    
    
    
}
