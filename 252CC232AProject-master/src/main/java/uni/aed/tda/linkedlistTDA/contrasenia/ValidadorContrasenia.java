package uni.aed.tda.linkedlistTDA.contrasenia;
import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import uni.aed.tda.listTDA.ListTDA;

public class ValidadorContrasenia {
    // Simbolos permitidos
    private static final String SIMBOLOS = "!@#$%^&*()_-+=";
    private static final int LONG_MAX =12;

    // Mensajes de error
    private static final String MSG_LONGITUD = "Muy corta (menos de 12 caracteres)";
    private static final String MSG_ESPACIO = "Contiene espacio en blanco";
    private static final String MSG_REPETIDO = "Caracter repetido consecutivamente";
    private static final String MSG_NO_PERMITIDO = "Caracter no permitido";
    private static final String MSG_FALTANTE = "Faltan tipos de caracteres requeridos";

    // Atributos de instancia
    private ListTDA<Character> lista;
    private StringBuilder resultado;
    private int codigoResultado; // 0 = valida, >0 = error
   
    public void registrarContrasenia(String contrasenia) {
        lista = new LinkedListTDA<>();
        for (char c : contrasenia.toCharArray())
            lista.add(c);        
        resultado = new StringBuilder();
        codigoResultado = -1; // aun no validada
    }
  
    public void validarContrasenia() {
        if (lista == null) {
            resultado = new StringBuilder("No se ha registrado una contrasenia.");
            codigoResultado = -1;
            return;
        }
        int len = lista.size();
        // Regla A: longitud minima
        if (len < LONG_MAX) {
            resultado.append("Posicion 1 -> ").append(MSG_LONGITUD);
            codigoResultado = 1;
            return;
        }

        boolean tieneMayus = false;
        boolean tieneMinus = false;
        boolean tieneDigito = false;
        boolean tieneSimbolo = false;

        Character anterior = null;
        for (int pos = 0; pos < len; pos++) {
            char c = lista.get(pos);

            // Regla B: sin espacios
            if (Character.isWhitespace(c)) {
                resultado.append("Posicion ").append(pos + 1).append(" -> ").append(MSG_ESPACIO);
                codigoResultado = pos + 1;
                return;
            }

            // Regla C: sin caracteres repetidos consecutivos
            if (anterior != null && c == anterior) {
                resultado.append("Posicion ").append(pos + 1)
                        .append(" -> ").append(MSG_REPETIDO)
                        .append(" ('").append(c).append("')");
                codigoResultado = pos + 1;
                return;
            }

            // Verificar tipo de caracter
            if (Character.isUpperCase(c))
                tieneMayus = true;
            else if (Character.isLowerCase(c))
                tieneMinus = true;
            else if (Character.isDigit(c))
                tieneDigito = true;
            else if (SIMBOLOS.indexOf(c) != -1)
                tieneSimbolo = true;
            else {
                resultado.append("Posicion ").append(pos + 1)
                        .append(" -> ").append(MSG_NO_PERMITIDO)
                        .append(" ('").append(c).append("')");
                codigoResultado = pos + 1;
                return;
            }
            anterior = c;
        }

        // Regla D: debe tener todos los tipos requeridos
        if (!tieneMayus || !tieneMinus || !tieneDigito || !tieneSimbolo) {
            resultado.append("Posicion 1 -> ").append(MSG_FALTANTE).append(": ");
            if (!tieneMayus) resultado.append("[Mayuscula] ");
            if (!tieneMinus) resultado.append("[Minuscula] ");
            if (!tieneDigito) resultado.append("[Digito] ");
            if (!tieneSimbolo) resultado.append("[Simbolo]");
            codigoResultado = 1;
            return;
        }

        resultado.append("0 -> Contrasenia valida");
        codigoResultado = 0;
    }
  
    @Override
    public String toString() {
        return lista.toString();
    }   
    
    public String getValidacion() {
        return resultado == null ? lista.toString() : resultado.toString();
    }  
    
    // metodo para obtener el codigo numerico, opcional
    public int getCodigoResultado() {
        return codigoResultado;
    }
}
