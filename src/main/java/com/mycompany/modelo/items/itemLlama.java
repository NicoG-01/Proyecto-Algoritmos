/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo.items;

import com.mycompany.modelo.Item;
import com.mycompany.modelo.Jugador;

/**
 *
 * @author diego
 */
public class itemLlama extends Item {
    @Override
    public void aplicarEfecto(Jugador pJugador) {
        pJugador.setRangoBomba(pJugador.getRangoBomba() + 1);
    }
}
