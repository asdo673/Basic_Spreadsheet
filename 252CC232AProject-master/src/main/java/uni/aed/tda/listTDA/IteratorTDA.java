package uni.aed.tda.listTDA;

public interface IteratorTDA<E> {
    public boolean hasNext();   //tiene siguiente elemento
    public E next() throws NoSuchElementException;//recuperar el valor del siguiente elemento    
}
