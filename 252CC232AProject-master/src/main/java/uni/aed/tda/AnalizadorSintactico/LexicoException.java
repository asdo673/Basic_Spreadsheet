package uni.aed.tda.AnalizadorSintactico;

public class LexicoException extends AnalizadorException {
    public LexicoException(String mensaje) {
        super("Error Lexico: " + mensaje);
    }    
}
