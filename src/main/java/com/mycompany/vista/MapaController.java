/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.vista;

import com.mycompany.modelo.Mapa;
import com.mycompany.modelo.Sesion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class MapaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TextField txfNombe;
    @FXML private TextField txfFila;
    @FXML private TextField txfColumna;
    @FXML private Button btnCrear;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }  
    
    @FXML
    public void crearMapa(ActionEvent event){
        String nombre = txfNombe.getText();
        int filas = Integer.parseInt(txfFila.getText());
        int columnas = Integer.parseInt(txfColumna.getText());
        
        Mapa mapa = new Mapa(filas, columnas, nombre);
        Sesion.getInstancia().setMapa(mapa);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/vista/SeleccionJugador.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
