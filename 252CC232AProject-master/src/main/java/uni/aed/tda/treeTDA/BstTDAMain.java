package uni.aed.tda.treeTDA;

import uni.aed.model.Persona;

public class BstTDAMain {
    StringBuilder str=new StringBuilder();
    public static void main(String[] args){
        BstTDAMain bstTDAMain=new BstTDAMain();       
        bstTDAMain.testBstTDAInteger();
        System.out.println("--------------------------------");
        bstTDAMain.testBstTDAPersona();
    }
    private void testBstTDAInteger(){
        BstTDA<Integer> tree=new BstTDA<>();
        tree.add(15);
        tree.add(4);
        tree.add(20);
        tree.add(17);
        tree.add(19);
        System.out.println("Visualizando el arbol");
        System.out.println(tree.toString());
        
        System.out.println("El IPL del arbol es: "+ tree.calcularIPL()); 
        System.out.println("La altura del arbol es: "+ tree.calculateHeight());         
        System.out.println("El Numero de nodos del arbol es: "+ tree.countNodes());                  
        System.out.println("El arbol esta balanceado?: "+ tree.isBalanced());
        
        System.out.println("Realizando una llamda inorder(LVR)");
        tree.inorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamda preorder(VLR)");
        str.setLength(0);
        tree.preorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamda postorder(LRV)");
        str.setLength(0);
        tree.postorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamada primero en amplitud");
        str.setLength(0);
        tree.breadthFirst(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando busqueda del valor 17");
        str.setLength(0);
        tree.visit(tree.search(17), str);
        System.out.println(str.toString());
        
        System.out.println("Realizando busqueda del valor 100");
        str.setLength(0);
        tree.visit(tree.search(100), str);
        System.out.println(str.toString());
        
        
        System.out.println("Realizando la eliminacion por copiado");
        str.setLength(0);
        int result=tree.deleteByCopying(15);
        if(result!=1)
            System.out.println("El valor a eliminar no se ubico en el arbol o el arbol esta vacio");
        System.out.println("Visualizando el arbol");
        System.out.println(tree.toString());      
        
        System.out.println("Realizando la eliminacion por fusion");
        str.setLength(0);
        result=tree.deleteByMerging(17);
        if(result!=1)
            System.out.println("El valor a eliminar no se ubico en el arbol o el arbol esta vacio");
        System.out.println("Visualizando el arbol");
        System.out.println(tree.toString());
        
        
    }
    
    private void testBstTDAPersona(){
        BstTDA<Persona> tree=new BstTDA<>();
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
        
        System.out.println("Realizando una llamda inorder(LVR)");
        tree.inorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamda preorder(VLR)");
        str.setLength(0);
        tree.preorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamda postorder(LRV)");
        str.setLength(0);
        tree.postorder(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando una llamada primero en amplitud");
        str.setLength(0);
        tree.breadthFirst(str);
        System.out.println(str.toString());
        
        System.out.println("Realizando busqueda de la persona "+ p6.toString());
        str.setLength(0);
        tree.visit(tree.search(p6), str);
        System.out.println(str.toString());
        
        System.out.println("Realizando busqueda de la persona "+ p7.toString());
        str.setLength(0);
        tree.visit(tree.search(p7), str);
        System.out.println(str.toString());
    }
}
