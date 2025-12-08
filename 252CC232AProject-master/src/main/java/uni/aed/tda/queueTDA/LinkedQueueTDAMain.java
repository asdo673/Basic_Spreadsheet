package uni.aed.tda.queueTDA;

import uni.aed.model.Persona;

public class LinkedQueueTDAMain {
     public static void main(String[] args){
        LinkedQueueTDAMain queueTDAMain=new LinkedQueueTDAMain(); 
        queueTDAMain.testLinkedQueueTDAInteger();
        System.out.println("-----------------------------");
        queueTDAMain.testLinkedQueueTDAString();
        System.out.println("-----------------------------");
        queueTDAMain.testLinkedQueueTDAPersona();
    }
     
    private void testLinkedQueueTDAInteger(){
        QueueTDA<Integer> cola=new LinkedQueueTDA<>();
        System.out.println("Antes de añadir elementos");
        System.out.println("La Cola contiene "+cola.size()+" elementos");
        cola.enqueue(30);
        cola.enqueue(10);
        cola.enqueue(20);
        cola.enqueue(5);
        System.out.println("Despues de añadir elementos");
        System.out.println("La Cola contiene "+cola.size()+" elementos");
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());

        System.out.println("Eliminando Elementos");
        System.out.println("Elemento Eliminado: "+cola.dequeue().toString());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());

        System.out.println("Recuperando Elementos");
        System.out.println("Elemento Recuperado: "+cola.peek().toString());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());
    }
    private void testLinkedQueueTDAString(){
        QueueTDA<String> cola=new LinkedQueueTDA<>();
        System.out.println("Antes de añadir elementos");
        System.out.println("La Cola contiene "+cola.size()+" elementos");
        cola.enqueue("Jose");
        cola.enqueue("Beatriz");
        cola.enqueue("Sandro");
        cola.enqueue("Franklin");
        System.out.println("Despues de añadir elementos");
        System.out.println("La Cola contiene "+cola.size()+" elementos");

        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());
        System.out.println("Eliminando Elementos");
        System.out.println("Elemento Eliminado: "+cola.dequeue());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());

        System.out.println("Recuperando Elementos");
        System.out.println("Elemento Recuperado: "+cola.peek());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());
    }
    private void testLinkedQueueTDAPersona(){
        QueueTDA<Persona> cola=new LinkedQueueTDA<>();
        cola.enqueue(new Persona("Jose",36,'M'));
        cola.enqueue(new Persona("Ana",46,'F'));
        cola.enqueue(new Persona("Juan",18,'M'));
        cola.enqueue(new Persona("Betty",66,'F'));
        System.out.println("Despues de añadir elementos");
        System.out.println("La Cola contiene "+cola.size()+" elementos");

        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());
        System.out.println("Eliminando Elementos");
        System.out.println("Elemento Eliminado: "+cola.dequeue());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());

        System.out.println("Recuperando Elementos");
        System.out.println("Elemento Recuperado: "+cola.peek());
        System.out.println("Visualizando la Cola");
        System.out.println(cola.toString());

    }

    
}
