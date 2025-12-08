package uni.aed.linkedlist.circlelinkedlist.Josephus;

import java.util.Scanner;
import uni.aed.linkedlist.circlelinkedlist.CircleLinkedList;

public class JosephusMain {
    private static final int ERROR=-1;
    private static final int EXITO=1;
    private static final int NOT_FOUND=-1;
    private static final Scanner scr=new Scanner(System.in).useDelimiter("\n");   
    CircleLinkedList lista=new CircleLinkedList();
    int M=0;
    public static void main(String[] args){
        JosephusMain josephus = new JosephusMain();
        josephus.menu();
    }
    
    private void menu(){
        int opcion=1;        
        String SEPARADOR="\n";
        try{
            do{
                System.out.print("Programa Caso Josephus"+SEPARADOR+
                "1.- Definir M "+SEPARADOR+
                "2.- Registrar estudiantes "+SEPARADOR+
                "3.- Ordenar estudiantes "+SEPARADOR+            
                "4.- Remover cada N-esimo estudiante "+SEPARADOR+            
                "5.- Visualizar lista "+SEPARADOR+            
                "6.- Salir "+SEPARADOR+
                "Elija una opcion:");
                opcion=scr.nextInt();
                switch(opcion)
                {
                    case 1->{inicializar();}
                    case 2->{registrar();visualizar();}
                    case 3->{ordenar();visualizar();}
                    case 4->{remover();visualizar();}
                    case 5->{visualizar();}                                    
                    default-> {break;}                
               }
            }while(opcion!=6);
            scr.close();
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }
    }
    private void inicializar(){
        System.out.println("Ingrese M");
        M=scr.nextInt();
        if(validar(M)==ERROR) return;
    }
    private void registrar(){
        if(validar(M)==ERROR) return;
        for(int i=0; i<M; i++){
            int estudiante=random(1,M);
            if(lista.search(estudiante)==NOT_FOUND)
                lista.addLast(estudiante);
            else
                i--;
        }
    }
    
    private void ordenar(){
        if(validar(M)==ERROR) return;
        lista.bubbleSort();
        visualizar();        
    }
    
    private void remover(){
        if(validar(M)==ERROR) return;
        System.out.println("Ingrese N");
        int n=scr.nextInt();
        if(validar(n)==ERROR) return;
        System.out.println("Removiendo cada n elementos de la lista");
        System.out.println("El sobreviviente es: " + lista.remover(n));        
    }
    
    private void visualizar(){
        if(validar(M)==ERROR) return;
        System.out.println("Visualizando la lista enlazada");
        System.out.println(lista.toString());
    }
    private int validar(int m){
        if(m<=0){
            System.out.println("Debe ingresar un numero valido");
            return ERROR;
        }
        return EXITO;
    }
    
    private int random(int low, int high){
        return (int)Math.floor(Math.random()*(high-low +1))+low;
    }
}
