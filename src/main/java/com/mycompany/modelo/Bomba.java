/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Bomba {
    private int posicion_x;
    private int posicion_y;
    private int rango;
    private int temporizador;
    private Jugador dueño;
    
    public Bomba(int x, int y, int rango, Jugador dueño){
        this.posicion_x = x;
        this.posicion_y = y;
        this.rango = rango;
        this.temporizador = 20;
        this.dueño = dueño;
    }
    
    public int getPosicion_x(){
        return posicion_x;
    }
    
    public int getPosicion_y(){
        return posicion_y;
    }
    
    public int getRango(){
        return rango;
    }
    
    public int getTemporizador(){
        return temporizador;
    }
    
    public Jugador getJugador(){
        return dueño;
    }
    
    public boolean bombaExplotada(){
        return temporizador <= 0;
    }
    
    public void actualizarTemp(){
        if (temporizador > 0)
            temporizador --;
    }
    
    public List<int[]> explotar(Mapa mapa){
        List<int[]> celdasAfectadas = new ArrayList<>();
        int[][]direcciones = {
            {0, -1},
            {0, 1},
            {-1, 0},
            {1, 0}
        };
        
        for (int[] dir: direcciones){
            for (int i = 1; i <= rango; i++){
                int nuevaPosX = getPosicion_x() + dir[0] * i;
                int nuevaPosY = getPosicion_y() + dir[1] * i;
                
                if (nuevaPosX < 0 || nuevaPosX >= mapa.getColumnas()) break;
                if (nuevaPosY < 0 || nuevaPosY >= mapa.getFilas()) break;
                
                Celda celda = mapa.getCelda(nuevaPosY, nuevaPosX);
                
                if (celda.getTipoCelda() == TipoCelda.PARED_FIJA) break;
                if (celda.getTipoCelda() == TipoCelda.PARED_DESTRUCTIBLE) {
                    celda.setVida(celda.getVida() - 1);
                    if (celda.getVida() <= 0) {
                        celda.setTipoCelda(TipoCelda.VACIO);
                        celdasAfectadas.add(new int[]{nuevaPosX, nuevaPosY});
                    }
                    break;
                }
                celdasAfectadas.add(new int[]{nuevaPosX, nuevaPosY});
            }
        }
        dueño.eliminarBomba(this);
        return celdasAfectadas;
    }
    
}


