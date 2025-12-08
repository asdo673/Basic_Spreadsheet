package uni.aed.linkedlist.simplelinkedlist;

public class SimpleLinkedListMain {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    
    public static void main(String[] args){
        SimpleLinkedListMain listaMain=new SimpleLinkedListMain();
        listaMain.testSimpleLinkedList();
        
    }
    private void testSimpleLinkedList(){
        SimpleLinkedList lista=new SimpleLinkedList();
        lista.addLast(10);
        lista.addLast(20);
        lista.addLast(30);
        lista.addFirst(40);
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("La lista contiene "+lista.size()+" elementos");
        System.out.println("Añadiendo un elemento en la posicion 50");
        int result=lista.addToPosition(50,2);        
        if(result==OUT_OF_LIMIT)
            System.out.println("La posicion indicada es incorrecta");
        System.out.println("Añadiendo un elemento en la posicion 3");
        result=lista.addToPosition(3,2);
        if(result==OUT_OF_LIMIT)
            System.out.println("La posicion indicada es incorrecta");
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("La lista contiene "+lista.size()+" elementos");
        System.out.println("Eliminando el elemento 20");
        lista.remove(20);        
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("Buscando el elemento 20");
        result=lista.search(20);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
        
        System.out.println("Buscando el elemento 2");
        result=lista.search(2);
        if(result==NOT_FOUND)
            System.out.println("El elemento NO se ubico en la lista");
        else
            System.out.println("El elemento se ubico en la posicion "+result);
        
        System.out.println("Ordenando los elementos de la lista");
        lista.bubbleSort();
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
    }
    
}
