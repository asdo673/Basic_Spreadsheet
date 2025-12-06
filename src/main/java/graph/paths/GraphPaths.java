package graph.paths;

import graph.HashGraph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Clase que define un grafo no dirigido donde se puede guardar caminos. Cada camino registrado se enlaza con los vertices que lo conforman
// y cada vertice se enlaza con los caminos registrados a los que pertenece. No permite caminos vacios, null o que que tengan mismo set de
// vertices que otro camino ya guardado.
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
        for(List<K> storedPath : Paths.keySet()) {
            boolean isThere = true;
            for (K storedVertex : storedPath)
                if (!path.contains(storedVertex)){
                    isThere = false;
                    break;
                }
            if(isThere) return true;
        }
        return false;
    }
    public void add(List<K> path){
        if(path == null) throw new IllegalArgumentException("Path is null");
        if(path.isEmpty() || contains(path)) return;

        PATH newPath = new PATH(path);
        Paths.put(path, newPath);

        int numberNodes = path.size();
        if(path.getFirst().equals(path.getLast()))
            numberNodes -= 1;           // Si el camino es un ciclo, no se considera el destino por ser igual al inicio

        while(numberNodes > 0){
            K node = path.get(numberNodes);

            NODE newNode = Nodes.get(node);
            // si newNode no existe en Nodes, entonces se agrega.
            if(newNode == null) {  // solo puede ser null si no existe en Nodes (pues Nodes no acepta valores null)
                newNode = new NODE(node);
                Nodes.put(node, newNode);
            }
            // se enlaza a newNode con su nuevo camino newPath, y a newPath con su nodo newNode
            newNode.paths.add(newPath);
            newPath.nodes.add(newNode);

            numberNodes--;
        }
    }
    // Elimina todos los caminos guardados que no existan mas. Ademas, busca y guarda ciclos (de existir) a partir de cada vertice
    // de los caminos eliminados.
    public void refreshPathsAndFindCircuits(){
        List<PATH> tmpPaths = new ArrayList<>(Paths.values()); // paths y Paths.values() referencian a distintas instancias pero con mismas referencias de elementos
        for(PATH p : tmpPaths)
            if(notValid(p)) {
                List<K> route = p.route;
                deletePath(p);
                for(K node : route){
                    List<K> path = MainGraph.getPath(node, node);
                    if(path != null) add(path);
                }
            }
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
        List<K> route;  // camino ingresado
        List<NODE> nodes = new ArrayList<>();   // lista de nodos del camino. No debe poseer repeticiones.
        public PATH (List<K> path){
            route = path;
        }
    }
}
