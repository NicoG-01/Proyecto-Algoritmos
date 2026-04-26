/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

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
    public Mapa getMapa() {
        return mapa;
    }
// // Retorna el primer jugador de la partida
    public Jugador getJugador1() {
        return jugador1;
    }
// // Retorna el Segundo jugador de la partida
    public Jugador getJugador2() {
        return jugador2;
    }
 // Retorna el tiempo transcurrido o restante del juego
    public int getTiempo() {
        return tiempo;
    }
 // Retorna el estado actual del juego (I=Iniciado, P=Pausado, F=Finalizado)
    public char getEstado() {
        return estado;
    }
// Cambia el estado actual del juego
    public void setEstado(char estado) {
        this.estado = estado;
    }
   
}
