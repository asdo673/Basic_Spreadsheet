package uni.aed.tda.arraylistTDA;

import uni.aed.tda.listTDA.IteratorTDA;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.listTDA.NoSuchElementException;

public class Iterador<E> implements IteratorTDA<E> {
    private final ListTDA<E> arraylist;
    private int currentIndex=0;

    public Iterador(ListTDA<E> arraylist) {
        this.arraylist = arraylist;
    }

    @Override
    public boolean hasNext() {
        return currentIndex<arraylist.size();
    }

    @Override
    public E next() throws NoSuchElementException {
        if(!hasNext())
            throw new NoSuchElementException("No hay mas elementos en la coleccion");
        return arraylist.get(currentIndex++);
    }
    
}
