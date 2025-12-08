package uni.aed.maxmin;

import java.util.Scanner;

public class MaxMinMain {
    private static final Scanner scr=new Scanner(System.in).useDelimiter("\n");
    private static Integer[] X;
    
    public static void main(String[] args){
        MaxMinMain maxMinMain=new MaxMinMain();
        //maxMinMain.testMaxMin();
        maxMinMain.menu();
    }
    
    private void menu(){
        int opcion=1;
        String Rpta="S";
        String SEPARADOR="\n";
        try{
            do{
                System.out.print("Algoritmos Maximo-Minimo"+SEPARADOR+
                        "1.- Carga de Datos en Array "+SEPARADOR+
                        "2.- Visualizar Array "+SEPARADOR+
                        "3.- Determinar Maximo-Minimo "+SEPARADOR+
                        "4.- Salir "+SEPARADOR+
                        "Elija una opcion:");
                opcion=scr.nextInt();
                switch(opcion)
                {
                    case 1->{X=carga();}
                    case 2->{visualizar(X);}
                    case 3->{getMaxMin(X);}
                    default-> {break;}                
                }
                System.out.print("Para continuar con la operaciones pulse S, para finalizar pulse N: ");
                Rpta=scr.next().toUpperCase();            
            }while(Rpta.equals("S")==true);
            scr.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    private Integer[] carga(){
        System.out.print("Ingrese el tamaño del Array:");
        int N=scr.nextInt();
        Integer[] X=new Integer[N];
        for(int i=0;i<X.length;i++)
        {
            System.out.print("Ingrese el valor X["+i+"]=");
            X[i]=scr.nextInt();     
        }   
        return X;
    }
    private void visualizar(Integer[] X){
        if(validar(X)==-1) return;
        System.out.print("El Array contiene los siguientes elementos:");
        for(int i=0;i<X.length;i++)        
            System.out.print(X[i]+ ",");
        System.out.println("");
        
    }
    private void getMaxMin(Integer[] X){
        if(validar(X)==-1) return;
        MaxMin mm=new MaxMin();
        System.out.println("");
        System.out.println("El maximo valor es: "+mm.getMaxValor(X));
        System.out.println("El maximo valor es: "+mm.getMinValor(X));
    }
    
    private int validar(Integer[] X){
        if(X==null || X.length==0){
            System.out.print("El Array esta vacio, primero debe cargar datos");
            return -1;
        }
        return 0;
    }
    
    
    
    private void testMaxMin(){
        //Se instancia la clase en la que estan definidos los algortimos Maximo y Minimo
        MaxMin mm=new MaxMin();
        //Declarar e inicializar y arreglo
        Integer X[]={15,1,25,60,69,86,3,78,2,10};
        //Visualizar el contenido del Array X
        for(int i=0;i<X.length;i++)
            System.out.print(X[i]+",");
        System.out.println("");
        //Determinando el maximo y minimo del Array
        System.out.println("El maximo valor es:"+ mm.getMaxValor(X));
        System.out.println("El minimo valor es:"+ mm.getMinValor(X));
    }
    
    
}
