package uni.aed.tda.stackTDA.tagsHTML;

import java.io.IOException;
import uni.aed.tda.stackTDA.ArrayStackTDA;
import uni.aed.tda.stackTDA.StackTDA;

public class HTMLRevisaEtiq {
    private StackTDA<HTMLEtiq> pila;
    private HTMLRecuperaEtiq etiqRecuperada;

    public HTMLRevisaEtiq(String nomArchivo) throws IOException {
        this.pila=new ArrayStackTDA<>();
        this.etiqRecuperada=new HTMLRecuperaEtiq(nomArchivo);
    }
    public boolean esValido(){
        HTMLEtiq etiqSiguiente=null, etiqTop=null;
        boolean nohayerror=true,termina=false;
        pila.clear();
        while(!termina){
            if(!etiqRecuperada.hayMasEtiq()){
                termina=true;
                if(!pila.isEmpty())
                   nohayerror=false;//implica que si habria error  
            }else{
                try{
                    etiqSiguiente=etiqRecuperada.etiqSiguiente();                    
                }catch(IOException e){e.printStackTrace();}
                if(etiqSiguiente.esEtiquetaApertura())
                    pila.push(etiqSiguiente);
                else if(etiqSiguiente.esEtiquetaCierre()){
                    etiqTop=pila.pop();//extrae elemento del top o cima de pila
                    if(!etiqTop.compara(etiqSiguiente)){
                        nohayerror=false;//implica que si habria error
                        termina=true;
                    }
                }
            }
        }
        return nohayerror;
    }
}
