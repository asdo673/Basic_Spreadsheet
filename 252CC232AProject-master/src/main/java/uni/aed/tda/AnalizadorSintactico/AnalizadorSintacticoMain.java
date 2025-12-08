package uni.aed.tda.AnalizadorSintactico;

import java.util.Scanner;

public class AnalizadorSintacticoMain {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            AnalizadorSintactico analizador = new AnalizadorSintactico();
            
            int opcion;
            do {
                System.out.println("===== MENU ANALIZADOR SINTACTICO =====");
                System.out.println("1. Registrar Consulta");
                System.out.println("2. Tokenizar Consulta");
                System.out.println("3. Realizar Analisis Sintactico");
                System.out.println("4. Visualizar Tokens y Resultado");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = sc.nextInt();
                sc.nextLine();
                
                switch (opcion) {
                    case 1 -> {
                        System.out.println("Ingrese la consulta SQL:");
                        String consulta = sc.nextLine();
                        analizador.registrarConsulta(consulta);
                    }
                    case 2 -> analizador.tokenizar();
                    case 3 -> {analizador.analizarSintaxis();
                               System.out.println(analizador.getAnalisis_result());}
                    case 4 ->  System.out.println(analizador.visualizarTokens());
                    case 5 ->  System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion invalida.");
                }
            } while (opcion != 5);
            
        }catch(AnalizadorException e){
            System.out.println("Error: "+ e.getMessage());
        }catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
