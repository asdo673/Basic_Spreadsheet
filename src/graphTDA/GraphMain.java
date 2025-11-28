package graphTDA;

import graphTDA.DataGraph.UndirectedGraph;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GraphMain {
    private static Scanner scr;
    private static int opcion=1;
    private static String SEPARADOR="\n";
    private static String Rpta="S";
    public static void main(String[] args) {
         GraphMain g=new GraphMain();
         scr=new Scanner(System.in).useDelimiter(SEPARADOR);
         g.menu();
    }
    private void menu(){        
        try{
            do			
            {	
                System.out.print("Grafos"+SEPARADOR+
                "1.- Grafo No Dirigido "+SEPARADOR+                                            
                "2.- Salir "+SEPARADOR+"Elija una opcion:");                
                opcion =scr.nextInt();            
                switch (opcion)
                {
                    case 1 -> {getUndirectedGraph();}                                                          
                    default -> {break;}
                }	            
                System.out.print("Para continuar con las operaciones pulsa S; Para finalizar pulse N: ");
                Rpta=scr.next().toUpperCase();			
            }while(Rpta.equals("S")==true);	
        }catch(InputMismatchException ex) {
            System.out.println("Debe ingresar obligatoriamente un número entero como opcion elegida.");
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }finally{            
            scr.close();
        }
    }
    
    private void getUndirectedGraph(){
        System.out.println("Grafo no dirigido");
        UndirectedGraph undirectedGraph=new UndirectedGraph();
        System.out.println(undirectedGraph.graph);
    }
    
}
