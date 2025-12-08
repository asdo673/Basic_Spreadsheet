package uni.aed.tda.stackTDA.recolector;

import uni.aed.tda.stackTDA.LinkedStackTDA;
import uni.aed.tda.stackTDA.StackEmptyExceptionTDA;
import uni.aed.tda.stackTDA.StackTDA;

/** 
 * Clase Recolector
 * Implementa un algoritmo de backtracking (DFS) que usa una pila para encontrar
 * una ruta que pase por todas las posiciones con paquetes 'P' y regrese a la base 'B'.
 * - No usa Java Collections Framework, reutiliza LinkedStackTDA del proyecto.
 * - Busca cualquier ruta valida; guarda la mejor encontrada (menor longitud).
 */
public class Recolector {
    private char[][] lab;
    private int filas, cols;
    private Posicion base;
    private int totalPaquetes;
    private StackTDA<Posicion> mejorRuta; // mejor ruta encontrada (top = ultimo)

    public Recolector(char[][] lab) {
        this.lab = lab;
        this.filas = lab.length;
        this.cols = (filas > 0) ? lab[0].length : 0;
        this.base = null;
        this.totalPaquetes = 0;
        this.mejorRuta = new LinkedStackTDA<>();
        analizarMapa();
    }

    // Analiza el mapa para localizar la base 'B' y contar paquetes 'P'
    private void analizarMapa() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                if (lab[i][j] == 'B') base = new Posicion(i, j);
                if (lab[i][j] == 'P') totalPaquetes++;
            }
        }
    }

    /**
     * Intenta encontrar alguna ruta que pase por todos los paquetes y regrese a la base.
     * Devuelve true si encontró al menos una ruta.
     */
    public boolean encontrarRuta() {
        if (base == null) return false; // sin base no tiene sentido
        if (totalPaquetes == 0) {
            // ya en base y sin paquetes -> ruta trivial: quedarse en B
            try {
                mejorRuta.push(base);
            } catch (Exception e) {}
            return true;
        }

        boolean[][] visit = new boolean[filas][cols];
        StackTDA<Posicion> pilaActual = new LinkedStackTDA<>();

        // DFS con backtracking (recursivo implementado con pila y recursion auxiliar)
        boolean encontrado = dfs(base.getFila(), base.getCol(), visit, pilaActual, 0);
        return encontrado;
    }

    // Direcciones: arriba, derecha, abajo, izquierda
    private final int[] dr = {-1, 0, 1, 0};
    private final int[] dc = {0, 1, 0, -1};

    /**
     * DFS recursivo que avanza, usa pilaActual para registrar la ruta actual.
     * @param r fila actual
     * @param c columna actual
     * @param visit matriz de visitados
     * @param pilaActual pila con la ruta actual (top = ultimo)
     * @param paquetesRecogidos número de paquetes ya recogidos en la ruta actual
     * @return true si encontró camino completo (todos los paquetes y volver a base)
     */
    private boolean dfs(int r, int c, boolean[][] visit, StackTDA<Posicion> pilaActual, int paquetesRecogidos) {
        // marcar y apilar
        visit[r][c] = true;
        try { pilaActual.push(new Posicion(r, c)); } catch (Exception e) {}
        boolean terminado = false;
        // Si estamos en una celda con paquete y no es base, incrementamos contador
        int recogidos = paquetesRecogidos + (lab[r][c] == 'P' ? 1 : 0);
        // Si recogimos todos los paquetes y estamos en la base, ruta valida
        if (recogidos == totalPaquetes && lab[r][c] == 'B') {
            // Copiar pilaActual a mejorRuta si es mejor (o mejorRuta vacia)
            copiarSiMejor(pilaActual);
            terminado = true;
        } else {
            // Explorar vecinos (orden fijo)
            for (int k = 0; k < 4 && !terminado; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];
                if (esValida(nr, nc) && !visit[nr][nc] && lab[nr][nc] != '#') {
                    // Si aún no hemos recogido todos paquetes y estamos en base, evitar volver si no tiene sentido
                    if (lab[nr][nc] == 'B' && recogidos < totalPaquetes) {
                        // permitir volver a base, pero no termina aquí
                    }
                    terminado = dfs(nr, nc, visit, pilaActual, recogidos);
                }
            }
        }
        // backtrack: desapilar y desmarcar
        try {
            pilaActual.pop();
        } catch (StackEmptyExceptionTDA ex) {
            // no debería ocurrir
        }
        visit[r][c] = false;

        return terminado;
    }

    // Verifica si (r,c) esta dentro del mapa
    private boolean esValida(int r, int c) {
        return r >= 0 && r < filas && c >= 0 && c < cols;
    }

    // Copia pilaActual en mejorRuta si mejorRuta esta vacia o la actual es mas corta.
    private void copiarSiMejor(StackTDA<Posicion> pilaActual) {
        try {
            // Si mejorRuta vacia, simplemente clonar
            if (mejorRuta.isEmpty())
                clonarPila(pilaActual, mejorRuta);
            else {
                // comparar tamaños y reemplazar si la actual es menor
                if (pilaActual.size() < mejorRuta.size()) {
                    mejorRuta.clear();
                    clonarPila(pilaActual, mejorRuta);
                }
            }
        } catch (Exception e) {
            // ignorar errores de clonacion
        }
    }

    /**
     * Clona una pila en otra sin destruir la pila fuente. Usamos una pila auxiliar
     * para voltear dos veces y conservar el orden original.
     */
    private void clonarPila(StackTDA<Posicion> fuente, StackTDA<Posicion> destino) throws StackEmptyExceptionTDA {
        StackTDA<Posicion> aux = new LinkedStackTDA<>();
        // vaciar fuente en aux (aux tendra elementos invertidos)
        while (!fuente.isEmpty()) {
            Posicion p = fuente.pop();
            aux.push(p);
        }
        // ahora aux tiene ruta en orden desde base->...->ultimo
        // reconstruir fuente y llenar destino con misma secuencia
        while (!aux.isEmpty()) {
            Posicion p = aux.pop();
            fuente.push(p);      // restaurar fuente
            destino.push(p);     // copiar en destino
        }
    }

    /**
     * Devuelve la mejor ruta encontrada como una cadena legible (desde inicio hasta fin).
     */
    public String rutaMejorComoCadena() {
        if (mejorRuta == null || mejorRuta.isEmpty()) return "(no se encontro ruta)";
        // Para mostrarla en orden (inicio->fin) clonamos y volcamos en aux
        try {
            StackTDA<Posicion> aux = new LinkedStackTDA<>();
            StackTDA<Posicion> copia = new LinkedStackTDA<>();
            clonarPila(mejorRuta, copia); // copia contiene top=ultimo
            // vaciar copia en aux para invertir
            while (!copia.isEmpty()) aux.push(copia.pop());
            // ahora aux tiene orden inicio->fin en su top (last pushed)
            StringBuilder sb = new StringBuilder();
            while (!aux.isEmpty()) {
                Posicion p = aux.pop();
                sb.append(p.toString()
                );
                if (!aux.isEmpty()) sb.append(" -> ");
            }
            return sb.toString();
        } catch (StackEmptyExceptionTDA ex) {
            return "(error al formar la salida)";
        }
    }

    public boolean tieneMejorRuta() { return mejorRuta != null && !mejorRuta.isEmpty(); }
}
