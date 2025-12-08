package uni.aed.tda.arraylistTDA;

import uni.aed.model.Persona;
import uni.aed.tda.listTDA.ListTDA;

public class ArrayListTDAMain {
    public static void main(String[] args){
        ArrayListTDAMain listTDAMain=new ArrayListTDAMain();
        //listTDAMain.testArrayListTDAString();
        listTDAMain.testArrayListTDAPersona();
        
    }
    private void testArrayListTDAString(){
        ListTDA<String> lista=new ArrayListTDA<>();
        System.out.println("Antes de añadir elementos");
        System.out.println("La lista contiene "+lista.size()+" elementos");
        lista.add("Jose");
        lista.add("Beatriz");
        lista.add("Sandro");
        lista.add("Franklin");        
        
        System.out.println("Despues de añadir elementos");
        System.out.println("La lista contiene "+lista.size()+" elementos");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
        
        lista.add(2,"Adelaida");        
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
        
        String data="Adelaida";
        if(lista.contain(data)){
            System.out.println("El elemento buscado "+ data+ 
            " se encuentra en la posicion "+ lista.indexOf(data)+
                    ", se procedera a su eliminacion");
            lista.delete(lista.indexOf(data));
        }else
            System.out.println("El elemento buscado "+ data+ 
            " NO se encuentra en la lista ");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
    }
    private void testArrayListTDAPersona(){
        ListTDA<Persona> lista=new ArrayListTDA<>();
        System.out.println("Antes de añadir elementos");
        System.out.println("La lista contiene "+lista.size()+" elementos");
        Persona p1=new Persona("Sonya Smith",30,'F');
        Persona p2=new Persona("Barack Obama",60,'M');
        Persona p3=new Persona("Dina Boluarte",50,'F');        
        Persona p4=new Persona("Donald Trump",70,'M');
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
        lista.add(p1);
        
        System.out.println("Despues de añadir elementos");
        System.out.println("La lista contiene "+lista.size()+" elementos");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
        
        lista.add(2,p4);        
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());        
        
        if(lista.contain(p4)){
            System.out.println("El elemento buscado "+ p4.toString()+ 
            " se encuentra en la posicion "+ lista.indexOf(p4)+
                    ", se procedera a su eliminacion");
            lista.delete(lista.indexOf(p4));
        }else
            System.out.println("El elemento buscado "+ p4.toString()+ 
            " NO se encuentra en la lista ");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
        
    }
}
