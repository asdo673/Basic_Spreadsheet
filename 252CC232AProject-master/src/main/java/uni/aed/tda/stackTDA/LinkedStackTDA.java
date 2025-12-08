package uni.aed.tda.stackTDA;

import uni.aed.tda.linkedlistTDA.Nodo;

public class LinkedStackTDA<E> implements StackTDA<E> {
    private Nodo<E> topPila;
    private int count;

    public LinkedStackTDA() {
        clear();
    }
    
    @Override
    public void push(E data) {
        Nodo<E> newTopPila=new Nodo<>(data);
        newTopPila.setNext(topPila);
        topPila=newTopPila;
        count++;
    }

    @Override
    public E pop() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        E temp=topPila.getData();//obtenemos elemento del top
        topPila=topPila.getNext();//el top se actualiza con el sig nodo
        count--;
        return temp;        
    }

    @Override
    public E peek() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        return topPila.getData();
    }

    @Override
    public void clear() {
        topPila=null;
        count=0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return (count==0);  
    }
    
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        Nodo<E> current=topPila;        
        str.append("[TOP]");
        while(current!=null){
            if(!str.isEmpty())
                str.append(",");
            str.append(current.getData().toString());
            current=current.getNext();
        }        
        return str.toString();
    }
}
