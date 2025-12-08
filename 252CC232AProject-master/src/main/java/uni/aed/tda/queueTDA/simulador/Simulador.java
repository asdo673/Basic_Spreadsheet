package uni.aed.tda.queueTDA.simulador;

import java.util.Random;
import uni.aed.tda.arraylistTDA.ArrayListTDA;
import uni.aed.tda.listTDA.ListTDA;
import uni.aed.tda.queueTDA.ArrayQueueTDA;
import uni.aed.tda.queueTDA.PriorityQueueTDA;
import uni.aed.tda.queueTDA.QueueTDA;

public class Simulador {
    public static final int TAMANIO_INICIAL_COLA=10;
    //Definimos constantes para la simulacion
    public static final int MIN_PRIORIDAD=1;
    public static final int MAX_PRIORIDAD=5;
    public static final int MIN_TIEMPO_EJECUCION=1;
    public static final int MAX_TIEMPO_EJECUCION=10;
    public static final String SEPARADOR="\n";
    public static final String TITULO_REPORTE="---VISUALIZACION DE AGENDA DE TRABAJO---";
    
    private final QueueTDA<Trabajo> colaEspera;
    private final QueueTDA<Trabajo> trabajosEjecutando;
    private final ListTDA<Estadistica> estadisticas;
    private final Random generador;
    private int tiempoSimulacion;
    private int tiempoSimulacionExtra=0;
    private int maxTrabajosSimultaneos;
    private int contadorTrabajos;
    private boolean simulacionActiva;
    StringBuilder reporte;

    public Simulador() {
        this.colaEspera =new PriorityQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.trabajosEjecutando= new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        this.generador=new Random();
        this.contadorTrabajos=0;
        this.simulacionActiva=false;
        this.reporte= new StringBuilder();
        
        this.estadisticas= new ArrayListTDA<>(MAX_PRIORIDAD);
        for(int i=MIN_PRIORIDAD;i<=MAX_PRIORIDAD;i++)
            this.estadisticas.add(i-1, new Estadistica(i));
        this.reporte.append(TITULO_REPORTE+SEPARADOR);
    }
    
    public void cargarAgenda(int minutos,int maxTrabajos){
        if(minutos<=0 || maxTrabajos<=0)
            throw new IllegalArgumentException("Los parametros deben ser positivos");
        //Reiniciar estado de la simulacion
        reiniciarSimulacion();
        this.tiempoSimulacion=minutos;              //M
        this.maxTrabajosSimultaneos=maxTrabajos;    //N
        this.simulacionActiva=true;
        //Ejecutar la simulacion minuto a minuto
        for(int minutoActual=1;minutoActual<=tiempoSimulacion;minutoActual++){
            procesarMinuto(minutoActual);
            setReporte();
        }
        //procesar trabajos restantes en la cola, al final de la simulacion
        procesarTrabajosRestantes();
        this.reporte.append(SEPARADOR+"Simulacion completada: ")
                .append(minutos).append(" minutos,")
                .append(maxTrabajos)
                .append(" trabajos simultaneos maximo"+SEPARADOR);        
    }
    //Procesamos los eventos de un minuto especifico
    private void procesarMinuto(int minutoActual){
        //1.- Llega un nuevo trabajo cada minuto
        generarNuevoTrabajo(minutoActual);
        //2.- Ejecutar trabajos actualmente en proceso
        ejecutarTrabajosActivos(minutoActual);
        //3.- Asignar nuevos trabajos si hay capacidad disponible
        asignarNuevosTrabajos();
    }
    // Generamos un nuevo trabajo aleatorio que llega en el minuto actual
    private void generarNuevoTrabajo(int minutoLlegada) {
        // Generar prioridad aleatoria (1-5)
        int prioridad = generador.nextInt(MAX_PRIORIDAD - MIN_PRIORIDAD + 1) + MIN_PRIORIDAD;        
        
        // Generar tiempo de ejecución aleatorio (1-10 minutos)
        int tiempoEjecucion = generador.nextInt(MAX_TIEMPO_EJECUCION - MIN_TIEMPO_EJECUCION + 1) + MIN_TIEMPO_EJECUCION;        
        
        // Crear y agregar el nuevo trabajo a la cola de espera
        Trabajo nuevoTrabajo = new Trabajo(contadorTrabajos++, prioridad, 
                                          tiempoEjecucion, minutoLlegada);
        colaEspera.enqueue(nuevoTrabajo);                
    }
    
