package uni.aed.tda.queueTDA;

public class ArrayQueueTDA<E> implements QueueTDA<E>{
    public static final int TAMANIO_DEFINIDO=10;
    private E[] queue;
    private int front;
    private int back;
    private int count;

    public ArrayQueueTDA(int tamanio) {
        if(tamanio<=0)
            throw new IllegalArgumentException("Capacaidad debe ser mayor a cero");
        queue=(E[])new Object[tamanio];
        count=0;
    }

    @Override
    public void enqueue(E data) {
        if(count==queue.length)
            expande();
        queue[back]=data;
        back=(back+1)%queue.length;
        count++;
    }
    private void expande(){
        int newLength=(int)(1.5*queue.length);
        E[] temp=(E[])new Object[newLength];
        int q_idx=front;
        int t_idx=front;
        for(int i=0;i<queue.length;i++){
            temp[t_idx]=queue[q_idx];
            q_idx=(q_idx+1)%queue.length;//nos brinda el nuevo front de la cola
            t_idx=(t_idx+1)%temp.length;//nos brinda el nuevo front de la temp            
        }
        back=t_idx;
        queue=temp;
    }
    

    @Override
    public E dequeue() throws QueueEmptyExceptionTDA {        
        if(isEmpty())
            throw new QueueEmptyExceptionTDA();
        E item=queue[front];
        queue[front]=null;
        front=(front+1)%queue.length;
        count--;
        return item;
    }

    @Override
    public E peek() throws QueueEmptyExceptionTDA {
        if(isEmpty())
            throw new QueueEmptyExceptionTDA();
        return queue[front];
    }

    @Override
    public void clear() {
        for(int i=0;i<queue.length;i++)
            queue[i]=null;
        front=back=count=0;        
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }
    
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        int current=front;        
        for(int i=0;i<count;i++){
            if(!str.isEmpty())
                str.append(",");
            str.append(queue[current].toString());
            current=(current+1)%queue.length;
        }        
        return str.toString();
    }
    
    @Override
    public String toString(String patron) {
        StringBuilder str=new StringBuilder();
        int current=front;
        for(int i=0;i<count;i++){
            if(!str.isEmpty())
                str.append(patron);
            str.append(queue[current].toString());
            current=(current+1)%queue.length;
        }
        return str.toString();
    }   

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
