package uni.aed.tda.stackTDA.recolector;

import java.util.Scanner;

/** 
 * MainRecolector
 * Interfaz de consola simple para probar el recolector:
 * a) Mostrar Laberinto
 * b) Simular Búsqueda de Ruta
 * c) Mostrar Mejor Ruta
 * d) Salir
 */
public class RecolectorMain {
    // Ejemplo de Almacen: 'B' base, 'P' paquete, '.' celda libre, '#' obstáculo
    private static char[][] ejemplo = {
            {'B', '.', '.', '#', '.'},
            {'.', '#', '.', '.', '.'},
            {'.', 'P', '.', '#', '.'},
            {'.', '.', '.', '.', 'P'},
            {'#', '.', '.', '.', 'B'}
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] lab = ejemplo;
        Recolector recolector = new Recolector(lab);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU RECOLECTOR ---");
            System.out.println("a) Mostrar Laberinto");
            System.out.println("b) Simular Busqueda de Ruta");
            System.out.println("c) Mostrar Mejor Ruta");
            System.out.println("d) Salir");
            System.out.print("Seleccione opcion: ");
            String op = sc.nextLine().trim().toLowerCase();

            switch (op) {
                case "a":
                    mostrarAlmacen(lab);
                    break;
                case "b":
                    System.out.println("Iniciando busqueda (backtracking)...");
                    boolean ok = recolector.encontrarRuta();
                    if (ok) System.out.println("Ruta encontrada (ver opción c para detalles).");
                    else System.out.println("No se encontro ruta que recoja todos los paquetes y vuelva a la base.");
                    break;
                case "c":
                    System.out.println("Mejor ruta encontrada:");
                    System.out.println(recolector.rutaMejorComoCadena());
                    break;
                case "d":
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
        sc.close();
        System.out.println("Saliendo...");
    }

    private static void mostrarAlmacen(char[][] lab) {
        System.out.println("\nAlmacen:");
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[0].length; j++) {
                System.out.print(lab[i][j] + " ");
            }
            System.out.println();
        }
    }
}
