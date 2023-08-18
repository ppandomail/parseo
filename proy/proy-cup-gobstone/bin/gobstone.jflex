import java_cup.runtime.Symbol;

%%

%public

%class Scanner

%standalone

%cup

%%

START {return new Symbol(sym.START);}
END {return new Symbol(sym.END);}

\d { return new Symbol(sym.NUMBER, Integer.valueOf(yytext())); }

Tablero { return new Symbol(sym.TABLERO);}

hayColor { return new Symbol(sym.HAYCOLOR); }
repeat { return new Symbol(sym.REPEAT);}
if { return new Symbol(sym.IF); }

Poner {return new Symbol(sym.PONER);}
Mover {return new Symbol(sym.MOVER);}
Sacar {return new Symbol(sym.SACAR);}

Dir {return new Symbol(sym.DIR);}
Color {return new Symbol(sym.COLOR);}

Norte | Sur | Este | Oeste { return new Symbol(sym.DIR, yytext()); }
Rojo | Azul { return new Symbol(sym.COLOR, yytext()); }

"(" { return new Symbol(sym.LPAREN); }
")" { return new Symbol(sym.RPAREN); }
, { return new Symbol(sym.COMMA); }
; { return new Symbol(sym.SCOLON); }
"{" { return new Symbol(sym.LBRACE); }
"}" { return new Symbol(sym.RBRACE); }
"!" { return new Symbol(sym.NEG); }

. {System.out.print(yytext() + "[Caracter Inválido]");}