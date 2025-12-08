package uni.aed.sort;
import java.util.concurrent.TimeUnit;

public class Sort {
    private Integer[] X;
    private int nComp=0;    //# Comparaciones
    private int nInt=0;     //# Intercambios
    private long tEjec=0;   //tiempo de ejecucion

    public void setX(Integer[] X) {
        this.X = X;
        clear();        
    }

    public Integer[] getX() {
        return X;
    }

    public void settEjec(long tEjec) {
        this.tEjec = tEjec;
    }

    public long gettEjec() {
        return tEjec;
    }

    public int getnComp() {
        return nComp;
    }

    public int getnInt() {
        return nInt;
    }
    
    
    private void clear(){
        this.nComp=0;    
        this.nInt=0;     
        this.tEjec=0;   
    }
    
    public void selectionSort(){
        int startIndex,minIndex,lenght,temp;
        lenght=X.length;
        clear();
        long tIni=System.nanoTime();
        for(startIndex=0;startIndex<=lenght-2;startIndex++){
            minIndex=startIndex;
            //buscamos el valor mas pequeño de la coleccion
            for(int i=startIndex+1;i<=lenght-1;i++){
                //realizamos la comparacion
                nComp++;
                if(X[i]<X[minIndex]) minIndex=i;
            }
            //intercambio
            if (minIndex!=startIndex){
                intercambio(startIndex,minIndex);
                nInt++;
            }
        }
        long tFin=System.nanoTime();
        this.settEjec(tFin-tIni);        
    }
    
    //metodo de ordenamiento de la burbuja
    public void bubbleSort(){
        int temp,bottom;
        boolean exchange=true;
        bottom=X.length-2;
        clear();
        long tIni=System.nanoTime();
        while(exchange){
            exchange=false;
            for(int i=0;i<=bottom;i++){
                nComp++;
                if(X[i]>X[i+1]){
                    temp=X[i];
                    X[i]=X[i+1];
                    X[i+1]=temp;
                    exchange=true;
                    nInt++;
                }                    
            }
            bottom--;            
        }
        long tFin=System.nanoTime();                
        this.settEjec(tFin-tIni);
    }
    
    public void insercionSort(){
        int aux,k;
        boolean sw=false;
        for(int i=1;i<X.length;i++){
            aux=X[i];
            k=i-1;
            sw=false;
            while(!sw && k>=0){
                if(aux<X[k])
                {
                   X[k+1]=X[k];
                   k--;
                }
                else
                    sw=true;
            }
            X[k+1]=aux;
        }//end for        
    }
    public void insercionBinariaSort(){
        int aux,p,u,c;
        for(int i=1;i<X.length;i++){
            aux=X[i];
            p=0;
            u=i-1;
            while(p<=u){
                c=(p+u)/2;
                if(aux<X[c])
                    u=c-1;
                else
                    p=c+1;
            }
            for(int k=i-1;k>=p;k--)
                X[k+1]=X[k];
            X[p]=aux;
        }//end for externo
    }    
    
    public void heapSort(){
        heapSortConstruct();//fase1: construccion
        heapSortExtract();//fase2: extraccion        
    }
    private void heapSortConstruct(){
        int current=0, maxChildIndex;
        boolean hecho;
        for(int i=(X.length/2)-1; i>=0;i--){
            current=i;
            hecho=false;
            while(!hecho){//2*i+1,2*i+2
                if(2*current + 1>X.length-1)
                    //nodo actual no tiene hijo
                    hecho=true;
                else{
                    maxChildIndex=heapSortMaxChild(current,X.length-1);
                    if(X[current]<X[maxChildIndex]){
                        intercambio(current,maxChildIndex);
                        current=maxChildIndex;
                    }else
                        hecho=true;
                }
            }
        }
    }
    private int heapSortMaxChild(int loc,int end){
        int result, izq,der;
        izq=2*loc+1;//indice impar
        der=2*loc+2;//indice par     
        if(der<=end && X[izq]<X[der])
            result=der;
        else
            result=izq;
        
        return result;  //retornando la pos del hijo que tiene el maximo valor
    }
    
