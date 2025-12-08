package uni.aed.tda.stackTDA;

public interface StackTDA<E> {
    public void push(E data);
    public E pop() throws StackEmptyExceptionTDA;
    public E peek() throws StackEmptyExceptionTDA;
    public void clear();
    public int size();
    public boolean isEmpty();
}
