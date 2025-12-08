package uni.aed.tda.AnalizadorSintactico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uni.aed.tda.circlelinkedlistTDA.CircleLinkedListTDA;
import uni.aed.tda.listTDA.IteratorTDA;

public class AnalizadorSintactico {
    private String consulta;
    private String analisis_result;
    private CircleLinkedListTDA<Token> tokens;    

    public AnalizadorSintactico() {        
        this.analisis_result = "";
    }

    public void setAnalisis_result(String result) {
        this.analisis_result = result;
    }

    public String getAnalisis_result() {
        return analisis_result;
    }
    
    
    public void registrarConsulta(String consulta) throws LexicoException{
        if (consulta == null || consulta.trim().isEmpty())
            throw new LexicoException("La consulta esta vacia o no se ha ingresado.");
        this.consulta = consulta.trim();        
        tokens = new CircleLinkedListTDA<>(); 
    }

    public void tokenizar() throws LexicoException{
        if (consulta == null || consulta.isEmpty())
            throw new LexicoException("No hay consulta registrada.");

        // Expresiones regulares para los distintos tipos de tokens
        String regex = "(?i)\\b(SELECT|FROM|WHERE|AND|OR)\\b" + // Palabras clave
                "|[a-zA-Z_][a-zA-Z0-9_]*" +                   // Identificadores
                "|>=|<=|<>|=|>|<" +                           // Operadores
                "|'[^']*'" +                                  // Literales de texto
                "|[0-9]+" +                                   // Números
                "|[,;]" +                                     // Delimitadores
                "|\\s+";                                      // Espacios

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(consulta);
        boolean encontrado = false;

        while (matcher.find()) {
            String lexema = matcher.group().trim();
            if (lexema.isEmpty()) continue;
            encontrado = true;
            String tipo;
            if (lexema.matches("(?i)SELECT|FROM|WHERE|AND|OR")) tipo = "KEYWORD";
            else if (lexema.matches("[a-zA-Z_][a-zA-Z0-9_]*")) tipo = "IDENTIFIER";
            else if (lexema.matches(">=|<=|<>|=|>|<")) tipo = "OPERATOR";
            else if (lexema.matches("'[^']*'|[0-9]+")) tipo = "VALUE";
            else if (lexema.matches("[,;]")) tipo = "DELIMITER";
            else continue;

            tokens.add(new Token(tipo, lexema.toUpperCase()));
        }

        if (!encontrado)
            throw new LexicoException("No se encontraron tokens validos en la consulta.");
    }

    public void analizarSintaxis() throws SintaxisException {
        if (tokens.size() == 0)
            throw new SintaxisException("No hay tokens generados. Ejecute la tokenizacion primero.");

        IteratorTDA<Token> it = tokens.iterador();
        int pos = 1;
        Token t;

        // SELECT obligatorio
        if (!it.hasNext() || !(t = it.next()).valor.equals("SELECT"))
            throw new SintaxisException("La consulta debe iniciar con SELECT (token " + pos + ")");

        // Lista de campos
        boolean esperandoCampo = true;
        boolean afterFrom = false;

        while (it.hasNext()) {
            pos++;
            t = it.next();

            if (t.valor.equals("FROM")) {
                afterFrom = true;
                break;
            }

            if (esperandoCampo && t.tipo.equals("IDENTIFIER")) {
                esperandoCampo = false;
            } else if (!esperandoCampo && t.valor.equals(",")) {
                esperandoCampo = true;
            } else if (!esperandoCampo && t.tipo.equals("IDENTIFIER")) {
                throw new SintaxisException("Falta coma antes del campo (token " + pos + ")");
            }
        }

        if (!afterFrom)
            throw new SintaxisException("Falta la clausula FROM.");

        // Tabla
        if (!it.hasNext())
            throw new SintaxisException("Falta el nombre de la tabla despues de FROM.");
        pos++;
        t = it.next();
        if (!t.tipo.equals("IDENTIFIER"))
            throw new SintaxisException("Se esperaba identificador de tabla (token " + pos + ")");

        // WHERE opcional
        boolean whereFound = false;
        if (it.hasNext()) {
            pos++;
            t = it.next();

            if (t.valor.equals("WHERE")) {
                whereFound = true;

                if (!it.hasNext())
                    throw new SintaxisException("Falta campo despues de WHERE (token " + pos + ")");
                pos++;
                Token campo = it.next();
                if (!campo.tipo.equals("IDENTIFIER"))
                    throw new SintaxisException("Se esperaba identificador en condicion (token " + pos + ")");

                if (!it.hasNext())
                    throw new SintaxisException("Falta operador en condicion.");
                pos++;
                Token op = it.next();
                if (!op.tipo.equals("OPERATOR"))
                    throw new SintaxisException("Se esperaba operador en condicion (token " + pos + ")");

                if (!it.hasNext())
                    throw new SintaxisException("Falta valor en condicion.");
                pos++;
                Token val = it.next();
                if (!val.tipo.equals("VALUE"))
                    throw new SintaxisException("Se esperaba valor en condicion (token " + pos + ")");
            } else if (!t.valor.equals(";")) {
                throw new SintaxisException("Token inesperado '" + t.valor + "' en posicion " + pos);
            }
        }

        // Debe terminar con ';'
        boolean terminaEnPuntoComa = false;
        while (it.hasNext()) {
            pos++;
            t = it.next();
            if (t.valor.equals(";")) {
                terminaEnPuntoComa = true;
                break;
            }
        }

        if (!terminaEnPuntoComa)
            throw new SintaxisException("La consulta debe terminar con ';'");        
        
        
        setAnalisis_result("Consulta SQL valida.");        
    }

    public String visualizarTokens() throws LexicoException {
        if (tokens.size() == 0)
            throw new LexicoException("No hay tokens para visualizar.");        
        return "TOKENS: "+tokens.toString()+"\n" + getAnalisis_result();
    }
}
