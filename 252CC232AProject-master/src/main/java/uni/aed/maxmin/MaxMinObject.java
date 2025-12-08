package uni.aed.maxmin;
import java.util.Comparator;

public class MaxMinObject {
    public Object getMaxValor(Object[] X, Comparator comp){
        if(X ==null || X.length==0) return -1;
        Object max=X[0];
        for(int i=1;i<X.length;i++){
            if(compare(X[i],max,comp)>0)
                max=X[i];            
        }
        return max;
    }
    
    public Object getMinValor(Object[] X, Comparator comp){
        if(X ==null || X.length==0) return -1;
        Object min=X[0];
        for(int i=1;i<X.length;i++){
            if(compare(X[i],min,comp)<0)
                min=X[i];            
        }
        return min;
    }
    
    //Metodo auxiliar que compara usando Comparator o Comparable
    private int compare(Object a,Object b,Comparator comp){
        if(comp!=null)//Para Datos no simples(primitivos), casos cuando se compara clases tipo: Persona, Empleado, etc
            return comp.compare(a, b);
        //si las clases a y b implementan comparable
        if(a instanceof Comparable && b instanceof Comparable)
            //si a>b -> 1, si a=b ->0, si a<b ->-1
            return ((Comparable)a).compareTo(b);
        
        throw new IllegalArgumentException("Los objetos no son comparable y no se proporcion Comparator");
    }
    
}
