package com.mycompany.vista;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.mycompany.modelo.Bomba;
import com.mycompany.modelo.Celda;
import com.mycompany.modelo.Item;
import com.mycompany.modelo.Juego;
import com.mycompany.modelo.Jugador;
import com.mycompany.modelo.Mapa;
import com.mycompany.modelo.Sesion;
import com.mycompany.modelo.TipoCelda;
import static com.mycompany.modelo.TipoCelda.PARED_DESTRUCTIBLE;
import static com.mycompany.modelo.TipoCelda.PARED_FIJA;
import static com.mycompany.modelo.TipoCelda.VACIO;
import com.mycompany.modelo.items.itemBomba;
import com.mycompany.modelo.items.itemLlama;
import com.mycompany.modelo.items.itemVelocidad;
import java.net.URL;
import java.util.ArrayList;
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
import java.util.List;
import java.util.Set;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    @FXML private Label lblGanador;
    
    private AnimationTimer gameLoop;
        
    private Juego juego;
    private Canvas canvas;
    private GraphicsContext gc;
    private Set<KeyCode> teclasPresionadas = new HashSet<>();
    private Set<KeyCode> teclasYaProcesadas = new HashSet<>();
    private Image imgVacio;
    private Image imgFija;
    private Image imgDestructible;    
    private Image imgDestructible2;
    private Image imgDestructible3;
    
    private Image[] framesJ1 = new Image[12];
    private Image[] framesJ2 = new Image[12];
    private Image[] frameBomb = new Image[24];
    
    private Image[] frameExp_Centro = new Image[7];
    private Image[] frameExp_Horizontal = new Image[7];
    private Image[] frameExp_Vertical = new Image[7];
    
    private int frameActualJ1 = 0;
    private int frameActualJ2 = 0;
    private int frameActualBomba = 0;
    private int frameActualExp = 0;
    
    private List<int[]> celdasExplosion = new ArrayList<>();
    private boolean hayExplosion = false;
    
    private Image imgBombaItem;
    private Image imgVelocidadItem;
    private Image imgLlamaItem;
    
    private int contadorMovJ1 = 0;
    private int contadorMovJ2 = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize arrancó");
        
        imgVacio = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/VACIO.jpeg"));
        
        imgFija = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/FIJA.jpeg"));
        
        imgDestructible = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/DESTRUCTIBLE.jpeg"));
        
        imgDestructible2 = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/DESTRUCTIBLE 2.jpeg"));
        imgDestructible3 = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/DESTRUCTIBLE 3.jpeg"));

        imgBombaItem = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/BOMBA.jpeg"));
            
        imgVelocidadItem = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/VELOCIDAD.jpeg"));
            
        imgLlamaItem = new Image(getClass().getResourceAsStream(
            "/com/mycompany/vista/sprites/LLAMA.jpeg"));
        
        Mapa mapa = Sesion.getInstancia().getMapa();
        
        juego = new Juego(mapa, null, null, 0, 'J');
        juego.iniciarJuego(mapa);
        
        canvas = new Canvas(
            juego.getMapa().getColumnas() * 48, 
            juego.getMapa().getFilas() * 48
        );
        
        gc = canvas.getGraphicsContext2D();
        
        paneJuego.getChildren().add(canvas);
        
        paneJuego.setFocusTraversable(true);
        paneJuego.requestFocus();
        
        paneJuego.setOnKeyPressed(e -> teclasPresionadas.add(e.getCode())); 
        
        paneJuego.setOnKeyReleased(e -> {
            teclasPresionadas.remove(e.getCode());
            teclasYaProcesadas.remove(e.getCode());
        });
        
        gameLoop = new AnimationTimer() {
            private long ultimoFrame = 0;

            @Override
            public void handle(long ahora) {
                if (ahora - ultimoFrame >= 150_000_000) {
                    juego.actualizarJuego();

                    // capturar celdas de explosión
                    List<int[]> nuevasExplosiones = juego.getUltimasExplosiones();
                    if (!nuevasExplosiones.isEmpty()) {
                        celdasExplosion = new ArrayList<>(nuevasExplosiones);
                        hayExplosion = true;
                        frameActualExp = 0;
                        juego.limpiarExplosiones();
                    }

                    // limpiar explosión después de 7 frames
                    if (hayExplosion && frameActualExp >= 6) {
                        hayExplosion = false;
                        celdasExplosion.clear();
                    }

                    if (juego.verificarGanador() != null) {
                        stop();
                        mostrarGanador(juego.verificarGanador());
                        return;
                    }

                    contadorMovJ1++;
                    contadorMovJ2++;

                    if (contadorMovJ1 >= (2 / juego.getJugador1().getVelocidad())) {
                        procesarTeclasJ1();
                        contadorMovJ1 = 0;
                    }

                    if (contadorMovJ2 >= (2 / juego.getJugador2().getVelocidad())) {
                        procesarTeclasJ2();
                        contadorMovJ2 = 0;
                    }

                    dibujarJuego();
                    ultimoFrame = ahora;
                }
            }
        };
        gameLoop.start();
        
        int personaje1 = Sesion.getInstancia().getPersonajeJ1();
        int personaje2 = Sesion.getInstancia().getPersonajeJ2();
        
        String prefijoJ1 = "Wraith_0" + personaje1 + "_Moving Forward_";
        String prefijoJ2 = "Wraith_0" + personaje2 + "_Moving Forward_";
        
        for (int i = 0; i < 12; i++){
            String nombreFrame = String.format("%03d", i);
            
            framesJ1[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/personajes/personaje " 
                + personaje1 + "/Walking/" + prefijoJ1 + nombreFrame + ".png"));
    
            framesJ2[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/personajes/personaje " 
                + personaje2 + "/Walking/" + prefijoJ2 + nombreFrame + ".png"));
        }
        System.out.println("FrameJ1[0]: " + framesJ1[0].isError());
        
        for (int i = 0; i < 24; i++) {
            String nombreFrame = String.format("%02d", i);
            frameBomb[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/bombas/bomba_" + nombreFrame + ".png"));
        }
        
        for (int i = 0; i < 7; i++){
            String nombreFrame = String.format("%02d", i);
            
            frameExp_Centro[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/bombas/exp_centro/explosion_centro" + nombreFrame + ".png"));
            
            frameExp_Horizontal[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/bombas/exp_horizontal/explosion_h"+ nombreFrame + ".png"));
            
            frameExp_Vertical[i] = new Image(getClass().getResourceAsStream(
                "/com/mycompany/vista/sprites/bombas/exp_vertical/explosion_v" + nombreFrame + ".png"));
        } 
    }
    
    private void dibujarJuego() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int f = 0; f < juego.getMapa().getFilas(); f++) {
            for (int c = 0; c < juego.getMapa().getColumnas(); c++) {
                Celda celda = juego.getMapa().getCelda(f, c); 
                
                switch (celda.getTipoCelda()) {
                    case VACIO: gc.drawImage(imgVacio, c*48, f*48, 48, 48); break;
                    case PARED_FIJA: gc.drawImage(imgFija, c*48, f*48, 48, 48); break;
                    case PARED_DESTRUCTIBLE:
                        if (celda.getVida() == 3) 
                            gc.drawImage(imgDestructible, c*48, f*48, 48, 48);
                        else if (celda.getVida() == 2) 
                            gc.drawImage(imgDestructible2, c*48, f*48, 48, 48);
                        else 
                            gc.drawImage(imgDestructible3, c*48, f*48, 48, 48);
                        break;
                }
                
                if (celda.tieneItem() && celda.getTipoCelda() == TipoCelda.VACIO){
                    Item item = celda.getItem();
                    Image imgItem = null;
                    
                    if (item instanceof itemBomba) imgItem = imgBombaItem;
                    else if (item instanceof itemLlama) imgItem = imgLlamaItem;
                    else if (item instanceof itemVelocidad) imgItem = imgVelocidadItem;
                    
                    if (imgItem != null) {
                        gc.drawImage(imgItem, c*48 + 8, f*48 + 8, 32, 32);
                    }
                }
            }
        }
        
        for(Bomba bomba: juego.getBombasGame()){
            gc.drawImage(frameBomb[frameActualBomba], 
                bomba.getPosicion_x() * 48, 
                bomba.getPosicion_y() * 48,
                48, 48);
        }
        
        frameActualBomba = (frameActualBomba + 1) % 24;
        
        if (hayExplosion) {
            int[] centro = juego.getUltimoCentroExplosion();

            // dibujar centro
            gc.drawImage(frameExp_Centro[frameActualExp],
                centro[0] * 48, centro[1] * 48, 48, 48);

            // dibujar celdas afectadas
            for (int[] celda : celdasExplosion) {
                int cx = celda[0];
                int cy = celda[1];

                if (cx == centro[0]) {
                    // misma columna que el centro → vertical
                    gc.drawImage(frameExp_Vertical[frameActualExp],
                        cx * 48, cy * 48, 48, 48);
                } else {
                    // misma fila que el centro → horizontal
                    gc.drawImage(frameExp_Horizontal[frameActualExp],
                        cx * 48, cy * 48, 48, 48);
                }
            }

            frameActualExp = (frameActualExp + 1) % 7;
        }
        
        Image frameJ1 = framesJ1[frameActualJ1];
        gc.drawImage(frameJ1,
            juego.getJugador1().getPosicionX() * 48,
            juego.getJugador1().getPosicionY() *48,
            64, 64
        );
        
        Image frameJ2 = framesJ2[frameActualJ2];
        gc.drawImage(frameJ2,
            juego.getJugador2().getPosicionX() * 48,
            juego.getJugador2().getPosicionY() *48,
            64, 64
        );
        
        frameActualJ1 = (frameActualJ1 + 1) % 12;
        frameActualJ2 = (frameActualJ2 + 1) % 12;
    }
    
    private void procesarTeclasJ1() {
        KeyCode[] teclasJ1 = juego.getJugador1().getTeclas();

        if (teclasPresionadas.contains(teclasJ1[0]))
            juego.getJugador1().mover(0, -1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[1]))
            juego.getJugador1().mover(0, 1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[2]))
            juego.getJugador1().mover(-1, 0, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[3]))
            juego.getJugador1().mover(1, 0, juego.getMapa());

        // bomba J1
        if (teclasPresionadas.contains(teclasJ1[4]) &&
            !teclasYaProcesadas.contains(teclasJ1[4])) {
            Bomba bomba = juego.getJugador1().colocarBomba();
            juego.agregarBombas(bomba);
            teclasYaProcesadas.add(teclasJ1[4]);
        }
    }

    private void procesarTeclasJ2() {
        KeyCode[] teclasJ2 = juego.getJugador2().getTeclas();

        if (teclasPresionadas.contains(teclasJ2[0]))
            juego.getJugador2().mover(0, -1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[1]))
            juego.getJugador2().mover(0, 1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[2]))
            juego.getJugador2().mover(-1, 0, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[3]))
            juego.getJugador2().mover(1, 0, juego.getMapa());

        // bomba J2
        if (teclasPresionadas.contains(teclasJ2[4]) &&
            !teclasYaProcesadas.contains(teclasJ2[4])) {
            Bomba bomba = juego.getJugador2().colocarBomba();
            juego.agregarBombas(bomba);
            teclasYaProcesadas.add(teclasJ2[4]);
        }
    }
    
    private void procesarTeclas() {
        System.out.println("Teclas presionadas: " + teclasPresionadas);
        
        KeyCode[] teclasJ1 = juego.getJugador1().getTeclas();
        KeyCode[] teclasJ2 = juego.getJugador2().getTeclas();

        if (teclasPresionadas.contains(teclasJ1[0]))
            juego.getJugador1().mover(0, -1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[1])) 
            juego.getJugador1().mover(0, 1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[2]))
            juego.getJugador1().mover(-1, 0, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ1[3]))
            juego.getJugador1().mover(1, 0, juego.getMapa());

        if (teclasPresionadas.contains(teclasJ2[0]))
            juego.getJugador2().mover(0, -1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[1]))
            juego.getJugador2().mover(0, 1, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[2]))
            juego.getJugador2().mover(-1, 0, juego.getMapa());
        if (teclasPresionadas.contains(teclasJ2[3]))
            juego.getJugador2().mover(1, 0, juego.getMapa());
        
        if (teclasPresionadas.contains(teclasJ1[4]) &&
            !teclasYaProcesadas.contains(teclasJ1[4])) {
            Bomba bomba = juego.getJugador1().colocarBomba();
            juego.agregarBombas(bomba);
            teclasYaProcesadas.add(teclasJ1[4]);
        }
        
        if (teclasPresionadas.contains(teclasJ2[4]) &&
            !teclasYaProcesadas.contains(teclasJ2[4])) {
            Bomba bomba = juego.getJugador2().colocarBomba();
            juego.agregarBombas(bomba);
            teclasYaProcesadas.add(teclasJ2[4]);
        }
    }
    
    private void mostrarGanador(Jugador ganador) {
        lblJ1.setVisible(false);
        lblJ2.setVisible(false);
        lblGanador.setVisible(true);
        lblGanador.setText("El ganador de la partida es: " + ganador.getNombre());
        gameLoop.stop();
        
        
        HBox hbox = (HBox) lblJ1.getParent();
        Button btnMenu = new Button("VOLVER AL MENÚ");
        btnMenu.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/mycompany/vista/MenuPrincipal.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) paneJuego.getScene().getWindow();
                stage.setScene(scene);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });
        System.out.println("Hijos del HBox: " + hbox.getChildren().size());
        hbox.getChildren().add(btnMenu);

        
    }
    
}
