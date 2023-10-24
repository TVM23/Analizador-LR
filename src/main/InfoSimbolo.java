
package main;

public class InfoSimbolo {
    private String nombre; // Nombre del símbolo
    private int tipo; // Tipo de dato del símbolo (int, float, char)
    private Object valor; // Valor del símbolo (puede ser un número, caracter.)
    private int linea; // Número de línea donde se encuentra el símbolo
    //private int columna; // Número de columna donde se encuentra el símbolo

    // Constructor
    public InfoSimbolo(String nombre, int tipo, Object valor, int linea) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        //this.columna = columna;
    }

    // Getters y setters para acceder a los campos
    public String getNombre() {
        return nombre;
    }

    public int getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    /*public int getColumna() {
        return columna;
    }*/
}