package uni.aed.tda.queueTDA.simulador;

public class Trabajo implements Comparable<Trabajo> {
    private final int id;               //identificador unico del trabajo(job)
    private final int prioridad;        //prioridad del trabajo (1:5, siendo 1 la mas alta prioridad)asignados aleatoriamente
    private final int tiempoEjecucion;  //valores entre 1:10 minutos de ejecucion, asignados aleatoriamente
    private final int tiempoLlegada;    //en que minuto llega a la cola
    private int tiempoRestante;        //tiempo restante de ejecucion

    public Trabajo(int id, int prioridad, int tiempoEjecucion, int tiempoLlegada) {
        //validar parametros de entrada
        if(prioridad<1 || prioridad>5)
            throw new IllegalArgumentException("Prioridad debe ser entre 1 y 5");
        if(tiempoEjecucion<1)
            throw new IllegalArgumentException("tiempo de ejecucion debe ser entre 1 y 10");
        if(tiempoLlegada<0)
            throw new IllegalArgumentException("tiempo de llegada no puede ser negativo");        
        
        this.id = id;
        this.prioridad = prioridad;
        this.tiempoEjecucion = tiempoEjecucion;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRestante= tiempoEjecucion;
    }

    public int getId() {return id;}
    public int getPrioridad() {return prioridad;}
    public int getTiempoEjecucion() {return tiempoEjecucion;}
    public int getTiempoLlegada() {return tiempoLlegada;}
    public int getTiempoRestante() {return tiempoRestante;}    
    
    public void setTiempoRestante(int tiempoRestante){
        if(tiempoRestante<0)
            throw new IllegalArgumentException("tiempo restante no puede ser negativo");      
        this.tiempoRestante=tiempoRestante;        
    }
    public boolean ejecutarUnMinuto(){
        if(tiempoRestante<=0)
            throw new IllegalArgumentException("El trabajo ya ejecuto todos sus minutos asignados");      
        tiempoRestante--;
        return tiempoRestante<=0;        
    }
    
    // Calculamos el tiempo de espera del trabajo
    public int calcularTiempoEspera(int tiempoActual) {
        if (tiempoActual < tiempoLlegada)
            throw new IllegalArgumentException("Tiempo actual no puede ser menor que tiempo de llegada");        
        return tiempoActual - tiempoLlegada;
    }

    @Override
    public int compareTo(Trabajo o) {
        int comparacionPrioridad=Integer.compare(this.prioridad, o.prioridad);
        if(comparacionPrioridad!=0)
            return comparacionPrioridad;
        return Integer.compare(this.tiempoLlegada, o.tiempoLlegada);
    }

    @Override
    public String toString() {
        return String.format("Trabajo[ID:%d, Prioridad:%d, TEjecucion:%d, TLlegada:%d, TRestante:%d]"
        ,id,prioridad,tiempoEjecucion,tiempoLlegada,tiempoRestante);
    }
        
}
