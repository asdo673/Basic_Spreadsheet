package uni.aed.tda.circlelinkedlistTDA;

import uni.aed.tda.listTDA.IteratorTDA;
import uni.aed.tda.listTDA.ListTDA;

public class CircleLinkedListTDA<E> implements ListTDA<E>  {
    private DNodo<E> head;
    private int lenght=0;
    
    @Override
    public void add(E data) {
        DNodo<E> newNodo = new DNodo<>(data);
        if (head == null) {
            // Primer elemento: se apunta a si mismo en ambas direcciones
            head = newNodo;
            head.setNext(head);
            head.setPrev(head);
        } else {
            // Inserta antes de head, al final de la lista
            DNodo<E> prev = head.getPrev();

            prev.setNext(newNodo);
            newNodo.setPrev(prev);
            newNodo.setNext(head);
            head.setPrev(newNodo);
        }
        lenght++;
    }
    
    @Override
    public boolean delete(E data) {
        if (head == null)
            return false; // lista vacia        
        DNodo<E> actual = head;
        do {
            if (actual.getData().equals(data)) {
                if (lenght == 1)
                    head = null;
                else {
                    // desconectar nodo
                    DNodo<E> prev = actual.getPrev();
                    DNodo<E> next = actual.getNext();
                    prev.setNext(next);
                    next.setPrev(prev);
                    if (actual == head)
                        head = next; // si eliminamos el primero                    
                }
                lenght--;
                return true;
            }
            actual = actual.getNext();
        } while (actual != head);
        return false; // no encontrado
    }
    
    @Override
    public E delete(int n) {
        if (head == null || n <= 0)
            return null;

        DNodo<E> current = head;
        while (lenght > 1) {
            // Avanza n-1 pasos
            for (int i = 1; i < n; i++)
                current = current.getNext();
            // Eliminar el nodo actual
            DNodo<E> prev = current.getPrev();
            DNodo<E> next = current.getNext();
            prev.setNext(next);
            next.setPrev(prev);

            // Si eliminamos el head, movemos la referencia
            if (current == head)
                head = next;

            // Continuar desde el siguiente
            current = next;
            lenght--;
        }

        // Al final queda un unico nodo
        return head.getData();
    }
    
    public boolean search(E data) {
        if (head == null)
            return false;
        DNodo<E> current = head;
        do {
            if (current.getData().equals(data))
                return true;
            current = current.getNext();
        } while (current != head);
        return false;
    }

    
    public void bubbleSort() {
    // Si la lista está vacía o tiene un solo elemento, no hay nada que ordenar
    if (head == null || head.getNext() == head) return;
    boolean swapped;
    DNodo<E> actual;
    DNodo<E> ultimo = null;
        do {
            swapped = false;
            actual = head;
            // Recorremos hasta el nodo antes del "ultimo" ya ordenado
            while (actual.getNext() != head && actual.getNext() != ultimo) {
                // Comparamos los datos (aseguramos que E implemente Comparable)
                E datoActual = actual.getData();
                E datoSiguiente = actual.getNext().getData();
                if (((Comparable<E>) datoActual).compareTo(datoSiguiente) > 0) {
                    // Intercambiamos los valores
                    actual.setData(datoSiguiente);
                    actual.getNext().setData(datoActual);
                    swapped = true;
                }
                actual = actual.getNext();
            }
            // El ultimo nodo ordenado ya esta en su lugar
            ultimo = actual;
        } while (swapped);
    }

    @Override
    public int size() {
        return lenght;
    }

    @Override
    public boolean isEmpty() {
        return lenght == 0;
    }
    
    @Override
    public String toString() {
        if (head == null) return "[]";
        StringBuilder sb = new StringBuilder("[");
        DNodo<E> actual = head;
        do {
            sb.append(actual.getData());
            actual = actual.getNext();
            if (actual != head) sb.append(" <-> ");
        } while (actual != head);

        sb.append("] (circular)");
        return sb.toString();
    }
    
    @Override
    public IteratorTDA<E> iterador(){
        return new uni.aed.tda.linkedlistTDA.Iterador(head);
    }

    @Override
    public void add(int index, E elemento) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean contain(E elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int indexOf(E elemento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    

    @Override
    public E modify(int index, E elemento) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String toString(String patron) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    

    @Override
    public ListTDA<E> union(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ListTDA<E> interseccion(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ListTDA<E> diferencia(ListTDA<E> l1, ListTDA<E> l2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