    private void intercambio(int p,int q){
        int temp=X[p];
        X[p]=X[q];
        X[q]=temp;
    }  

    //Solucion 1PC: Ejercicio2        
    private void heapSortExtract(){        
        int current, maxChildIndex=0;
        boolean hecho;
        for(int i=X.length-1;i>0;i--){
            intercambio(0, i);  
            //reconstruir el heap
            current=0;
            hecho=false;
            while(!hecho){
                if(2*current + 1 > (i-1) )//validando la restriccion estructural
                    hecho=true;
                else{ //verificamos si el nodo actual tiene al menos un hijo
                    maxChildIndex=heapSortMaxChild(current,i-1);
                    if(X[current]<X[maxChildIndex]){
                        intercambio(current,maxChildIndex);
                        current=maxChildIndex;
                    }else
                        hecho=true;
                }
            }//end while
        }//end for        
    }
    //version origen usando 2 arreglos
    private void heapSortExtractOrigen(){
        Integer[] Y=new Integer[X.length];
        int current, maxChildIndex;
        boolean hecho;
        for(int i=X.length-1;i>=0;i--){
            Y[i]=X[0];      //consignando en Y el root del heap(X)
            X[0]=X[i];      //reemplazando el root con el ult.elemento del heap
            //reconstruir el heap
            current=0;
            hecho=false;
            while(!hecho){
                if(2*current + 1 > i )//validando la restriccion estructural
                    hecho=true;
                else{ //verificamos si el nodo actual tiene al menos un hijo
                    maxChildIndex=heapSortMaxChild(current,i);
                    if(X[current]<X[maxChildIndex]){
                        intercambio(current,maxChildIndex);
                        current=maxChildIndex;
                    }else
                        hecho=true;
                }
            }//end while
        }//end for
        this.X=Y;
    }
    
    public void quickSort(int start, int end) {
        if (start < end) {
            int pIndex = quickSortPartition(start, end);
            quickSort(start, pIndex - 1);
            quickSort(pIndex + 1, end);
        }        
    }

    private int quickSortPartition(int start, int end) {
        int pivot = X[end];
        int pIndex = start;
        for (int i = start; i < end; i++) {
            if (X[i] <= pivot) {
                intercambio(i, pIndex);
                pIndex++;
            }
        }
        intercambio(pIndex, end);
        return pIndex;
    }   
            
    public Integer[] mergeSort(Integer[] X)
    {
        int n=X.length;
        if(n < 2) return X;
        int mid = n/2;
        Integer[] left=new Integer[mid];
        Integer[] right=new Integer[n - mid];
        for(int i=0;i<mid;i++)
            left[i] = X[i];
        for(int i=mid;i<n;i++)
            right[i - mid] = X[i];        
        mergeSort(left);
        mergeSort(right);
        merge(X,left,right );
        this.X=X;
        return X;
    }
    private void merge(Integer[] X,Integer[] left,Integer[] right){
        int nL=left.length;
        int nR=right.length;
        int i=0,j=0,k=0;
        while(i<nL && j<nR){
            if(left[i] <= right[j]){
                X[k]=left[i];
                i++;
            }else{
                X[k]=right[j];
                j++;                
            }
            k++;                
        }
        while(i<nL){
            X[k]=left[i];
            i++;
            k++;
        }
        while(j<nR){
            X[k]=right[j];
            j++;
            k++;
        }
        
    }

    @Override
    public String toString() {
        if(X==null ||X.length==0) return "";
        StringBuilder str=new StringBuilder();
        for(Integer x:X){
            if(str.isEmpty())
                str.append(x);
            else
                str.append(","+x);
        }
        return str.toString()+"\n nComp= "+nComp+" nInt= "+ 
                nInt +" tEjec(ns)= "+tEjec 
                +" tEjec(ms)= "+ TimeUnit.NANOSECONDS.toMillis(tEjec);
    }
    
}
