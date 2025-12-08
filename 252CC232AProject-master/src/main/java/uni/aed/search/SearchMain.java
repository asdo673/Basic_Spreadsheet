package uni.aed.search;

public class SearchMain {    
    public static void main(String[] args){
        SearchMain searchMain=new SearchMain();
        searchMain.testSearchLineal();
        //searchMain.testSearchBinaria();
    }
    private void testSearchLineal(){
        Search s=new Search();
        //Integer X[]={15,1,25,60,69,86,3,78,2,10};
        Integer X[]={15,1,25,15,69,86,3,78,2,25};
        s.setX(X);
        //visualizando X
        System.out.println("El contenido del arreglo es:"+s.toString());                
        //Busqueda Satisfactoria
        int valorB=15;
        int result=s.searchLineal(valorB);
        if(result==-1)
            System.out.println("El valor buscado "+valorB+" no existe");
        else
            System.out.println("El valor buscado "+valorB+" se encontro en la posicion "+ result);        
        //Busqueda Insatisfactoria
        valorB=25;        
        result=s.searchLineal(valorB);
        if(result==-1)
            System.out.println("El valor buscado "+valorB+" no existe");
        else
            System.out.println("El valor buscado "+valorB+" se encontro en la posicion "+ result);        
    }
    private void testSearchBinaria(){
        Search s=new Search();
        //Arreglo ordenado        
        Integer X[]={5,12,17,23,38,44,77,84,90};
        s.setX(X);
        //visualizando el arreglo
        System.out.println("El contenido del arreglo es:"+s.toString());                
        //Busqueda Satisfactoria
        int valorB=44;
        int result=s.searchBinaria(valorB);
        if(result==-1)
            System.out.println("El valor buscado "+valorB+" no existe");
        else
            System.out.println("El valor buscado "+valorB+" se encontro en la posicion "+ result);        
        //invocando el analisis de las comparaciones
        System.out.println("La busqueda binaria realizo "+ s.getnComp()+ " comparaciones");
        System.out.println(s.getAnalisis());        
        
        //Busqueda Insatisfactoria
        valorB=45;
        s.clear();
        result=s.searchBinaria(valorB);
        if(result==-1)
            System.out.println("El valor buscado "+valorB+" no existe");
        else
            System.out.println("El valor buscado "+valorB+" se encontro en la posicion "+ result);        
        //invocando el analisis de las comparaciones
        System.out.println("La busqueda binaria realizo "+ s.getnComp()+ " comparaciones");
        System.out.println(s.getAnalisis());        
        
    }
}
