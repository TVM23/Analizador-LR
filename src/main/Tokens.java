
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
    
    private final String valorTok;

    private Tokens() {
        this.valorTok = null;
    }

    private Tokens(String valorTok) {
        this.valorTok = valorTok;
    }

    public String getValor() {
        return valorTok;
    }
}
