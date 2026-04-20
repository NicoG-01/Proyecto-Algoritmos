/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

/**
 *
 * @author diego
 */

public class Celda {
    private TipoCelda tipo_celda;
    // private Item item; atributo de tipo clase Item
    private boolean tiene_bomba;
    
    public Celda(){
        this.tipo_celda = TipoCelda.VACIO;
        //this.item = null;
        this.tiene_bomba = false;
    }
}
