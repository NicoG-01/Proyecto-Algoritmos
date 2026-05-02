    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
     */
    package com.mycompany.vista;

    import com.mycompany.modelo.Celda;
    import com.mycompany.modelo.Editor;
import com.mycompany.modelo.Item;
import com.mycompany.modelo.Mapa;
import com.mycompany.modelo.Sesion;
    import com.mycompany.modelo.TipoCelda;
    import com.mycompany.modelo.items.itemBomba;
    import com.mycompany.modelo.items.itemLlama;
    import com.mycompany.modelo.items.itemVelocidad;
    import javafx.scene.image.Image;
    import java.io.File;
    import java.io.IOException;
    import java.net.URL;
    import java.util.ResourceBundle;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.control.ToggleButton;
    import javafx.scene.control.ToggleGroup;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.paint.Color;
    import javafx.stage.FileChooser;
import javafx.stage.Stage;

    /**
     * FXML Controller class
     *
     * @author diego
     */
    public class EditorController implements Initializable {
        private Image imgVacio;
        private Image imgFija;
        private Image imgDestructible;
        private Image imgBomba;
        private Image imgVelocidad;
        private Image imgLlama;
        
        private Editor editor;
        private Canvas canvas;
        private GraphicsContext gc;

        /**
         * Initializes the controller class.
         */
        @FXML private BorderPane vistaEditor;
        @FXML private ToggleButton btnVacio;
        @FXML private ToggleButton btnParedFija;
        @FXML private ToggleButton btnParedDestructible;
        @FXML private ToggleButton btnItemBomba;
        @FXML private ToggleButton btnItemLlama;
        @FXML private ToggleButton btnItemVelocidad;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            Mapa mapaGuardado = Sesion.getInstancia().getMapa();
            
            editor = new Editor(mapaGuardado);
            //editor = new Editor(2, 2, "Mapa-Dev");
            
            //editor.getMapa().getCelda(0, 0).setTipoCelda(TipoCelda.PARED_FIJA);
            //editor.getMapa().getCelda(1, 1).setTipoCelda(TipoCelda.PARED_DESTRUCTIBLE);
            imgVacio = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/VACIO.jpeg"));
            
            imgFija = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/FIJA.jpeg"));
            
            imgDestructible = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/DESTRUCTIBLE.jpeg"));
            
            imgBomba = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/BOMBA.jpeg"));
            
            imgVelocidad = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/VELOCIDAD.jpeg"));
            
            imgLlama = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/LLAMA.jpeg"));
            
            System.out.println("Vacio: " + imgVacio.isError());
            System.out.println("Fija: " + imgFija.isError());
            System.out.println("Destructible: " + imgDestructible.isError());
            
            int columnas = editor.getMapa().getColumnas();
            int filas = editor.getMapa().getFilas();

            canvas = new Canvas(columnas * 48, filas * 48);
            gc = canvas.getGraphicsContext2D();
            canvas.setOnMousePressed(e -> manejarClick(e));
            canvas.setOnMouseDragged(e -> manejarClick(e));
            
            vistaEditor.setCenter(canvas);
            dibujarMapa();
            
            ToggleGroup grupo = new ToggleGroup();
            btnVacio.setToggleGroup(grupo);
            btnParedFija.setToggleGroup(grupo);
            btnParedDestructible.setToggleGroup(grupo);
            btnItemBomba.setToggleGroup(grupo);
            btnItemLlama.setToggleGroup(grupo);
            btnItemVelocidad.setToggleGroup(grupo);
            
            btnVacio.setSelected(true);
            
            grupo.selectedToggleProperty().addListener((obs, anterior, nuevo) -> {
                if (nuevo == btnVacio){
                    editor.setHerramientaActual(TipoCelda.VACIO);
                } else if (nuevo == btnParedFija){
                    editor.setHerramientaActual(TipoCelda.PARED_FIJA);
                } else if (nuevo == btnParedDestructible) {
                    editor.setHerramientaActual(TipoCelda.PARED_DESTRUCTIBLE);
                } else if (nuevo == btnItemBomba) {
                    editor.setItemActual(new itemBomba());
                } else if (nuevo == btnItemLlama) {
                    editor.setItemActual(new itemLlama());
                } else if (nuevo == btnItemVelocidad) {
                    editor.setItemActual(new itemVelocidad());
                }
            });
            // TODO
        }    
        
        private void manejarClick(javafx.scene.input.MouseEvent e){
            int columna = (int)(e.getX() / 48);
            int fila = (int)(e.getY() / 48);
            
            int nFilas = editor.getMapa().getFilas();
            int nColumnas = editor.getMapa().getColumnas();
            
            if (fila >= 0 && fila < nFilas && columna >= 0 && columna < nColumnas){
                editor.pintarCelda(fila, columna);
                dibujarMapa();
            }
        }

        private void dibujarMapa(){

            int columnas = editor.getMapa().getColumnas();
            int filas = editor.getMapa().getFilas();

            for (int f = 0; f < filas; f++){
                for (int c = 0; c < columnas; c ++) {
                    Celda celda = editor.getMapa().getCelda(f, c);

                    switch (celda.getTipoCelda()) {
                        case VACIO:
                            gc.drawImage(imgVacio, c * 48, f * 48, 48, 48);
                            break;
                        case PARED_FIJA:
                            gc.drawImage(imgFija, c * 48, f * 48, 48, 48);
                            break;
                        case PARED_DESTRUCTIBLE:
                            gc.drawImage(imgDestructible, c * 48, f * 48, 48, 48);
                            break;
                    }
                    
                    if (celda.tieneItem()) {
                        Item item = celda.getItem();
                        
                        Image imgItem = null;
                        
                        if (item instanceof itemBomba) imgItem = imgBomba;
                        else if (item instanceof itemLlama) imgItem = imgLlama;
                        else if (item instanceof itemVelocidad) imgItem = imgVelocidad;
                        
                        if (imgItem != null) {
                            gc.drawImage(imgItem, c*48 +8, f*48 +8, 32, 32);
                        }
                    }
                    gc.setStroke(Color.DARKGRAY);
                    gc.strokeRect(c * 48 , f * 48, 48, 48);
                }
            }
        }
        
        @FXML
        private void guardarMapa(ActionEvent event){
            String nombre = editor.getMapa().getNombre();
            try {
               editor.getMapa().guardar_Mapa(nombre + ".csv"); 
            } catch (IOException e) {
                System.out.println("Error al guardar: " + e.getMessage());
            }
        }
        
        @FXML 
        private void cargarMapa(ActionEvent event){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Cargar Mapa");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos CSV", "*.csv")
            );
            File archivo = fileChooser.showOpenDialog(vistaEditor.getScene().getWindow());
            if (archivo != null){
                try {
                    editor.cargar(archivo.getAbsolutePath());
                    dibujarMapa();
                } catch (IOException e) {
                    System.err.println("Error al cargar el mapa: " + e.getMessage());
                }
            }
        }
        
        @FXML 
        private void limpiarMapa(ActionEvent event){
            editor.limpiarMapa();
            dibujarMapa();
        }
        
        @FXML
        private void jugar(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/vista/Juego.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
    }
