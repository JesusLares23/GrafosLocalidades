
package org.itson.grafoslocalidades;

import grafos.Grafo;
import java.util.Scanner;

/**
 *
 * @author
 */
public class GrafosLocalidades {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Mostrar grafo");
            System.out.println("2. Árbol de esparcimiento mínimo");
            System.out.println("3. Ruta más corta");
            System.out.println("4. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    grafo.imprimirGrafo();
                    break;
                case 2:
                    grafo.kruskalVisual();
                    break;
                case 3:
                    System.out.print("Ingrese el vértice de origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ingrese el vértice de destino: ");
                    String destino = scanner.nextLine();
                    grafo.dijkstraRutaMasCorta(origen, destino);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);

        scanner.close();
    }
}
