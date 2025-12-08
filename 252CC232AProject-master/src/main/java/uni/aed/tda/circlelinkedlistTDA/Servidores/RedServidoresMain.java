package uni.aed.tda.circlelinkedlistTDA.Servidores;

import java.util.Scanner;
import uni.aed.tda.circlelinkedlistTDA.CircleLinkedListTDA;

public class RedServidoresMain {
    private static final int ERROR=-1;
    private static final int EXITO=1;
    CircleLinkedListTDA<Integer> lista = new CircleLinkedListTDA<>();
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    int M=0;

    public static void main(String[] args) {     
        try{
            RedServidoresMain servidor = new RedServidoresMain();
            servidor.menu();
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }
        
    }
    private void menu(){
        boolean salir = false;                
        while (!salir) {
            System.out.println("Programa Caso Apagado de Servidores");
            System.out.println("a) Definir M ");
            System.out.println("b) Generar Red de Servidores");            
            System.out.println("c) Apagar Servidores");
            System.out.println("d) visualizar lista ");
            System.out.println("e) salir ");
            System.out.println("Elija una opción del menu: ");    
            String opcion = scan.next();
            switch (opcion) {
                case "a" -> {
                    System.out.print("inserte el M: ");
                    int m = scan.nextInt();
                    definirM(m);
                }
                case "b" -> registrar();                
                case "c" -> remover();                                    
                case "d" -> visualizar();
                case "e" -> salir = true;
            }
        }
        scan.close();
    }

    private void definirM(int m) {
        if(validar(m)==ERROR) return;
        M = m;
    }
    private int validar(int m){
        if(m<=0){
            System.out.println("Debe ingresar un numero de servidores valido");
            return ERROR;
        }
        return EXITO;
    }
            
    private void registrar() {
        if(validar(M)==ERROR) return;
        for (int i = 0; i < M; i++) {
            int servidor=random(1, M);
            if(!lista.search(servidor))
                lista.add(servidor);
            else
                i--;
        }
    }

    private void remover() {
        if(validar(M)==ERROR) return;
        System.out.print("inserte N: ");
        int n = scan.nextInt();
        lista.bubbleSort();
        visualizar();
        System.out.println("Apagando cada N servidores de la lista");
        System.out.println("El unico servidor activo es: " + lista.delete(n));        
    }

    private void visualizar() {        
        if(validar(M)==ERROR) return;
        System.out.println("Visualizando el contenido de la lista");
        System.out.println(lista.toString());
    }
    
    private int random(int low, int high) {
        return (int) Math.floor(Math.random() * (high - low + 1)) + low;
    }
}
