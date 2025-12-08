package uni.aed.tda.EditorTexto;

import uni.aed.tda.linkedlistTDA.LinkedListTDA;
import uni.aed.tda.linkedlistTDA.Nodo;

public class TextEditorLinkedListTDA<E> extends LinkedListTDA<E>{
    public static final String RANGO_MSG1="Rango Invalido";
    public static final String RANGO_MSG2="El rango excede el tamaño de la lista.";    
    
    @Override
    public void add(E data) {
        super.add(data);
    }
    @Override
    public void add(int index, E data) throws IndexOutOfBoundsException {
        super.add(index, data);
    }
    public void deleteRange(int start, int end) {
        // Validaciones iniciales
        if (head == null || start >= super.count) return;
        if (start < 0) start = 0;
        if (end >= super.count) end = super.count - 1;
        if (start > end) return;

        // Caso 1: El rango incluye la cabeza
        if (start == 0) {
            for (int i = 0; i <= end && head != null; i++) {
                head = head.getNext();
                super.count--;
            }
        } 
        // Caso 2: El rango esta mas adelante
        else {
            Nodo<E> actual = head;

            // Avanzar hasta el nodo anterior al inicio del rango
            for (int i = 0; i < start - 1 && actual != null; i++) {
                actual = actual.getNext();
            }

            // Si llegamos a un nodo valido, saltamos los que deben eliminarse
            if (actual != null) {
                Nodo<E> temp = actual;
                for (int i = start; i <= end && temp.getNext() != null; i++) {
                    temp.setNext(temp.getNext().getNext());
                    super.count--;
                }
            }
        }
    }   
    public String printRange(int inicio, int fin) {
        StringBuilder str = new StringBuilder(); 

        if (inicio < 0 || fin < inicio) {
            str.append(RANGO_MSG1);
            return str.toString();
        }

        Nodo<E> actual = head;
        int index = 0;
        boolean firstPrinted = true;

        while (actual != null && index <= fin) {
            if (index >= inicio) {
                if (!firstPrinted)
                    str.append("\n"); // agrega salto antes, no después
                else
                    firstPrinted = false;                
                str.append(index + 1).append("> ").append(actual.getData());
            }
            actual = actual.getNext();
            index++;
        }

        if (inicio > index)
            str.append(RANGO_MSG2);

        return str.toString();
    }   
    
}
