package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Clase que define un grafo no dirigido donde se puede guardar caminos. Cada camino registrado se enlaza con los vertices que lo conforman
// y cada vertice se enlaza con los caminos registrados a los que pertenece. No permite caminos vacios, caminos null o caminos que tengan mismo set de
// vertices que otro camino ya guardado.
public class GraphPaths<K extends Comparable<K>, T extends Comparable<T>> {
    private final HashMap<K, NODE> Nodes;
    private final HashMap<List<K>, PATH> Paths;
    private HashGraph<K, T> MainGraph;

    public GraphPaths(HashGraph<K, T> graph){
        if(graph == null) throw new NullPointerException("Graph not instantiated.");
        MainGraph = graph;
        Nodes = new HashMap<>();
        Paths = new HashMap<>();
    }

    // verifica existencia de node ne Nodes
    public boolean contains (K node){
        return Nodes.containsKey(node);
    }
    // verifica existencia de path en Paths
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
    // Agrega un camino path a Paths. No agrega si path esta vacio o ya existe en Paths. Lanza excepcion si path es null.
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
            if(newNode == null) {  // solo puede ser null si no existe en Nodes (pues Nodes no admite valores null)
                newNode = new NODE(node);
                Nodes.put(node, newNode);
            }
            // se enlaza a newNode con su nuevo camino newPath, y a newPath con su nodo newNode
            newNode.paths.add(newPath);
            newPath.nodes.add(newNode);

            numberNodes--;
        }
    }

    // Realiza 2 funciones importantes en el siguiente orden:
    // 1. Verifica si el vertice dado como argumento pertenece a un ciclo. Caso afirmativo se almacena.
    // 2. Busca en Paths los caminos que ya no se consideren validos para eliminarlos. Tras la eliminacion, se
    // busca algun ciclo involuntario entre los vertices del camino eliminado. Al encontrarse alguno se guarda en Paths.
    public void refreshCircuitsAndCheckFor(K location){
        // Revisar si location forma parte de un ciclo
        List<K> circuit = MainGraph.getPath(location, location);
        if (circuit != null) add(circuit); // Almacenar ciclo en caso afirmativo

        List<PATH> tmpPaths = new ArrayList<>(Paths.values()); // paths y Paths.values() referencian a distintas instancias pero con mismas referencias de elementos
        for(PATH p : tmpPaths)
            if(notValid(p)) {   // Si el camino no es valido (ya no se encuentran los vertices correctamente enlazados) ...
                // Eliminar camino
                List<K> route = p.route;
                deletePath(p);
                // Revisar si algun vertice del camino eliminado forma un ciclo. (ciclo involuntario)
                for(K node : route){
                    List<K> path = MainGraph.getPath(node, node);
                    if(path != null) add(path); // Almacenar ciclo en caso afirmativo
                }
            }
    }

    // Retorna true si camino no es valido, esto es, si se mantienen los enlazes que hacen posible el camino. Caso contrario, false.
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

    // Elimina camino path de Paths
    private void deletePath(PATH path){
        if(path == null) throw new NullPointerException("Path is null.");

        List<NODE> rout = path.nodes;   // rout y path.nodes referencian a la misma instancia
        while(!rout.isEmpty()){     // desenlaza cada nodo totalmente desde el primero hasta el ultimo
            NODE n = rout.getFirst();
            n.paths.remove(path);
            path.nodes.remove(n);
            if(n.paths.isEmpty()) Nodes.remove(n.vertex);   // si nodo ya no pertenece a ningun camino conocido se descarta
        }
        Paths.remove(path.route);   // se elimina registro del camino en la coleccion
    }

    // relaciona vertice con todos los caminos registrados a los que pertenece
    class NODE {
        K vertex;   // vertice infresado
        List<PATH> paths = new ArrayList<>();   // lista de caminos del vertice. No debe poseer repeticiones.
        public NODE (K vertex){
            this.vertex = vertex;
        }
    }
    // relaciona camino con cada vertice que lo conforma
    class PATH {
        List<K> route;  // camino ingresado
        List<NODE> nodes = new ArrayList<>();   // lista de nodos del camino. No debe poseer repeticiones.
        public PATH (List<K> path){
            route = path;
        }
    }
}