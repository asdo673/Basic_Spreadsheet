package uni.aed.linkedlist.doublelinkedlist;

public class DoubleLinkedListMain {
    private static final int NOT_FOUND=-1;
    public static void main(String[] args){
        DoubleLinkedListMain listaMain=new DoubleLinkedListMain();
        listaMain.testDoubleLinkedList();
         
    }
    private void testDoubleLinkedList(){
        DoubleLinkedList lista=new DoubleLinkedList();
        lista.addLast(10);
        lista.addLast(20);
        lista.addLast(30);
        lista.addFirst(40);
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("La lista contiene "+lista.size()+" elementos");
        System.out.println("Eliminando el elemento 20");
        lista.remove(20);        
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("Buscando el elemento 20");
        int result=lista.search(20);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
        
        System.out.println("Buscando el elemento 40");
        result=lista.search(40);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
        
        System.out.println("Ordenando los elementos de la lista");
        lista.bubbleSort();
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        
        System.out.println("Realizando Busqueda Mejorada el elemento 30");
        result=lista.searchMejorado(30);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
    }
}
