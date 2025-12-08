package uni.aed.tda.graphTDA.shortestpath;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import uni.aed.tda.graphTDA.Graph;
import uni.aed.tda.graphTDA.Vertex;
import uni.aed.tda.graphTDA.Edge;

/**
 * Dijkstra shortest path
 * solo funciona con pesos de ruta no negativa
 * Devuelve una tupla del costo total de la ruta mas corta, e imprime la ruta
 */
public class Dijkstra<T extends Comparable<T>> {
    //metodo que determina la ruta mas corta
    public Map<Vertex<T>,CostPathPair<T>>
            getShortestPaths(Graph<T> graph, Vertex<T> start ){
        //hashmap de vertices y sus aristas
        Map<Vertex<T>,List<Edge<T>>> paths = new HashMap<>();
        //hashmap de vertices y sus costos
        Map<Vertex<T>,CostVertexPair<T>> costs = new HashMap<>();
        //llamada al metodo sobrecargado getshrotestpath con 4 parametros
        getShortestPath(graph,start,null,paths,costs);
        //hashmap de vertices y sus costo de ruta
        Map<Vertex<T>,CostPathPair<T>> map = new HashMap<>();
        //recorremos el mapa de vertices y sus costos
        for(CostVertexPair<T> pair : costs.values() ){
            int cost=pair.getCost();
            Vertex<T> vertex = pair.getVertex();
            List<Edge<T>> path = paths.get(vertex);
            //consignamos en el hashmap de vertices y costo de la ruta:
            //el vertice,el objeto CostPathPair con el costo y la ruta
            map.put(vertex,new CostPathPair<>(cost,path));            
        }
        return map;
    }
    private CostPathPair<T>
            getShortestPath(Graph<T> graph, Vertex<T> start, Vertex<T> end,
            Map<Vertex<T>,List<Edge<T>>> paths,
            Map<Vertex<T>,CostVertexPair<T>> costs){
        if(graph==null)
           throw (new NullPointerException("El Grafo no puede ser Nulo"));     
        if(start==null)
           throw (new NullPointerException("El vertice inicial no puede ser Nulo"));     
        //algoritmo de Dijkstra ponderado y no negativo
        boolean hasNegativeEdge = checkForNegativeEdges(graph.getAllVertices());
        if(hasNegativeEdge)
            throw (new IllegalArgumentException("Aristas con costos negativos no estan permitidos"));
        //descargas los vertices en el hasmap paths 
        for(Vertex<T> v: graph.getAllVertices())
            paths.put(v,new ArrayList<>());
        //recorremos el grado y por cada vertice se compara con el vertice inicial
        for(Vertex<T> v: graph.getAllVertices()){
            if(v.equals(start)) //si se trata del vertice inicial, consigna cero como costo del vertice
                costs.put(v,new CostVertexPair<>(0,v));
            else    //si no es el vertice inicial, inicializa el costo con un entero muy grande(equiv. inf)
                costs.put(v,new CostVertexPair<>(Integer.MAX_VALUE,v));                
        }
        //declaramos una cola prioritaria
        Queue<CostVertexPair<T>> unvisited = new PriorityQueue<>();
        //añadimos a la cola el hashmap de costos del vertice inicial
        unvisited.add(costs.get(start));
        //mientras la cola no este vacia
        while(!unvisited.isEmpty()){
            //desencolar
            CostVertexPair<T> pair = unvisited.remove();
            //recuperamos el vertice del objeto desencolado
            Vertex<T> vertex = pair.getVertex();
            //Calculo de los costos desde el vertice actual hacia todos los vertices adyacentes que no han sido visitados
            for(Edge<T> e : vertex.getEdges()){
                //obtenemos el objeto CostVertexPair<T> del hashmap de costos
                CostVertexPair<T> toPair = costs.get(e.getToVertex());//vertice adyancente
                //obtenemos el objetos CostVertexPair<T> con menor costo
                CostVertexPair<T> lowestCostToThisVertex = costs.get(vertex);
                int cost = lowestCostToThisVertex.getCost() + e.getCost();
                //verificar si el costo del vertice destino es infinito
                if(toPair.getCost()==Integer.MAX_VALUE){
                    //desencolar-> setear-> encolar el vertice con el costo actualizado
                    unvisited.remove(toPair);
                    toPair.setCost(cost);
                    unvisited.add(toPair);
                    //actualizar las rutas
                    List<Edge<T>> set = paths.get(e.getToVertex());
                    set.addAll(paths.get(e.getFromVertex()));//aristas del vertice origen se añade a la lista set
                    set.add(e);
                    
                }else if(cost < toPair.getCost()){
                    //encontro un camino mas costo
                    //desencolar el par, actualizar el costo y volver a encolar
                    unvisited.remove(toPair);
                    toPair.setCost(cost);
                    unvisited.add(toPair);
                    //Actualizar ruta
                    List<Edge<T>> set = paths.get(e.getToVertex());
                    set.clear();
                    set.addAll(paths.get(e.getFromVertex()));//aristas del vertice origen se añade a la lista set
                    set.add(e);
                }
            }//end for
            //condiciones de terminacion del ciclo
            if(end!=null && vertex.equals(end))
                break;
        }//end while
        if(end!=null){
            CostVertexPair<T> pair = costs.get(end);
            List<Edge<T>> set = paths.get(end);
            return (new CostPathPair<>(pair.getCost(),set));            
        } 
        return null;
    }//end metodo
    private boolean checkForNegativeEdges(Collection<Vertex<T>> vertices){
        for(Vertex<T> v: vertices){
            for(Edge<T> e: v.getEdges()){
                if(e.getCost()<0)
                    return true;
            }
        }
        return false;
    }
}
