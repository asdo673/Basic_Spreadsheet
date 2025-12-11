package program;

import graph.GraphPaths;
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
    private final GraphPaths<String, String> Circuits; 
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
        Circuits = new GraphPaths<>(MainGraph);
    }
    
    public void setCell(String location, String content) {
        // 1. Validamos celda
        if (!location.matches("[A-H](?:[0-9]|1[0-9]|20)")) {
            return; // ubicación invalida, no hacemos nada
        }

        // 2. Siempre, al inicio, borro los links antiguos de esa celda
        MainGraph.deleteLinksOf(location);

        // 3. Caso 1: referencia simple 
        if (content.startsWith("+")) {
            handleReference(location, content);
            return;
        }

        // 4. Caso 2: operacion
        if (isOperation(content)) {
            handleOperation(location, content);
            return;
        }

        // 5. Caso 3: valor normal puede ser palabra o numero
        refreshCircuitsFor(location);
        MainGraph.setCellContent(location, content);
    }

    private void handleReference(String location, String content) {
        if (isReference(content)) { // se valida si es una referencia valida
            String ref = content.substring(1).trim(); // celda de referencia sin el '+'
            MainGraph.setCellLink(location, ref);     // enlace de referencia

            refreshCircuitsFor(location);
            MainGraph.setCellContent(location, content); // guarda el contenido tal cual "+B2"
        } else {
            MainGraph.setCellContent(location, content); // referencia invalida
        }
    }

    private void handleOperation(String location, String content) {
        if (validarOperacion(content)) {
            // Enlaces a celdas individuales
            crearLinksDesdeExpresion(location, content);

            // Enlaces a bloques
            crearLinksDesdeBloques(location, content);

            refreshCircuitsFor(location);
            
            MainGraph.setCellContent(location, content);
        } else {
            MainGraph.setCellContent(location, content); // operación invalida
        }
    }

    private void refreshCircuitsFor(String location) {
        Circuits.refreshPathsAndFindCircuits();              // limpia y recalcula
        List<String> circuit = MainGraph.getPath(location, location); // ciclo desde la propia celda
        if (circuit != null && !Circuits.contains(circuit)) {
            Circuits.add(circuit);
        }
    }

    
    private boolean isReference(String s) {
        return s.matches("\\+([A-H][0-9]|[A-H]20|[A-H][1][0-9])");// Comprueba si contiene el patron \\+(signo literal),[A-H]Letra entre A-H,[1-9] numero entre 1-9,[0-9]? numero opcional entre 0 y 9 puesto que las filas son del 1 al 20
    } 
    
    private boolean isOperation(String s) {
        return s.contains("+") || s.contains("-") ||
                s.contains("*") || s.contains("/") ||
                s.contains("@");
    } //Busca en la expresion si contiene alguno de estos elementos
    
    private boolean validarOperacion(String expr) {//El metodo simplemente espera que se lanze un error si la operacion no es valida
        try {
            StreamTokenizer st = new StreamTokenizer(
                    new BufferedReader(new StringReader(expr))
        );
            st.resetSyntax(); //borra toda las reglas por defecto del tokenizador
            st.parseNumbers();//Esto reconoce a los numeros no como strings si no como doubles
            st.wordChars('A', 'Z');//Permite que palabras contengan letras
            st.wordChars('a','z');
            st.wordChars('0','9');//Permite que palabras contengan numeros
            st.whitespaceChars(' ', ' ');//Elimina todo los espacios en blanco;

//Convierte a todo estos simbolos en elementos unicos//
            st.ordinaryChar('+');
            st.ordinaryChar('-');
            st.ordinaryChar('*');
            st.ordinaryChar('/');
            st.ordinaryChar('(');
            st.ordinaryChar(')');
            st.ordinaryChar('@');
            st.ordinaryChar('.');
            

            validarExpresion(st); 
            return true;
            
        } catch (IOException e) {
            return false;
        }
    }
    
    private void validarExpresion(StreamTokenizer st) throws IOException {
        validarTermino(st);
        
        while (true) {
            st.nextToken();
            if (st.ttype == '+' || st.ttype == '-') {//Manjea a las operacions + o -
                validarTermino(st);//Lanzando denuevo al ValidarTermino
            } else {
                st.pushBack();//Si no es asi regresa, y retrona
                return;
            }
        }
    }

    private void validarTermino(StreamTokenizer st) throws IOException {
        validarFactor(st);
        
        while (true) {
            st.nextToken();
            if (st.ttype == '*' || st.ttype == '/') { //lee el siguiente y maneja las operacions * o /
                validarFactor(st);//Lanzando de nuevo el validarFactor
            } else {
                st.pushBack(); //Si no, retrocede el token y retorna;
                return;
            }
        }
    }

    private void validarFactor(StreamTokenizer st) throws IOException {
        st.nextToken(); //Pasa al primer termino tokenizado

        while (st.ttype == '+' || st.ttype == '-') {//Consume los + o - que esten por delante
            st.nextToken();
        }

        if (st.ttype == StreamTokenizer.TT_NUMBER) return; //verifica si es numero,si lo es vuelve
 
        if (st.ttype == StreamTokenizer.TT_WORD) { //Comprueba si es una celda y la valida
            if (!st.sval.matches("[A-H][0-9]|[A-H]20|[A-H][1][0-9]"))
                throw new IOException("Celda invalida");
            return;
        }

        if (st.ttype == '(') { //Aca si es una operacion compleja,vuelve a llamar a validarexpresion
            validarExpresion(st);
            st.nextToken();
            if (st.ttype != ')')//Si la expresion es valida comprueba que este debidamente cerrada
                throw new IOException("Falta )");
            return;
        }

        if (st.ttype == '@') { //Valida si la operacion '@' sea valida
            st.nextToken(); //Especificar que sea sum,avg,max,min #SOLUTION
            if (st.ttype == StreamTokenizer.TT_WORD)
                if(!(st.sval.equals("sum") || st.sval.equals("avg") || st.sval.equals("max") || st.sval.equals("min")) )
                    throw new IOException("Operacion invalida");

            st.nextToken();
            if (st.ttype != '(') //Lo que sigue sea parentesis
                throw new IOException("Falta (");

            st.nextToken();
            if (!st.sval.matches("[A-H][0-9]|[A-H]20|[A-H][1][0-9]"))//Lo que sigue sea un indice de celda valido
                throw new IOException("Celda invalida");

            st.nextToken();
            if (st.ttype != '.') throw new IOException();
            st.nextToken();
            if (st.ttype != '.') throw new IOException(); //Lo que sigue sea ".."

            st.nextToken();
            if (!st.sval.matches("[A-H][0-9]|[A-H]20|[A-H][1][0-9]"))
                throw new IOException("Rango invalido"); //Denuevo si es un indice de celda valido

            st.nextToken();
            if (st.ttype != ')')
                throw new IOException("Falta )");//Si al terminar se cierra con ) debidamente

            return;
        }

        throw new IOException("Factor invalido"); //Si es una palabra o otro termino incorrecto
    }

    private void crearLinksDesdeExpresion(String location, String expr) {
        Matcher m = Pattern.compile("[A-H][0-9]|[A-H]20|[A-H][1][0-9]").matcher(expr);//Crea un objeto matcher para utilizarlo
        //Una mejora puede ser que evite a las celdas dentro de una operacion #upgrade
        //sobre la expresion
        while (m.find()) { //usa find para empezar a encontrar las condiciones
            String ref = m.group(); //y group para obtener el valor de la coincidencia
            MainGraph.setCellLink(location, ref);//setea el enlace
        }
    }
    
    private void crearLinksDesdeBloques(String location, String expr) {
        Pattern pattern = Pattern.compile(
            "@(sum|avg|min|max)\\(([A-H][0-9]|[A-H]20|[A-H][1][0-9])\\.\\.([A-H][0-9]|[A-H]20|[A-H][1][0-9])\\)"
        );
        Matcher matcher = pattern.matcher(expr); //Crea un objeto matcher para tokenizar la expresion
        //Con el regex,lo agrupa en 3 elementos, la operacion,celda1 y celda2;

        while (matcher.find()) {

            String from = matcher.group(2);//Celda 1;
            String to   = matcher.group(3);//Celda 2;

            int colFrom = COLUMNS.indexOf(from.substring(0, 1));//Copiado y modificado de akim
            int rowFrom = Integer.parseInt(from.substring(1));

            int colTo = COLUMNS.indexOf(to.substring(0, 1));
            int rowTo = Integer.parseInt(to.substring(1));

            int minCol = Math.min(colFrom, colTo);
            int maxCol = Math.max(colFrom, colTo);
            int minRow = Math.min(rowFrom, rowTo);
            int maxRow = Math.max(rowFrom, rowTo);

            for (int c = minCol; c <= maxCol; c++) {
                for (int r = minRow; r <= maxRow; r++) {

                    String key = COLUMNS.get(c) + r;
                    MainGraph.setCellLink(location, key);//Setea todo los enlaces del bloque
                    //No se si crea un problema pero repite los vertices si existe una funcion
                    //tal como @sum(A1..B1) -> crea doble enlace con A1 y B1;

                }
            }
        }
    }

    
    
    public String getValue(String location) {
        return MainGraph.getCellContent(location); //Regresa el contenido de la celda;
    }    

    
    public String getCell(String location) {//Metodo para obtener valor de la celda;
        String content = Circuits.contains(location) ? "AUTOREF" : MainGraph.getCellContent(location);
        content = content.trim(); //Elimina espacos en blanco

        // CASO 1: referencia directa +A1 -> copia valor
        if (content.startsWith("+")) {
            String ref = content.substring(1).trim();
            return getCell(ref); //Simplemente una recursion para obtener el valor de referencia
        }

        // CASO 2: Expresion matematca
        if (isOperation(content)) {
            try {
                double result = interpreter(location);//Se llama al interpreter
                
                //Elimina los "." y devuelve el contenido como string
                if (result == (long) result) { //
                    return Long.toString((long) result);
                } else {
                    return Double.toString(result);
                }

            } catch (IOException e) {
                return "ERROR"; //Si se cambia el contenido de una celda que aparecia en la operacion
                //con un valor no permitido como una palabra devuelve Error
            }
        }

        // CASO 3: Aca es simplemente si el contenido es un numero o palabra;
        return content;
    }
    
    
    
    private double interpreter(String location) throws IOException {
        String expression = getValue(location);

        StreamTokenizer st = new StreamTokenizer(
                new BufferedReader(new StringReader(expression))
        );

        st.resetSyntax();
        st.parseNumbers();
        st.wordChars('A','Z');
        st.wordChars('a','z');
        st.whitespaceChars(' ',' ');
        st.whitespaceChars('\t','\t');

        st.ordinaryChar('+');
        st.ordinaryChar('-');
        st.ordinaryChar('*');
        st.ordinaryChar('/');
        st.ordinaryChar('(');
        st.ordinaryChar(')');
        st.ordinaryChar('@');
        st.ordinaryChar('.');

        return expression(st);
    }
    
    private double expression(StreamTokenizer st) throws IOException {
        double t = term(st); 

        while (true) {
            st.nextToken();

            switch (st.ttype) {
                case '+' -> t += term(st);
                case '-' -> t -= term(st);
                default -> {
                    st.pushBack();
                    return t;
                }
            }
        }
    }

    private double term(StreamTokenizer st) throws IOException {
        double f = factor(st);

        while (true) {
            st.nextToken();

            switch (st.ttype) {
                case '*' -> f *= factor(st);
                case '/' -> f /= factor(st);
                default -> {
                    st.pushBack();
                    return f;
                }
            }
        }
    }

    private double factor(StreamTokenizer st) throws IOException {
        double sign = 1.0;
        double val;

        st.nextToken();

        while (st.ttype == '+' || st.ttype == '-') {
            if (st.ttype == '-') sign *= -1;
            st.nextToken();
        }

        switch (st.ttype) {
            case StreamTokenizer.TT_NUMBER -> val = st.nval;
            case StreamTokenizer.TT_WORD -> {
                // puede ser referencia a una celda
                String tok = st.sval;
                if (tok.matches("[A-H][1-9][0-9]?")) { //Valida la celda
                    String raw = getCell(tok);  // evaluea la celda
                    if (raw.isBlank()) {
                        val = 0.0;
                    } else {
                        try {
                            val = Double.parseDouble(raw);
                        } catch (NumberFormatException e) {
                            throw new IOException("Celda " + tok + "no contiene un numero");
                        }
                    }
                } else {
                    throw new IOException("Token no valido " + tok);
                }
            }
            case '(' -> {
                // subexpresion entre parentesis
                val = expression(st);
                st.nextToken();
                if (st.ttype != ')') {
                    throw new IOException("Falta )");
                }
            }
            case '@' -> // operacion @sum|@avg|@min|@max
                val = parseFunction(st);
            default -> throw new IOException("Token inesperado");
        }

        return sign * val;
    }
    
    private double parseFunction(StreamTokenizer st) throws IOException {
        // st está después de '@'
        st.nextToken();
        if (st.ttype != StreamTokenizer.TT_WORD) {
            if(!(st.sval.equals("sum") || st.sval.equals("avg") || st.sval.equals("max") || st.sval.equals("min")) )
                throw new IOException("Nombre de operacion invlido");
        }

        String func = st.sval;//Lee sum|avg|max|min

        st.nextToken();
        if (st.ttype != '(') {
            throw new IOException("Falta ( en operacion @");
        }

        // lee el primer extremo del rango: A1
        st.nextToken();
        if (st.ttype != StreamTokenizer.TT_WORD ||
            !st.sval.matches("[A-H][1-9][0-9]?")) {
            throw new IOException("Rango invalido");
        }
        String from = st.sval;

        // lee los dos puntos ".."
        st.nextToken();
        if (st.ttype != '.') throw new IOException("Falta '.' en operacion");
        st.nextToken();
        if (st.ttype != '.') throw new IOException("Falta '.' en operacion");

        // leer segundo extremo: B2
        st.nextToken();
        if (st.ttype != StreamTokenizer.TT_WORD ||
            !st.sval.matches("[A-H][1-9][0-9]?")) {
            throw new IOException("Rango invalido");
        }
        String to = st.sval;

        // lee ')'
        st.nextToken();
        if (st.ttype != ')') {
            throw new IOException("Falta ) en operacion");
        }

        // obtener todos los valores numericos del bloque
        List<Double> values = getRangeValues(from, to);
        
        //Reglas si todos los bloques no tienen valor
        if (values.isEmpty()) {
            // sum|min|max|avg devuelven 0
            return 0.0;
        }

        double result;
        //switch sencillo para calcular la operacion
        //Debo volver a verificar las celdas en la operaciones #SOLU
        switch (func) {
            case "sum" -> {
                result = 0.0;
                for (double d : values) result += d;
                return result;
            }
            case "avg" -> {
                result = 0.0;
                for (double d : values) result += d;
                return result / values.size();
            }
            case "min" -> {
                result = values.getFirst();
                for (double d : values)
                    if (d < result) result = d;
                return result;
            }
            case "max" -> {
                result = values.getFirst(); //
                for (double d : values)
                    if (d > result) result = d;
                return result;
            }
            default -> throw new IOException("Funcion @ no implementada");
        }
    }


    private List<Double> getRangeValues(String from, String to) throws IOException {
        List<Double> vals = new ArrayList<>();

        int colFrom = COLUMNS.indexOf(from.substring(0, 1));
        int rowFrom = Integer.parseInt(from.substring(1));

        int colTo = COLUMNS.indexOf(to.substring(0, 1));
        int rowTo = Integer.parseInt(to.substring(1));

        int minCol = Math.min(colFrom, colTo);
        int maxCol = Math.max(colFrom, colTo);
        int minRow = Math.min(rowFrom, rowTo);
        int maxRow = Math.max(rowFrom, rowTo);

        for (int c = minCol; c <= maxCol; c++) {
            for (int r = minRow; r <= maxRow; r++) {
                String key = COLUMNS.get(c) + r;
                String raw = getCell(key);
                if (raw.isBlank()) continue;
                try {
                    double d = Double.parseDouble(raw);
                    vals.add(d);
                } catch (NumberFormatException e) {}
            }
        }

        return vals;
    }
    
} 
    
