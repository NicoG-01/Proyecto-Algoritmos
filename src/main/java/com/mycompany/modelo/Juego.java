/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.input.KeyCode;

/**
 *
 * @author JosephDG
 */
public class Juego {
    private Mapa mapa;
    private Jugador jugador1;
    private Jugador jugador2;
    private int tiempo;
    private char estado; 
    private List<Bomba> bombasGame = new ArrayList<>();
    
    public Juego(Mapa mapa, Jugador jugador1, Jugador jugador2, int tiempo, char estado){
        this.mapa = mapa;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.tiempo = tiempo;
        this.estado = 'J';
    }
    
    public Mapa getMapa() {
        return mapa;
    }
    
    public Jugador getJugador1() {
        return jugador1;
    }
    
    public Jugador getJugador2() {
        return jugador2;
    }
    
    public int getTiempo() {
        return tiempo;
    }
    
    public char getEstado() {
        return estado;
    }
    
    public void setEstado(char estado) {
        this.estado = estado;
    }
    
    public void agregarBombas(Bomba bomba) {
        if (bomba != null) 
            bombasGame.add(bomba);
    }
    
    public void iniciarJuego(Mapa mapa){
        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        
        j1.setTeclas(new KeyCode[] {
            KeyCode.W,
            KeyCode.S, 
            KeyCode.A,
            KeyCode.D,
            KeyCode.F
        });
        
        j2.setTeclas(new KeyCode[]{
            KeyCode.UP,  
            KeyCode.DOWN,
            KeyCode.LEFT, 
            KeyCode.RIGHT, 
            KeyCode.COLORED_KEY_0  
        });
        
        j1.setPosicionX(0);
        j1.setPosicionY(0);
        
        j2.setPosicionX(mapa.getColumnas()-1);
        j2.setPosicionY(mapa.getFilas()-1);
        
        this.mapa = mapa;
        this.jugador1 = j1;
        this.jugador2 = j2;
    }
    
    public void actualizarJuego(){
        for (int i = bombasGame.size() - 1; i >= 0; i--){
            Bomba bomba = bombasGame.get(i);
            bomba.actualizarTemp();
            if (bomba.bombaExplotada()) {
                bomba.explotar(mapa);
                bombasGame.remove(i);
            }
        }
        verificarGanador();
    }
    
    public Jugador verificarGanador() {
        if (jugador1 == null || jugador2 == null) return null;
        
        if (!jugador1.isVivo())
            return jugador2;
        else if (!jugador2.isVivo()) 
            return jugador1;
        else 
            return null;
    }
   
}
