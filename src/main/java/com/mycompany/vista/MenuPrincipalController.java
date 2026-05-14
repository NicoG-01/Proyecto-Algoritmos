/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.vista;

import com.mycompany.modelo.Editor;
import com.mycompany.modelo.Sesion;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class MenuPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void nuevoJuego(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/vista/Mapa.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    
    @FXML 
    private void cargarMapa(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar Mapa");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
        );
        
        File archivo = fileChooser.showOpenDialog(
            ((Node)event.getSource()).getScene().getWindow()
        );
        
        if (archivo != null){
            try {
                Editor editor = new Editor(1, 1, "temp");
                editor.cargar(archivo.getAbsolutePath());
                
                Sesion.getInstancia().setMapa(editor.getMapa());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/mycompany/vista/SeleccionJugador.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void salir(ActionEvent event) {
        Platform.exit();
    }
}
