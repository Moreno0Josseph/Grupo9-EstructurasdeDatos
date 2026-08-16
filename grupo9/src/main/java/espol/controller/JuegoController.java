/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.controller;

/**
 *
 * @author Joseph
 */
public class JuegoController {

    private Juego juego;

    @FXML
    private Button btn00;

    @FXML
    private Button btn01;

    // ... los nueve botones

    @FXML
    private void jugar00() {

        juego.jugarHumano(0, 0);

        actualizarInterfaz();

        if (!juego.termino()) {
            juego.jugarComputadora();
            actualizarInterfaz();
        }
    }

    private void actualizarInterfaz() {
        // ...
    }
}
