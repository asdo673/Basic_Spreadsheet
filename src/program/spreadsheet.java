package program;

import graph.SpreadsheetGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clase para definir el shepadsheet.
// Tal vez se podria crear una clase para manejar el grafo especial para el spreadsheet
// y esta clase que utilize a esa otra de modo que aqui solo se maneje las operaciones.
// Spreadsheet creo que igual y deberia manejar mas que valores, es decir, eerencias a celdas y asi
// Aunque celda creo que si o si deberia ser solo valore (Numero, Texto);
// Tal vez se podria usar de key un nuevo objeto que involucre COLUMNA y NUMERO en lugar de una cadena.
public class spreadsheet {
    private static final List<String> COLUMNS;

    private static final int rowsNumber = 20;   // Numero de filas
    private static final int columnsNumber = COLUMN.values().length;    // Numero de columnas
    private final SpreadsheetGraph MainGraph;     // Grafo que conforma la estructura del spreadsheet

    public static void main (String[] args) {
        spreadsheet sh= new spreadsheet();
        System.out.println("sh");
        sh.setCell("A1", "10");
        sh.setCell("B1", "20");
        sh.setCell("A2", "30");
        sh.setCell("D1", "+A1");
        sh.setCell("C1", "@sum(A1..B2)");
        sh.setCell("D1", "+C1");
        sh.setCell("H3", "@avg(A1..B2)");

        System.out.println(sh.getCell("A1"));
        System.out.println(sh.getCell("B1"));
        System.out.println(sh.getCell("A2"));
        System.out.println(sh.getCell("D1"));
        System.out.println(sh.getCell("C1"));
        System.out.println(sh.getCell("D3"));
        System.out.println(sh.getCell("H3"));
    }

    static {
        // inicializacion de lista de columnas
        COLUMNS = new ArrayList<>(COLUMN.values().length);
        for(COLUMN c : COLUMN.values()){
            COLUMNS.add(c.toString());
        }
    }

    public spreadsheet (){  // constructor principal
        List<String> keys = new ArrayList<>(rowsNumber * columnsNumber);
        // Inicializacion de keys y cells. Va de fila en fila.
        for(int i = 0; i < rowsNumber; i++){
            for(String c : COLUMNS){
                keys.add(c + (i + 1));
            }
        }
        MainGraph = new SpreadsheetGraph(keys);
    }

    // operacion
    public void setCell (String location, int content){
        setCell(location, String.valueOf(content));
    }
    public void setCell (String location, String content) {
        // Asigna nuevo contenido a la celda especificada.
        MainGraph.deleteOfLinksOf(location);

        if(content.startsWith("+")){
            String referenced = content.substring(1);
            MainGraph.setCellLink(location, referenced);    // celda actual apuntara a referenciada
        }
        else if (content.startsWith("@")){
            String regex = "[A-H]|1[0-9]|20|[0-9]";
            List<String> matches = getMatches(regex, content);

            String lesserColumn;
            String lesserRow;
            String greaterColumn;
            String greaterRow;

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

            int minCol = COLUMNS.indexOf(lesserColumn);
            int maxCol = COLUMNS.indexOf(greaterColumn);
            int minRow = Integer.parseInt(lesserRow);
            int maxRow = Integer.parseInt(greaterRow);
            for(int i = minCol; i <= maxCol; i++)
                for(int j = minRow; j <= maxRow; j++)
                    MainGraph.setCellLink(location, COLUMNS.get(i) + j);
        }

        MainGraph.setCellContent(location, content);
    }

    private List<String> getMatches(String regex, String content){
        Matcher matcher = Pattern.compile(regex).matcher(content);
        List<String> matchesList = new ArrayList<>();
        while(matcher.find())
            matchesList.add(matcher.group());

        return matchesList;
    }

    public String getCell (String location) {
        // Devuelve el contenido de la celda especificada. Si el contenido es una operacion entonces devuelve
        // el resultado; si es una referncia, devuelve el contenido de la celda referida.
        String content = MainGraph.getCellContent(location);

        if(content.startsWith("+")){
            String referenced = content.substring(1);
            content = getCell(referenced);
        }
        else if (content.startsWith("@")) {
            String operation = content.substring(1, 4);
            switch (operation){
                case "max" :
                    content = maxOperation(location);
                    break;
                case "min" :
                    content = minOperation(location);
                    break;
                case "avg" :
                    content = avgOperation(location);
                    break;
                case "sum" :
                    content = sumOperation(location);
                    break;
            }
        }

        return content;
    }

    private String maxOperation (String location) { // SI NO NUMEROS, 0
        List<String> adjacents = MainGraph.getAllCellLinks(location);
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
    private String minOperation (String location) { // SI NO NUMEROS, 0
        List<String> adjacents = MainGraph.getAllCellLinks(location);
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
    private String avgOperation (String location) { // SI NO NUMEROS, ERROR
        List<String> adjacents = MainGraph.getAllCellLinks(location);
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
    private String sumOperation (String location) {
        List<String> adjacents = MainGraph.getAllCellLinks(location);
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

  /*  private class Cell implements Comparable<Cell> {   // Inner class para representar celdas del spreadsheet
        private String content;
        public COLUMN column;
        public int row;

        public Cell (COLUMN column, int row) {
            this (column, row, "");
        }
        public Cell(COLUMN column, int row, String content){
            this.column = column;
            this.row = row;
            this.content = content;
        }
        @Override
        public int compareTo(Cell o) {
            // TODO
            return 0;
        }
        @Override
        public String toString() {
            return "[" + column + row + ": " + content + "]";
        }
    }*/
}
