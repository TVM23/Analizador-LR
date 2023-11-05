/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 *
 * @author juanp
 */
public class Intermedio {

    public Stack<String> pilaV = new Stack();
    public ArrayList<String> operadores = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "(", ")"));

    public String Declaraciones(String nombre, InfoSimbolo simbolo) {
        String tipo, result = "";
        switch (simbolo.getTipo()) {
            case 0 -> {
                tipo = "int";
                result += tipo + " " + nombre + "; \n";
            }
            case 1 -> {
                tipo = "float";
                result += tipo + " " + nombre + "; \n";
            }
            default -> {
                tipo = "char";
                result += tipo + " " + nombre + "; \n";
            }
        }
        return result;
    }

    public String asignaciones(String lexema, String V) {
        String result = "";
        int v1, v2;
        if (V.equals("")) {
            if (!operadores.contains(lexema)) {
                pilaV.add(lexema);
                result = "V" + pilaV.size() + "=" + lexema + "; \n";
                return result;
            } else {
                v2 = pilaV.size();
                pilaV.pop();
                v1 = pilaV.size();
                pilaV.pop();
                result = "V" + (pilaV.size() + 1) + "=" + "V" + v1 + lexema + "V" + v2 + "; \n";
                pilaV.add(v1 + lexema + v2);
                return result;
            }
        } else {
            return result = V + "= V" + (pilaV.size());
        }

    }
}
