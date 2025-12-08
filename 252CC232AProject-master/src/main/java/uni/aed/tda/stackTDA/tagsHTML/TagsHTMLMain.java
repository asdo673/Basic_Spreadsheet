package uni.aed.tda.stackTDA.tagsHTML;

import java.io.IOException;

public class TagsHTMLMain {
     public static void main(String[] args){
        String nomArchivo="src\\main\\java\\uni\\aed\\tda\\stackTDA\\tagsHTML\\index.html";
        try{
            HTMLRevisaEtiq revisor=new HTMLRevisaEtiq(nomArchivo);
            if(revisor.esValido())
                System.out.println("El Archivo html es valido ");
            else
                System.out.println("El Archivo html es invalido ");
            
        }catch(IOException e){
            System.out.println("Error: "+ e.getMessage());
        }
        
    }
}
