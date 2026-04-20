/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

/**
 *
 * @author diego
 */
public class Editor {
    private Mapa mapa;
    private TipoCelda herramienta_actual;
    //private Item item_actual;
    
    public Editor(int filas, int columnas, String nombre){
        this.mapa = new Mapa(filas, columnas, nombre);
        this.herramienta_actual = TipoCelda.VACIO;
        //this.item_actual = null;
    }
}
