package ar.edu.ub.dc.compilador_identificadores;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%line

%column

Digito = [0-9]

Letra = [a-zA-Z]

ID = {Letra}({Letra}|{Digito})*

%%

{Digito} { System.out.println("[" + yyline + "," + yycolumn + "] DIGITO: " + yytext()); }

{Letra} { System.out.println("[" + yyline + "," + yycolumn + "] LETRA: " + yytext()); }

{ID} { System.out.println("[" + yyline + "," + yycolumn + "] ID: " + yytext()); }

. { System.out.println("[" + yyline + "," + yycolumn + "] OTRO: " + yytext()); }