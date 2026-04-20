/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

/**
 *
 * @author diego
 */
public class Mapa {
    private Celda[][] dimension;
    private String nombre; 
    
    public Mapa(int filas, int columnas, String nombre){
        this.dimension = new Celda[filas][columnas];
        this.nombre = nombre;
    }
}
