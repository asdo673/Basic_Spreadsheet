package uni.aed.model;

public class Bicicleta implements Comparable<Bicicleta>{
    private String nomPropietario;

    public Bicicleta(String nomPropietario) {
        this.nomPropietario = nomPropietario;
    }

    public void setNomPropietario(String nomPropietario) {
        this.nomPropietario = nomPropietario;
    }

    public String getNomPropietario() {
        return nomPropietario;
    }

    @Override
    public String toString() {
        return "{" + nomPropietario + '}';
    }
    
    @Override
    public int compareTo(Bicicleta o) {
        return this.nomPropietario.compareTo(o.getNomPropietario());
    }
    
}
