package graph;

import java.util.ArrayList;
import java.util.List;

public class SpreadsheetGraph extends HashGraph<String, String>{

    public SpreadsheetGraph(List<String> keys) {
        super(keys);
        for(int i = 0; i < keys.size(); i++)
            getAllVertices().get(i).setValue("");
    }
    public SpreadsheetGraph(List<String> keys, List<String> values) {
        super(keys, values);
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

    public List<String> getAllCellLinks (String key) {
        List<String> adjacents = new ArrayList<>();

        for(Vertex<String, String> v : getVertex(key).getAdjacents())
            adjacents.add(v.getKey());
        return adjacents;
    }

    public void deleteOfLinksOf(String key){
        getVertex(key).clear();
        getVertex(key).setValue("");
    }

}
