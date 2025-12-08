package uni.aed.tda.listTDA.fortunecookie.fin;

import java.util.Random;
import uni.aed.tda.listTDA.ListTDA;

public class FortuneCookieManager {
    private ListTDA<String> fortunas;    
    FortuneCookieFile fortuneCookieFile;

    public FortuneCookieManager(String nombreArchivo) {
        fortuneCookieFile=new FortuneCookieFile(nombreArchivo);
        fortunas=fortuneCookieFile.getLista();        
    }
    
    public String nexFortune() {
        Random random = new Random();
        int index = random.nextInt(fortunas.size());
        return fortunas.get(index);        
    }
    
    public void addFortuneCookie(String fortune){
        fortunas.add(fortune);
    }
    
    public void deleteFortuneCookie(String fortune){
        fortunas.delete(fortune);
    }
    public String deleteFortuneCookie(int index){
        return fortunas.delete(index);
    }
    
    public String getFortuneCookie(int index){
        return fortunas.get(index);
    }
    
    public int getSize(){
        return fortunas.size();
    }
    
    public void updateFile(String filePath){
        fortuneCookieFile.updateFile(filePath);
    }
    
    @Override
    public String toString() {
        return fortunas.toString("\n");
    }
    
}
