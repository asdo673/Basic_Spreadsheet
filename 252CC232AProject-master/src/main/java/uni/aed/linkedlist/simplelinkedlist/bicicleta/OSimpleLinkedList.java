package uni.aed.linkedlist.simplelinkedlist.bicicleta;

public class OSimpleLinkedList {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    private static final int EXITO=1;
    private ONodo head;          //cabecera de lista
    private int lenght=0;
    
    public void addFirst(Object data){
        ONodo newNodo=new ONodo(data);
        newNodo.setNext(head);
        head=newNodo;
        lenght++;
    }
    public void addLast(Object data){
        ONodo newNodo=new ONodo(data);
        if(head==null){
            head=newNodo;
            lenght++;
            return;
        }
        ONodo current=head;
        while(current.getNext()!=null)
            current=current.getNext();
        current.setNext(newNodo);
        lenght++;
    }
    
    public int addToPosition(int position,Object data){
        ONodo newNodo=new ONodo(data);
        ONodo current;
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
    
    public int search(Object data){
        ONodo current=head;
        boolean isFound=false;
        int index=0;
        while(current!=null && isFound==false){
            if(((Comparable)current.getData()).compareTo(data)==0)
                isFound=true;   //se encontro el valor
            else{
                current=current.getNext();//se desplaza al siguiente nodo
                index++;
            }
        }
        if(isFound)
            return index;
        else
            return NOT_FOUND;
    }
    
    //Solucion Cap16.Ej7
    public String searchAll(Object data){
        StringBuilder str=new StringBuilder();
        ONodo current=head;
        int index=0;
        while(current!=null){
            if( ((Comparable)current.getData()).compareTo(data)==0){
                if(!str.isEmpty())
                    str.append(",");
                str.append(index);//descargamos las posiciones que corresponden al mismo propietario
            }
            current=current.getNext();
            index++;
        }
        if(str.isEmpty())
            str.append(NOT_FOUND);
        return str.toString();
    }
    
    public void remove(Object data){
        if(head==null)
            return;
        //si el elemento a eliminar es el primer nodo
        if(((Comparable)head.getData()).compareTo(data)==0){
           head=head.getNext();
           lenght--;
           return;
        }
        //si el elemento a eliminar no es el primer nodo
        ONodo current=head;
        while(current.getNext()!=null
                && ((Comparable)current.getNext().getData()).compareTo(data)!=0)
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
        ONodo actual;
        ONodo ultimo=null;
        do{
            swapped=false;
            actual=head;
            while(actual.getNext()!=ultimo){
                if(((Comparable)actual.getData()).compareTo(actual.getNext().getData())>0){
                    //intercambiar
                    Object temp=(Object)actual.getData();
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
        ONodo current=head;
        while(current!=null){
            if(!str.isEmpty())
                str.append("->");
            str.append(current.getData().toString());
            current=current.getNext();
        }
        return str.toString();
    }
}
