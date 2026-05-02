/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.stage.FileChooser;

/**
 *
 * @author diego
 */
public class Editor {
    private Mapa mapa;
    private TipoCelda herramienta_actual;
    private Item item_actual;
    
    public Editor(Mapa mapa){
        this.mapa = mapa;
        this.herramienta_actual = TipoCelda.VACIO;
        this.item_actual = null;
    }
    
    public Editor(int filas, int columnas, String nombre){
        this.mapa = new Mapa(filas, columnas, nombre);
        this.herramienta_actual = TipoCelda.VACIO;
        this.item_actual = null;
    }
    
    public Mapa getMapa(){
        return mapa;
    }
    
    public TipoCelda getHerramientaActual(){
        return herramienta_actual;
    }
    
    public Item getItemActual(){
        return item_actual;
    }
    
    public void setHerramientaActual(TipoCelda pHerramienta){
        this.herramienta_actual = pHerramienta;
        this.item_actual = null;
    }
    
    public void setItemActual(Item pItem){
        this.herramienta_actual = null;
        this.item_actual = pItem;
    }
    
    public void pintarCelda(int fila, int columna){
        Celda vCelda = mapa.getCelda(fila, columna);
        if (item_actual != null){
            if (vCelda.getTipoCelda() == TipoCelda.PARED_DESTRUCTIBLE){
                vCelda.setItem(item_actual);
            }
        } else {
            vCelda.setTipoCelda(herramienta_actual);
            vCelda.setItem(null);
        }
    }
    
    public void cargar(String ruta) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        
        String primer_linea = br.readLine();
        String[] datos = null;
        if (primer_linea != null) {
            datos = primer_linea.split(",");
        }
        
        int filas = Integer.parseInt(datos[1]);
        int columnas = Integer.parseInt(datos[2]);
        
        Mapa mapa_Archivo = new Mapa(filas, columnas, datos[0]);
        
        for (int f = 0; f < filas; f++){
            String linea = br.readLine();
            String[] valores = linea.split(",");
            for (int c = 0; c < columnas; c++) {
                TipoCelda tipo = TipoCelda.values()[Integer.parseInt(valores[c])];
                mapa_Archivo.getCelda(f, c).setTipoCelda(tipo);
            }
        }
        
        this.mapa = mapa_Archivo;
        
        br.close();
    }
    
    public void limpiarMapa(){
        int filas = mapa.getFilas();
        int columnas = mapa.getColumnas();
        
        for (int f = 0; f < filas; f++){
            for (int c = 0; c < columnas; c++){
                mapa.getCelda(f, c).setTipoCelda(TipoCelda.VACIO);
                mapa.getCelda(f, c).setItem(null);
            }
        }
    }
}
