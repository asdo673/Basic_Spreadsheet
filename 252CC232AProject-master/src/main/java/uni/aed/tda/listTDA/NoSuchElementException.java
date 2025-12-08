package uni.aed.tda.listTDA;


public class NoSuchElementException extends RuntimeException{

    public NoSuchElementException() {
        this("Data solicitada no existe en la estructura de datos");
    }

    public NoSuchElementException(String message) {
        super(message);
    }
    
    
}