    // Ejectuamos todos los trabajos actualmente en proceso por un minuto
    private void ejecutarTrabajosActivos(int minutoActual) {
        ArrayQueueTDA<Trabajo> trabajosTemporales = new ArrayQueueTDA<>(TAMANIO_INICIAL_COLA);
        
        // Procesar cada trabajo en ejecucion
        while (!trabajosEjecutando.isEmpty()) {
            Trabajo trabajo = trabajosEjecutando.dequeue();
            boolean completado = trabajo.ejecutarUnMinuto();
            
            if (completado) {
                // Trabajo completado: registrar estadisticas
                int tiempoEspera = trabajo.calcularTiempoEspera(minutoActual);                
                estadisticas.get(trabajo.getPrioridad()-1).registrarTiempoEspera(tiempoEspera);
            } else
                // Trabajo no completado: mantener en ejecución
                trabajosTemporales.enqueue(trabajo);            
        }
        
        // Restaurar trabajos no completados
        while (!trabajosTemporales.isEmpty()) {
            trabajosEjecutando.enqueue(trabajosTemporales.dequeue());
        }
    }
    
    // Asignamos nuevos trabajos de la cola de espera si hay capacidad disponible
    private void asignarNuevosTrabajos() {
        // Mientras haya capacidad y trabajos en espera
        while (trabajosEjecutando.size() < maxTrabajosSimultaneos && !colaEspera.isEmpty()) {
            Trabajo trabajo = colaEspera.dequeue();
            trabajosEjecutando.enqueue(trabajo);
        }
    }
    
    // Procesamos todos los trabajos restantes en la cola al final de la simulación
    private void procesarTrabajosRestantes() {
        //int minutoExtra = tiempoSimulacion + 1;
        tiempoSimulacionExtra= tiempoSimulacion + 1;
        
        // Continuar procesando hasta que no queden trabajos
        while (!colaEspera.isEmpty() || !trabajosEjecutando.isEmpty()) {
            // Ejecutar trabajos activos
            ejecutarTrabajosActivos(tiempoSimulacionExtra);
            
            // Asignar nuevos trabajos si hay capacidad
            asignarNuevosTrabajos();
            setTiempoSimulacionExtra(tiempoSimulacionExtra);
            setReporte(); 
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
        if (!simulacionActiva) {
            return 0.0;
        }
        
        int totalTrabajos = 0;
        int tiempoTotal = 0;
        
        for (int i = MIN_PRIORIDAD; i <= MAX_PRIORIDAD; i++) {
            Estadistica stats = estadisticas.get(i-1);
            totalTrabajos += stats.getTotalTrabajos();
            tiempoTotal += stats.getTiempoEsperaTotal();
        }        
        
        return totalTrabajos > 0 ? (double) tiempoTotal / totalTrabajos : 0.0;
    }

    public ListTDA<Estadistica> getEstadistica() {
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
        sb.append("--- ESTADO ACTUAL DE LA AGENDA DE TRABAJO ---\n");
        
        if (!simulacionActiva) {
            sb.append("No hay simulación activa. Cargue una agenda primero.\n");
            return sb.toString();
        }
        
        // Información general
        sb.append(String.format("Simulación: %d minutos, maximo %d trabajos simultaneos\n", 
                               tiempoSimulacion, maxTrabajosSimultaneos));
        sb.append(String.format("Tiempo de espera promedio general: %.2f minutos\n\n", 
                               obtenerTiempoEsperaPromedio()));
        
        // Estadísticas por prioridad
        sb.append("ESTADISTICAS POR NIVEL DE PRIORIDAD:\n");
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

    public void getEstadisticaPorPrioridad() {     
        reporte.append(SEPARADOR).append("Traza Estadistica Trabajos Concluidos o en Ejecucion\n(No incluye los Trabajos en Espera): ").append(SEPARADOR);
        for (int i = 0; i < estadisticas.size(); i++)
            reporte.append(estadisticas.get(i).toString()).append(SEPARADOR);                            
    }
    
    
    private void setReporte(){        
        reporte.append("\nTRABAJOS COLA DE ESPERA:\n").append(colaEspera.toString(SEPARADOR));
        reporte.append("\nTRABAJOS EN EJECUCION:\n").append(trabajosEjecutando.toString(SEPARADOR));
        if (getTiempoSimulacionExtra()> getTiempoSimulacion()){
            reporte.append("\nTiempo de Simuluacion Extra:\n").append(getTiempoSimulacionExtra());
            getEstadisticaPorPrioridad();
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
