package uni.aed.tda.listTDA.fortunecookie.fin;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class FortuneCookieMain {
    private static FortuneCookieManager fortuna;
    private static Scanner scr=new Scanner(System.in).useDelimiter("\n");    
    private static String archivo;
    
    public static void main(String[] args) {
        FortuneCookieMain fortunaMain=new FortuneCookieMain();                        
        //leemos el archivo de las Galletas de la fortuna
        archivo=fortunaMain.selectFile();
        //validamos si se aperturo exitosamente el archivo
        if(archivo==null){
            System.out.print("No se seleccionó ningún archivo");
            return;
        }
        //creamos una instancia de la clase FortuneCookieManager para realiza las operaciones con las Galletas de la fortuna
        fortuna=new FortuneCookieManager(archivo);
        fortunaMain.menu();        
    }    
    
    private void menu(){
        int opcion=1;        
        String SEPARADOR="\n";
        try{
            do{
                System.out.print("Programa Caso Galletas de la Fortuna"+SEPARADOR+
                "1.- Obtener una Galleta aleatoria"+SEPARADOR+
                "2.- Obtener una Galleta especifica"+SEPARADOR+
                "3.- Añadir Galleta "+SEPARADOR+
                "4.- Eliminar Galleta "+SEPARADOR+            
                "5.- Listar Galletas "+SEPARADOR+                            
                "6.- Salir "+SEPARADOR+
                "Elija una opcion:");
                opcion=scr.nextInt();
                switch(opcion)
                {
                    case 1->{getFortuneCookie();}
                    case 2->{getEspecificFortuneCookie();}
                    case 3->{addFortuneCookie ();}
                    case 4->{deleteFortuneCookie();}
                    case 5->{listFortuneCookie();}                    
                    default-> {break;}                
               }
            }while(opcion!=6);            
        }catch(Exception e){
            System.out.println("Error presentado "+ e.getMessage());
        }finally{
            fortuna.updateFile(archivo);
            scr.close();
        }
    }
    private String selectFile(){
        JFileChooser fileChooser=new JFileChooser();
        //Establece un filtro para mostrar solo los archivos de texto
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto (*.txt)","txt"));
        //Desactivar la opcion "All Files"
        fileChooser.setAcceptAllFileFilterUsed(false);
        //declarando un objeto de tipo File
        File selectedFile=null;
        //Abrir el cuadro de dialogo para seleccionar el archivo de las fortunas
        int returnValue=fileChooser.showOpenDialog(null);
        if(returnValue==JFileChooser.APPROVE_OPTION)
            selectedFile=fileChooser.getSelectedFile();
        if(selectedFile!=null)
            return selectedFile.getAbsolutePath();
        return null;
    }
    
    private void getFortuneCookie(){        
        String rpta;        
        System.out.println("****Galletas de la Fortuna****");
        System.out.println(fortuna.nexFortune());        
        System.out.println("Pulse S para continuar obteniendo galletas de la fortuna, N para salir:");        
        while(true){
            rpta=scr.next();
            if(!rpta.equalsIgnoreCase("N"))
                System.out.println(fortuna.nexFortune());    
            else break;
        }
    }
    private void getEspecificFortuneCookie(){
        System.out.println("Ingrese la ubicacion de la Galleta de la Fortuna que desee obtener:");
        int pos=scr.nextInt();
        System.out.println(fortuna.getFortuneCookie((pos - 1)));
    }
    private void addFortuneCookie(){
        System.out.println("Ingrese una Galleta de la Fortuna");
        String galleta=scr.next();        
        fortuna.addFortuneCookie(galleta);        
    }
    private void deleteFortuneCookie(){
        System.out.println("Ingrese la ubicacion de la Galleta de la Fortuna que desee eliminar:");
        int pos=scr.nextInt();        
        System.out.println("Eliminando fortuna ("+ (pos - 1)+"): "+ fortuna.deleteFortuneCookie(pos));
    }
    
    private void listFortuneCookie(){
        System.out.println("La lista tiene " + fortuna.getSize()+" Galletas de la fortuna");
        System.out.println(fortuna.toString());
        
    }
}
