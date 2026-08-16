/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.model;

/**
 *
 * @author Joseph
 */
public class Juego {

    private Tablero tablero;
    private char simboloHumano;
    private char simboloComputadora;
    private boolean turnoHumano;

    private Minimax ia;

    public Juego(
            char simboloHumano,
            char simboloComputadora,
            boolean humanoInicia) {

        // ...
    }

    public boolean jugarHumano(int fila, int columna) {
        // ...
    }

    public void jugarComputadora() {
        // llamar Minimax
    }

    public boolean termino() {
        // ...
    }

    public void reiniciar() {
        // ...
    }
}
