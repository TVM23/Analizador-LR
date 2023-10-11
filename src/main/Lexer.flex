
package main;
import static main.Tokens.*;

%%

%class Lexer
%type Tokens
%line
%column
%{
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
"int" {posLinea=yyline; lexema=yytext(); return entero;}
"float" {posLinea=yyline; lexema=yytext(); return flotante;}
"char" {posLinea=yyline; lexema=yytext(); return caracter;}

/* Identificador */
{id} {posLinea=yyline; lexema=yytext(); return id;}

/* Literal caracter */
{caracter} {posLinea=yyline; lexema=yytext(); return car;}

/* num */
{num} {posLinea=yyline; lexema=yytext(); return num;}

/* Simbolos de puntuación o comas */
"," {posLinea=yyline; lexema=yytext(); return Coma;}
";" {posLinea=yyline; lexema=yytext(); return PuntoComa;}

/* Parentesis */
"(" {posLinea=yyline; lexema=yytext(); return ParentesisAbre;}
")" {posLinea=yyline; lexema=yytext(); return ParentesisCierra;}

/* Simbolos */
"=" {posLinea=yyline; lexema=yytext(); return Igual;}
"*" {posLinea=yyline; lexema=yytext(); return Producto;}
"/" {posLinea=yyline; lexema=yytext(); return Division;}
"+" {posLinea=yyline; lexema=yytext(); return Suma;}
"-" {posLinea=yyline; lexema=yytext(); return Resta;}

 . {posLinea=yyline; lexema=yytext(); return Error;}
