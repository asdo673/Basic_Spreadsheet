package uni.aed.linkedlist.circlelinkedlist;

public class CircleLinkedListMain {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    public static void main(String[] args){
        CircleLinkedListMain listaMain=new CircleLinkedListMain();
        listaMain.testCircleLinkedList();        
    }
    private void testCircleLinkedList(){
        CircleLinkedList lista=new CircleLinkedList();
        lista.addLast(60);
        lista.addLast(40);
        lista.addLast(30);
        lista.addLast(10);
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("La lista contiene "+lista.size()+" elementos");
        
        System.out.println("Ordenando los elementos de la lista");
        lista.bubbleSort();
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        
        System.out.println("Eliminando el elemento 30");
        lista.remove(30);        
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("Buscando el elemento 10");
        int result=lista.search(10);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
        
        System.out.println("Buscando el elemento 30");
        result=lista.search(30);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
    }
}
