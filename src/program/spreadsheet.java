package program;

import java.util.ArrayList;
import java.util.List;

public class spreadsheet {
    private static final int rowsNumber = 20;   // Numero de filas
    private enum Column {   // Columnas por letra
        A, B, C, D, E, F, G, H
    }
    private class celda {
        private String content;
        public Column column;
        public int row;

        public celda () {}
        public celda (Column column, int row, String content){
            this.column = column;
            this.row = row;
            this.content = content;
        }
    }

    public spreadsheet (){
        List<String> vertices = new ArrayList<>(rowsNumber * Column.values().length);
        List<String> edges = new ArrayList<>();
        for (int i = 0; i < rowsNumber * Column.values().length; i++)
            vertices.add("");


    }

    public void randommethod (){
        celda s = new celda();
        s.content = "s";
    }

}
