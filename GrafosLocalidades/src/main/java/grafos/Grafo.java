package grafos;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import javax.swing.JFrame;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 *
 * @author darkm
 */
public class Grafo {

    private int[][] matrizAdy;
    private List<Vertice> vertices;
    private List<Arista> aristas;

    public Grafo() {
        matrizAdy = new int[0][0];
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
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
        System.out.println("\nGrafo (Lista de Aristas):");
        for (Arista a : aristas) {
            System.out.println(a);
        }
    }

    public void kruskal() {
        // Lista para almacenar las aristas del Árbol de Esparcimiento Mínimo (MST)
        List<Arista> mst = new ArrayList<>();

        // Crear un objeto UnionFind para manejar la unión de vértices
        UnionFind uf = new UnionFind(vertices.size());

        // Mapa para asociar cada vértice con su índice en el conjunto
        Map<Vertice, Integer> verticeIndice = new HashMap<>();

        for (int i = 0; i < vertices.size(); i++) {
            verticeIndice.put(vertices.get(i), i);
        }

        // Ordenar las aristas en orden ascendente según el peso
        aristas.sort(Comparator.comparingInt(Arista::getPeso));

        // Iterar sobre las aristas ordenadas
        for (Arista arista : aristas) {
            // Obtener los índices de los vértices origen y destino
            int origen = verticeIndice.get(arista.getOrigen());
            int destino = verticeIndice.get(arista.getDestino());

            // Si los vértices no están conectados (es decir, no formarían un ciclo)
            if (uf.union(origen, destino)) {
                // Agregar la arista al MST
                mst.add(arista);
            }
        }

        // Imprimir el resultado: el Árbol de Expansión Mínima
        System.out.println("\nÁrbol de Esparcimiento Mínimo (MST):");
        for (Arista a : mst) {
            System.out.println(a);
        }
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

}
