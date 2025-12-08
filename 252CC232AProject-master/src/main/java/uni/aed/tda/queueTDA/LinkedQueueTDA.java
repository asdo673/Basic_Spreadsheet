package uni.aed.tda.queueTDA;

import uni.aed.tda.linkedlistTDA.Nodo;
import uni.aed.tda.stackTDA.StackEmptyExceptionTDA;

public class LinkedQueueTDA<E> implements QueueTDA<E> {
    private Nodo<E> front;
    private Nodo<E> back;
    private int count;

    public LinkedQueueTDA() {
        clear();
    }

    @Override
    public void enqueue(E data) {
        Nodo<E> newNodo=new Nodo<>(data);
        if(isEmpty())
           front=back=newNodo;
        back.setNext(newNodo);//insertamos el nuevo nodo en la parte final de la cola
        back=newNodo;
        count++;
    }
    @Override
    public E dequeue() throws QueueEmptyExceptionTDA {
        if(isEmpty())
          throw new StackEmptyExceptionTDA();
        E item=front.getData();
        front=front.getNext();
        count--;
        return item;
    }

    @Override
    public E peek() throws QueueEmptyExceptionTDA {
        if(isEmpty())
          throw new StackEmptyExceptionTDA();
        return front.getData();
    }

    @Override
    public void clear() {
        front=back=null;
        count=0;        
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }
    
     @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        Nodo<E> current=front;        
        while(current!=null){
            if(!str.isEmpty())
                str.append(",");
            str.append(current.getData().toString());
            current=current.getNext();
        }        
        return str.toString();
    }

    @Override
    public String toString(String patron) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
