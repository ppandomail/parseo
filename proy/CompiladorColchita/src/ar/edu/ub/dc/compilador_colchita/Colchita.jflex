package ar.edu.ub.dc.compilador_colchita;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%%

"giro" {return new Symbol(sym.GIRO);}

"costura" {return new Symbol(sym.COSTURA);}

"a" {return new Symbol(sym.RETAZO_A);}

"b" {return new Symbol(sym.RETAZO_B);}

"(" {return new Symbol(sym.PARABRE);}

")" {return new Symbol(sym.PARCIERRA);}

"," {return new Symbol(sym.COMA);}

. {System.err.println("Caracter Inválido");}