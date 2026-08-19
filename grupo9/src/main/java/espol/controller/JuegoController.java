/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.controller;

/**
 *
 * @author Joseph
 */

import espol.model.Juego;
import espol.model.Tablero;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class JuegoController {
    private Juego juego;
 
    @FXML
    private Label lblEstado;
 
    @FXML
    private Button btn00;
    @FXML
    private Button btn01;
    @FXML
    private Button btn02;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;
    @FXML
    private Button btn12;
    @FXML
    private Button btn20;
    @FXML
    private Button btn21;
    @FXML
    private Button btn22;
 
    private Button[][] botones;
 
    @FXML
    private void initialize() {
        juego = new Juego('X', 'O', true);
 
        botones = new Button[][]{
            {btn00, btn01, btn02},
            {btn10, btn11, btn12},
            {btn20, btn21, btn22}
        };
 
        actualizarInterfaz();
    }
 
    @FXML
    private void jugar00() {
        jugar(0, 0);
    }
 
    @FXML
    private void jugar01() {
        jugar(0, 1);
    }
 
    @FXML
    private void jugar02() {
        jugar(0, 2);
    }
 
    @FXML
    private void jugar10() {
        jugar(1, 0);
    }
 
    @FXML
    private void jugar11() {
        jugar(1, 1);
    }
 
    @FXML
    private void jugar12() {
        jugar(1, 2);
    }
 
    @FXML
    private void jugar20() {
        jugar(2, 0);
    }
 
    @FXML
    private void jugar21() {
        jugar(2, 1);
    }
 
    @FXML
    private void jugar22() {
        jugar(2, 2);
    }
 
    // Método central: lo llaman los 9 métodos jugarXX de arriba.
    private void jugar(int fila, int columna) {
 
        if (juego.termino()) {
            return;
        }
 
        boolean jugadaValida = juego.jugarHumano(fila, columna);
 
        if (!jugadaValida) {
            return; // casilla ocupada o no era el turno del humano
        }
 
        actualizarInterfaz();
 
        if (!juego.termino()) {
            juego.jugarComputadora();
            actualizarInterfaz();
        }
 
        mostrarResultadoSiTermino();
    }
 
    @FXML
    private void reiniciarJuego() {
        juego.reiniciar();
        actualizarInterfaz();
        lblEstado.setText("Tu turno (X)");
    }
 
    private void actualizarInterfaz() {
        Tablero tablero = juego.getTablero();
 
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
 
                char simbolo = tablero.obtener(fila, columna);
                botones[fila][columna].setText(simbolo == '\0' ? " " : String.valueOf(simbolo));
                botones[fila][columna].setDisable(simbolo != '\0');
            }
        }
    }
 
    private void mostrarResultadoSiTermino() {
        Tablero tablero = juego.getTablero();
 
        if (tablero.hayGanador('X')) {
            lblEstado.setText("¡Ganaste!");
            deshabilitarTodo();
        } else if (tablero.hayGanador('O')) {
            lblEstado.setText("Ganó la computadora");
            deshabilitarTodo();
        } else if (tablero.estaLleno()) {
            lblEstado.setText("Empate");
        } else {
            lblEstado.setText("Tu turno (X)");
        }
    }
 
    private void deshabilitarTodo() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                botones[fila][columna].setDisable(true);
            }
        }
    }
}