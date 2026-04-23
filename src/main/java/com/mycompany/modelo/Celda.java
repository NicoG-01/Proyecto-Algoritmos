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
    private Item item; 
    private boolean tiene_bomba;
    
    public Celda(){
        this.tipo_celda = TipoCelda.VACIO;
        this.item = null;
        this.tiene_bomba = false;
    }
    
    public Celda(TipoCelda pTipo_celda){
        this.tipo_celda = pTipo_celda;
        this.item = null;
        this.tiene_bomba = false;
    }
    
    public TipoCelda getTipoCelda(){
        return this.tipo_celda;
    }
   
    public void setTipoCelda(TipoCelda pCelda){
        this.tipo_celda = pCelda;
    }
    
    public Item getItem(){
        return this.item;
    }
    
    public void setItem(Item pItem){
        this.item = pItem;
    }
    
    public boolean tieneItem(){
        return this.item != null;
    }
}
