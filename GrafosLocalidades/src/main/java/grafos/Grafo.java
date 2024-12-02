
package grafos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author darkm
 */
public class Grafo {
    private int[][] matrizAdy;
    private List<String> vertices;
    private int numAristas;
    
    public Grafo(int tam) {
        matrizAdy = new int[tam][tam];
        vertices = new ArrayList<>();
        numAristas = 0;
    }

    public void agregarVertice(String vertice) {
        if (!vertices.contains(vertice)) {
            vertices.add(vertice);
        }
    }

    public void eliminarVertice(String vertice) {
        int indice = vertices.indexOf(vertice);
        if (indice != -1) {
            vertices.remove(indice);
            for (int i = 0; i < matrizAdy.length; i++) {
                matrizAdy[i][indice] = 0;
                matrizAdy[indice][i] = 0;
            }
        }
    }

    public String getVertice(int indice) {
        if (indice >= 0 && indice < vertices.size()) {
            return vertices.get(indice);
        }
        return null;
    }

    public int getIndice(String vertice) {
        return vertices.indexOf(vertice);
    }

    public int getNumVertices() {
        return vertices.size();
    }

    public List<String> getVertices() {
        return new ArrayList<>(vertices);
    }

    public int[][] getAdyacencia() {
        return matrizAdy;
    }

    public boolean estaVacio() {
        return vertices.isEmpty();
    }

    public void limpiar() {
        vertices.clear();
        matrizAdy = new int[matrizAdy.length][matrizAdy.length];
        numAristas = 0;
    }

    public void agregarArista(String vertice1, String vertice2, int peso) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1 && matrizAdy[indice1][indice2] == 0) {
            matrizAdy[indice1][indice2] = peso;
            matrizAdy[indice2][indice1] = peso;
            numAristas++;
        }
    }

    public void eliminarArista(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1 && matrizAdy[indice1][indice2] != 0) {
            matrizAdy[indice1][indice2] = 0;
            matrizAdy[indice2][indice1] = 0;
            numAristas--;
        }
    }

    public int getPesoArista(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1) {
            return matrizAdy[indice1][indice2];
        }
        return 0;
    }

    public void setPesoArista(String vertice1, String vertice2, int peso) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        if (indice1 != -1 && indice2 != -1) {
            matrizAdy[indice1][indice2] = peso;
            matrizAdy[indice2][indice1] = peso;
        }
    }

    public boolean esAdyacente(String vertice1, String vertice2) {
        int indice1 = vertices.indexOf(vertice1);
        int indice2 = vertices.indexOf(vertice2);
        return indice1 != -1 && indice2 != -1 && matrizAdy[indice1][indice2] != 0;
    }

    public int getNumAristas() {
        return numAristas;
    }

    public void imprimirGrafo() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + ": ");
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(matrizAdy[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void kruskal() {
        List<Arista> aristas = new ArrayList<>();
        for (int i = 0; i < matrizAdy.length; i++) {
            for (int j = i + 1; j < matrizAdy[i].length; j++) {
                if (matrizAdy[i][j] != 0) {
                    aristas.add(new Arista(i, j, matrizAdy[i][j]));
                }
            }
        }

        aristas.sort(Comparator.comparingInt(edge -> edge.peso));

        BusquedaUnion busquedaUnion = new BusquedaUnion(vertices.size());
        List<Arista> mst = new ArrayList<>();

        for (Arista arista : aristas) {
            if (busquedaUnion.union(arista.origen, arista.destino)) {
                mst.add(arista);
            }
        }

        System.out.println("Aristas del Árbol de Expansión Mínima:");
        for (Arista arista : mst) {
            System.out.println(vertices.get(arista.origen) + " - " + vertices.get(arista.destino) + " : " + arista.peso);
        }
    }
    
    public void dijkstra(String fuente) {
        int nVertices = vertices.size();
        int[] distancia = new int[nVertices];
        boolean[] visitado = new boolean[nVertices];
        PriorityQueue<Par> pq = new PriorityQueue<>(nVertices, Comparator.comparingInt(pair -> pair.distancia));

        Arrays.fill(distancia, Integer.MAX_VALUE);
        distancia[getIndice(fuente)] = 0;
        pq.add(new Par(getIndice(fuente), 0));

        while (!pq.isEmpty()) {
            int u = pq.poll().nodo;

            if (visitado[u]) continue;
            visitado[u] = true;

            for (int v = 0; v < nVertices; v++) {
                if (matrizAdy[u][v] != 0 && !visitado[v] && distancia[u] != Integer.MAX_VALUE && distancia[u] + matrizAdy[u][v] < distancia[v]) {
                    distancia[v] = distancia[u] + matrizAdy[u][v];
                    pq.add(new Par(v, distancia[v]));
                }
            }
        }

        imprimirSolucion(distancia);
    }

    private void imprimirSolucion(int[] distancia) {
        System.out.println("Distancia desde el nodo fuente:");
        for (int i = 0; i < distancia.length; i++) {
            System.out.println(vertices.get(i) + " -> " + distancia[i]);
        }
    }

}
