package uni.aed.tda.arraylistTDA;

import uni.aed.tda.listTDA.IteratorTDA;
import uni.aed.tda.listTDA.ListTDA;

public class ArrayListTDA<E> implements ListTDA<E>{
    public static final int TAMANIO_DEFINIDO=25;
    public static final int NOT_FOUND=-1;
    
    private E[] datos;
    private int count;

    public ArrayListTDA() {
       this(TAMANIO_DEFINIDO);
    }

    public ArrayListTDA(int size) {
        if(size<=0)
           throw new IllegalArgumentException("Tamaño de la lista debe ser mayor a cero");
        datos=(E[])new Object[size];
        this.count = 0;
    }
    

    @Override
    public void add(E data) {
        if(count==datos.length)
            expande();
        datos[count]=data;
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
    public void add(int index, E data) throws IndexOutOfBoundsException {
        revisaPosInsercion(index);//validamos la posicion
        if(count==datos.length)
            expande();        
        for(int i=count;i>index;i--)//hacemos un espacio a la derecha
            datos[i]=datos[i-1];
        datos[index]=data;
        count++;
    }
    private void revisaPosInsercion(int index){
        if(index<0)
            throw new IndexOutOfBoundsException("Indice negativo es invalido");
        else if(index>size())
            throw new IndexOutOfBoundsException(index+" es mayor al permitido "+ size());
    }

    @Override
    public void clear() {
         for(int i=0; i<count;i++)
             datos[i]=null;
         count=0;
    }

    @Override
    public boolean contain(E data) {
        boolean resultado=true;
        int index=indexOf(data);
        if(index==NOT_FOUND)
            resultado=false;
        return resultado;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        revisaPosAcceso(index);//validamo index
        return datos[index];
    }
    
    private void revisaPosAcceso(int index){
        if(size()==0)
            throw new IndexOutOfBoundsException("Indice "+ index+" es ivalido. Lista vacia");
        else if(index<0)
            throw new IndexOutOfBoundsException("Indice negativo "+ index+" es ivalido");
        else if(index > size()-1)
            throw new IndexOutOfBoundsException("Indice "+ index+" es mayor al limite superior valido "+ (size()-1));
    }
            
    @Override
    public int indexOf(E data) {
        int index=0;
        while(index<count && !datos[index].equals(data))
            index++;
        if(index==count)//salio del bucle while por la 1° condicion
            index=NOT_FOUND;
        return index;
    }

    @Override
    public boolean isEmpty() {
        return (count==0);
    }

    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        revisaPosAcceso(index);//validamo index
        E elemento=datos[index];
        for(int i=index; i<count;i++)
            datos[i]=datos[i+1];    //desplazamiento con la finalidad de cubrir el espacio del elemento eliminado
        datos[count]=null;          //limpiamos la ultima posicion del array
        count--;
        return elemento;
    }

    @Override
    public boolean delete(E data) {
        int index=indexOf(data);
        if(index==NOT_FOUND)
            return false;        
        delete(index);
        return true;        
    }

    @Override
    public E modify(int index, E data) throws IndexOutOfBoundsException {
        revisaPosAcceso(index);   //valida el index
        E elemento=datos[index]; //se reserva en una variable el valor a modificar
        datos[index]=data;
        return elemento;        //retorna elemento modificado
    }

    @Override
    public int size() {
        return count;
    }    

    @Override
    public IteratorTDA<E> iterador() {
        return new Iterador<>(this);
    }
    
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        IteratorTDA<E> it=this.iterador();
        while(it.hasNext()){
            if(!str.isEmpty())
                str.append(",");
            str.append(it.next().toString());
        }
        return str.toString();
    }    
    
    @Override
    public String toString(String patron) {
        StringBuilder str=new StringBuilder();
        IteratorTDA<E> it=this.iterador();
        while(it.hasNext()){
            if(!str.isEmpty())
                str.append(patron);
            str.append(it.next().toString());
        }
        return str.toString();
    }

    @Override
    public ListTDA<E> union(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ListTDA<E> interseccion(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ListTDA<E> diferencia(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toArray() {
        IteratorTDA<E> it=this.iterador();
        Object[] newArray=new Object[size()];
        int i=0;
        while(it.hasNext()){
            newArray[i]=(Object)it.next();
            i++;
        }
        return newArray;
    }
    
}
