/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.model;

/**
 *
 * @author Joseph
 */
public class Tablero { //Representa el estado concreto del juego

    private char[][] casillas;

    public Tablero() {
        casillas = new char[3][3];
    }

    public boolean colocar(int fila, int columna, char simbolo) {

        if (estaLibre(fila, columna)) {
            casillas[fila][columna] = simbolo;
            return true;
        }

        return false;
    }

    public boolean estaLibre(int fila, int columna) { //un char[][] recién creado contiene '\0' en las posiciones vacías.
        return casillas[fila][columna] == '\0';
    }

    public char obtener(int fila, int columna) { //getter de solo lectura, usado por la interfaz gráfica
        return casillas[fila][columna];
    }

    public boolean hayGanador(char simbolo) {

        // Filas
        for (int i = 0; i < 3; i++) {
            if (casillas[i][0] == simbolo
                    && casillas[i][1] == simbolo
                    && casillas[i][2] == simbolo) {

                return true;
            }
        }

        // Columnas
        for (int j = 0; j < 3; j++) {
            if (casillas[0][j] == simbolo
                    && casillas[1][j] == simbolo
                    && casillas[2][j] == simbolo) {

                return true;
            }
        }

        // Diagonal principal
        if (casillas[0][0] == simbolo
                && casillas[1][1] == simbolo
                && casillas[2][2] == simbolo) {

            return true;
        }

        // Diagonal secundaria
        if (casillas[0][2] == simbolo
                && casillas[1][1] == simbolo
                && casillas[2][0] == simbolo) {

            return true;
        }

        return false;
    }

    public boolean estaLleno() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (estaLibre(i, j)) { //si existe al menos una casilla libre, entonces no está lleno
                    return false;
                }
            }
        }
        return true;
    }

    public int calcularUtilidad(char jugador, char oponente) {// Pjugador - Poponente
        int pJugador = calcularP(jugador, oponente);
        int pOponente = calcularP(oponente, jugador);

        return pJugador - pOponente;
    }

    private int calcularP(char jugador, char oponente) { // una línea está disponible para un jugador si no contiene ningún símbolo del oponente.
        int contador = 0;

        // Revisar filas
        for (int i = 0; i < 3; i++) {

            if (casillas[i][0] != oponente
                    && casillas[i][1] != oponente
                    && casillas[i][2] != oponente) {

                contador++;
            }
        }

        // Revisar columnas
        for (int j = 0; j < 3; j++) {

            if (casillas[0][j] != oponente
                    && casillas[1][j] != oponente
                    && casillas[2][j] != oponente) {

                contador++;
            }
        }

        // Diagonal principal
        if (casillas[0][0] != oponente
                && casillas[1][1] != oponente
                && casillas[2][2] != oponente) {

            contador++;
        }

        // Diagonal secundaria
        if (casillas[0][2] != oponente
                && casillas[1][1] != oponente
                && casillas[2][0] != oponente) {

            contador++;
        }

        return contador;                                  //Pj como el número de filas, columnas y diagonales disponibles para el jugador j.
    }

    public Tablero copiar() { //para construir el árbol de posibles jugadas sin modificar el tablero actual.
        Tablero copia = new Tablero();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copia.casillas[i][j] = this.casillas[i][j];
            }
        }

        return copia;
    }
}
