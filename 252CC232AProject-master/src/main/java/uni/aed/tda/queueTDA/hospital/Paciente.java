package uni.aed.tda.queueTDA.hospital;

public class Paciente implements Comparable<Paciente> {
    private final int id;                  // Identificador unico del trabajo
    private final int prioridad;           // Prioridad del trabajo (1-5, siendo 1 la mas alta)
    private final int tiempoEjecucion;     // Tiempo necesario para ejecutar el trabajo
    private final int tiempoLlegada;       // Minuto en que llegó el trabajo
    private int tiempoEspera;       // Minuto en que llegó el trabajo
    private int tiempoRestante;            // Tiempo restante de ejecución    
    
    // Creamos un constructor para crear un nuevo trabajo
    public Paciente(int id, int prioridad, int tiempoEjecucion, int tiempoLlegada) {
        if (prioridad < 1 || prioridad > 5)
            throw new IllegalArgumentException("Prioridad debe estar entre 1 y 5");        
        if (tiempoEjecucion < 1)
            throw new IllegalArgumentException("Tiempo de ejecución debe ser positivo");        
        if (tiempoLlegada < 0)
            throw new IllegalArgumentException("Tiempo de llegada no puede ser negativo");
        
        this.id = id;
        this.prioridad = prioridad;
        this.tiempoEjecucion = tiempoEjecucion;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRestante = tiempoEjecucion;
        this.tiempoEspera = 0;
    }
    
    // Definimos los Getters y Setters
    public int getId() { return id; }
    public int getPrioridad() { return prioridad; }
    public int getTiempoEjecucion() { return tiempoEjecucion; }
    public int getTiempoLlegada() { return tiempoLlegada; }
    public int getTiempoRestante() { return tiempoRestante; }

    public int getTiempoEspera() {
        return tiempoEspera;
    }
    
    
    public void setTiempoRestante(int tiempoRestante) {
        if (tiempoRestante < 0)
            throw new IllegalArgumentException("Tiempo restante no puede ser negativo");        
        this.tiempoRestante = tiempoRestante;
    }
    
    // Ejecutamos el trabajo por un minuto
    public boolean ejecutarUnMinuto() {
        if (tiempoRestante <= 0)
            throw new IllegalStateException("El trabajo ya esta completado");        
        tiempoRestante--;
        return tiempoRestante <= 0;
    }
    
    // Calculamos el tiempo de espera del trabajo
    public int calcularTiempoEspera(int tiempoActual) {
        if (tiempoActual < tiempoLlegada)
            throw new IllegalArgumentException("Tiempo actual no puede ser menor que tiempo de llegada");        
        //return tiempoActual - tiempoLlegada;
        tiempoEspera = tiempoActual-tiempoLlegada;
        return tiempoLlegada;
    }
    
    // Implementamos Comparable para ordenar por prioridad
    @Override
    public int compareTo(Paciente otro) {
        // Prioridades mas bajas tienen mayor precedencia
        int comparacionPrioridad = Integer.compare(this.prioridad, otro.prioridad);
        if (comparacionPrioridad != 0)
            return comparacionPrioridad;        
        // Si tienen la misma prioridad, el que llego primero tiene precedencia
        return Integer.compare(this.tiempoLlegada, otro.tiempoLlegada);
    }    
    
    @Override
    public String toString() {
        return String.format("Trabajo[ID:%d, Prioridad:%d, TiempoEjec:%d, TiempoLlegada:%d, Restante:%d, TEspera:%d]",
                id, prioridad, tiempoEjecucion, tiempoLlegada, tiempoRestante, tiempoEspera);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente trabajo = (Paciente) obj;
        return id == trabajo.id;
    }    
}

