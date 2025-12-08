package uni.aed.linkedlist.circlelinkedlist;
import uni.aed.linkedlist.simplelinkedlist.Nodo;
public class CircleLinkedList {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    private static final int EXITO=1;
    private Nodo head;          //cabecera de lista
    private int lenght=0;
    
    public void addLast(int data){
        Nodo newNodo=new Nodo(data);
        if(head==null){
            head=newNodo;
            head.setNext(head);//se apunte asimismo por ser circular
            lenght++;
            return;
        }
        Nodo current=head;
        while(current.getNext()!=head)//por ser circular
            current=current.getNext();
        current.setNext(newNodo);
        newNodo.setNext(head);//por ser circular, debe apuntar al head
        lenght++;
    }
    public boolean searchJ(int key){
        if(head==null)
            return false;
        Nodo current=head;
        do{
            if(current.getData()==key)
                return true;
            current=current.getNext();
        }while(current!=head);
        return false;
    }
    
    public int search(int key){
        if(head==null)
            return NOT_FOUND;
        Nodo current=head;
        int index=0;
        do{
            if(current.getData()==key)
                return index;   //se encontro el valor
            current=current.getNext();//se desplaza al siguiente nodo
            index++;
        }while(current!=head);
        return NOT_FOUND;
    }
    
    public void remove(int data){
        if(head==null)//no hay lista
            return;
        //si el elemento a eliminar se encuentra en la parte frontal
        if(head.getData()==data){
            Nodo current=head;
            while(current.getNext()!=head)
                current=current.getNext();
            head=head.getNext();  //actualizamos head
            current.setNext(head);//el ultimo nodo vincularlo al nuevo head
            lenght--;
            return;                
        }
        //asumiento que se encuentra en el cuerpo de la lista
        Nodo current=head;
        while(current.getNext()!=head
                && current.getNext().getData()!=data)
            current=current.getNext();
        if(current.getNext()!=head){
            current.setNext(current.getNext().getNext());
            lenght--;
        }            
    }
    //Metodo remove para resolver el problema de Joshepus
    public int remover(int n){
        if(head==null || n<=0)
            return OUT_OF_LIMIT;
        head=remover(head,n);
        return head.getData();
    }
    //Metodo recursivo para resolver el problema de Joshepus
    private Nodo remover(Nodo head,int n){
        if(head==null || head.getNext()==head)
            return head; //caso base
        Nodo current=head;
        Nodo prev=null;
        //desplazarnos n nodos
        for(int i=1; i<n;i++){
            prev=current;
            current=current.getNext();            
        }
        //Eliminar el nodo actual
        prev.setNext(current.getNext());
        lenght--;
        return remover(prev.getNext(),n);
    }
    
    //Metodo de Ordenamiento Burbuja
    public void bubbleSort(){
        if(head==null || head.getNext()==head)  return;//lista vacia o solo tiene un nodo
        boolean swapped;
        Nodo actual;
        Nodo ultimo=null;
        do{
            swapped=false;
            actual=head;
            while(actual.getNext()!=ultimo
                    && actual.getNext()!=head){
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
        do{
            if(!str.isEmpty())
                str.append("->");
            str.append(current.getData());
            current=current.getNext();
        }while(current!=head);
        return "->"+str.toString()+"->";//para señalar que se trata de una lista circular
    }
}
