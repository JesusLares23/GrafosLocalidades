package grafos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jesús Amarillas - ######, Angel Beltrán - 244865, Jesús Lares -
 * 233383
 */
public class Grafo {

    private int[][] matrizAdj;
    private List<String> vertices;
    private int numAristas;

    // Constructor que crea un grafo vacío
    public Grafo(int tam) {
        matrizAdj = new int[tam][tam];
        vertices = new ArrayList<>();
        numAristas = 0;
    }

    // agregarVertice que agrega un vértice al grafo, si no existe
    public void agregarVertice(String vertice) {
        if (!vertices.contains(vertice)) {
            vertices.add(vertice);
        }
    }

    // eliminarVertice que elimina un vértice del grafo, si existe
    public void eliminarVertice(String vertice) {
        int indice = vertices.indexOf(vertice);
        if (indice != -1) {
            vertices.remove(indice);
            for (int i = 0; i < matrizAdj.length; i++) {
                matrizAdj[i][indice] = 0;
                matrizAdj[indice][i] = 0;
            }
        }
    }

    // getVertice que regresa el vértice de un índice dado en la lista de vértices
    public String getVertice(int indice) {
        if (indice >= 0 && indice < vertices.size()) {
            return vertices.get(indice);
        }
        return null;
    }

    // getIndice que regresa el índice del vértice dado en la lista de vértices
    public int getIndice(String vertice) {
        return vertices.indexOf(vertice);
    }

    // getNumVertices que obtiene el número de vértices en un grafo
    public int getNumVertices() {
        return vertices.size();
    }

    // getVertices que regresa una lista de los vértices del grafo
    public List<String> getVertices() {
        return new ArrayList<>(vertices);
    }

    // getMatrizAdj que regresa la matriz de adyacencias
    public int[][] getMatrizAdj() {
        return matrizAdj;
    }

    // estaVacio que determina si un grafo está vacío
    public boolean estaVacio() {
        return vertices.isEmpty();
    }

    // limpiar que elimina todos los vértices y aristas de un grafo
    public void limpiar() {
        vertices.clear();
        matrizAdj = new int[matrizAdj.length][matrizAdj.length];
        numAristas = 0;
    }

    // agregarArista que agrega una arista entre dos vértices si no existe
    public void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1 && matrizAdj[indice1][indice2] == 0) {
            matrizAdj[indice1][indice2] = peso;
            matrizAdj[indice2][indice1] = peso; // Para grafos no dirigidos
            numAristas++;
        }
    }

    // eliminarArista que elimina la arista entre dos vértices si existe
    public void eliminarArista(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1 && matrizAdj[indice1][indice2] != 0) {
            matrizAdj[indice1][indice2] = 0;
            matrizAdj[indice2][indice1] = 0; // Para grafos no dirigidos
            numAristas--;
        }
    }

    // getPesoArista que obtiene el peso de una arista
    public int getPesoArista(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1) {
            return matrizAdj[indice1][indice2];
        }
        return 0;
    }

    // setPesoArista que establece el peso de una arista
    public void setPesoArista(String vertice1, String vertice2, int peso) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1) {
            matrizAdj[indice1][indice2] = peso;
            matrizAdj[indice2][indice1] = peso; // Para grafos no dirigidos
        }
    }

    // sonAdjacentes que determina si hay una arista entre dos vértices
    public boolean sonAdjacentes(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        return indice1 != -1 && indice2 != -1 && matrizAdj[indice1][indice2] != 0;
    }

    // getNumAristas que determina el número de aristas de un grafo
    public int getNumAristas() {
        return numAristas;
    }

    // Método para imprimir el grafo
    public void imprimirGrafo() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + ": ");
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(matrizAdj[i][j] + " ");
            }
            System.out.println();
        }
    }
}
