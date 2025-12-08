package uni.aed.tda.treeTDA;
public class BstNodeTDA<E> {
    private E key;
    private BstNodeTDA<E> left, right;
    private static final String SEPARADOR="\n";

    public BstNodeTDA() {
        left=right=null;
    }

    public BstNodeTDA(E key) {
        this.key = key;
    }

    public void setKey(E key) {
        this.key = key;
    }

    public void setLeft(BstNodeTDA<E> left) {
        this.left = left;
    }

    public void setRight(BstNodeTDA<E> right) {
        this.right = right;
    }

    public E getKey() {
        return key;
    }

    public BstNodeTDA<E> getLeft() {
        return left;
    }

    public BstNodeTDA<E> getRight() {
        return right;
    }
    
    private void print(StringBuilder buffer,String prefix,String childrenPrefix){
        buffer.append(prefix);
        buffer.append(key);
        buffer.append(SEPARADOR);
        
        //logica de visualizacion del subarbol izquierdo
        if(left!=null && right!=null){
            //existen ambos hijos
            left.print(buffer, childrenPrefix+"---", childrenPrefix+"   ");
            right.print(buffer, childrenPrefix+"+++", childrenPrefix+"|  ");
        }else if(left!=null)
            //solo tiene hijo izq
            left.print(buffer, childrenPrefix+"---", childrenPrefix+"   ");
        else if (right!=null)
            right.print(buffer, childrenPrefix+"+++", childrenPrefix+"|  ");
    }            

    @Override
    public String toString() {
        StringBuilder buffer=new StringBuilder();
        print(buffer,"","");
        return buffer.toString();
    }
    
    
    
    
}
