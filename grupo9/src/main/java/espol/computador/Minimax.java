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
    
    public Minimax(char SimboloComputadora, char simboloHumano){
        this.simboloComputadora = simboloComputadora;
        this.simboloHumano = simboloHumano;
    }
    
    // Paso 1, ver y guardar la mejor jugada a largo plazo
    public int[] mejorJugada(Tablero tableroActual){
        //tablero actual
        Tree<Tablero> arbol = new Tree<>(tableroActual);
        TreeNode<Tablero> raiz = arbol.getRoot();
        
        int mejorValor = Integer.MIN_VALUE;
        int[] mejorPosicion = null;
        
        //recorrer solo las casillas que esten vacias
        for(int f=0; f<3; f++){
            for(int c=0; c<3; c++){
                
                if(tableroActual.estaLibre(f,c)){
                    
                    // Se crea la tabla hijo que simula la posible jugada
                    Tablero tableroHijo = tableroActual.copiar();
                    tableroHijo.colocar(f,c,simboloComputadora);
                    //
                    TreeNode<Tablero> nodoHijo = new TreeNode<>(tableroHijo);
                    raiz.addChild(nodoHijo);
                    
                    construirArbol(nodoHijo,false);
                    int valor = evaluar(nodoHijo,false);
                    
                    if(valor>mejorValor){
                        mejorValor = valor;
                        mejorPosicion= new int[]{f,c};
                    } 
                }
            }
        }
        return mejorPosicion;
    }
    
    private void construirArbol(TreeNode<Tablero> nodo, boolean turnoCompu){
        Tablero tablero = nodo.getData();
        
        boolean esTerminal = tablero.hayGanador(simboloComputadora) ||
                tablero.hayGanador(simboloHumano) || tablero.estaLleno();
        
        if(esTerminal){
            return;
        }
        

        char simbolo = turnoCompu ? simboloComputadora : simboloHumano;

        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {

                if (tablero.estaLibre(fila, columna)) {

                    Tablero tableroHijo = tablero.copiar();
                    tableroHijo.colocar(fila, columna, simbolo);

                    TreeNode<Tablero> nodoHijo = new TreeNode<>(tableroHijo);
                    nodo.addChild(nodoHijo);

                    construirArbol(nodoHijo, !turnoCompu);
                }
            }
        }
    }        
    
    
    private int evaluar(TreeNode<Tablero> nodo, boolean turnoComputadora) {

        if (nodo.isLeaf()) {
            return nodo.getData().calcularUtilidad(simboloComputadora, simboloHumano);
        }

        if (turnoComputadora) {
            int mejorValor = Integer.MIN_VALUE;
            for (TreeNode<Tablero> hijo : nodo.getChildren()) {
                mejorValor = Math.max(mejorValor, evaluar(hijo, false));
            }
            return mejorValor;
        } else {
            int peorValor = Integer.MAX_VALUE;
            for (TreeNode<Tablero> hijo : nodo.getChildren()) {
                peorValor = Math.min(peorValor, evaluar(hijo, true));
            }
            return peorValor;
        }
    }    
    
    
}
