package uni.aed.tda.queueTDA.hospital;

import uni.aed.tda.queueTDA.PriorityQueueTDA;
import uni.aed.tda.queueTDA.ArrayQueueTDA;
import java.util.Random;
import uni.aed.tda.arraylistTDA.ArrayListTDA;
import uni.aed.tda.listTDA.ListTDA;

public class SimuladorHospital {
    private static final int TAMANIO_INICIAL_COLA = 10;
    private final PriorityQueueTDA<Paciente> colaEspera;             // Cola de trabajos esperando
    private final ArrayQueueTDA<Paciente> trabajosEjecutando;        // Trabajos actualmente ejecutandose    
    ListTDA<Estadisticas> estadisticas;
    private final Random generador;                                 // Generador de numeros aleatorios
    private int tiempoSimulacion;                                   // Minutos totales a simular
    private int tiempoSimulacionExtra=0;
    private int maxTrabajosSimultaneos;                             // Maximo trabajos ejecutandose simultaneamente
    private int contadorTrabajos;                                   // Contador para IDs unicos de trabajos
    private boolean simulacionActiva;                               // Estado de la simulacion
    
    // Definimos las constantes para la simulación
    private static final int MIN_PRIORIDAD = 1;
    private static final int MAX_PRIORIDAD = 5;
    private static final int MIN_TIEMPO_EJECUCION = 1;
    private static final int MAX_TIEMPO_EJECUCION = 10;
    private static final String SEPARADOR = "\n";
    private static final String TITULO_REPORTE = "--- VISUALIZACION DE LA AGENDA DE TRABAJO ---";
    StringBuilder reporte;
    
    // constructor del simulador
    public SimuladorHospital() {
        this.colaEspera = new PriorityQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.trabajosEjecutando = new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.generador = new Random();
        this.contadorTrabajos = 0;//1
        this.simulacionActiva = false;
        reporte = new StringBuilder();
        
        // Inicializar estadisticas para cada nivel de prioridad (1-5)
        this.estadisticas= new ArrayListTDA<>(MAX_PRIORIDAD + 1);        
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++)
            estadisticas.add(i-1, new Estadisticas(i));
        
