package uni.aed.tda.queueTDA;

public class QueueEmptyExceptionTDA extends RuntimeException {

    public QueueEmptyExceptionTDA() {
        this("Cola vacia");
    }

    public QueueEmptyExceptionTDA(String message) {
        super(message);
    }
    
    
}
