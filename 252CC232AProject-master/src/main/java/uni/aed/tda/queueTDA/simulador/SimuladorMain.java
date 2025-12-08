package uni.aed.tda.queueTDA.simulador;
/*Caso 1 (Pag 1097-Ej 9 C.Thomas Wu)
Escriba un programa de simulación de agenda de trabajo. En las buenas épocas, mucho antes de la revolución de las PC, los estudiantes de ciencias de la computación escribían programas con el uso de máquinas perforadoras de tarjetas y los ejecutaban en las computadoras centrales. Un programa se registraba sobre una pila de tarjetas perforadas, una sentencia por tarjeta perforada, Para ejecutar sus programas, los estudiantes debían entregar el programa (la pila de tarjetas) al operador de la computadora. Un programa se llamaba trabajo (Job). A los estudiantes que tomaban un curso de computación se les asignaba cierto número de unidades (un tipo de dinero virtual) y se les cargaban N unidades para ejecutar un programa. La cantidad real cargada se determina por la prioridad que los estudiantes asignaban a sus programas. Para este ejercicio, suponga que las prioridades varían de 1 a 5, con 1 como la más alta.
Escriba un programa en java que simule la agenda de trabajo. Las entradas al programa con M, que es el número de minutos a simular y N que es el número de trabajos que la computadora puede ejecutar concurrentemente. Trate a cada minuto como un evento discreto. Suponga que un solo trabajo llega cada minuto. Cuando llega un trabajo, asigne en forma aleatoria su prioridad y el número de minutos que necesita para ejecutarse. Elija cualquier entero entre 1 minuto y 10 minutos, inclusive, para el tiempo de ejecución. Un trabajo que llega se coloca en una cola de prioridad. En cada evento discreto, si el número de trabajos asignado a la computadora esta abajo del número máximo de trabajos que puede manejar la computadora, remueve trabajos de la cola de prioridad y los asigna a la computadora.
Al final de la simulación, obtenga el tiempo de espera promedio y el tiempo de espera máximo para cada nivel de prioridad. Vea el ejercicio 8 acerca de como manejar la situación en la que la cola no este vacía al final de los minutos simulados.
*/
import java.util.InputMismatchException;
import java.util.Scanner;
import uni.aed.tda.listTDA.ListTDA;

public class SimuladorMain {
    private final Simulador simulador = new Simulador();                // Instancia del simulador de agenda
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");      // Scanner para entrada de usuario        
    public static void main(String[] args) {
        SimuladorMain simuladorMain=new SimuladorMain();
        simuladorMain.menu();
    }
    
    private void menu() {
        String SEPARADOR = "\n";
        int opcion = 5;
        try {            
            do {                
                System.out.println("--- SIMULADOR DE AGENDA DE TRABAJO ---");
                System.out.print("--- MENU DE OPCIONES ---" + SEPARADOR +
                        "1.- Cargar Agenda de Trabajo " + SEPARADOR +                        
                        "2.- Obtener el tiempo de espera promedio" + SEPARADOR +
                        "3.- Obtener el tiempo de espera maximo por nivel de prioridad" + SEPARADOR +                        
                        "4.- Eliminar actual Agenda de Trabajo" + SEPARADOR +
                        "5.- Visualizar Agenda de Trabajo" + SEPARADOR +                        
                        "6.- Salir " + SEPARADOR + "Elija una opcion: ");
                opcion = scanner.nextInt();  // Leer la opción del usuario

                // Acciones según la opción seleccionada
                switch (opcion) {
                    case 1 -> { cargarAgendaTrabajo(); visualizarAgendaTrabajo();}                    
                    case 2 -> { obtenerTiempoEsperaPromedio(); }
                    case 3 -> { obtenerTiempoEsperaMaximoPorPrioridad(); }                    
                    case 4 -> { eliminarAgendaTrabajo(); }
                    case 5 -> { visualizarAgendaTrabajo(); }                    
                    case 6 -> { System.out.println("Saliendo del programa..."); return; }  
                    default -> System.out.println("Opcion no valida. Por favor, elija una opcion correcta(1-6).");
                }
            } while (opcion != 6);  
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar obligatoriamente un numero entero como opcion elegida." + e.toString());
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
            
            // Solicitar numero maximo de trabajos simultaneos
            System.out.print("Ingrese el numero maximo de trabajos simultaneos (N): ");
            int maxTrabajos = leerEnteroPositivo();
            
            // Confirmar parametros
            System.out.printf("\nConfirmacion de parametros:\n");
            System.out.printf("- Minutos a simular: %d\n", minutos);
            System.out.printf("- Trabajos simultaneos maximo: %d\n", maxTrabajos);
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
            System.out.println("Error inesperado al cargar la agenda: " + e.getMessage());
        }
    }
    
    // Opcion b) Obtener el tiempo de espera promedio
    private void obtenerTiempoEsperaPromedio() {
        System.out.println("--- TIEMPO DE ESPERA PROMEDIO ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una agenda primero.");
            return;
        }        
        double tiempoPromedio = simulador.obtenerTiempoEsperaPromedio();
        System.out.printf("Tiempo de espera promedio general: %.2f minutos\n", tiempoPromedio);
    }
    
    // Opcion c) Obtener el tiempo de espera maximo por nivel de prioridad
    private void obtenerTiempoEsperaMaximoPorPrioridad() {
        System.out.println("--- TIEMPO DE ESPERA MAXIMO POR NIVEL DE PRIORIDAD ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa. Cargue una agenda primero.");
            return;
        }
        
        ListTDA<Estadistica> estadisticas = simulador.getEstadistica();
        boolean hayEstadistica = false;        
        for (int i = 0; i < estadisticas.size(); i++) {
            if (estadisticas.get(i).tieneTrabajos()) {
                System.out.println(estadisticas.get(i).toString());
                hayEstadistica = true;
            }
        }
        
        if (!hayEstadistica)
            System.out.println("No hay estadisticas disponibles para ningun nivel de prioridad.");        
    }
    
    // Opción d) Eliminar actual Agenda de Trabajo
    private void eliminarAgendaTrabajo() {
        System.out.println("--- ELIMINAR AGENDA DE TRABAJO ---");        
        if (!simulador.isSimulacionActiva()) {
            System.out.println("No hay simulacion activa para eliminar.");
            return;
        }        
        System.out.print("¿Esta seguro de que desea eliminar la agenda actual? (s/n): ");
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
