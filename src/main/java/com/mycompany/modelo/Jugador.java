/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

/**
 *
 * @author ASUS
 */
public class Jugador {

    private int posicion_x;
    private int posicion_y;
    private String nombre;
    private char teclas[];
    private int velocidad;
    private int vida;
    private int max_bomba;
    private int rango_bomba;
    
    public Jugador(){
        this.posicion_x = -1;
        this.posicion_y = -1;
        this.nombre = null;
        this.teclas = null;
        this.velocidad = 1;
        this.vida = 5;
        this.max_bomba = 1;
        this.rango_bomba = 1;
    }
    
    public int getVelocidad(){
        return velocidad;
    }
    
    public int getRangoBomba(){
        return rango_bomba;
    }
    
    public int getMaxBomba(){
        return max_bomba;
    }
    
    public void setVelocidad(int pVelocidad){
        velocidad = pVelocidad;
    }
    
    public void setRangoBomba(int pRangoBomba){
        rango_bomba = pRangoBomba;
    }
    
    public void setMaxBomba(int pNumeroBombas){
        max_bomba = pNumeroBombas;
    }
}
