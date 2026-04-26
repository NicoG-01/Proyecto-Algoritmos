/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    
    public String getNombre(){
        return this.nombre;
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
    
    private void crea_Mapa(){
        for (int f = 0; f < dimension.length; f++){
            for (int c = 0; c < dimension[f].length; c++){
                dimension[f][c] = new Celda();
            }
        }
    }
    
    public void guardar_Mapa(String ruta) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
        
        bw.write(nombre + "," + getFilas() + "," + getColumnas());
        bw.newLine();
        
        for (int f = 0; f < getFilas(); f++){
            for (int c = 0; c < getColumnas(); c++){
                bw.write(dimension[f][c].getTipoCelda().ordinal() + "");
                if (c < dimension[f].length - 1)
                    bw.write(",");
            }
            bw.newLine();
        }
        
        bw.close();
    }
}
