package uni.aed.pc1;
import uni.aed.model.Producto;
import java.util.Scanner;

public class MarketplaceMain {
    private static final Scanner scr=new Scanner(System.in).useDelimiter("\n"); 
    private Venta venta;
    private int N;
   
    public static void main(String[] args){
        MarketplaceMain market = new MarketplaceMain();
        market.inicializa();        
        market.Menu();
    }
    
    private void Menu(){
        int opcion=1;        
        String SEPARADOR="\n";
        try{
            do{
                System.out.print("Gestion de Ventas en un Marketplace "+SEPARADOR+
                        "1.- Cargar productos "+SEPARADOR+
                        "2.- Mostrar productos registrados "+SEPARADOR +
                        "3.- Calcular el producto mas y menos vendido "+SEPARADOR+
                        "4.- Ordenar segun cantidad vendida "+SEPARADOR+
                        "5.- Ordenar segun Precio unitario "+SEPARADOR+
                        "6.- Buscar un producto "+SEPARADOR+
                        "7.- Salir"+ SEPARADOR +
                        "Elija una opcion: ");
                opcion=scr.nextInt();
                switch(opcion)
                {
                    case 1 ->{ agregarProducto(); }
                    case 2 ->{ mostrarProductos();}      
                    case 3 ->{ getMaxMin();}                          
                    case 4 ->{ ordenarProductos(Producto.CANTIDAD);}                              
                    case 5 ->{ ordenarProductos(Producto.PRECIO);}                              
                    case 6 ->{ buscarProducto();}
                     
                    default-> {break;}                
               }
            }while(opcion!=7);
            scr.close();
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }
    }
    private void inicializa(){
        System.out.print("Indique el numero de producto a registrar: "); 
        N=scr.nextInt();
        venta=new Venta(N);        
    }
    
    private void agregarProducto() {        
        System.out.println("Ingrese Datos del producto "+ (venta.size() + 1) +" : ");        
        System.out.print("Descripcion: "); 
        String descripcion=scr.next();
        System.out.print("Cantidad vendida: "); 
        int cantidad = scr.nextInt();
        System.out.print("Precio unitario: "); 
        double precio = scr.nextDouble(); 
        Producto p=new Producto(venta.size()+1,descripcion,cantidad,precio);
        venta.add(p);
    }

    private void mostrarProductos() {
        System.out.println("Productos Registrados:"); 
        System.out.println(venta.toString());         
    }
    
    private void getMaxMin(){                                
        System.out.println("El producto mas vendido es: "+ venta.getMaxVenta().toString());
        System.out.println("El producto menos vendido es: "+ venta.getMinVenta().toString());        
    }
    
    private Producto[] ordenarProductos(int criterioOrden) {
        String criterioDescripcion="";
        switch(criterioOrden)
                {
                    case 0 ->{ criterioDescripcion="cantidad"; }
                    case 1 ->{ criterioDescripcion="precio";} 
                    case 2 ->{ criterioDescripcion="descripcion";} 
                }
        System.out.println("Productos ordenados por "+ criterioDescripcion+": "); 
        Producto[] sortedlist = venta.sort( criterioOrden );        
        for(Producto p: sortedlist)
            System.out.println( p.toString( ) );
        return sortedlist;
    }
    private void buscarProducto() {
        System.out.println("Ingrese Datos del producto a buscar: ");        
        System.out.print("Descripcion: "); 
        String descripcion=scr.next();
        System.out.print("Cantidad vendida: "); 
        int cantidad = scr.nextInt();
        System.out.print("Precio unitario: "); 
        double precio = scr.nextDouble(); 
        Producto p=new Producto(0,descripcion,cantidad,precio);
        Producto[] listaOrdenada=ordenarProductos(Producto.DESCRIPCION);
        int result=venta.search(listaOrdenada,p);
        if(result==-1)
            System.out.println("El producto "+ p.getDescripcion()+" no se ubico en el registro"); 
        else
            System.out.println("El producto "+ listaOrdenada[result].toString()+" se ubico en el registro, en la posicion "+result);                 
    }
    
     
}
   
