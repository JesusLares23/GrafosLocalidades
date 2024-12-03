/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectooaxaca;

/**
 *
 * @author j_ama
 */
public class Arista {

    private Vertice origen;
    private Vertice destino;
    int peso;

    public Arista(Vertice origen, Vertice destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }
    
    public Vertice getOrigen() {
        return origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
    
    @Override
    public String toString() {
        return origen.getNombre() + " - " + destino.getNombre() + " : " + peso;
    }
}
