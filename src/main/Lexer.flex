
package main;
import static main.Tokens.*;
import java.util.Map;
import java.util.LinkedHashMap;

class Token {
    public String tok, lex;
    public int numLinea;

    public Token(String lex, String tok, int numLinea) {
        this.lex = lex;
        this.tok = tok;
        this.numLinea = numLinea;
    }

    public String getToken() {
        return tok;
    }

    public void setToken(String tok) {
        this.tok = tok;
    }

    public String getLexema() {
        return lex;
    }

    public void setLexema(String lex) {
        this.lex = lex;
    }

    public int getnumLinea() {
        return numLinea;
    }

    public void setnumLinea(int numLinea) {
        this.numLinea = numLinea;
    }
    
    @Override
    public String toString() {
        return super.toString(); 
    }
}

%%

%class Lexer
%type Tokens
%line
%column
%{
   Map<String,Token> tablaSimbolos = new LinkedHashMap<>();
   public int posLinea;
   public String lexema;
%}

terminadorLinea = \r|\n|\r\n
espacioVacio = {terminadorLinea} | [ \t\f]

letra = [a-zA-ZñÑ_$á-źÁ-Ź]
digito = [0-9]
espacio = [ ]+
caracter = \'(\\.|[^\'\\])?\'
flotante = ([1-9][0-9]*\.[0-9]*[1-9])|(0\.0)|([1-9][0-9]*\.0)|([1-9][0-9]*\.[0-9]*[1-9][eE][-+][1-9][0-9]*)|(0\.[0-9]*[1-9][eE][-+][1-9][0-9]*)
entero = (0|[1-9][0-9]*)
num = {entero} | {flotante}
id = {letra}({letra}|{digito})*
%%

{espacioVacio} { /* Saltar */ }

/* tipo de valor para asignacion */
"int" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"int",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return entero;}
"float" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"float",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return flotante;}
"char" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"char",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return caracter;}

/* Identificador */
{id} {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"id",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return id;}

/* Literal caracter */
{caracter} {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"car",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return car;}

/* num */
{num} {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"num",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return num;}

/* Simbolos de puntuación o comas */
"," {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),",",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Coma;}
";" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),";",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return PuntoComa;}

/* Parentesis o agrupacion */
"(" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"(",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return ParentesisAbre;}
")" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),")",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return ParentesisCierra;}

/* Simbolos */
"=" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"=",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Igual;}
"*" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"*",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Producto;}
"/" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"/",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Division;}
"+" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"+",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Suma;}
"-" {posLinea=yyline; lexema=yytext(); Token t = new Token(yytext(),"-",yyline); tablaSimbolos.put(yytext()+yyline+yycolumn,t); return Resta;}

 . {posLinea=yyline; lexema=yytext(); return Error;}
