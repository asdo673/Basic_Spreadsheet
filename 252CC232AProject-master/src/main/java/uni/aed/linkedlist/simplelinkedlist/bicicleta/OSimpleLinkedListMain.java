package uni.aed.linkedlist.simplelinkedlist.bicicleta;

import uni.aed.model.Bicicleta;

public class OSimpleLinkedListMain {
    private static final int OUT_OF_LIMIT=-1;
    private static final int NOT_FOUND=-1;
    
    public static void main(String[] args){
        OSimpleLinkedListMain listaMain=new OSimpleLinkedListMain();
        listaMain.testOSimpleLinkedList();
        
    }
    
    private void testOSimpleLinkedList(){
        OSimpleLinkedList lista=new OSimpleLinkedList();
        Bicicleta b1=new Bicicleta("Sonya Smith");
        Bicicleta b2=new Bicicleta("Barack Obama");
        Bicicleta b3=new Bicicleta("Kamala Harris");
        Bicicleta b4=new Bicicleta("Dina Boluarte");
        Bicicleta b5=new Bicicleta("Steve Jobs");
        Bicicleta b6=new Bicicleta("Sonya Smith");
        Bicicleta b7=new Bicicleta("Bill Gates");
        Bicicleta b8=new Bicicleta("Sonya Smith");
        
        lista.addLast(b1);
        lista.addLast(b2);
        lista.addLast(b3);
        lista.addLast(b4);
        lista.addLast(b5);        
        lista.addLast(b6);
        lista.addLast(b7);
        lista.addLast(b8);
        
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        System.out.println("La lista contiene "+lista.size()+" elementos");
        //pendiente probar busqueda similar a la lista simplemente enlazada        
        System.out.println("Determinando si el propietario "+ b1.getNomPropietario()+" tiene Bicicletas registradas");
        int result= lista.search(b1);
        if(result==NOT_FOUND)
            System.out.println("El propietario NO cuenta con bicicletas registradas");
        else
            System.out.println("La bicicleta del propietario se ubico en la posicion "+result);
        
        System.out.println("Buscando bicicletas del propietario " + b1.getNomPropietario());        
        System.out.println("Se ubicaron las bicicletas: " + lista.searchAll(b1));        
        
        System.out.println("Ordenando los elementos de la lista");
        lista.bubbleSort();
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
        
    }
}
