package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class HashGraph<K, T extends Comparable<T> > {

    private Map<K, Vertex<K, T>> Vertices = new HashMap<>();   // lista de llaves con vertices

    public HashGraph(List<K> keys){
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Value de cada vertice es null.
        int i = 0;
        for(K key : keys){
            Vertices.put(
                    key,
                    new Vertex<>(key)
            );
        }
    }
    public HashGraph(List<K> keys, List<T> values){
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Se asigna el contenido de values para cada vertice.
        int i = 0;
        for(K key : keys){
            Vertices.put(
                    key,
                    new Vertex<>(key, values.get(i++))
            );
        }
    }
    
    public List<Vertex<K, T>> getAdjacentVertices(K key){
        return getVertex(key).getAdjacents();
    }
    
    public Vertex<K, T> getVertex(K key){
        if(!Vertices.containsKey(key))
            throw new NullPointerException("Not such key in the graph.");
        return Vertices.get(key);
    }

    public void setVertex(K key, Vertex<K, T> vertex){
        Vertices.replace(key, vertex);
    }

    public List<Vertex<K, T>> getAllVertices () {
        List<Vertex<K, T>> vertices = new ArrayList<>(Vertices.size());

        vertices.addAll(Vertices.values());
        return vertices;
    }

    public void replaceVertexValue(K key, T value){
        Vertices.get(key).clear();
        Vertices.get(key).setValue(value);
    }

    // Retorna una secuencia de llaves de vertices que representa un camino entre vertice start y vertice end.
    // En caso no se pueda determinar un camino, retorna null.
    public List<K> getPath(K start, K end){
        // Clase local para instanciar vertices con un campo que especifique si fueron visitados.
        // Hace falta usar el metodo setAdjacents para obtener los adyacentes.
        class vertex{
            public K node;
            public boolean visited; // false por default
            public List<vertex> adjacents;

            public vertex(K node){
                this.node = node;
            }
            public void setAdjacents(Stack<vertex> stack, K end){
                adjacents = new ArrayList<>();
                for(Vertex<K, T> v : getVertex(node).getAdjacents())
                    // solo anade los vertices por los que no se haya pasado (los del stack) y el que no sea final.
                    // La condicion para verificar si es el final solo existe para poder encontrar ciclos.
                    if(!contains(v.getKey(), stack) || v.getKey().equals(end))
                        adjacents.add(new vertex(v.getKey()));
            }
            private boolean contains(K vert, Stack<vertex> stack){
                for(vertex v : stack)
                    if(v.node.equals(vert))
                        return true;
                return false;
            }
        }

        List<K> finalPath = null;   // Resultado final
        Stack<vertex> path = new Stack<>();     // Stack para obtener el camino

        // Se obtiene el camino (si existe) por medio de backtracking.
        vertex trav = new vertex(start);    // variable para traversar
        do {
            boolean allvisited = true;
            if(trav.adjacents == null) trav.setAdjacents(path, end); // lista puede tener vertices o estar vacia (imposible null)
            for(vertex v : trav.adjacents){
                if(!v.visited){     // si v no ha sido visitado, se avanza por alli
                    path.push(trav);    // se registra vertice actual
                    trav = v;           // se avanza
                    allvisited = false;
                    break;
                }
            }
            if(allvisited){     // si no se encontraron vertices adyacentes no visitados, se retrocede o se termina busqueda
                if(path.isEmpty()) break;   // si path esta vacia, no se puede retroceder y se termina busqueda
                trav.visited = true;    // se marca vertice actual como visitado
                trav = path.pop();      // se retrocede al vertice anterior
            }
        } while (!trav.node.equals(end));   // si trav llego al vertice end, se encontro el camino y se termina la busqeuda

        // Si existe el camino, se guarda en finalPath
        if(!path.isEmpty()){
            finalPath = new LinkedList<>();
            for(vertex v : path){
                finalPath.add(v.node);
            }
            // Se agrega el final
            finalPath.add(end);
        }

        return finalPath;
    }
}