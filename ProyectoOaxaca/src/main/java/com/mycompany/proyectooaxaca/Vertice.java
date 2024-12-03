/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectooaxaca;

import java.util.Objects;

/**
 *
 * @author Jesús Amarillas - 207653, Angel Beltrán - 244865, Jesús Lares - 233383
 */
public class Vertice {
    private String nombre;

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertice vertice = (Vertice) obj;
        return Objects.equals(nombre, vertice.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
