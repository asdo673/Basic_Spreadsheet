package uni.aed.model;

public class Producto implements Comparable<Producto>{
    public static final int CANTIDAD = 0;
    public static final int PRECIO = 1;    
    public static final int DESCRIPCION = 2;    
    private static final int LESS = -1;
    private static final int EQUAL = 0;    
    private static final int MORE  = 1;    
    private static int compareAttribute= CANTIDAD;   
    
    private int id;              
    private String descripcion;
    private int cantidad;
    private double precio;
    
    public Producto(int id, String descripcion, int cantidad, double precio) { 
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    
    public int getID() {  
        return id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getCantidad() {
        return cantidad;
    }    
    public void setCantidad(int cantidad){    //setters
        this.cantidad=cantidad; 
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }
    
    public void setCompareAttribute( int attribute ) {
        compareAttribute = attribute;
    }    
    public int compareTo( Producto producto, int attribute ) {
        int comparisonResult=EQUAL;        
        switch(attribute){
            case CANTIDAD->{
                            if (this.cantidad < producto.getCantidad())
                                comparisonResult = LESS;
                            else if (this.cantidad == producto.getCantidad())
                                comparisonResult = EQUAL;
                            else                
                                comparisonResult = MORE;
                        }
            case PRECIO->{
                            if (this.precio < producto.getPrecio())
                                comparisonResult = LESS;
                            else if (this.precio == producto.getPrecio())
                                comparisonResult = EQUAL;
                            else                
                                comparisonResult = MORE;
                        }
            case DESCRIPCION->{                                
                            comparisonResult = this.descripcion.compareTo(producto.getDescripcion());
                            }
        }
        return comparisonResult;
    }        

    @Override
    public int compareTo(Producto o) {
        return compareTo((Producto)o, compareAttribute);
    }    
    
}
