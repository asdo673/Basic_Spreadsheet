package uni.aed.tda.stackTDA.recolector;

/**
 * Código estudiante: [COLOQUE_SU_CODIGO_AQUI]
 *
 * Clase Posicion
 * Representa una coordenada (fila, columna) en el tablero.
 */
public class Posicion {
    private int fila;
    private int col;

    public Posicion(int fila, int col) {
        this.fila = fila;
        this.col = col;
    }

    public int getFila() { return fila; }
    public int getCol() { return col; }

    @Override
    public String toString() {
        return "(" + fila + "," + col + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Posicion)) return false;
        Posicion p = (Posicion) o;
        return this.fila == p.fila && this.col == p.col;
    }
}
