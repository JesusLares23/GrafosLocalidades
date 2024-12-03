/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectooaxaca;

/**
 *
 * @author j_ama
 */
public class UnionFind {
    int[] padre;
    int[] rango;

    public UnionFind(int tam) {
        padre = new int[tam];
        rango = new int[tam];
        for (int i = 0; i < tam; i++) {
            padre[i] = i;
            rango[i] = 0;
        }
    }

    public int encontrar(int elemento) {
        if (padre[elemento] != elemento) {
            padre[elemento] = encontrar(padre[elemento]); // Path compression
        }
        return padre[elemento];
    }

    public boolean union(int set1, int set2) {
        int root1 = encontrar(set1);
        int root2 = encontrar(set2);

        if (root1 != root2) {
            if (rango[root1] > rango[root2]) {
                padre[root2] = root1;
            } else if (rango[root1] < rango[root2]) {
                padre[root1] = root2;
            } else {
                padre[root2] = root1;
                rango[root1]++;
            }
            return true;
        }
        return false;
    }
}
