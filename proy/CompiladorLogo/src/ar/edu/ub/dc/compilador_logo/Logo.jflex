package ar.edu.ub.dc.compilador_logo;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%%

"I" {return new Symbol(sym.comienza);}

"N" {return new Symbol(sym.norte);}

"S" {return new Symbol(sym.sur);}

"E" {return new Symbol(sym.este);}

"O" {return new Symbol(sym.oeste);}

"F" {return new Symbol(sym.fin);}

. {System.err.println("Caracter Inválido");}