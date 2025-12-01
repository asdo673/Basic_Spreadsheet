package program;

import graph.SpreadsheetGraph;

import java.util.ArrayList;
import java.util.List;

// Clase para definir el shepadsheet.
// Tal vez se podria crear una clase para manejar el grafo especial para el spreadsheet
// y esta clase que utilize a esa otra de modo que aqui solo se maneje las operaciones.
// Spreadsheet creo que igual y deberia manejar mas que valores, es decir, eerencias a celdas y asi
// Aunque celda creo que si o si deberia ser solo valore (Numero, Texto);

public class spreadsheet {
    private static final int rowsNumber = 20;   // Numero de filas
    private static final int columnsNumber = COLUMN.values().length;    // Numero de columnas
    private final SpreadsheetGraph MainGraph;     // Grafo que conforma la estructura del spreadsheet

    public static void main (String[] args) {
        spreadsheet sh= new spreadsheet();
        System.out.println("sh");

        // sh.setCell("A1", 23);
        // sh.setCell(spreadsheet.Column.B, 2, "asda");  ?
        // sh.setCell("C2", "+A3");
        // sh.getCell("A2");
        // sh.getCell(spreadsheet.Column.A, 2);   ?
        //
    }

    public spreadsheet (){  // constructor principal
        List<String> keys = new ArrayList<>(rowsNumber * columnsNumber);
        // Inicializacion de keys y cells. Va de fila en fila.
        for(int i = 0; i < rowsNumber; i++){
            for(COLUMN c : COLUMN.values()){
                keys.add(c.toString() + (i + 1));
            }
        }

        MainGraph = new SpreadsheetGraph(keys);
    }

    // operacion
    public void setCell (String location, int content){
        setCell(location, String.valueOf(content));
    }
    public void setCell (String location, String content) {
        // Asigna nuevo contenido a la celda especificada. Se eliminan los enlaces con las celdas que esta referencia
        // (si tuviera) y se establece el nuevo contenido. Si el content es operacion o referencia, entonces
        // se establecen nuevos enlaces referenciando a las celdas requeridas. Finalmente, revisa si existen
        // celdas que referencien a la celda especificada. Si es que si, entonces se actualizan sus contenidos acorde
        // al cambio en esta.
        // Aclaracion: que una celda A referencie a otra B significa que B posee una arista dirigida hacia A. Esto
        // es asi porque en caso se modifique el contenido de B, inmediatamente se puede acceder a A (que usa su contenido)
        // para actualizarlo.
        MainGraph.deleteOfLinksOf(location);

        if(content.startsWith("+")){
            String referencedCell = content.substring(1);
            MainGraph.setCellLink(referencedCell, location);    // enlaza celda referenciada con la actual
            content = MainGraph.getCellContent(referencedCell);

        } else if (){

        }

        MainGraph.setCellContent(location, content);

        // Actualiza los contenidos de las celdas que referencian a esta
        for (String cell : MainGraph.getAllCellLinks(location)){
            setCell();
        }
    }

    public void getCell () {

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