        reporte.append(TITULO_REPORTE+SEPARADOR);
    }
    
    // Cargamos y ejecutamos una nueva simulacion de agenda
    public void cargarAgenda(int minutos, int maxTrabajos) {
        if (minutos <= 0 || maxTrabajos <= 0)
            throw new IllegalArgumentException("Los parametros deben ser positivos");        
        
        // Reiniciar estado de la simulacion        
        reiniciarSimulacion();
        
        this.tiempoSimulacion = minutos;
        this.maxTrabajosSimultaneos = maxTrabajos;
        this.simulacionActiva = true;
        
        // Ejecutar la simulación minuto a minuto
        for (int minutoActual = 1; minutoActual <= tiempoSimulacion; minutoActual++) {
            procesarMinuto(minutoActual);    
            setReporte();     
        }
        
        // Procesar trabajos restantes en la cola al final de la simulacion
        procesarTrabajosRestantes();   
        reporte.append(SEPARADOR + "Simulacion completada: ").append(minutos).append(" minutos, ").append(maxTrabajos).append(" trabajos simultaneos maximo"+SEPARADOR);        
    }
    
    // Procesamos los eventos de un minuto especifico
    private void procesarMinuto(int minutoActual) {
        // 1. Llega un nuevo trabajo cada minuto
        generarNuevoTrabajo(minutoActual);
        
        // 2. Ejecutar trabajos actualmente en proceso
        ejecutarTrabajosActivos(minutoActual);
        
        // 3. Asignar nuevos trabajos si hay capacidad disponible
        asignarNuevosTrabajos(minutoActual);
        
        // 4. Actualiza tiempo de Espera
        actualizaTiempoEspera(minutoActual);
    }
    
    // Generamos un nuevo trabajo aleatorio que llega en el minuto actual
    private void generarNuevoTrabajo(int minutoLlegada) {
        // Generar prioridad aleatoria (1-5)
        int prioridad = generador.nextInt(MAX_PRIORIDAD - MIN_PRIORIDAD + 1) + MIN_PRIORIDAD;        
        
        // Generar tiempo de ejecución aleatorio (1-10 minutos)
        int tiempoEjecucion = generador.nextInt(MAX_TIEMPO_EJECUCION - MIN_TIEMPO_EJECUCION + 1) + MIN_TIEMPO_EJECUCION;        
        
        // Crear y agregar el nuevo trabajo a la cola de espera
        Paciente nuevoTrabajo = new Paciente(contadorTrabajos++, prioridad, 
                                          tiempoEjecucion, minutoLlegada);
        colaEspera.enqueue(nuevoTrabajo);                
    }
    
    // Ejectuamos todos los trabajos actualmente en proceso por un minuto
    private void ejecutarTrabajosActivos(int minutoActual) {
        ArrayQueueTDA<Paciente> trabajosTemporales = new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        
        // Procesar cada trabajo en ejecucion
        while (!trabajosEjecutando.isEmpty()) {
            Paciente trabajo = trabajosEjecutando.dequeue();
            boolean completado = trabajo.ejecutarUnMinuto();
            
            if (completado)
                estadisticas.get(trabajo.getPrioridad()-1).registrarTiempoEspera(trabajo.getTiempoEspera());
            else
                // Trabajo no completado: mantener en ejecución
                trabajosTemporales.enqueue(trabajo);            
        }
        
        // Restaurar trabajos no completados
        while (!trabajosTemporales.isEmpty())
            trabajosEjecutando.enqueue(trabajosTemporales.dequeue());        
    }
    
    // Asignamos nuevos trabajos de la cola de espera si hay capacidad disponible
    private void asignarNuevosTrabajos(int minutoActual) {
        // Mientras haya capacidad y trabajos en espera
        while (trabajosEjecutando.size() < maxTrabajosSimultaneos && !colaEspera.isEmpty())            
            trabajosEjecutando.enqueue(colaEspera.dequeue());
    }
    
    private void actualizaTiempoEspera(int minutoActual) {
        Object[] trabajosEnColaEspera=colaEspera.toArray();
        for( Object o : trabajosEnColaEspera){
            Paciente trabajo=(Paciente)o;
            //calculo del tiempo que espera el trabajo hasta el momento que es asignado para su ejecucion
            trabajo.calcularTiempoEspera(minutoActual);                            
        }
    }
    
    // Procesamos todos los trabajos restantes en la cola al final de la simulación
    private void procesarTrabajosRestantes() {
        //int minutoExtra = tiempoSimulacion + 1;
        tiempoSimulacionExtra= tiempoSimulacion + 1;        
        // Continuar procesando hasta que no queden trabajos
        while (!colaEspera.isEmpty() || !trabajosEjecutando.isEmpty()) {
            setReporte(); 
            // Ejecutar trabajos activos
            ejecutarTrabajosActivos(tiempoSimulacionExtra);            
            // Asignar nuevos trabajos si hay capacidad
            asignarNuevosTrabajos(tiempoSimulacionExtra);
            // Actualiza tiempo de Espera
            actualizaTiempoEspera(tiempoSimulacionExtra);
            setTiempoSimulacionExtra(tiempoSimulacionExtra);            
            tiempoSimulacionExtra++;            
        }
    }

    public void setTiempoSimulacionExtra(int tiempoSimulacionExtra) {
        this.tiempoSimulacionExtra = tiempoSimulacionExtra;
    }

    public int getTiempoSimulacionExtra() {
        return tiempoSimulacionExtra;
    }
    
    
    // Obtenemos el tiempo de espera promedio de todos los trabajos procesados
    public double obtenerTiempoEsperaPromedio() {
        if (!simulacionActiva)
            return 0.0;        
        
        int totalTrabajos = 0;
        int tiempoTotal = 0;
        
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++) {
            Estadisticas stats = estadisticas.get(i-1);
            totalTrabajos += stats.getTotalTrabajos();
            tiempoTotal += stats.getTiempoEsperaTotal();
        }        
        
        return totalTrabajos > 0 ? (double) tiempoTotal / totalTrabajos : 0.0;
    }

    public ListTDA<Estadisticas> getEstadisticas() {
        return estadisticas;
    }
    
    // Eliminamos la agenda de trabajo actual y reinicia el simulador
    public void eliminarAgenda() {
        reiniciarSimulacion();
        System.out.println("Agenda de trabajo eliminada y simulador reiniciado");
    }
    
    // Reiniciamos el estado completo del simulador
    private void reiniciarSimulacion() {
        // Limpiar colas
        colaEspera.clear();
        trabajosEjecutando.clear();
        
        // Reiniciar estadisticas                
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++)
            estadisticas.get(i-1).reiniciar();        
        
        // Reiniciar contadores
        contadorTrabajos = 1;
        simulacionActiva = false;
        tiempoSimulacion = 0;
        maxTrabajosSimultaneos = 0;
        
        tiempoSimulacionExtra = 0;
        reporte.setLength(0);        
    }
    
    // Visualizamos el estado actual de la agenda de trabajo
    public String visualizarAgenda() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ESTADO ACTUAL DE LA ATENCION MEDICA ---\n");
        
        if (!simulacionActiva) {
            sb.append("No hay simulacion activa. Cargue una la programacion medica primero.\n");
            return sb.toString();
        }
        
        // Información general
        sb.append(String.format("Simulación: %d minutos, máximo %d trabajos simultáneos\n", 
                               tiempoSimulacion, maxTrabajosSimultaneos));
        sb.append(String.format("Tiempo de espera promedio general: %.2f minutos\n\n", 
                               obtenerTiempoEsperaPromedio()));
        
        // Estadísticas por prioridad
        sb.append("ESTADISTICAS POR NIVEL DE URGENCIA:\n");
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++) {
            sb.append(estadisticas.get(i-1).toString()).append("\n");
        }
        
        // Estado de las colas
        sb.append(String.format("\nTRABAJOS EN ESPERA: %d\n", colaEspera.size()));
        sb.append(String.format("TRABAJOS EJECUTANDOSE: %d\n", trabajosEjecutando.size()));
        
        return sb.toString();
    }    

    public int getTiempoSimulacion() {
        return tiempoSimulacion;
    }

    public void getEstadisticasPorPrioridad() {     
        reporte.append("\nTraza Estadistica Atencion Medica Concluida\n(No incluye los Trabajos en Ejecucion y Espera):\n");
        for (int i = 0; i < estadisticas.size(); i++)
            reporte.append(estadisticas.get(i).toString()).append(SEPARADOR);                            
    }    
    
    private void setReporte(){
        reporte.append("\nTRABAJOS COLA DE ESPERA:\n").append(colaEspera.toString(SEPARADOR));
        reporte.append("\nTRABAJOS EN EJECUCION:\n").append(trabajosEjecutando.toString(SEPARADOR));
        if (getTiempoSimulacionExtra()> getTiempoSimulacion()){
            reporte.append("\nTiempo de Simulacion Extra:\n").append(getTiempoSimulacionExtra());
            getEstadisticasPorPrioridad();
        }
    }

    @Override
    public String toString() {
        return reporte.toString();
    }
    
    // Verificamos si hay una simulacion activa
    public boolean isSimulacionActiva() {
        return simulacionActiva;
    }
}


