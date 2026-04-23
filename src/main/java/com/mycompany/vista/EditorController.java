/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.vista;

import com.mycompany.modelo.Celda;
import com.mycompany.modelo.Mapa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class EditorController implements Initializable {
    private Mapa mapa;
    private Canvas canvas;
    private GraphicsContext gc;
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private BorderPane vistaEditor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapa = new Mapa(11, 13, "Mapa-Dev");
        canvas = new Canvas(mapa.getColumnas()*48, mapa.getFilas()*48);
        gc = canvas.getGraphicsContext2D();
        vistaEditor.setCenter(canvas);
        dibujarMapa();
        // TODO
    }    
    
    private void dibujarMapa(){
        for (int f = 0; f < mapa.getFilas(); f++){
            for (int c = 0; c < mapa.getColumnas(); c ++) {
                Celda celda = mapa.getCelda(f, c);
                
                switch (celda.getTipoCelda()) {
                    case VACIO:
                        gc.setFill(Color.LIGHTGRAY);
                        break;
                    case PARED_FIJA:
                        gc.setFill(Color.DARKGRAY);
                        break;
                    case PARED_DESTRUCTIBLE:
                        gc.setFill(Color.BROWN);
                        break;
                }
                gc.fillRect(c * 48, f * 48, 48, 48);
                
                gc.setStroke(Color.BLACK);
                gc.strokeRect(c * 48 , f * 48, 48, 48);
            }
        }
    }
    
}
