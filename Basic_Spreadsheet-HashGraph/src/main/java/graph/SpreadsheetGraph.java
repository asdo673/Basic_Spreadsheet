package graph;

import java.io.BufferedReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

// Clase especifica para definir la estructura del spreadsheet. Es subclase de HashGraph
public class SpreadsheetGraph extends HashGraph<String, String>{
    StreamTokenizer fIn;
    public SpreadsheetGraph(List<String> keys) {
        // Se inicializa grafo con los elementos de keys como llaves y un vertice para
        // cada uno. Value de cada vertice es String vacio.
        super(keys);
        for(int i = 0; i < keys.size(); i++)
            getAllVertices().get(i).setValue("");
    }

    public String getCellContent (String key){
        return getVertex(key).getValue();
    }

    public void setCellContent (String key, String content){
        getVertex(key).setValue(content);
    }

    public void setCellLink(String OriginKey, String DestinyKey){
        getVertex(OriginKey).setAdjacent(
                getVertex(DestinyKey));
    }

    public String getCellLink(String key){
        // Retorna la ubicacion de la unica celda enlazada. Si existen mas, entonces
        // retorna la de la primera celda en haberse enlazado.
        return getVertex(key).getFirstAdjacent().getKey();
    }

    public List<String> getAllCellLinks (String key) {
        List<String> adjacents = new ArrayList<>();

        for(Vertex<String, String> v : getVertex(key).getAdjacents())
            adjacents.add(v.getKey());
        return adjacents;
    }

    public void deleteLinksOf(String key){
        getVertex(key).clear();
        getVertex(key).setValue("");
    }
    

}
