package uni.aed.tda.circlelinkedlistTDA;
import uni.aed.tda.linkedlistTDA.Nodo;

public class DNodo<E> extends Nodo<E> {
    private DNodo<E> prev;

    public DNodo(E data) {
        super(data);
        this.prev = null;
    }

    public DNodo<E> getPrev() {
        return prev;
    }

    public void setPrev(DNodo<E> prev) {
        this.prev = prev;
    }

    @Override
    public DNodo<E> getNext() {        
        return (DNodo<E>) super.getNext();
    }

    @Override
    public void setNext(Nodo<E> next) {        
        super.setNext(next);
    } 
    
    @Override    
    public void setData(E data) {
        super.setData(data);        
    }
    
    @Override
    public E getData() {
        return super.getData();
    }
}
