package uni.aed.tda.stackTDA;

import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import uni.aed.tda.listTDA.ListTDA;

public class ListStackTDA<E> implements StackTDA<E> {
    public static final int FRENTE=0;//representa el top de la lista
    private final ListTDA<E> lista;

    public ListStackTDA() {
        this.lista = new LinkedListTDA<>();
    }

    @Override
    public void push(E data) {
        lista.add(FRENTE, data);
    }

    @Override
    public E pop() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        return lista.delete(FRENTE);//elimina el elemento del top de la pila
    }

    @Override
    public E peek() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        return lista.get(FRENTE);
    }

    @Override
    public void clear() {
        lista.clear();
    }

    @Override
    public int size() {
        return lista.size();
    }

    @Override
    public boolean isEmpty() {
        return lista.isEmpty();
    }

    @Override
    public String toString() {
        return lista.toString();
    }
    
    
}
