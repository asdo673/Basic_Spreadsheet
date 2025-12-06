package program;

import graph.paths.GraphPaths;
import graph.SpreadsheetGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clase para definir el shepadsheet.
public class Spreadsheet {
    private static final List<String> COLUMNS;  // Lista de columnas a usar como String

    private static final int rowsNumber = 20;   // Numero de filas
    private static final int columnsNumber = 8;    // Numero de columnas
    private final SpreadsheetGraph MainGraph;     // Grafo que conforma la estructura del spreadsheet
    private final GraphPaths<String, String> Circuits;      // Grafo que guarda ciclos

    static {
        // inicializacion de lista de columnas
        COLUMNS = new ArrayList<>(COLUMN.values().length);
        int counter = 0;
        for(COLUMN c : COLUMN.values()){
            if(counter > columnsNumber) break;
            COLUMNS.add(c.toString());
            counter++;
        }
    }
    public static void main(String[] args){
        Spreadsheet e = new Spreadsheet();
        e.setCell("A1", "+A2");
        e.setCell("A2", "+B2");
        e.setCell("B2", "+B1");
        e.setCell("B1", "+A1");
        System.out.println(e.getCell("A2"));
        System.out.println(e.getCell("A1"));
        System.out.println(e.getCell("B2"));
        System.out.println(e.getCell("B1"));
        e.setCell("B1", "gaaaaa");
        System.out.println(e.getCell("A2"));
        System.out.println(e.getCell("A1"));
        System.out.println(e.getCell("B2"));
        System.out.println(e.getCell("B1"));
    }
    public Spreadsheet(){  // constructor principal
        List<String> keys = new ArrayList<>(rowsNumber * columnsNumber);
        // Inicializacion de keys y celdas. Va de fila en fila.
        // Cada key es la ubicacion de la celda, e.g., "A3".
        for(int i = 0; i < rowsNumber; i++){
            for(String c : COLUMNS){
                keys.add(c + (i + 1));
            }
        }
        MainGraph = new SpreadsheetGraph(keys);
        Circuits = new GraphPaths<>(MainGraph);
    }
    
    public void setCell (String location, double content){
        setCell(location, String.valueOf(content));
    }
    public void setCell (String location, int content){
        setCell(location, String.valueOf(content));
    }
    public void setCell (String location, String content) {
        // Asigna nuevo contenido a la celda especificada. Se admite guardar los comandos de las operaciones
        // en las celdas.
        MainGraph.clearCell(location);  // elimina las conecciones con las celdas que referenciaba y elimina su contenido

        if(content.startsWith("+")){
            String referenced = content.substring(1);   // obtiene celda refernciada
            MainGraph.setCellLink(location, referenced);    // celda actual apuntara a referenciada
        }
        else if (content.startsWith("@")){
            String regex = "[A-H]|1[0-9]|20|[0-9]";
            List<String> matches = getMatches(regex, content);      // se obtiene lista con las 2 columnas y
                                                                    // las 2 filas que delimitan el bloque seleccionado
            String lesserColumn;
            String lesserRow;
            String greaterColumn;
            String greaterRow;

            // se determina columna menor y mayor, asi como fila menor y mayor.
            if(matches.get(0).compareTo(matches.get(2)) < 0){
                lesserColumn = matches.get(0);
                greaterColumn = matches.get(2);
            } else {
                lesserColumn = matches.get(2);
                greaterColumn = matches.get(0);
            }
            if (Integer.parseInt(matches.get(1)) < Integer.parseInt(matches.get(3))){
                lesserRow = matches.get(1);
                greaterRow = matches.get(3);
            } else {
                lesserRow = matches.get(3);
                greaterRow = matches.get(1);
            }

            // se obtiene indices
            int minCol = COLUMNS.indexOf(lesserColumn);
            int maxCol = COLUMNS.indexOf(greaterColumn);
            int minRow = Integer.parseInt(lesserRow);
            int maxRow = Integer.parseInt(greaterRow);
            for(int i = minCol; i <= maxCol; i++)
                for(int j = minRow; j <= maxRow; j++)
                    // Se enlaza la celda actual con cada vertice del bloque seleccionado
                    MainGraph.setCellLink(location, COLUMNS.get(i) + j);
        }

        Circuits.refreshPathsAndFindCircuits();   // Actualiza para eliminar los ciclos guardados que ya no existan (por haberse reenlazado celdas).

        List<String> circuit = MainGraph.getPath(location, location);   // Se obtiene ciclo de location. Si no existe, se obtiene null.
        if(circuit != null && !Circuits.contains(circuit))
            Circuits.add(circuit);      // Se guarda ciclo si este es nuevo

        MainGraph.setCellContent(location, content);    // Se fija contenido en la celda.
    }

