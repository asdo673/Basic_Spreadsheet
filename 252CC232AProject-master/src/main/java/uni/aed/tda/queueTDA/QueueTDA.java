package uni.aed.tda.queueTDA;

public interface QueueTDA<E> {
    public void enqueue(E data);
    public E dequeue() throws QueueEmptyExceptionTDA;
    public E peek() throws QueueEmptyExceptionTDA;
    public void clear();
    public int size();
    public boolean isEmpty();
    public String toString(String patron);
    public Object[] toArray();
}
