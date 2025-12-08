package uni.aed.maxmin;
public class MaxMinObjectMain {
    public static void main(String[] args){
        MaxMinObjectMain maxMinObjectMain=new MaxMinObjectMain();
        maxMinObjectMain.testMaxMinInteger();  
        maxMinObjectMain.testMaxMinString();
    }
    private void testMaxMinInteger(){
        MaxMinObject mm=new MaxMinObject();
        //Tipo de datos simple o primitivo
        Object X[]={15,1,25,60,69,86,3,78,2,10};
        //visualizando el arreglo
        System.out.println("El contenido del arreglo es:");
        for(Object x:X)
            System.out.print(x.toString()+",");
        System.out.println("");
        //Determinando el maximo y minimo del Array
        System.out.println("El maximo valor es:"+ mm.getMaxValor(X,null));
        System.out.println("El minimo valor es:"+ mm.getMinValor(X,null));        
    }
    private void testMaxMinString(){
        MaxMinObject mm=new MaxMinObject();
        //Tipo de datos simple o primitivo
        Object X[]={"Jose","Juan","Ana","Salome","Beatriz","Mario","Ronald","Rita","Michael","Donald"};
        //visualizando el arreglo
        System.out.println("El contenido del arreglo es:");
        for(Object x:X)
            System.out.print(x.toString()+",");
        System.out.println("");
        //Determinando el maximo y minimo del Array
        System.out.println("El maximo valor es:"+ mm.getMaxValor(X,null));
        System.out.println("El minimo valor es:"+ mm.getMinValor(X,null)); 
    }
    private void testMaxMinPersona(){
    }
}
