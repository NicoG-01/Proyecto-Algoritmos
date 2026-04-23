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
        crea_Mapa();
    }
    
    private void crea_Mapa(){
        for (int f = 0; f < dimension.length; f++){
            for (int c = 0; c < dimension[f].length; c++){
                dimension[f][c] = new Celda();
            }
        }
    }
    
    public int getFilas(){
        return this.dimension.length;
    }
    
    public int getColumnas(){
        return this.dimension[0].length;
    }
    
    public Celda getCelda(int fila, int columna){
        return this.dimension[fila][columna];
    }
}
