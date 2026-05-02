package com.mycompany.vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.mycompany.modelo.Sesion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class SeleccionJugadorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Label lblTurno;
    @FXML private ImageView spriteP1, spriteP2, spriteP3;
    @FXML private Button elegrP1, elegrP2, elegrP3, btnContinuar;
    
    private int personajeJ1 = -1;
    private int personajeJ2 = -1; 
    private boolean turnoJ1 = true; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearAnimacion(spriteP1, "personaje 1", "Wraith_01_Taunt_", 17).play();
        crearAnimacion(spriteP2, "personaje 2", "Wraith_02_Taunt_", 17).play();
        crearAnimacion(spriteP3, "personaje 3", "Wraith_03_Taunt_", 17).play();
        // TODO
    }   
    
    @FXML
    private void elegirP1(ActionEvent event) {
        seleccionarPersonaje(1);
        elegrP1.setDisable(true);
    }

    @FXML
    private void elegirP2(ActionEvent event) {
        seleccionarPersonaje(2);
        elegrP2.setDisable(true);
    }

    @FXML
    private void elegirP3(ActionEvent event) {
        seleccionarPersonaje(3);
        elegrP3.setDisable(true);
    }
    
    @FXML
    private void continuar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/vista/Editor.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void seleccionarPersonaje(int personaje) {
        if (turnoJ1) {
            personajeJ1 = personaje;
            turnoJ1 = false;
            lblTurno.setText("JUGADOR 2 ELIGE TU PERSONAJE");
        } else {
            personajeJ2 = personaje;
            // guardar en Sesion
            Sesion.getInstancia().setPersonajeJ1(personajeJ1);
            Sesion.getInstancia().setPersonajeJ2(personajeJ2);
            btnContinuar.setDisable(false);
        }
    }
    
    private Timeline crearAnimacion(ImageView sprite, String carpeta, String prefijo, int totalFrames){
        int[] frame = {0};
        Timeline animacion = new Timeline(
            new KeyFrame(Duration.millis(100), e -> {
                String ruta = "/com/mycompany/vista/sprites/personajes/" 
                    + carpeta + "/Taunt/" 
                    + prefijo 
                    + String.format("%03d", frame[0]) 
                    + ".png";            
                Image img = new Image(getClass().getResourceAsStream(ruta));
                sprite.setImage(img);
                frame[0] = (frame[0] + 1) % totalFrames;
            })
        );
        
        animacion.setCycleCount(Timeline.INDEFINITE);
        return animacion; 
    }
}
