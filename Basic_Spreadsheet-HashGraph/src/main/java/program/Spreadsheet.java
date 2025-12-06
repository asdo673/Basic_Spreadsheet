package program;

import graph.SpreadsheetGraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;

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
    private StreamTokenizer fIn;
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
        MainGraph.deleteLinksOf(location);

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

            // se determina columna menor y mayor, y fila menor y mayor.
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
                    // Se enlaza la celda actual con cada vertice del bloque seleccionado
                    MainGraph.setCellLink(location, COLUMNS.get(i) + j);
        }

        MainGraph.setCellContent(location, content);
    }

    private List<String> getMatches(String regex, String content){
        // retorna lista con las subcadenas que encagen con el patron determinado en regex.
        Matcher matcher = Pattern.compile(regex).matcher(content);
        List<String> matchesList = new ArrayList<>();
        while(matcher.find())
            matchesList.add(matcher.group());

        return matchesList;
    }
    
    
    public double interpreter(String location) throws IOException{
        String expression = getValue(location);
        
        fIn = new StreamTokenizer(
                new BufferedReader(new StringReader(expression))
        );
        
        fIn.wordChars('0','9');
        fIn.wordChars('$','$');
        fIn.wordChars('.', '.');

        fIn.ordinaryChar('/');
        fIn.ordinaryChar('-');
        fIn.ordinaryChar(',');
        return  expression();
        
    }
    
    private double expression() throws IOException{
        double t = term();
        
        while(true){
            fIn.nextToken();
            
            switch(fIn.ttype){
                case '+': t += term(); break;
                case '-': t -= term(); break;
                default : fIn.pushBack(); return t;
            }
        }  
    }
    
    
    private double term() throws IOException{
        double f = factor();
    
        while(true){
            fIn.nextToken();
            
            switch(fIn.ttype){
                case '*': f *= factor(); break;
                case '/': f /= factor(); break;
                default : fIn.pushBack(); return f;
            }
        }
    }
    
    private double factor() throws IOException{ 
        double val, minus = 1.0;
        fIn.nextToken();
        while(fIn.ttype == '+' || fIn.ttype == '-'){
            if(fIn.ttype == '-')
                minus *= -1;
            fIn.nextToken();
        }
        
        if(fIn.ttype == fIn.TT_NUMBER || fIn.ttype == '.'){
            if(fIn.ttype == fIn.TT_NUMBER){
                val = fIn.nval;
                fIn.nextToken();
            }
             else val = 0;
            if(fIn.ttype == ','){
                fIn.nextToken();
                if(fIn.ttype == fIn.TT_NUMBER){
                    String s = fIn.nval + "";
                    s = "." + s.substring(0,s.indexOf('.'));
                    val += Double.valueOf(s);
                }
                else fIn.pushBack();
            }
            else fIn.pushBack();
        }
        else if(fIn.ttype == '('){
            val = expression();
            if(fIn.ttype == ')')
                fIn.nextToken();
            else {
                System.out.println("Falta un parentesis al cerrar la expresion");
                Runtime.getRuntime().exit(-1);
            }
        }
        else {
            val = Double.parseDouble(); A1 @sum(A1..A2) A1 = 10 + C5
        }
        
        return minus*val;
    }
    public String getCellOperation(String operation){
        
    }
    
    public String getCell (String location) {
        // Devuelve el contenido de la celda especificada. Si el contenido es una operacion entonces devuelve
        // el resultado; si es una referncia, devuelve el contenido de la celda referida.
        String content = MainGraph.getCellContent(location);

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
        // Retorna lista con los contenidos de las celdas de adjacents que son numeros.
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
