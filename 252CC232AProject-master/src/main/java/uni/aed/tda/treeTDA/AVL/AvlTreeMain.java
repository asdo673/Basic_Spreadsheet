package uni.aed.tda.treeTDA.AVL;

import uni.aed.tda.treeTDA.BstTDA;

public class AvlTreeMain {
    public static void main(String[] args){
        AvlTreeMain avlTreeMain=new AvlTreeMain();
        avlTreeMain.testAvlTreeInteger();
    }
    
    private void testAvlTreeInteger(){
        BstTDA<Integer> bst=new BstTDA<>();
        bst.add(10);
        bst.add(20);
        bst.add(30);
        bst.add(40);
        bst.add(50);
        bst.add(25);
        System.out.println("Visualizando el arbol- BST");
        System.out.println(bst.toString()); 
        
        AvlTree<Integer> avl=new AvlTree<>();
        avl.add(10);
        avl.add(20);
        avl.add(30);
        avl.add(40);
        avl.add(50);
        avl.add(25);
        System.out.println("Visualizando el arbol- AVL");
        System.out.println(avl.toString());        
    }
}
