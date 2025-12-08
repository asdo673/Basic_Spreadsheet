package uni.aed.tda.linkedlistTDA;

import uni.aed.model.Persona;
import uni.aed.tda.listTDA.ListTDA;

public class LinkedListTDAMain {
    public static void main(String[] args){
        LinkedListTDAMain listTDAMain=new LinkedListTDAMain();
        //listTDAMain.testLinkedListTDAString();
        //listTDAMain.testLinkedListTDAPersona();
        //listTDAMain.testLinkedListTDAUnion();        
        //listTDAMain.testLinkedListTDAInterseccion();
        listTDAMain.testLinkedListTDADiferencia();
    }
    
    private void testLinkedListTDAString(){
        ListTDA<String> lista=new LinkedListTDA<>();
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
            System.out.println(lista.delete(lista.indexOf(data)));
        }else
            System.out.println("El elemento buscado "+ data+ 
            " NO se encuentra en la lista ");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());
    }
    
    private void testLinkedListTDAPersona(){
        ListTDA<Persona> lista=new LinkedListTDA<>();
        System.out.println("Antes de añadir elementos");
        System.out.println("La lista contiene "+lista.size()+" elementos");
        Persona p1=new Persona("Sonya Smith",30,'F');
        Persona p2=new Persona("Barack Obama",60,'M');
        Persona p3=new Persona("Dina Boluarte",50,'F');
        Persona p4=new Persona("Donald Trump",70,'M');
        lista.add(p1);
        lista.add(p2);
        lista.add(p3);
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
            System.out.println(lista.delete(lista.indexOf(p4)));
        }else
            System.out.println("El elemento buscado "+ p4.toString()+ 
            " NO se encuentra en la lista ");
        
        System.out.println("Visualizando la lista");
        System.out.println(lista.toString());        
    }
    private void testLinkedListTDAUnion(){
        ListTDA<String> l1=new LinkedListTDA();
        l1.add("uno");
        l1.add("dos");
        l1.add("tres");
        l1.add("cuatro");
        l1.add("cinco");
        System.out.println("Visualizando la lista1");
        System.out.println(l1.toString());
        
        ListTDA<String> l2=new LinkedListTDA();
        l2.add("uno");
        l2.add("cinco");
        l2.add("cero");
        l2.add("cuatro");
        l2.add("ocho");
        System.out.println("Visualizando la lista2");
        System.out.println(l2.toString());
        
        ListTDA<String> lista=new LinkedListTDA();
        System.out.println("Visualizando la union de las listas");
        System.out.println(lista.union(l1, l2).toString());        
    }
    private void testLinkedListTDAInterseccion(){
        ListTDA<String> l1=new LinkedListTDA();
        l1.add("uno");
        l1.add("dos");
        l1.add("tres");
        l1.add("cuatro");
        l1.add("cinco");
        System.out.println("Visualizando la lista1");
        System.out.println(l1.toString());
        
        ListTDA<String> l2=new LinkedListTDA();
        l2.add("uno");
        l2.add("cinco");
        l2.add("cero");
        l2.add("cuatro");
        l2.add("ocho");
        System.out.println("Visualizando la lista2");
        System.out.println(l2.toString());
        
        ListTDA<String> lista=new LinkedListTDA();
        System.out.println("Visualizando la interseccion de las listas");
        System.out.println(lista.interseccion(l1, l2).toString());        
    }
    private void testLinkedListTDADiferencia(){
        ListTDA<String> l1=new LinkedListTDA();
        l1.add("uno");
        l1.add("dos");
        l1.add("tres");
        l1.add("cuatro");
        l1.add("cinco");
        System.out.println("Visualizando la lista1");
        System.out.println(l1.toString());
        
        ListTDA<String> l2=new LinkedListTDA();
        l2.add("uno");
        l2.add("cinco");
        l2.add("cero");
        l2.add("cuatro");
        l2.add("ocho");
        System.out.println("Visualizando la lista2");
        System.out.println(l2.toString());
        
        ListTDA<String> lista=new LinkedListTDA();
        System.out.println("Visualizando la diferencia de las listas");
        System.out.println(lista.diferencia(l1, l2).toString());        
    }
}
