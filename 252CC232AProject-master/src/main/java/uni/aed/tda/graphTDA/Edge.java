package uni.aed.tda.graphTDA;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {
    private Vertex<T> from=null; //vertice origen
    private Vertex<T> to=null; //vertice destino
    private int cost=0;
    //1° constructor
    public Edge(int cost,Vertex<T> from,Vertex<T> to) {
        if(from==null || to==null)
            throw new NullPointerException("Vertices no deben ser nulo");
        this.cost=cost;
        this.from=from;
        this.to=to;
    }
    //2° constructor
    public Edge(Edge<T> e) {
        this(e.cost,e.from,e.to);        
    }

    public Vertex<T> getFromVertex() {
        return from;
    }

    public Vertex<T> getToVertex() {
        return to;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    
    @Override
    public int compareTo(Edge<T> o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("[")      
                .append(from.getValue())    //vertice origen
                .append("(")      
                .append(from.getWeigth())   //peso del vertice origen
                .append(")")      
                .append("]")      
                .append("->")      
                .append(to.getValue())      //vertice destino
                .append("(")      
                .append(to.getWeigth())     //peso del vertice destino
                .append(")")      
                .append("]")      
                .append("=")      
                .append(cost)               //costo de la arista
                .append("\n");
        return builder.toString();                
    }
    
    
    
}
