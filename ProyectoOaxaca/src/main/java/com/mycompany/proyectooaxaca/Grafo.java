/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectooaxaca;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.layout.mxCircleLayout;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author Jesús Amarillas - 207653, Angel Beltrán - 244865, Jesús Lares - 233383
 */
public class Grafo {

    private int[][] matrizAdy;
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public Grafo() {
        matrizAdy = new int[0][0];
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
        inicializarGrafo();
    }

    private Vertice buscarVertice(String nombre) {
        for (Vertice v : vertices) {
            if (v.getNombre().equals(nombre)) {
                return v;
            }
        }
        return null;
    }

    public void agregarVertice(String nombre) {
        if (buscarVertice(nombre) == null) {
            vertices.add(new Vertice(nombre));
        }
        // Redimensionamos la matriz de adyacencia
        int nuevoTamaño = vertices.size();
        int[][] nuevaMatriz = new int[nuevoTamaño][nuevoTamaño];

        // Copiamos los valores de la matriz anterior a la nueva matriz
        for (int i = 0; i < nuevoTamaño - 1; i++) {
            for (int j = 0; j < nuevoTamaño - 1; j++) {
                nuevaMatriz[i][j] = matrizAdy[i][j];
            }
        }
        // Inicializamos la nueva fila y columna con valores por defecto (-1, indicando que no hay arista)
        for (int i = 0; i < nuevoTamaño; i++) {
            nuevaMatriz[i][nuevoTamaño - 1] = -1; // No hay arista
            nuevaMatriz[nuevoTamaño - 1][i] = -1; // No hay arista
        }
        matrizAdy = nuevaMatriz;
    }

    public void eliminarVertice(String nombre) {
        // Buscar el vértice en la lista
        Vertice verticeAEliminar = buscarVertice(nombre);

        if (verticeAEliminar == null) {
            System.out.println("El vértice \"" + nombre + "\" no existe en el grafo.");
            return;
        }

        // Eliminar el vértice de la lista de vértices
        vertices.remove(verticeAEliminar);

        // Eliminar todas las aristas que conectan al vértice
        aristas.removeIf(arista -> arista.getOrigen().equals(verticeAEliminar)
                || arista.getDestino().equals(verticeAEliminar));

        System.out.println("El vértice \"" + nombre + "\" y sus conexiones han sido eliminados.");
    }

