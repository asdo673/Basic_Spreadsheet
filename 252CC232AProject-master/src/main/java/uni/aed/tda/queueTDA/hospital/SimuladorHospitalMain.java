package uni.aed.tda.queueTDA.hospital;

import java.util.InputMismatchException;
import java.util.Scanner;
import uni.aed.tda.listTDA.ListTDA;

public class SimuladorHospitalMain {
    private final SimuladorHospital simulador = new SimuladorHospital();                // Instancia del simulador de agenda
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");      // Scanner para entrada de usuario        
    public static void main(String[] args) {
        SimuladorHospitalMain simuladorMain=new SimuladorHospitalMain();
        simuladorMain.menu();
    }
    
    private void menu() {
        String SEPARADOR = "\n";
        int opcion = 5;
        try {            
            do {                
                System.out.println("--- SIMULADOR ATENCION HOSPITAL ---");
                System.out.print("--- MENU DE OPCIONES ---" + SEPARADOR +
                        "1.- Simular atencion medica " + SEPARADOR +                        
                        "2.- Obtener el tiempo de espera promedio" + SEPARADOR +
                        "3.- Obtener el tiempo de espera maximo por nivel de urgencia" + SEPARADOR +                        
                        "4.- Eliminar simulacion" + SEPARADOR +
                        "5.- Visualizar simulacion" + SEPARADOR +                        
                        "6.- Salir " + SEPARADOR + "Elija una opcion: ");
                opcion = scanner.nextInt();  // Leer la opción del usuario

                // Acciones según la opción seleccionada
                switch (opcion) {
                    case 1 -> { cargarAgendaTrabajo(); visualizarAgendaTrabajo();}                    
                    case 2 -> { obtenerTiempoEsperaPromedio(); }
                    case 3 -> { obtenerTiempoEsperaMaximoPorPrioridad(); }                    
                    case 4 -> { eliminarAgendaTrabajo(); }
                    case 5 -> { visualizarAgendaTrabajo(); }                    
                    case 6 -> { System.out.println("Saliendo del programa..."); return; }  // Salir del programa
                    default -> System.out.println("Opcion no valida. Por favor, elija una opcion correcta(1-6).");
                }
            } while (opcion != 6);  // El bucle continuará hasta que se elija la opción 6
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar obligatoriamente un numero entero como opción elegida." + e.toString());
        } catch (Exception e) {
            System.out.println("Error presentado: " + e.getMessage());
        }
    }
    // Opcion a) Cargar Agenda de Trabajo
    private void cargarAgendaTrabajo() {
        System.out.println("--- CARGAR AGENDA DE TRABAJO ---");
        
        try {
            // Solicitar minutos a simular
            System.out.print("Ingrese el numero de minutos a simular (M): ");
            int minutos = leerEnteroPositivo();
            
            // Solicitar numero maximo de doctores para atencion en el horario
            System.out.print("Ingrese el numero maximo de doctores para atencion (N): ");
            int maxTrabajos = leerEnteroPositivo();
            
            // Confirmar parametros
            System.out.printf("\nConfirmacion de parametros:\n");
            System.out.printf("- Minutos a simular: %d\n", minutos);
            System.out.printf("- Maximo Doctores que atienden: %d\n", maxTrabajos);
            System.out.print("¿Proceder con la simulacion? (s/n): ");
            
            String confirmacion = scanner.next().trim().toLowerCase();
            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                System.out.println("\nEjecutando simulacion...");                
                // Ejecutar la simulacion
                long tiempoInicio = System.currentTimeMillis();
                simulador.cargarAgenda(minutos, maxTrabajos);
                long tiempoFin = System.currentTimeMillis();                
                System.out.printf("Simulacion completada en %d ms\n", (tiempoFin - tiempoInicio));                
            } else
                System.out.println("Simulacion cancelada.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al cargar la programacion: " + e.getMessage());
        }
    }
    
    // Opcion b) Obtener el tiempo de espera promedio
    private void obtenerTiempoEsperaPromedio() {
        System.out.println("--- TIEMPO DE ESPERA PROMEDIO ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una programacion primero.");
            return;
        }        
        double tiempoPromedio = simulador.obtenerTiempoEsperaPromedio();
        System.out.printf("Tiempo de espera promedio general: %.2f minutos\n", tiempoPromedio);
    }
    
    // Opcion c) Obtener el tiempo de espera maximo por nivel de prioridad
    private void obtenerTiempoEsperaMaximoPorPrioridad() {
        System.out.println("--- TIEMPO DE ESPERA MAXIMO POR NIVEL DE URGENCIA ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una agenda primero.");
            return;
        }
        
        ListTDA<Estadisticas> estadisticas = simulador.getEstadisticas();
        boolean hayEstadisticas = false;        
        for (int i = 0; i < estadisticas.size(); i++) {
            if (estadisticas.get(i).tieneTrabajos()) {
                System.out.println(estadisticas.get(i).toString());
                hayEstadisticas = true;
            }
        }
        
        if (!hayEstadisticas)
            System.out.println("No hay estadisticas disponibles para ningun nivel de urgencia.");        
    }
    
    // Opción d) Eliminar actual Simulacion
    private void eliminarAgendaTrabajo() {
        System.out.println("--- ELIMINAR PROGRAMACION DE ATENCION MEDICA ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa para eliminar.");
            return;
        }        
        System.out.print("¿Esta seguro de que desea eliminar la Simulacion de Atencion Medica? (s/n): ");
        String confirmacion = scanner.next().trim().toLowerCase();        
        if (confirmacion.equals("s") || confirmacion.equals("si"))
            simulador.eliminarAgenda();
        else
            System.out.println("Eliminacion cancelada.");        
    }
    
    // Opcion e) Visualizar Agenda de Trabajo
    private void visualizarAgendaTrabajo() {        
        System.out.println(simulador.toString());        
    }
    
    // Leemos un numero entero positivo del usuario
    private int leerEnteroPositivo() {
        while (true) {
            try {
                int numero = scanner.nextInt();                
                if (numero <= 0)
                    throw new IllegalArgumentException("El numero debe ser positivo");                
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un numero entero valido.");                
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.print("Intente nuevamente: ");
        }
    }    
}
