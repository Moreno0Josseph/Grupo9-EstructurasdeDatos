/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.model;

import espol.computador.Minimax;

/**
 *
 * @author Joseph
 */
public class Juego {

    private Tablero tablero;
    private char simboloHumano;
    private char simboloComputadora;
    private boolean turnoHumano;

    private Minimax computador;

    public Juego(
            char simboloHumano,
            char simboloComputadora,
            boolean humanoInicia) {
        
        this.tablero = new Tablero();
        this.simboloHumano = simboloHumano;
        this.simboloComputadora = simboloComputadora;
        this.turnoHumano = humanoInicia;        
        
        this.computador = new Minimax(simboloComputadora, simboloHumano);
    }

    public boolean jugarHumano(int fila, int columna) {
        if (!turnoHumano) {
            return false;
        }

        boolean jugadaValida = tablero.colocar(fila, columna, simboloHumano);

        if (jugadaValida) {
            turnoHumano = false;
        }

        return jugadaValida;
    }

    public void jugarComputadora() {
        int[] jugada = computador.mejorJugada(tablero);

        if (jugada != null) {
            tablero.colocar(jugada[0], jugada[1], simboloComputadora);
        }

        turnoHumano = true;        
    }

    public boolean termino() {
        return tablero.hayGanador(simboloHumano)
                || tablero.hayGanador(simboloComputadora)
                || tablero.estaLleno();
    }

    public void reiniciar() {
        this.tablero = new Tablero();
        this.turnoHumano = true;
    }
    public Tablero getTablero(){
        return tablero;
    }
    
}
