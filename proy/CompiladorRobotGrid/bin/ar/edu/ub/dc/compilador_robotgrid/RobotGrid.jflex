package ar.edu.ub.dc.compilador_robotgrid;

import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%%

// Direcciones
N | S | E | W { return new Symbol(sym.DIR, yytext()); }

// Sentencias de Control
if { return new Symbol(sym.IF); }
while { return new Symbol(sym.WHILE); }
iterate { return new Symbol(sym.ITERATE); }
\d { return new Symbol(sym.NUMBER, Integer.valueOf(yytext())); }

// Start/End
START { return new Symbol(sym.START); }
END { return new Symbol(sym.END); }

// Sentencias de Inicialización
drawGrid { return new Symbol(sym.DRAWGRID); }
placeRobot { return new Symbol(sym.PLACEROBOT); }
placeWall { return new Symbol(sym.PLACEWALL); }

// Instrucciones de construcción
move { return new Symbol(sym.MOVE); }
turnLeft { return new Symbol(sym.TURNLEFT); }

// Condiciones
frontClear { return new Symbol(sym.FCLEAR); }

// Otros
"(" { return new Symbol(sym.LPAREN); }
")" { return new Symbol(sym.RPAREN); }
, { return new Symbol(sym.COMMA); }
; { return new Symbol(sym.SCOLON); }
"{" { return new Symbol(sym.LBRACE); }
"}" { return new Symbol(sym.RBRACE); }
"!" { return new Symbol(sym.NEQ); }
\s { }  //ignorar espacio en blanco
. { System.out.print(yytext() + "[Caracter Inválido]"); }