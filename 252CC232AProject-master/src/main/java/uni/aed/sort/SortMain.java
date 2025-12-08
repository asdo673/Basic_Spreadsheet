package uni.aed.sort;

import java.util.Random;
import java.util.Scanner;

public class SortMain {
    private static final Scanner scr=new Scanner(System.in).useDelimiter("\n");     
    private static Sort sort=new Sort();
    private Integer dataOrigen[]=null;
    
    public static void main(String[] args){
        SortMain sortMain=new SortMain();
        sortMain.menu();        
    }
    private void menu(){
        int opcion=1;        
        String SEPARADOR="\n";
        try{
            do{
                System.out.print("Algoritmos Ordenamiento"+SEPARADOR+
                        "1.- Carga de Datos en Array "+SEPARADOR+
                        "2.- Carga de Datos Duplicados en Array "+SEPARADOR+
                        "3.- Carga Personalizada en Array "+SEPARADOR+
                        "4.- Carga Aleatoria en Array "+SEPARADOR+
                        "5.- Visualizar Array "+SEPARADOR+
                        "6.- Ordenar - metodo de Seleccion "+SEPARADOR+
                        "7.- Ordenar - metodo Burbuja "+SEPARADOR+
                        "8.- Ordenar - metodo Insercion "+SEPARADOR+
                        "9.- Ordenar - metodo Insercion Binaria "+SEPARADOR+
                        "10.- Ordenar - metodo HeapSort "+SEPARADOR+
                        "11.- Ordenar - metodo QuickSort "+SEPARADOR+
                        "12.- Ordenar - metodo MergeSort "+SEPARADOR+
                        "13.- Salir "+SEPARADOR+
                        "Elija una opcion:");
                opcion=scr.nextInt();
                switch(opcion)
                {
                    case 1->{carga();visualizar();}
                    case 2->{cargaDuplicados();visualizar();}
                    case 3->{cargaPersonalizada();visualizar();}
                    case 4->{cargaAleatoria();visualizar();}
                    case 5->{visualizar();}
                    case 6->{testSelectionSort();}
                    case 7->{testBubbleSort();}
                    case 8->{testInsercionSort();}
                    case 9->{testInsercionBinariaSort();}
                    case 10->{testHeapSort();}
                    case 11->{testQuickSort();}
                    case 12->{testMergeSort();}                    
                    default-> {break;}                
               }
            }while(opcion!=13);
            scr.close();
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }
    }
    private void carga(){
        Integer X[]={5,14,24,39,43,65,84,45};        
        dataOrigen=X.clone();
        sort.setX(X);
    }
    private void cargaDuplicados(){
        Integer X[]={5,14,24,14,43,5,84,45};        
        dataOrigen=X.clone();
        sort.setX(X);
    }
    private void cargaPersonalizada(){
        System.out.println("Ingrese el tamaño del array ");
        int N=scr.nextInt();
        Integer[] X=new Integer[N];
        for(int i=0;i<X.length;i++){
            System.out.print("Ingrese el valor X["+i+"]= ");
            X[i]=scr.nextInt();
        }
        dataOrigen=X.clone();
        sort.setX(X);        
    }
    private void cargaAleatoria(){       
        int min=0;
        //int N=5000;        
        //int max=20000;        
        System.out.println("Carga Aleatoria");
        System.out.println("Ingrese el tamaño del array ");
        int N=scr.nextInt();
        System.out.println("Ingrese el rango superior de los elementos aleatorios del array ");
        int max=scr.nextInt();        
        Integer[] X=new Integer[N];
        Random random=new Random();
        for(int i=0;i<X.length;i++)
            X[i]=random.nextInt(max);
        dataOrigen=X.clone();
        sort.setX(X);
    }
    private void visualizar(){
        System.out.println("El Array contiene los siguientes elementos");
        System.out.println(sort.toString());
    }
    private void visualizarDataOrigen(){
        sort.setX(dataOrigen.clone());
        System.out.println("El Array contiene los siguientes elementos");
        System.out.println(sort.toString());
    }
    private void testSelectionSort(){
        visualizarDataOrigen();//setear la data origen y visualizarla
        sort.selectionSort();//invocamos al algoritmo de ordenamiento
        System.out.println(sort.toString());//visualizamos el resultado
    }
    
    private void testBubbleSort(){                
        visualizarDataOrigen();
        sort.bubbleSort();
        System.out.println(sort.toString());        
    }
    
    private void testInsercionSort(){                 
        visualizarDataOrigen();
        sort.insercionSort();                
        System.out.println(sort.toString());        
    }
    private void testInsercionBinariaSort(){      
        visualizarDataOrigen();        
        sort.insercionBinariaSort();        
        System.out.println(sort.toString());        
    }
    
    private void testHeapSort(){
        visualizarDataOrigen();//setear la data origen y visualizarla
        sort.heapSort();//invocamos al algoritmo de ordenamiento
        System.out.println(sort.toString());//visualizamos el resultado
    }
    
    private void testQuickSort(){          
        visualizarDataOrigen();        
        sort.quickSort(0,sort.getX().length-1);
        System.out.println(sort.toString()+ ",");   
    }          
    private void testMergeSort(){          
        visualizarDataOrigen();        
        sort.mergeSort(sort.getX());
        System.out.println(sort.toString()+ ",");   
    }         
}
