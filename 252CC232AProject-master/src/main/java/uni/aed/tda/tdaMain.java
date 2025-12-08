package uni.aed.tda;

public class tdaMain {
    public static void main(String[] args){
        tdaMain tda=new tdaMain();
        tda.testCasillero1();
        tda.testCasillero2();
        tda.testCasillero4();        
    }
    private void testCasillero1(){
        Casillero1<String> casilleroUno=new Casillero1<>();
        Casillero1<Integer> casilleroDos=new Casillero1<>();
        casilleroUno.setContenido("Mochila");
        casilleroDos.setContenido(100);
        System.out.println("El casillero1 tiene el contenido "+casilleroUno.getContenido());
        System.out.println("El casillero2 tiene el contenido "+casilleroDos.getContenido());                
    }
    private void testCasillero2(){
        Casillero2<String, Integer> casillero   =  new Casillero2<>("Mochila",100);
        Casillero2<String, String> saludo   =   new Casillero2<>("Hola","AED");
        
        System.out.println("El casillero tiene el contenido "+casillero.getContenido1()+"-"+casillero.getContenido2());
        System.out.println("El casillero tiene el contenido "+saludo.getContenido1()+" "+saludo.getContenido2());
    }
    private void testCasillero4(){
        Casillero4<Number> numerico1=new Casillero4<>(100);
        Casillero4<Double> numerico2=new Casillero4<>(10.0);
        Casillero4<Integer> numerico3=new Casillero4<>(10);
        System.out.println("El casillero4 tiene el contenido numerico: "+
                numerico1.getContenido()+"|"+
                numerico2.getContenido()+"|"+
                numerico3.getContenido());
    }
}
