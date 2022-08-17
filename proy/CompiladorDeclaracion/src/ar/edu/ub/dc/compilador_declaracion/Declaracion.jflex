package ar.edu.ub.dc.compilador_declaracion;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

Identifier = [:jletter:] [:jletterdigit:]*


%%

{Identifier} {return new Symbol(sym.ID);}

"&&" {return new Symbol(sym.ENTERO);}

"!!" {return new Symbol(sym.BOOLEANO);}

";" {return new Symbol(sym.PUNTOCOMA);}

[ \t\r\n]+ {;}

. {System.err.println("Caracter Inválido");}