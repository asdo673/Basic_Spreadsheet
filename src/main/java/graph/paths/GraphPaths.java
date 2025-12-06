package graph.paths;

import graph.HashGraph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Clase que define un grafo no dirigido donde se puede guardar caminos. Cada camino registrado se enlaza con los vertices que lo conforman
// y cada vertice se enlaza con los caminos registrados a los que pertenece. No permite caminos repetidos.
public class GraphPaths<K extends Comparable<K>, T extends Comparable<T>> {
    private HashMap<K, NODE> Nodes;
    private HashMap<List<K>, PATH> Paths;
    private HashGraph<K, T> MainGraph;

    public GraphPaths(HashGraph<K, T> graph){
        if(graph == null) throw new NullPointerException("Graph not instantiated.");
        MainGraph = graph;
        Nodes = new HashMap<>();
        Paths = new HashMap<>();
    }

    public boolean contains (K node){
        return Nodes.containsKey(node);
    }
    public boolean contains (List<K> path){
        return Paths.containsKey(path);
    }
    public void add(List<K> path){
        if(contains(path)) throw new IllegalArgumentException("Path already exist");

        PATH newPath = new PATH(path);
        Paths.put(path, newPath);

        for(K node : newPath.route) {
            NODE newNode = Nodes.get(node);
            // si newNode no existe en Nodes, entonces se agrega.
            if(newNode == null) {  // solo puede ser null si no existe en Nodes (pues Nodes no acepta valores null)
                newNode = new NODE(node);
                Nodes.put(node, newNode);
            }
            // se enlaza a newNode con su nuevo camino newPath, y a newPath con su nodo newNode
            newNode.paths.add(newPath);
            newPath.nodes.add(newNode);
        }
    }
    // Elimina todos los caminos guardados que no existan mas.
    public void refreshPaths(){
        List<PATH> tmpPaths = new ArrayList<>(Paths.values()); // paths y Paths.values() referencian a distintas instancias pero con mismas referencias de elementos
        for(PATH p : tmpPaths)
            if(notValid(p)) deletePath(p);
    }

    private boolean notValid(PATH path){
        boolean isValid = false;
        List<K> pathVertices = path.route;
        // Si el bucle es interrumpido, entonces el camino no es valido y retorna true
        for(int i = 0; i < pathVertices.size() - 1; i++){
            K current = pathVertices.get(i);        // vertice actual
            Vertex<K, T> next = MainGraph.getVertex(pathVertices.get(i + 1));     // vertice siguiente
            if(!MainGraph.getAdjacentVertices(current).contains(next)){         // si actual no apunta a siguiente, entonces el camino ha sido roto.
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    private void deletePath(PATH path){
        if(path == null) throw new NullPointerException("Path is null.");

        List<NODE> rout = path.nodes;   // rout y path.nodes referencian a la misma instancia
        while(!rout.isEmpty()){     // desenlaza cada nodo totalmente desde el primero hasta el ultimo
            NODE n = rout.getFirst();
            n.paths.remove(path);
            path.nodes.remove(n);
            if(n.paths.isEmpty()) Nodes.remove(n.vertex);   // si nodo ya no pertenece a ningun ciclo conocido se descarta
        }
        Paths.remove(path.route);   // se elimina registro del camino en la coleccion
    }

    class NODE {    // relaciona vertice con todos los caminos registrados a los que pertenece
        K vertex;
        List<PATH> paths = new ArrayList<>();
        public NODE (K vertex){
            this.vertex = vertex;
        }
    }
    class PATH {    // relaciona camino con cada vertice que lo conforma
        List<K> route;
        List<NODE> nodes = new ArrayList<>();
        public PATH (List<K> path){
            route = path;
        }
    }
}
