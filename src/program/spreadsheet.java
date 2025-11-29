package program;

import graphTDA.Edge;
import graphTDA.Graph;
import graphTDA.Vertex;

import java.util.ArrayList;
import java.util.List;

// Clase para definir el shepadsheet.
// Tal vez se podria crear una clase para manejar el grafo especial para el spreadsheet
// y esta clase que utilize a esa otra de modo que aqui solo se maneje las operaciones.
// Spreadsheet creo que igual y deberia manejar mas que valores, es decir, eerencias a celdas y asi
// Aunque celda creo que si o si deberia ser solo valore (Numero, Texto);

public class spreadsheet {
    private static final int rowsNumber = 20;   // Numero de filas
    private static final int columnsNumber = Column.values().length;    // Numero de columnas
    private enum Column {   // Columnas por letra
        A, B, C, D, E, F, G, H
    }   // tal vez con protected podria hacerse que se pueda usar defrente ne todas las clases?

    private final Graph<Cell> spreadSheetGraph;     // Grafo que conforma la estructura del spreadsheet

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

    private class Cell implements Comparable<Cell> {   // Inner class para representar celdas del spreadsheet
        private String content;
        public Column column;
        public int row;

        public Cell (Column column, int row) {
            this (column, row, "");
        }
        public Cell(Column column, int row, String content){
            this.column = column;
            this.row = row;
            this.content = content;
        }
        @Override
        public int compareTo(Cell o) {
            return 0;
        }
        @Override
        public String toString() {
            return "[" + column + row + ": " + content + "]";
        }
    }

    public spreadsheet (){  // constructor principal
        List<Vertex<Cell>> vertices = new ArrayList<>(rowsNumber * columnsNumber);
        List<Edge<Cell>> edges = new ArrayList<>();
       // List<Cell> vertices = new ArrayList<>(rowsNumber * columnsNumber);
       // List<Cell> edges = new ArrayList<>();

        for (Column c : Column.values())
            for (int i = 0; i < rowsNumber; i++)
                vertices.add(new Vertex<>(
                        new Cell(c, i + 1)
                ));

        spreadSheetGraph = new Graph<>(vertices, edges);
    }

    // operacion
    public void setCell () {
        // Cada que haces set en alguna celda, verifica si esta enlazada conotra celda, es decir,
        // verifica si hay otra celda que referencie a esta ya sea para operaciones o simple re__
        // ferencia. Actualizar el valor en cada una de estas celdas referenciantes adecuadamente.
        // Si la celda en la que se realiza set era una operacion o referencia, entonces se tienen
        // los casos:
        // Tiene las posiciones de las celdas, asi que las usa para obtener sus valores

        // Asigna nuevo contenido a la celda especificada. Se eliminan los enlaces con las celdas que referencia
        // (si tuviera) y se establece el nuevo contenido. Si fuese operacion o referencia, entonces
        // se establecen nuevos enlaces referenciando a las celdas especificadas. Finalmente, revisa si existen
        // celdas que referencien a esta. Si es que si, entonces se actualizan sus contenidos acorde.
        // Aclaracion: si una celda referncia a otra, entonces
    }

    public void getCell () {

    }
}
