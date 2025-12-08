package uni.aed.linkedlist.simplelinkedlist;

public class SimpleLinkedList {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    private static final int EXITO=1;
    private Nodo head;          //cabecera de lista
    private int lenght=0;
    
    public void addFirst(int data){
        Nodo newNodo=new Nodo(data);
        newNodo.setNext(head);
        head=newNodo;
        lenght++;
    }
    public int addToPosition(int position,int data){
        Nodo newNodo=new Nodo(data);
        Nodo current;
        int index=0;
        if(position<0 || position >(lenght-1))
            return OUT_OF_LIMIT;
        if(position==0)
            addFirst(newNodo.getData());
        else{
            current=head;
            while(index<position-1){
                current=current.getNext();
                index++;
            }
            newNodo.setNext(current.getNext());
            current.setNext(newNodo);
            lenght++;
        }
        return EXITO;
    }
    public void addLast(int data){
        Nodo newNodo=new Nodo(data);
        if(head==null){
            head=newNodo;
            lenght++;
            return;
        }
        Nodo current=head;
        while(current.getNext()!=null)
            current=current.getNext();
        current.setNext(newNodo);
        lenght++;
    }
    public int search(int key){
        Nodo current=head;
        int index=0;
        while(current!=null){
            if(current.getData()==key)
                return index;   //se encontro el valor
            current=current.getNext();//se desplaza al siguiente nodo
            index++;
        }
        return NOT_FOUND;
    }
    public void remove(int data){
        if(head==null)
            return;
        //si el elemento a eliminar es el primer nodo
        if(head.getData()==data){
           head=head.getNext();
           lenght--;
           return;
        }
        //si el elemento a eliminar no es el primer nodo
        Nodo current=head;
        while(current.getNext()!=null
                && current.getNext().getData()!=data)
            current=current.getNext();
        
        if(current.getNext()!=null){
            current.setNext(current.getNext().getNext());
            lenght--;
        }            
    }
    //Metodo de Ordenamiento Burbuja
    public void bubbleSort(){
        if(head==null) return;
        boolean swapped;
        Nodo actual;
        Nodo ultimo=null;
        do{
            swapped=false;
            actual=head;
            while(actual.getNext()!=ultimo){
                if(actual.getData()>actual.getNext().getData()){
                    //intercambiar
                    int temp=actual.getData();
                    actual.setData(actual.getNext().getData());
                    actual.getNext().setData(temp);
                    swapped=true;
                }
                actual=actual.getNext();                
            }
            ultimo=actual;//despues de cada pasada , el mayor valor queda al final de la lista
        }while(swapped);
    }
    
    
    public void clear(){
        head=null;
        lenght=0;        
    }
    
    public boolean isEmpty(){
        return (lenght==0);
    }
    
    public int size(){
        return lenght;
    }

    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        Nodo current=head;
        while(current!=null){
            if(!str.isEmpty())
                str.append("->");
            str.append(current.getData());
            current=current.getNext();
        }
        return str.toString();
    }
    
    
    
}
