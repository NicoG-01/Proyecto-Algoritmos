/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

/**
 *
 * @author diego
 */
public class Sesion {
    private static Sesion instancia;
    private Mapa mapa;
    private int personajeJ1;
    private int personajeJ2;
    
    private Sesion(){
    }
    
    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }
    
    public Mapa getMapa(){ 
        return mapa; 
    }
    
    public void setMapa(Mapa mapa){ 
        this.mapa = mapa; 
    }
    
    public int getPersonajeJ1(){ 
        return personajeJ1;
    }
    
    public void setPersonajeJ1(int p){
        this.personajeJ1 = p;
    }
    
    public int getPersonajeJ2(){
        return personajeJ2;
    }
    
    public void setPersonajeJ2(int p){ 
        this.personajeJ2 = p; 
    }
    
    public String getCarpetaPersonaje(int numPersonaje){
        return "/com/mycompany/vista/sprites/personajes/personaje \" \n" + 
                numPersonaje + "/Walking/";
    }
}