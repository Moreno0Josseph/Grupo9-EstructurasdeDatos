package espol.controller;

import espol.model.Juego;
import espol.model.ModoJuego;
import espol.model.Tablero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;

public class JuegoController {

    private Juego juego;

    // Timeline para Computadora vs Computadora para que no se muestre inmediatamente quien ganó
    private Timeline timelineComputadoras;
    private int victoriasHumano;
    private int victoriasComputadora;
    private int empates;
    private boolean resultadoContabilizado;
    // ESTADO

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblvictoriasHumano;
    private Label lblvictoriasComputadora;
    private Label lblempates;
    // MODO DE JUEGO

    @FXML
    private RadioButton rbHumanoVsComputadora;

    @FXML
    private RadioButton rbComputadoraVsComputadora;

    private ToggleGroup grupoModo;

    // CONFIGURACIÓN HUMANO VS COMPUTADORA
    @FXML
    private RadioButton rbHumanoX;

    @FXML
    private RadioButton rbHumanoO;

    @FXML
    private RadioButton rbIniciaHumano;

    @FXML
    private RadioButton rbIniciaComputadora;

    private ToggleGroup grupoSimbolo;
    private ToggleGroup grupoInicio;

    // BOTONES GENERALES
    @FXML
    private Button btnComenzar;

    @FXML
    private Button btnReiniciar;

    // TABLERO
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

    // INITIALIZE
    @FXML
    private void initialize() {
        victoriasHumano = 0;
        victoriasComputadora = 0;
        empates = 0;
        resultadoContabilizado = false;
        botones = new Button[][]{
            {btn00, btn01, btn02},
            {btn10, btn11, btn12},
            {btn20, btn21, btn22}
        };

        // -------------------------
        // Grupo del modo de juego
        // -------------------------
        grupoModo = new ToggleGroup();

        rbHumanoVsComputadora.setToggleGroup(grupoModo);
        rbComputadoraVsComputadora.setToggleGroup(grupoModo);

        // -------------------------
        // Grupo del símbolo
        // -------------------------
        grupoSimbolo = new ToggleGroup();

        rbHumanoX.setToggleGroup(grupoSimbolo);
        rbHumanoO.setToggleGroup(grupoSimbolo);

        // -------------------------
        // Grupo de quién inicia
        // -------------------------
        grupoInicio = new ToggleGroup();

        rbIniciaHumano.setToggleGroup(grupoInicio);
        rbIniciaComputadora.setToggleGroup(grupoInicio);

        // Valores iniciales
        rbHumanoVsComputadora.setSelected(true);
        rbHumanoX.setSelected(true);
        rbIniciaHumano.setSelected(true);

        juego = null;

        /*
         * Si el usuario cambia el modo,
         * habilitamos o deshabilitamos las opciones
         * específicas del humano.
         */
        rbHumanoVsComputadora.setOnAction(e
                -> actualizarControlesConfiguracion());

        rbComputadoraVsComputadora.setOnAction(e
                -> actualizarControlesConfiguracion());

        actualizarControlesConfiguracion();

        lblEstado.setText(
                "Selecciona la configuración y comienza la partida"
        );

        limpiarTablero();

    }

    // CONFIGURACIÓN DE CONTROLES
    private void actualizarControlesConfiguracion() {

        boolean modoComputadoras
                = rbComputadoraVsComputadora.isSelected();

        /*
         * En Computadora vs Computadora no necesitamos
         * escoger símbolo humano ni quién comienza.
         */
        rbHumanoX.setDisable(modoComputadoras);
        rbHumanoO.setDisable(modoComputadoras);

        rbIniciaHumano.setDisable(modoComputadoras);
        rbIniciaComputadora.setDisable(modoComputadoras);
    }

    // COMENZAR PARTIDA
    @FXML
    private void comenzarJuego() {

        detenerTimeline();
        resultadoContabilizado = false;

        // COMPUTADORA VS COMPUTADORA
        if (rbComputadoraVsComputadora.isSelected()) {

            juego = new Juego();

            actualizarInterfaz();
            mostrarEstado();

            iniciarComputadoraVsComputadora();

            return;
        }

        // HUMANO VS COMPUTADORA
        char simboloHumano;

        if (rbHumanoX.isSelected()) {
            simboloHumano = 'X';
        } else {
            simboloHumano = 'O';
        }

        char simboloComputadora;

        if (simboloHumano == 'X') {
            simboloComputadora = 'O';
        } else {
            simboloComputadora = 'X';
        }

        boolean humanoInicia
                = rbIniciaHumano.isSelected();

        juego = new Juego(
                simboloHumano,
                simboloComputadora,
                humanoInicia
        );

        /*
         * Si comienza la computadora,
         * realiza inmediatamente su primera jugada.
         */
        if (!juego.esTurnoHumano()) {

            juego.jugarComputadora();
        }

        actualizarInterfaz();
        mostrarEstado();
    }

