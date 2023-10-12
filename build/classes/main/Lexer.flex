
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

"int" {lexema=yytext(); posLinea=yyline; return entero;}
"float" {lexema=yytext(); posLinea=yyline; return flotante;}
"char" {lexema=yytext(); posLinea=yyline; return caracter;}

{id} {lexema=yytext(); posLinea=yyline; return id;}

{caracter} {lexema=yytext(); posLinea=yyline; return car;}

{num} {lexema=yytext(); posLinea=yyline; return num;}

/* Simbolos de puntuación o comas */
"," {lexema=yytext(); posLinea=yyline; return Coma;}
";" {lexema=yytext(); posLinea=yyline; return PuntoComa;}

"(" {lexema=yytext(); posLinea=yyline; return ParentesisAbre;}
")" {lexema=yytext(); posLinea=yyline; return ParentesisCierra;}

"=" {lexema=yytext(); posLinea=yyline; return Igual;}
"*" {lexema=yytext(); posLinea=yyline; return Producto;}
"/" {lexema=yytext(); posLinea=yyline; return Division;}
"+" {lexema=yytext(); posLinea=yyline; return Suma;}
"-" {lexema=yytext(); posLinea=yyline; return Resta;}

 . {posLinea=yyline; lexema=yytext(); return Error;}
