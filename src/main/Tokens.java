
package main;

public enum Tokens {
    id,
    car,
    num,
    entero("int"),
    flotante("float"),
    caracter("char"),
    Igual("="),
    Suma("+"),
    Resta("-"),
    Division("/"),
    Producto("*"),
    ParentesisAbre("("),
    ParentesisCierra(")"),
    Coma(","),
    PuntoComa(";"),
    Error;
    
    private final String simbolo;

    private Tokens() {
        this.simbolo = null;
    }

    private Tokens(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}
