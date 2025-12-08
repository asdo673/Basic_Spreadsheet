package uni.aed.pc1;

import uni.aed.model.Producto;
import java.util.NoSuchElementException;
import uni.aed.maxmin.MaxMinObject;
import uni.aed.search.SearchObject;
import uni.aed.sort.SortObject;

public class Venta {
    private static final int  DEFAULT_SIZE = 25;
    private static final int  NOT_FOUND    = -1;
    private Producto[]   entry;    
    private int        count;
    
    public Venta( )
    {
        this( DEFAULT_SIZE );
    }

    public Venta( int size )
    {
        count = 0;
        if (size <= 0 )
            throw new IllegalArgumentException("Tamaño debe ser positivo");        
        entry = new Producto[size];                
    }

    public Producto[] getEntry() {
        return entry;
    }
    
    public void add( Producto newProducto )
    {
        if (count == entry.length)
            enlarge( );        
        entry[count] = newProducto;
        count++;
    }
    public Producto getMaxVenta(){
        validar(); 
        MaxMinObject mm=new MaxMinObject();                   
        return (Producto)mm.getMaxValor(entry,null);
    }
    
    public Producto getMinVenta(){
        validar(); 
        MaxMinObject mm=new MaxMinObject();                   
        return (Producto)mm.getMinValor(entry,null);
    }
    
     //implementacion del metodo sort que atiende el enunciado 1 de la 1PC
    public Producto[] sort ( int attribute) {
        validar();        
        if (!(attribute == Producto.CANTIDAD || attribute == Producto.PRECIO || attribute == Producto.DESCRIPCION) )
            throw new IllegalArgumentException( );                        
        Producto[] sortedList = new Producto[ count ];        
        //copiamos las referencias a la lista ordenada
        entry[0].setCompareAttribute(attribute);
        for (int i = 0; i < count; i++)
            sortedList[i] = entry[i];                //Object[] X=getY().clone();
        SortObject sortObject=new SortObject();                 
        sortObject.setX(sortedList);
        sortObject.callMergeSort();                          
        return sortedList;
    }
    public int search(Producto[] sortedList,Object searchValue) {   
        validar();
        SearchObject searchObject=new SearchObject();    
        searchObject.setX(sortedList);
        return searchObject.searchBinaria(searchValue);
    }
    
    private void validar(){
        if (entry == null || size() == 0)
            throw new NoSuchElementException("El array esta vacio, primero debe cargar los productos.");
    }
    
    private void enlarge( )
    {        
        int newLength = (int) (1.5 * entry.length);
        Producto[] temp = new Producto[newLength];        
        for (int i = 0; i < entry.length; i++) {
            temp[i] = entry[i];
        }        
        entry = temp;   
    }
    
    public int size(){
        return count;
    }
    
    @Override
    public String toString() {
        validar();
        StringBuilder str=new StringBuilder();
        for(Producto p: entry){
            if (p==null) break;
            if(!str.isEmpty())
                str.append("\n");
            str.append(p.toString());                        
        }
        return str.toString();
    }
    
}
