package uni.aed.tda.treeTDA.TBT;

import uni.aed.model.Persona;

public class TBTMain {
    StringBuilder str=new StringBuilder();
    public static void main(String[] args){
        TBTMain tBTMain=new TBTMain();  
        tBTMain.testTBTInteger();
        System.out.println("--------------------------------");
        tBTMain.testTBTPersona();
    } 
    private void testTBTInteger(){
        TBT<Integer> tree=new TBT<>();
        tree.add(15);
        tree.add(4);
        tree.add(20);
        tree.add(17);
        tree.add(19);
        System.out.println("Visualizando el arbol");
        System.out.println(tree.toString());
        
        System.out.println("Realizando una llamada inorder(LVR)");
        str.setLength(0);
        tree.TBTInOrder(str);
        System.out.println(str.toString());
    }
    
    private void testTBTPersona(){
        TBT<Persona> tree=new TBT<>();
        Persona p1=new Persona("Elon Musk", 60,'M');
        Persona p2=new Persona("Barack Obama", 65,'M');
        Persona p3=new Persona("Jose Jeri", 35,'M');
        Persona p4=new Persona("Mick jagger", 72,'M');
        Persona p5=new Persona("Sonya Smith", 30,'F');
        Persona p6=new Persona("Donald Trump", 70,'M');
        Persona p7=new Persona("Javier Macias", 40,'M');
        
        tree.add(p1);
        tree.add(p2);
        tree.add(p3);
        tree.add(p4);
        tree.add(p5);
        tree.add(p6);
        
        System.out.println("Visualizando el arbol");
        System.out.println(tree.toString());
        
        System.out.println("Realizando una llamada inorder(LVR)");
        str.setLength(0);
        tree.TBTInOrder(str);
        System.out.println(str.toString());
    }
}
