package ar.edu.ub.dc.compilador_booleano;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

Operando = "true"|"false"

Operador = "&&" | "||"

%%

{Operando} {return new Symbol(sym.OPERANDO);}

{Operador} {return new Symbol(sym.OPERADOR);}

";" {return new Symbol(sym.FIN_SENTENCIA);}

. {System.err.println("Caracter Inválido");}