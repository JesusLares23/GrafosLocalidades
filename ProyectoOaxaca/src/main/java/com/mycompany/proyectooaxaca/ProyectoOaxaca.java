/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectooaxaca;

import Imagen.ImagenGrafo;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 *
 * @author Jesús Amarillas - 207653, Angel Beltrán - 244865, Jesús Lares - 233383
 */
public class ProyectoOaxaca {

    public static void main(String[] args) {
       // Soluciona el problema de caracteres especiales mostrados incorrectamente 
        if(!"UTF-8".equals(System.out.charset().displayName())){ 
            System.setOut(new PrintStream(new FileOutputStream(
                    FileDescriptor.out), true, StandardCharsets.UTF_8)); 
        }
        
        Grafo grafo = new Grafo();
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Mostrar grafo");
            System.out.println("2. Matriz de adyacencia");
            System.out.println("3. Arbol de esparcimiento mínimo");
            System.out.println("4. Ruta mas corta");
            System.out.println("5. Salir");
            System.out.print("Elija una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    ImagenGrafo.mostrar("src/imagen/Grafo_Localidades_Oaxaca.jpg");
                    grafo.imprimirGrafo();
                    break;
                case 2:
                    grafo.imprimirMatrizAdyacencia();
                    break;
                case 3:
                    grafo.kruskal();
                    grafo.kruskalVisual();
                    ImagenGrafo.mostrar("src/imagen/Oaxaca_MST.jpg");
                    break;
                case 4:
                    System.out.print("Ingrese el vertice de origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Ingrese el vertice de destino: ");
                    String destino = scanner.nextLine();
                    grafo.dijkstraRutaMasCorta(origen, destino);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 5);

        scanner.close();
    }

}
