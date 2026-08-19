package espol.model;

import espol.computador.Minimax;

public class Juego {

    private Tablero tablero;

    // Configuración Humano vs Computadora
    private char simboloHumano;
    private char simboloComputadora;
    private boolean humanoInicia; 
    private boolean turnoHumano;

    // Modo actual
    private ModoJuego modo;

    // para Humano vs Computadora
    private Minimax computador;

    // para Computadora vs Computadora
    private Minimax computadoraX;
    private Minimax computadoraO;

    // Indica qué computadora juega actualmente
    private char turnoComputadora;

    
    // CONSTRUCTOR: HUMANO VS COMPUTADORA
   

    public Juego(
            char simboloHumano,
            char simboloComputadora,
            boolean humanoInicia) {

        this.tablero = new Tablero();

        this.simboloHumano = simboloHumano;
        this.simboloComputadora = simboloComputadora;

        this.humanoInicia = humanoInicia;
        this.turnoHumano = humanoInicia;

        this.modo = ModoJuego.HUMANO_VS_COMPUTADORA;

        this.computador = new Minimax(
                simboloComputadora,
                simboloHumano
        );
    }

    
    // CONSTRUCTOR: COMPUTADORA VS COMPUTADORA
    

    public Juego() {

        this.tablero = new Tablero();

        this.modo =
                ModoJuego.COMPUTADORA_VS_COMPUTADORA;

        this.computadoraX =
                new Minimax('X', 'O');

        this.computadoraO =
                new Minimax('O', 'X');

        // X empieza
        this.turnoComputadora = 'X';
    }

    
    public boolean jugarHumano(int fila, int columna) {

        if (modo != ModoJuego.HUMANO_VS_COMPUTADORA) {
            return false;
        }

        if (!turnoHumano || termino()) {
            return false;
        }

        boolean jugadaValida =
                tablero.colocar(
                        fila,
                        columna,
                        simboloHumano
                );

        if (jugadaValida) {
            turnoHumano = false;
        }

        return jugadaValida;
    }

    
    // COMPUTADORA EN HUMANO VS COMPUTADORA
    

    public void jugarComputadora() {

        if (modo != ModoJuego.HUMANO_VS_COMPUTADORA) {
            return;
        }

        if (turnoHumano || termino()) {
            return;
        }

        int[] jugada =
                computador.mejorJugada(tablero);

        if (jugada != null) {

            tablero.colocar(
                    jugada[0],
                    jugada[1],
                    simboloComputadora
            );
        }

        turnoHumano = true;
    }

    //  COMPUTADORA VS COMPUTADORA
    

    public void jugarTurnoComputadora() {

        if (modo != ModoJuego.COMPUTADORA_VS_COMPUTADORA) {
            return;
        }

        if (termino()) {
            return;
        }

        Minimax computadoraActual;

        if (turnoComputadora == 'X') {
            computadoraActual = computadoraX;
        } else {
            computadoraActual = computadoraO;
        }

        int[] jugada =
                computadoraActual.mejorJugada(tablero);

        if (jugada != null) {

            tablero.colocar(
                    jugada[0],
                    jugada[1],
                    turnoComputadora
            );
        }

        // Cambiar el turno
        if (turnoComputadora == 'X') {
            turnoComputadora = 'O';
        } else {
            turnoComputadora = 'X';
        }
    }

    

    public boolean termino() {

        return tablero.hayGanador('X')
                || tablero.hayGanador('O')
                || tablero.estaLleno();
    }

    

    public void reiniciar() {

        tablero = new Tablero();

        if (modo == ModoJuego.HUMANO_VS_COMPUTADORA) {

            turnoHumano = humanoInicia;

        } else {

            turnoComputadora = 'X';
        }
    }

    

    public Tablero getTablero() {
        return tablero;
    }

    public char getSimboloHumano() {
        return simboloHumano;
    }

    public char getSimboloComputadora() {
        return simboloComputadora;
    }

    public boolean esTurnoHumano() {
        return turnoHumano;
    }

    public char getTurnoComputadora() {
        return turnoComputadora;
    }

    public ModoJuego getModo() {
        return modo;
    }
}