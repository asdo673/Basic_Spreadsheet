package uni.aed.tda.graphTDA.recubrimiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import uni.aed.tda.graphTDA.Edge;
import uni.aed.tda.graphTDA.Graph;
import uni.aed.tda.graphTDA.Vertex;
import uni.aed.tda.graphTDA.shortestpath.CostPathPair;

public class Kruskal {
    public Kruskal() {
    }
    public static CostPathPair<Integer>
    getMinimumSpanningTree(Graph<Integer> graph){
        if(graph==null)
           throw (new NullPointerException("El Grafo no puede ser Nulo")); 
        //Kruskal funciona con grafos no dirigidos
        if(graph.getType()==Graph.TYPE.DIRECTED)
            throw (new IllegalArgumentException("El Grafo debe ser No Dirigido")); 
        int cost=0;
        //Declaramos el objeto que va contener las aristas que conforman el arbol
        List<Edge<Integer>> path=new ArrayList<>();
        //HashSet: permite almacenar no duplicados, no mantiene un orden
        Map<Vertex<Integer>,HashSet<Vertex<Integer>>>
        membershipMap=new HashMap<>();
        //Inicializando el mapa con los vertices del grafo
        for(Vertex<Integer> v: graph.getAllVertices()){
            HashSet<Vertex<Integer>> set=new HashSet<>();
            set.add(v);//añadimos cada vertice del grafo en el conjunto
            membershipMap.put(v, set);//añadimos cada vertice del grafo y el set(conjunto) a la tabla
        }
        //declaramos cola prioritaria con todas las aristas del grafo
        Queue<Edge<Integer>> edgeQueue=new PriorityQueue<>(graph.getAllEdges());
        while(!edgeQueue.isEmpty()){
            Edge<Integer> edge=edgeQueue.poll();//equivalente a remove o dequeue
            //si los vertices que unen la arista no son lo mismo
            if(!isTheSamePart(edge.getFromVertex(),edge.getToVertex(),membershipMap)){
                union(edge.getFromVertex(),edge.getToVertex(),membershipMap);
                path.add(edge);
                cost+=edge.getCost();
            }//end if            
        }//end while
        return (new CostPathPair<>(cost,path));            
    }
    
    private static boolean isTheSamePart(Vertex<Integer> v1,Vertex<Integer> v2,
            Map<Vertex<Integer>,HashSet<Vertex<Integer>>> membershipMap){
        return membershipMap.get(v1)==membershipMap.get(v2);
    }
    private static void union(Vertex<Integer> v1,Vertex<Integer> v2,
            Map<Vertex<Integer>,HashSet<Vertex<Integer>>> membershipMap){
       HashSet<Vertex<Integer>> firstSet =  membershipMap.get(v1);
       HashSet<Vertex<Integer>> secondSet =  membershipMap.get(v2);
       if(secondSet.size()>firstSet.size()){
           //Realizamos un intercambio
           HashSet<Vertex<Integer>> tempSet=firstSet;
           //asignamos el conjunto mas grande en el objeto firsSet
           firstSet=secondSet;
           //asignamos el conjunto mas pequeño en el objeto secondSet
           secondSet=tempSet;
       }
       //descargamos en la tabla cada vertice del segundo conjunto
       for(Vertex<Integer> v: secondSet)
           membershipMap.put(v,firstSet);
       //los vertices que no se encuentren en el conjunto mas grande, seran añadidos
       firstSet.addAll(secondSet);       
    }
    
}
