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
    private boolean vivo;
    
    public Jugador(){
        this.posicion_x = -1;
        this.posicion_y = -1;
        this.nombre = null;
        this.teclas = null;
        this.velocidad = 1;
        this.vida = 5;
        this.max_bomba = 1;
        this.rango_bomba = 1;
        this.vivo = true;
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
    
    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public int getPosicionX() {
        return posicion_x;
    }

    public void setPosicionX(int x) {
        this.posicion_x = x;
    }

    public int getPosicionY() {
        return posicion_y;
    }

    public void setPosicionY(int y) {
        this.posicion_y = y;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void cogerItem(Item ventaja){
        ventaja.aplicarEfecto(this);               
    }
    
    public void mover(int nuevo_posicionx, int nuevo_posiciony, Mapa mapa){
        int nuevox = getPosicionX()+ nuevo_posicionx;
        int nuevoy = getPosicionY()+ nuevo_posiciony;
        
        if (nuevox <0 || nuevox >mapa.getColumnas()) return;
        if (nuevoy <0 || nuevoy >mapa.getFilas()) return;
        
        TipoCelda tipo = mapa.getCelda(nuevoy, nuevox).getTipoCelda();
        
        if (tipo == TipoCelda.PARED_DESTRUCTIBLE) return;
        if (tipo == TipoCelda.PARED_FIJA)return;
        
        setPosicionX(nuevox);
        setPosicionY(nuevoy);
        
        Celda celda = mapa.getCelda(nuevoy, nuevox);
        if (celda.tieneItem()){
            cogerItem(celda.getItem());
            celda.setItem(null);
        }    
    }                
}