    // COMPUTADORA VS COMPUTADORA
    private void iniciarComputadoraVsComputadora() {

        timelineComputadoras = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        evento -> ejecutarTurnoComputadora()
                )
        );

        timelineComputadoras.setCycleCount(
                Animation.INDEFINITE
        );

        timelineComputadoras.play();
    }

    private void ejecutarTurnoComputadora() {

        if (juego == null) {
            detenerTimeline();
            return;
        }

        if (juego.termino()) {

            detenerTimeline();
            mostrarEstado();

            return;
        }

        /*
         * Realiza exactamente UNA jugada.
         */
        juego.jugarTurnoComputadora();

        actualizarInterfaz();
        mostrarEstado();

        /*
         * Verificar si esa jugada terminó la partida.
         */
        if (juego.termino()) {
            detenerTimeline();
        }
    }

    private void detenerTimeline() {

        if (timelineComputadoras != null) {

            timelineComputadoras.stop();
            timelineComputadoras = null;
        }
    }

    // JUGADAS DEL HUMANO
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

    // MÉTODO CENTRAL DE JUGADA HUMANA
    private void jugar(int fila, int columna) {

        if (juego == null) {
            return;
        }

        /*
         * Si estamos en Computadora vs Computadora,
         * el humano no puede tocar el tablero.
         */
        if (juego.getModo()
                != ModoJuego.HUMANO_VS_COMPUTADORA) {

            return;
        }

        if (juego.termino()) {
            return;
        }

        boolean jugadaValida
                = juego.jugarHumano(
                        fila,
                        columna
                );

        if (!jugadaValida) {
            return;
        }

        actualizarInterfaz();

        // Humano pudo haber ganado
        if (juego.termino()) {

            mostrarEstado();
            return;
        }

        // Turno de la computadora
        juego.jugarComputadora();

        actualizarInterfaz();
        mostrarEstado();
    }

    // REINICIAR
    @FXML
    private void reiniciarJuego() {

        if (juego == null) {
            return;
        }

        detenerTimeline();
        resultadoContabilizado = false;

        juego.reiniciar();

        // Computadora vs Computadora
        if (juego.getModo()
                == ModoJuego.COMPUTADORA_VS_COMPUTADORA) {

            actualizarInterfaz();
            mostrarEstado();

            iniciarComputadoraVsComputadora();

            return;
        }

        // Humano vs Computadora
        if (!juego.esTurnoHumano()) {
            juego.jugarComputadora();
        }

        actualizarInterfaz();
        mostrarEstado();
    }

    // ACTUALIZAR TABLERO
    private void actualizarInterfaz() {

        if (juego == null) {

            limpiarTablero();
            return;
        }

        Tablero tablero
                = juego.getTablero();

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0;
                    columna < 3;
                    columna++) {

                char simbolo
                        = tablero.obtener(
                                fila,
                                columna
                        );

                if (simbolo == '\0') {

                    botones[fila][columna]
                            .setText("");

                } else {

                    botones[fila][columna]
                            .setText(
                                    String.valueOf(simbolo)
                            );
                }

                boolean deshabilitar;

                /*
                 * En PC vs PC el tablero siempre
                 * está deshabilitado para el usuario.
                 */
                if (juego.getModo()
                        == ModoJuego.COMPUTADORA_VS_COMPUTADORA) {

                    deshabilitar = true;

                } else {

                    deshabilitar
                            = simbolo != '\0'
                            || !juego.esTurnoHumano()
                            || juego.termino();
                }

                botones[fila][columna]
                        .setDisable(deshabilitar);
            }
        }
    }

    // MOSTRAR ESTADO
    private void mostrarEstado() {

        if (juego == null) {

            lblEstado.setText(
                    "Selecciona una configuración"
            );

            return;
        }

        Tablero tablero
                = juego.getTablero();

        // COMPUTADORA VS COMPUTADORA
        if (juego.getModo()
                == ModoJuego.COMPUTADORA_VS_COMPUTADORA) {

            if (tablero.hayGanador('X')) {

                lblEstado.setText(
                        "Ganó la computadora X"
                );

                detenerTimeline();
                return;
            }

            if (tablero.hayGanador('O')) {

                lblEstado.setText(
                        "Ganó la computadora O"
                );

                detenerTimeline();
                return;
            }

            if (tablero.estaLleno()) {

                lblEstado.setText(
                        "Empate entre las computadoras"
                );

                detenerTimeline();
                return;
            }

            lblEstado.setText(
                    "Turno de la computadora "
                    + juego.getTurnoComputadora()
            );

            return;
        }

        // HUMANO VS COMPUTADORA
        if (tablero.hayGanador(
                juego.getSimboloHumano())) {

            lblEstado.setText(
                    "¡Ganaste!"
            );
            if (!resultadoContabilizado) {
                
                victoriasHumano++;
                resultadoContabilizado=true;
                actualizarMarcador();
                
            }

            deshabilitarTodo();
            return;
        }

        if (tablero.hayGanador(
                juego.getSimboloComputadora())) {

            lblEstado.setText(
                    "Ganó la computadora"
            );
            if (!resultadoContabilizado) {
                
                victoriasComputadora++;
               resultadoContabilizado=true;
                actualizarMarcador();
               
            }

            deshabilitarTodo();
            return;
        }

        if (tablero.estaLleno()) {

            lblEstado.setText(
                    "Empate"
            );

            deshabilitarTodo();
            return;
        }

        lblEstado.setText(
                "Tu turno ("
                + juego.getSimboloHumano()
                + ")"
        );
    }

    // AUXILIARES
    private void deshabilitarTodo() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0;
                    columna < 3;
                    columna++) {

                botones[fila][columna]
                        .setDisable(true);
            }
        }
    }

    private void limpiarTablero() {

        for (int fila = 0; fila < 3; fila++) {

            for (int columna = 0;
                    columna < 3;
                    columna++) {

                botones[fila][columna]
                        .setText("");

                botones[fila][columna]
                        .setDisable(true);
            }
        }
    }

    private void actualizarMarcador() {
        lblvictoriasHumano.setText("Humano: " + victoriasHumano);
        lblvictoriasComputadora.setText("Computadora: " + victoriasComputadora);
        lblempates.setText("Empates: " + empates);
    }
}
