package uni.aed.tda.queueTDA.simulador;

public class Estadistica {
    private final int prioridad;    //Nivel de prioridad: 1-5
    private int totalTrabajos;      //Total trabajos procesados
    private int tiempoEsperaTotal;  //Sumarizar todos los tiempos de espera para cada prioridad
    private int tiempoEsperaMaximo; //Determinar el tiempo de espera maximo para cada prioridad

    public Estadistica(int prioridad) {
        //validar parametros de entrada
        if(prioridad<1 || prioridad>5)
            throw new IllegalArgumentException("Prioridad debe ser entre 1 y 5");
        
        this.prioridad = prioridad;
        this.totalTrabajos=0;
        this.tiempoEsperaTotal=0;
        this.tiempoEsperaMaximo=0;
    }

    public int getPrioridad() {return prioridad;}
    public int getTotalTrabajos() {return totalTrabajos;}
    public int getTiempoEsperaTotal() {return tiempoEsperaTotal;}
    public int getTiempoEsperaMaximo() {return tiempoEsperaMaximo;}
    
    //Registramos el tiempo de espera de un trabajo procesado
    public void registrarTiempoEspera(int tiempoEspera){
        if(tiempoEspera<0)
            throw new IllegalArgumentException("tiempo de espera no puede ser negativo");        
        tiempoEsperaTotal+=tiempoEspera;//acumulamos el tiempo de espera
        totalTrabajos++;    //incrementamos el numero de trabajos procesado
        //Actualizar el tiempo de espera maximo, si hubiere
        if(tiempoEspera>tiempoEsperaMaximo)
            tiempoEsperaMaximo=tiempoEspera;
    }
    //Calculo del tiempo de Espera promedio
    public double calcularTiempoEsperaPromedio(){
        if(totalTrabajos==0)
            return 0.0;
        return (double)tiempoEsperaTotal/totalTrabajos;
    }
    
    //verificar si existen trabajos registrados para esta prioridad
    public boolean tieneTrabajos(){
        return totalTrabajos>0;
    }
    //Reiniciar parametros estadisticos
    public void reiniciar(){
        this.totalTrabajos=0;
        this.tiempoEsperaTotal=0;
        this.tiempoEsperaMaximo=0;     
    }

    @Override
    public String toString() {
        if(!tieneTrabajos())
            return String.format("Prioridad %d: Sin trabajos procesados", prioridad);
        return String.format("Prioridad %d: %d Trabajos, Promedio: %.2f min, Maximo: %d min"
                , prioridad, totalTrabajos,calcularTiempoEsperaPromedio(),tiempoEsperaMaximo);
    }
    
    
    
}
