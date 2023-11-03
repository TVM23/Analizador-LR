/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author juanp
 */
public class Intermedio {

    public String Declaraciones(String nombre, InfoSimbolo simbolo) {
        String tipo, result = "";
        switch (simbolo.getTipo()) {
            case 0 -> {
                tipo = "int";
                result += tipo +" "+ nombre + "; \n";
            }
            case 1 -> {
                tipo = "float";
                result += tipo +" "+ nombre + "; \n";
            }
            default -> {
                tipo = "char";
                result += tipo +" "+ nombre + "; \n";
            }
        }
        return result;
    }
    
    public String asignaciones(){
        
        return "";
    }
}
