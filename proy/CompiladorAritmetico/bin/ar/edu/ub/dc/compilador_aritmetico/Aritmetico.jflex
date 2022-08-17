package ar.edu.ub.dc.compilador_aritmetico;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%line

%column

%%
"+" { return new Symbol(sym.MAS); }
"-" { return new Symbol(sym.MENOS); }
"*" { return new Symbol(sym.POR); }
"/" { return new Symbol(sym.DIVISION); }
"%" { return new Symbol(sym.MODULO); }
"^" { return new Symbol(sym.POTENCIA); }
";" { return new Symbol(sym.PUNTOYCOMA); }
"(" { return new Symbol(sym.LPAREN); }
")" { return new Symbol(sym.RPAREN); }
[:digit:]+ { return new Symbol(sym.NUMERO, new Integer(yytext())); }
[ \t\r\n]+ {;}
. { System.out.println("Error léxico."+yytext()+"-"); }