    private List<String> getMatches(String regex, String content){
        // retorna lista con las subcadenas que encagen con el patron determinado en regex.
        Matcher matcher = Pattern.compile(regex).matcher(content);
        List<String> matchesList = new ArrayList<>();
        while(matcher.find())
            matchesList.add(matcher.group());

        return matchesList;
    }

    public String getCell (String location) {
        // Devuelve el contenido de la celda especificada. Si el contenido es una operacion entonces devuelve
        // el resultado; si es una referncia, devuelve el contenido de la celda referida. En caso esta celda pertenezca
        // a algun ciclo, retorna 'AUTOREF'.
        String content = Circuits.contains(location) ? "AUTOREF" : MainGraph.getCellContent(location);  // Si celda pertenece a un ciclo en el grafo, entonces devuelve AUTOREF

        if(content.startsWith("+")){
            String referenced = MainGraph.getCellLink(location);    // se obtiene celda a la que refiere
            content = getCell(referenced);
        }
        else if (content.startsWith("@")) {
            String operation = content.substring(1, 4);
            List<String> adjacents = MainGraph.getAllCellLinks(location);   // se obtienen todas las celdas a las que refiere
            switch (operation){
                case "max" :
                    content = maxOperation(location, adjacents);
                    break;
                case "min" :
                    content = minOperation(location, adjacents);
                    break;
                case "avg" :
                    content = avgOperation(location, adjacents);
                    break;
                case "sum" :
                    content = sumOperation(location, adjacents);
                    break;
            }
        }

        return content;
    }

    private String maxOperation (String location, List<String> adjacents) { // SI NO NUMEROS, 0
        List<Double> values = getAdjacentValues(adjacents);
        String result = "0";
        double max;
        if(!values.isEmpty()){
            max = values.getFirst();
            for(double num : values){
                if(num > max) max = num;
            }
            result = String.valueOf(max);
        }
        return result;
    }
    private String minOperation (String location, List<String> adjacents) { // SI NO NUMEROS, 0
        List<Double> values = getAdjacentValues(adjacents);
        String result = "0";
        double min;
        if(!values.isEmpty()){
            min = values.getFirst();
            for(double num : values){
                if(num < min) min = num;
            }
            result = String.valueOf(min);
        }
        return result;
    }
    private String avgOperation (String location, List<String> adjacents) { // SI NO NUMEROS, ERROR
        List<Double> values = getAdjacentValues(adjacents);
        String result = "ERROR";
        if(!values.isEmpty()){
            double number = values.size();
            double sum = 0;
            for(double num : values){
                sum += num;
            }
            result = String.valueOf(sum / number);
        }
        return result;
    }
    private String sumOperation (String location, List<String> adjacents) {
        List<Double> values = getAdjacentValues(adjacents);
        String result = "0";
        double sum = 0;
        if(!values.isEmpty()){
            for(double num : values){
                sum += num;
            }
            result = String.valueOf(sum);
        }
        return result;
    }
    
    private List<Double> getAdjacentValues(List<String> adjacents){
        // Retorna lista con los contenidos de las celdas de adyacentes que son numeros.
        // Si no se reconoce ninguno como numero, entonces la lista estara vacia.
        List<Double> values = new ArrayList<>(adjacents.size());
        for(String cell : adjacents){
            double value = 0;
            try {
                value = Double.parseDouble(getCell(cell));
                values.add(value);
            } catch (NumberFormatException _){}
        }
        return values;
    }
    
    public String getValue(String location){
        return MainGraph.getCellContent(location);
    }
}
