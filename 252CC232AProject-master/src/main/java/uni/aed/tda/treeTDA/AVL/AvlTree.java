package uni.aed.tda.treeTDA.AVL;

public class AvlTree<E extends Comparable<E>> {
    private static final int NOT_FOUND=-1;
    private AvlTreeNode<E> root;
    
    public void add(E key){
        if(findAvlTreeNode(root,key)==null)
            root=AvlInsert(root,key);
    }
    private AvlTreeNode<E> AvlInsert(AvlTreeNode<E> node, E key){
        if(node==null)
            return new AvlTreeNode(key);
        else if(key.compareTo(node.getKey())<0)
            node.setLeft(AvlInsert(node.getLeft(),key));
        else
            node.setRight(AvlInsert(node.getRight(),key));
        //proceso para validar si el arbol esta balanceado
        return balanceTree(node);
    }
    private AvlTreeNode<E> findAvlTreeNode(AvlTreeNode<E> node, E key){
        if(node==null || key.compareTo(node.getKey())==0)
            return node;
        if(key.compareTo(node.getKey())<0)
            return findAvlTreeNode(node.getLeft(),key);
        else
            return findAvlTreeNode(node.getRight(),key);
    }
    private AvlTreeNode<E> balanceTree(AvlTreeNode<E> node){
        updateHeight(node);
        int balance=Balance(node);
        if(balance>1){
            if(Balance(node.getRight())<0){
                node.setRight(rotateRigth(node.getRight()));
                return rotateLeft(node);
            }else
                return rotateLeft(node);
        }
        if(balance<-1){
            if(Balance(node.getLeft())>0){
                node.setLeft(rotateLeft(node.getLeft()));
                return rotateRigth(node);
            }else
                return rotateRigth(node);
        }
        return node;
    }
    
    private void updateHeight(AvlTreeNode<E> node){
        node.setHeight(Math.max(
                Height(node.getLeft()),
                Height(node.getRight()))+1);
    }
    
    private int Height(AvlTreeNode<E> node){
        if(node==null)
            return 0;
        else
            return node.getHeight();
    }
    //calcular el factor de balance
    private int Balance(AvlTreeNode<E> node){
        if(node==null)
            return 0;
        else{
            int fb=Height(node.getRight())-Height(node.getLeft());
            return fb;
        }
    }
    
    private AvlTreeNode<E> rotateLeft(AvlTreeNode<E> x){
        AvlTreeNode<E> y=x.getRight();
        AvlTreeNode<E> T2=y.getLeft();
        y.setLeft(x);
        x.setRight(T2);
        updateHeight(x);
        updateHeight(y);
        return y;
    }
    private AvlTreeNode<E> rotateRigth(AvlTreeNode<E> y){
        AvlTreeNode<E> x=y.getLeft();
        AvlTreeNode<E> T2=x.getRight();
        x.setRight(y);
        y.setLeft(T2);
        updateHeight(y);
        updateHeight(x);
        return x;        
    }

    @Override
    public String toString() {
        return root.toString();
    }
    
    
}   

