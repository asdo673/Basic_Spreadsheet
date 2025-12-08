package uni.aed.tda.circlelinkedlistTDA;

import uni.aed.tda.circlelinkedlistTDA.DNodo;
import uni.aed.tda.listTDA.IteratorTDA;
import uni.aed.tda.listTDA.NoSuchElementException;

public class Iterador<E> implements IteratorTDA<E> {
    private DNodo<E> actual;
    private final DNodo<E> inicio;
    private boolean primeraVez; // controla el ciclo circular

    public Iterador(DNodo<E> inicio) {
        this.actual = inicio;
        this.inicio = inicio;
        this.primeraVez = true;
    }

    @Override
    public boolean hasNext() {
        // Si la lista esta vacia (inicio == null), no hay siguiente
        if (inicio == null) return false;
        // Si aun no hemos empezado o no hemos dado la vuelta completa
        return (primeraVez || actual != inicio);
    }

    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException("No hay mas elementos en la lista circular.");

        E data = actual.getData();
        actual = actual.getNext();
        primeraVez = false;
        return data;
    }
}
