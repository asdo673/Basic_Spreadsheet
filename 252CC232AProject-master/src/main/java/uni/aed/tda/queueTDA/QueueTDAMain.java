package uni.aed.tda.queueTDA;

public class QueueTDAMain {
    public static void main(String[] args){
        QueueTDAMain queueTDAMain=new QueueTDAMain(); 
        queueTDAMain.testPriorityQueueTDAInteger();        
    }
    private void testPriorityQueueTDAInteger(){
        int n=7;
        QueueTDA<Integer> q=new PriorityQueueTDA<>(n);
        q.enqueue(56);
        q.enqueue(88);
        q.enqueue(7);
        q.enqueue(60);
        q.enqueue(2);
        q.enqueue(71);
        q.enqueue(59);
        
        System.out.println("Despues de añadir elementos");
        System.out.println("La Cola contiene "+q.size()+" elementos");
        System.out.println("Visualizando la Cola");        
        System.out.println(q.toString());
        
        System.out.println("Recuperando Elementos");
        System.out.println("Elemento Recuperado: "+q.peek().toString());
        System.out.println("Visualizando la Cola");        
        System.out.println(q.toString());
        
        System.out.println("Eliminando Elementos");
        for(int i=0; i<n;i++)            
            System.out.println("Elemento Eliminado: "+q.dequeue().toString());
        
        System.out.println("Despues de eliminar elementos");
        System.out.println("La Cola contiene "+q.size()+" elementos");        
    }
}
