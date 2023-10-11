/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import java.util.HashMap;

public class TablaSimbolos {
    private HashMap<String, InfoSimbolo> tabla;

    public TablaSimbolos() {
        tabla = new HashMap<>();
    }

    public void agregarSimbolo(String nombre, InfoSimbolo informacion) {
        tabla.put(nombre, informacion);
    }

    public InfoSimbolo obtenerSimbolo(String nombre) {
        return tabla.get(nombre);
    }

    public boolean contieneSimbolo(String nombre) {
        return tabla.containsKey(nombre);
    }
}
