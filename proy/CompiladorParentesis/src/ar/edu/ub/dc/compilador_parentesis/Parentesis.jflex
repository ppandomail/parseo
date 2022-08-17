package ar.edu.ub.dc.compilador_parentesis;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%%

"(" {return new Symbol(sym.PARABRE);}

")" {return new Symbol(sym.PARCIERRA);}

.   {System.err.println("Caracter Invalido");}