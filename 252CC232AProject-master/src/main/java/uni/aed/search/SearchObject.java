package uni.aed.search;

public class SearchObject {
    private Object[] X;
    private final int NO_ENCONTRADO=-1;        

    public void setX(Object[] X) {
        this.X = X;
    }

    public Object[] getX() {
        return X;
    }    
    
    public int searchBinaria(Object valor){
        int bajo=0,alto=X.length-1, medio=(bajo+alto)/2;
        int nComp=0;
        while(bajo<=alto && ((Comparable)X[medio]).compareTo(valor)!=0){//X[medio]!=valor
            if(((Comparable)X[medio]).compareTo(valor)<0)
                bajo=medio+1;
            else
                alto=medio-1;
            
            medio=(bajo+alto)/2;
        }
        if(bajo>alto)
            medio=NO_ENCONTRADO;
        return medio;
    }
}
