package uni.aed.tda.AnalizadorSintactico;

public class SintaxisException extends AnalizadorException {
    public SintaxisException(String mensaje) {
        super("Error Sintactico: " + mensaje);
    }
    
}
