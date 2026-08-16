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
        // ...
    }

    public boolean estaLibre(int fila, int columna) {
        // ...
    }

    public boolean hayGanador(char simbolo) {
        // ...
    }

    public boolean estaLleno() {
        // ...
    }

    public int calcularUtilidad(char jugador, char oponente) {
        // Pjugador - Poponente
    }

    public Tablero copiar() {
        // ...
    }
}
