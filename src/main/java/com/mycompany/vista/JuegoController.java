package com.mycompany.vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.mycompany.modelo.Juego;
import com.mycompany.modelo.Mapa;
import com.mycompany.modelo.Sesion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class JuegoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Label lblTiempo;
    @FXML private Label lblJ1;
    @FXML private Label lblJ2;
    @FXML private Pane paneJuego;
        
    private Juego juego;
    private Canvas canvas;
    private GraphicsContext gc;
    private Set<KeyCode> teclasPresionadas = new HashSet<>();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Mapa mapa = Sesion.getInstancia().getMapa();
        
        juego = new Juego(mapa, null, null, 0, 'J');
        juego.iniciarJuego(mapa);
        
        canvas = new Canvas(
            juego.getMapa().getColumnas() * 48, 
            juego.getMapa().getFilas() * 48
        );
        
        gc = canvas.getGraphicsContext2D();
        
        paneJuego.getChildren().add(canvas);
        
        canvas.setFocusTraversable(true);
        canvas.requestFocus();
        
        AnimationTimer gameLoop = new AnimationTimer() {
        private long ultimoFrame = 0;

        @Override
        public void handle(long ahora) {
            // actualizar cada 200ms para que no sea muy rápido
            if (ahora - ultimoFrame >= 200_000_000) {
                juego.actualizarJuego();
                dibujarJuego();
                ultimoFrame = ahora;
            }
        }
        
        private void dibujarJuego() {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (int f = 0; f < juego.getMapa().getFilas(); f++) {
                for (int c = 0; c < juego.getMapa().getColumnas(); c++) {
                    switch (juego.getMapa().getCelda(f, c).getTipoCelda()) {
                        case VACIO: gc.setFill(Color.LIGHTGRAY); break;
                        case PARED_FIJA: gc.setFill(Color.DARKGRAY); break;
                        case PARED_DESTRUCTIBLE: gc.setFill(Color.BROWN); break;
                    }
                    gc.fillRect(c * 48, f * 48, 48, 48);
                }
            }
        }
    };
    gameLoop.start();
    }    
    
}
