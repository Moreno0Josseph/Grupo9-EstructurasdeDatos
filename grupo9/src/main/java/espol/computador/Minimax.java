/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.computador;

/**
 *
 * @author Joseph
 */
import espol.model.Tablero;
import espol.tree.Tree;
import espol.tree.TreeNode;

public class Minimax {

    private char simboloComputadora;
    private char simboloHumano;

    public Minimax(char SimboloComputadora, char simboloHumano) {
        this.simboloComputadora = simboloComputadora;
        this.simboloHumano = simboloHumano;
    }

    // Paso 1, ver y guardar la mejor jugada a largo plazo
    public int[] mejorJugada(Tablero tableroActual) {
        //tablero actual
        Tree<Tablero> arbol = new Tree<>(tableroActual);
        TreeNode<Tablero> raiz = arbol.getRoot();

        int mejorValor = Integer.MIN_VALUE;
        int[] mejorPosicion = null;

        //recorrer solo las casillas que esten vacias
        for (int f = 0; f < 3; f++) {
            for (int c = 0; c < 3; c++) {

                if (tableroActual.estaLibre(f, c)) {

                    // Se crea la tabla hijo que simula la posible jugada
                    Tablero tableroHijo = tableroActual.copiar();
                    tableroHijo.colocar(f, c, simboloComputadora);
                    //
                    TreeNode<Tablero> nodoHijo = new TreeNode<>(tableroHijo);
                    raiz.addChild(nodoHijo);

                    generarRespuestasHumano(nodoHijo);
                    int valor = utilidadMinima(nodoHijo);

                    if (valor > mejorValor) {
                        mejorValor = valor;
                        mejorPosicion = new int[]{f, c};
                    }
                }
            }
        }
        return mejorPosicion;
    }

    private void generarRespuestasHumano(TreeNode<Tablero> nodoComputadora) {

        Tablero tablero = nodoComputadora.getData();

        if (tablero.hayGanador(simboloComputadora)
                || tablero.hayGanador(simboloHumano)
                || tablero.estaLleno()) {
            return;
        }

        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {

                if (tablero.estaLibre(fila, columna)) {

                    Tablero copia = tablero.copiar();
                    copia.colocar(fila, columna, simboloHumano);

                    nodoComputadora.addChild(
                            new TreeNode<>(copia)
                    );
                }
            }
        }
    }

    private int utilidadMinima(TreeNode<Tablero> nodo) {

        if (nodo.isLeaf()) {
            return nodo.getData().calcularUtilidad(
                    simboloComputadora,
                    simboloHumano
            );
        }

        int minimo = Integer.MAX_VALUE;

        for (TreeNode<Tablero> hijo : nodo.getChildren()) {

            int utilidad = hijo.getData().calcularUtilidad(
                    simboloComputadora,
                    simboloHumano
            );

            minimo = Math.min(minimo, utilidad);
        }

        return minimo;
    }

}
