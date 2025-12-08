package uni.aed.tda.linkedlistTDA.contrasenia;
import java.util.Scanner;

public class ValidadorContraseniaMain {    
    ValidadorContrasenia validador = new ValidadorContrasenia();
    Scanner scan = new Scanner(System.in).useDelimiter("\n");    

    public static void main(String[] args) {     
        try{
            ValidadorContraseniaMain validadorMain = new ValidadorContraseniaMain();
            validadorMain.menu();
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }
        
    }
    private void menu(){
        boolean salir = false;                
        while (!salir) {
            System.out.println("Programa Validador de Contraseñas");
            System.out.println("a) Registrar Contraseña  ");
            System.out.println("b) Validar Contraseña ");                        
            System.out.println("c) visualizar lista ");
            System.out.println("d) salir ");
            System.out.println("Elija una opción del menu: ");    
            String opcion = scan.next();
            switch (opcion) {                
                case "a" -> registrar();                
                case "b" -> validar();                                    
                case "c" -> visualizar();
                case "d" -> salir = true;
            }
        }
        scan.close();
    }
    private void registrar(){
        System.out.print("Ingrese su Contraseña ");//Abc123!Abc,Abc123!AbcXYZ@
        String contrasenia = scan.next();
        validador.registrarContrasenia(contrasenia);
    }
    
    private void validar(){                
        validador.validarContrasenia();
        System.out.println(validador.toString()+": "+validador.getValidacion());        
    }
    
    private void visualizar() {            
        System.out.println("Visualizando el contenido de la lista");
        System.out.println(validador.toString());
    }
    
    
}