    public void agregarArista(String origen, String destino, int peso) {
        Vertice vOrigen = buscarVertice(origen);
        Vertice vDestino = buscarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            int i = vertices.indexOf(vOrigen);
            int j = vertices.indexOf(vDestino);

            // Actualizar la matriz de adyacencia
            matrizAdy[i][j] = peso;
            matrizAdy[j][i] = peso;

            // Agregar la arista a la lista de aristas
            aristas.add(new Arista(vOrigen, vDestino, peso));
            aristas.add(new Arista(vDestino, vOrigen, peso));
        }
    }

    public void eliminarArista(String origen, String destino) {
        Vertice vOrigen = buscarVertice(origen);
        Vertice vDestino = buscarVertice(destino);

        if (vOrigen == null || vDestino == null) {
            System.out.println("Uno o ambos vértices no existen.");
            return;
        }

        // Encontrar el índice de los vértices en la lista de vértices
        int i = vertices.indexOf(vOrigen);
        int j = vertices.indexOf(vDestino);

        // Eliminar la arista de la lista de aristas
        Arista aristaAEliminar = null;
        for (Arista arista : aristas) {
            if (arista.getOrigen().equals(vOrigen) && arista.getDestino().equals(vDestino)) {
                aristaAEliminar = arista;
                break;
            }
        }
        if (aristaAEliminar != null) {
            aristas.remove(aristaAEliminar);
            System.out.println("Arista eliminada: " + aristaAEliminar);
        } else {
            System.out.println("No se encontró la arista en la lista de aristas.");
        }

        // Actualizar la matriz de adyacencia
        if (i != -1 && j != -1) {
            matrizAdy[i][j] = -1; // Eliminar la conexión
            matrizAdy[j][i] = -1; // Para grafos no dirigidos
            System.out.println("Conexión eliminada de la matriz de adyacencia.");
        } else {
            System.out.println("Índices de vértices inválidos en la matriz de adyacencia.");
        }
    }

    public void destruir() {
        // Limpiar la lista de vértices y aristas
        vertices.clear();
        aristas.clear();

        // Liberar la matriz de adyacencia (asignándola a una nueva matriz vacía)
        matrizAdy = new int[0][0];

        System.out.println("El grafo ha sido destruido.");
    }

    public void imprimirGrafo() {
        Set<String> aristasImpresas = new HashSet<>();

        System.out.println("\nGrafo (Lista de Aristas):");
        for (Arista arista : aristas) {
            String identificador1 = arista.getOrigen().getNombre() + "-" + arista.getDestino().getNombre();
            String identificador2 = arista.getDestino().getNombre() + "-" + arista.getOrigen().getNombre();

            // Verificar si la arista ya fue impresa
            if (!aristasImpresas.contains(identificador1) && !aristasImpresas.contains(identificador2)) {
                System.out.println(arista);
                aristasImpresas.add(identificador1);
                aristasImpresas.add(identificador2);
            }
        }
    }

    public void imprimirMatrizAdyacencia() {
        if (matrizAdy == null || matrizAdy.length == 0) {
            System.out.println("La matriz de adyacencia está vacía o no ha sido inicializada.");
            return;
        }

        // Determinar el ancho máximo para cada celda
        int maxWidth = 0;

        // Considerar longitudes de nombres de vértices y pesos en la matriz
        for (Vertice vertice : vertices) {
            maxWidth = Math.max(maxWidth, vertice.getNombre().length());
        }

        for (int[] fila : matrizAdy) {
            for (int peso : fila) {
                String value = (peso == Integer.MAX_VALUE) ? "∞" : String.valueOf(peso);
                maxWidth = Math.max(maxWidth, value.length());
            }
        }

        // Añadir un pequeño margen para mayor claridad
        maxWidth += 2;

        // Encabezado de columnas
        System.out.printf("%-" + maxWidth + "s", ""); // Espacio vacío para el encabezado
        for (Vertice vertice : vertices) {
            System.out.printf("%-" + maxWidth + "s", vertice.getNombre());
        }
        System.out.println();

        // Filas de la matriz
        for (int i = 0; i < matrizAdy.length; i++) {
            // Encabezado de la fila (nombre del vértice)
            System.out.printf("%-" + maxWidth + "s", vertices.get(i).getNombre());

            // Valores de la fila
            for (int j = 0; j < matrizAdy[i].length; j++) {
                String value = (matrizAdy[i][j] == Integer.MAX_VALUE) ? "∞" : String.valueOf(matrizAdy[i][j]);
                System.out.printf("%-" + maxWidth + "s", value);
            }
            System.out.println();
        }
    }

    public void kruskal() {
        List<Arista> mst = new ArrayList<>();
        UnionFind uf = new UnionFind(vertices.size());
        Map<Vertice, Integer> verticeIndice = new HashMap<>();
        int sumaPesos = 0; // Inicializamos el contador de la suma de pesos

        // Asignar índices a los vértices
        for (int i = 0; i < vertices.size(); i++) {
            verticeIndice.put(vertices.get(i), i);
        }

        // Ordenar las aristas por peso
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Construcción del MST
        for (Arista arista : aristas) {
            int origen = verticeIndice.get(arista.getOrigen());
            int destino = verticeIndice.get(arista.getDestino());
            if (uf.union(origen, destino)) {
                mst.add(arista);
                sumaPesos += arista.getPeso(); // Sumar el peso de la arista al total
            }
        }

        // Imprimir el MST y la suma de pesos
        System.out.println("\nÁrbol de Expansión Mínima:");
        for (Arista a : mst) {
            System.out.println(a);
        }
        System.out.println("Suma total de pesos del MST: " + sumaPesos);
    }

    public List<Vertice> dijkstraRutaMasCorta(String origen, String destino) {
        Vertice vOrigen = buscarVertice(origen);
        Vertice vDestino = buscarVertice(destino);
        if (vOrigen == null || vDestino == null) {
            System.out.println("El vértice de origen o destino no existe.");
            return null;
        }

        Map<Vertice, Integer> distancias = new HashMap<>();
        Map<Vertice, Vertice> predecesores = new HashMap<>();
        PriorityQueue<Arista> cola = new PriorityQueue<>(Comparator.comparingInt(Arista::getPeso));

        for (Vertice v : vertices) {
            distancias.put(v, Integer.MAX_VALUE);
            predecesores.put(v, null);
        }
        distancias.put(vOrigen, 0);

        cola.add(new Arista(vOrigen, vOrigen, 0));

        while (!cola.isEmpty()) {
            Arista actual = cola.poll();
            Vertice u = actual.getDestino();

            if (u.equals(vDestino)) {
                break; // Si llegamos al destino, terminamos la búsqueda
            }

            for (Arista arista : aristas) {
                if (arista.getOrigen().equals(u)) {
                    Vertice v = arista.getDestino();
                    int nuevaDistancia = distancias.get(u) + arista.getPeso();
                    if (nuevaDistancia < distancias.get(v)) {
                        distancias.put(v, nuevaDistancia);
                        predecesores.put(v, u);
                        cola.add(new Arista(u, v, nuevaDistancia));
                    }
                }
            }
        }

        // Reconstruir la ruta desde el destino al origen
        List<Vertice> ruta = new ArrayList<>();
        Vertice paso = vDestino;
        while (paso != null) {
            ruta.add(0, paso); // Insertar al inicio para construir la ruta en orden
            paso = predecesores.get(paso);
        }

        // Verificar si la ruta existe
        if (ruta.size() == 1 && !ruta.get(0).equals(vOrigen)) {
            System.out.println("No hay ruta entre " + origen + " y " + destino);
            return null;
        }

        System.out.println("\nRuta más corta de " + origen + " a " + destino + ":");
        for (int i = 0; i < ruta.size(); i++) {
            System.out.print(ruta.get(i).getNombre());
            if (i < ruta.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println("\nDistancia total: " + distancias.get(vDestino));
        return ruta;
    }

    // Método para mostrar el grafo visualmente (opcional, si estás usando librerías gráficas)
    public void mostrarGrafoVisual(SimpleWeightedGraph<String, DefaultWeightedEdge> grafo) {
        // Adaptador para JGraphT (ejemplo de librería de gráficos)
        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter
                = new JGraphXAdapter<>(grafo);

        // Layout para distribuir los nodos en círculo
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        // Crear componente gráfico
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.setTitle("Grafo Generado");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void kruskalVisual() {
        List<Arista> mst = new ArrayList<>();
        UnionFind uf = new UnionFind(vertices.size());
        Map<Vertice, Integer> verticeIndice = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            verticeIndice.put(vertices.get(i), i);
        }

        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        for (Arista arista : aristas) {
            int origen = verticeIndice.get(arista.getOrigen());
            int destino = verticeIndice.get(arista.getDestino());
            if (uf.union(origen, destino)) {
                mst.add(arista);
            }
        }

        // Crear un nuevo grafo para mostrar el MST
        SimpleWeightedGraph<String, DefaultWeightedEdge> mstGraph
                = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        for (Vertice v : vertices) {
            mstGraph.addVertex(v.getNombre());
        }

        for (Arista a : mst) {
            mstGraph.addEdge(a.getOrigen().getNombre(), a.getDestino().getNombre());
            mstGraph.setEdgeWeight(
                    mstGraph.getEdge(a.getOrigen().getNombre(), a.getDestino().getNombre()),
                    a.getPeso());
        }

        // Mostrar el MST gráficamente
        mostrarGrafoVisual(mstGraph);
    }

    public void inicializarGrafo() {
        agregarVertice("Oaxaca");
        agregarVertice("Santa Lucia del Camino");
        agregarVertice("San Jacinto Amilpas");
        agregarVertice("Santa Maria Atzompa");
        agregarVertice("San Antonio de la Cal");
        agregarVertice("Santa Cruz Xoxocotlan");
        agregarVertice("Villa de Zaachila");
        agregarVertice("Mahuatlan de Porfirio Diaz");
        agregarVertice("Puerto Escondido");
        agregarVertice("Crucecita");
        agregarVertice("Santiago Pinotepa Nacional");
        agregarVertice("Tlaxiaco");
        agregarVertice("Huajuapan de Leon");
        agregarVertice("Tuxtepec");
        agregarVertice("Loma Bonita");
        agregarVertice("Matias Romero Avendaño");
        agregarVertice("Ixtepec");
        agregarVertice("Santo Domingo Tehuantepec");
        agregarVertice("Salina Cruz");
        agregarVertice("Juchitlan de Zaragoza");

        //Adyacencias con Oaxaca
        agregarArista("Oaxaca", "Santa Lucia del Camino", 4);
        agregarArista("Oaxaca", "Santa Maria Atzompa", 8);
        agregarArista("Oaxaca", "San Jacinto Amilpas", 8);

        //Adyacencias con Santa Lucia del Camino
        agregarArista("Santa Lucia del Camino", "Tuxtepec", 215);
        agregarArista("Santa Lucia del Camino", "Loma Bonita", 327);
        agregarArista("Santa Lucia del Camino", "Matias Romero Avendaño", 394);
        agregarArista("Santa Lucia del Camino", "Santo Domingo Tehuantepec", 246);

        agregarArista("Tuxtepec", "Loma Bonita", 37);

        //Adyacencias con Santa Maria Atzompa
        agregarArista("Santa Maria Atzompa", "San Jacinto Amilpas", 2);

        //Adjacencias con San Jacinto Amilpas
        agregarArista("San Jacinto Amilpas", "Huajapan de Leon", 165);
        agregarArista("San Jacinto Amilpas", "Tlaxiaco", 158);
        agregarArista("San Jacinto Amilpas", "Tuxtepec", 356);

        agregarArista("Huajuapan de Leon", "Tlaxiaco", 120);
        agregarArista("Huajuapan de Leon", "Santiago Pinotepa Nacional", 286);
        agregarArista("Tlaxiaco", "Santiago Pinotepa Nacional", 217);

        agregarArista("Santiago Pinotepa Nacional", "Puerto Escondido", 153);

        //Adjacencias con Puerto Escondido
        agregarArista("Puerto Escondido", "Mahuatlan de Porfirio Diaz", 145);
        agregarArista("Puerto Escondido", "Villa de Zaachila", 239);
        agregarArista("Puerto Escondido", "Crucecita", 114);

        //Adjacencias con Mahuatlan de Porfirio Diaz
        agregarArista("Mahuatlan de Porfirio Diaz", "Villa de Zaachila", 94);
        agregarArista("Mahuatlan de Porfirio Diaz", "San Antonio de la Cal", 99);
        agregarArista("Mahuatlan de Porfirio Diaz", "Crucecita", 235);

        agregarArista("Villa de Zaachila", "Santa Cruz Xoxocotlan", 11);

        agregarArista("Santa Cruz Xoxocotlan", "San Antonio de la Cal", 7);
        agregarArista("San Antonio de la Cal", "Santa Lucia del Camino", 6);

        agregarArista("Crucecita", "Salina Cruz", 146);

        agregarArista("Salina Cruz", "Santo Domingo Tehuantepec", 16);

        agregarArista("Santo Domingo Tehuantepec", "Juchitlan de Zaragoza", 28);
        agregarArista("Santo Domingo Tehuantepec", "Ixtepec", 39);

        agregarArista("Juchitlan de Zaragoza", "Ixtepec", 21);

        agregarArista("Ixtepec", "Matias Romero Avendaño", 69);

        agregarArista("Matias Romero Avendaño", "Loma Bonita", 209);
    }

}
