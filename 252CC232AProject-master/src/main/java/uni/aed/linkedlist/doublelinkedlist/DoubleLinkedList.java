package uni.aed.linkedlist.doublelinkedlist;

public class DoubleLinkedList {
    private static final int NOT_FOUND=-1;
    private DNodo head;  //apuntador a la cabecera de la lista
    private DNodo ultimo;//apuntador a la cola de la lista
    private int lenght=0;
    
    //metodo para añadir elementos en la parte frontal de la lista
    public void addFirst(int data){
        DNodo newNodo=new DNodo(data);
        if(head==null){//lista vacia
            head=newNodo;
            ultimo=newNodo;
        }else{//la lista tiene elementos
            newNodo.setNext(head);
            head.setPrev(newNodo);
            head=newNodo;
        }
        lenght++;   
    }
    public void addLast(int data){
        DNodo newNodo=new DNodo(data);
        if(ultimo==null){//equivalente a si head==null
            head=newNodo;
            ultimo=newNodo;
        }else{
            newNodo.setPrev(ultimo);
            ultimo.setNext(newNodo);
            ultimo=newNodo;
        }
        lenght++; 
    }
    public void remove(int data){
        DNodo current=head;
        //buscando el elemento a eliminar
        while(current!=null && current.getData()!=data)
            current=current.getNext();
        //verificamos si salio del bucle porque encontro la data
        if(current!=null){
            //verificamos si el nodo a eliminar esta en la parte frontal
            if(current==head){
                head=head.getNext();
                if(head!=null)
                    head.setPrev(null);
                else
                    ultimo=null;
            }else if(current==ultimo){//el nodo a eliminar esta en la cola de la lista
                ultimo=current.getPrev();
            }else{//el nodo a eliminar esta en el cuerpo de la lista
                current.getPrev().setNext(current.getNext());
                current.getNext().setPrev(current.getPrev());
            }
            lenght--;
        }
        
    }
    public int search(int data){
        DNodo current=head;
        int index=0;
        while(current!=null){
            if(current.getData()==data)
                return index;
            current=current.getNext();//se desplaza al siguiente nodo
            index++;
        }
        return NOT_FOUND;
    }
    /*
    En listas doblemente enlazadas se puede aprovechar ambos punteros(head y ultimo)
    para buscar desde los dos extremos al mismo tiempo
    Esto reduce el numero de comparaciones casi a la mitad en el peor caso
    */
    public int searchMejorado(int data){
        DNodo izq=head;
        DNodo der=ultimo;
        int leftIndex=0;
        int rightIndex=size()-1;
        while(izq!=null && der!=null && leftIndex<=rightIndex){
            if(izq.getData()==data)
                return leftIndex;
            if(der.getData()==data)
                return rightIndex;
            
            izq=izq.getNext();
            der=der.getPrev();
            leftIndex++;
            rightIndex++;
        }
        return NOT_FOUND;
    }
    
     //Metodo de Ordenamiento Burbuja
    public void bubbleSort(){
        if(head==null) return;
        boolean swapped;
        DNodo actual;
        DNodo ultimo=null;
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
        ultimo=null;
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
        DNodo current=head;
        while(current!=null){
            if(!str.isEmpty())
                str.append("<->");
            str.append(current.getData());
            current=current.getNext();
        }
        return str.toString();
    }
}
