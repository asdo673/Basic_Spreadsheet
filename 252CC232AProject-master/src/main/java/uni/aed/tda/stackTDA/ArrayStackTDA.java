package uni.aed.tda.stackTDA;

public class ArrayStackTDA<E> implements StackTDA<E> {
    public static final int TAMANIO_DEFINIDO=10;
    private E[] datos;
    private int count;

    public ArrayStackTDA() {
        this(TAMANIO_DEFINIDO);
    }
    public ArrayStackTDA(int size) {
        if(size<=0)
            throw new IllegalArgumentException("Tamaño de Pila debes mayor a cero");
        datos=(E[])new Object[size];
        count=0;
    }

    @Override
    public void push(E data) {
        if(count==datos.length)
            expande();
        datos[count]=data;  //insertar en el top de la pila
        count++;
    }
    
    private void expande(){
        int newLength=(int)(1.5*datos.length);
        E[] temp=(E[])new Object[newLength];
        for(int i=0; i<datos.length;i++)
            temp[i]=datos[i];
        datos=temp;//ahora datos apunta a temp, como tal tiene el 50% mayor de capacidad
    }

    @Override
    public E pop() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        E data=datos[count-1];//obtengo el top de la pila
        datos[count-1]=null;//elimina el top de la pila
        count--;
        return data;        
    }

    @Override
    public E peek() throws StackEmptyExceptionTDA {
        if(isEmpty())
            throw new StackEmptyExceptionTDA();
        return datos[count-1];  //retorna el top de la pila
    }

    @Override
    public void clear() {
        for(int i=0; i<count;i++)
            datos[i]=null;
        count=0;
    }

    @Override
    public int size() {
      return count;
    }

    @Override
    public boolean isEmpty() {
      return (count==0);  
    }
    
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        int current=count-1;
        str.append("[TOP]");
        while(current>=0){
            if(!str.isEmpty())
                str.append(",");
            str.append(datos[current].toString());
            current--;
        }        
        return str.toString();
    }
}
