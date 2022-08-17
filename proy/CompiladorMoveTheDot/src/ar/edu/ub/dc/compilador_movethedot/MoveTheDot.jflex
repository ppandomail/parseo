package ar.edu.ub.dc.compilador_movethedot;
import java_cup.runtime.Symbol;
%%
%public
%class Scanner
%standalone
%cup
%%
"i"|"I"|"inicio"|"Inicio"|"INICIO" {return new Symbol(sym.INICIO);} 
"f"|"F"|"fin"|"Fin"|"FIN" {return new Symbol(sym.FIN);} 
"r"|"R"|"repetir"|"Repetir"|"REPETIR" {return new Symbol(sym.REPETIR);} 
"u"|"U"|"ubicar"|"Ubicar"|"UBICAR" {return new Symbol(sym.UBICAR);} 
"l"|"L"|"mov_lat"|"Mov_Lat"|"MOV_LAT" {return new Symbol(sym.LATERAL);} 
"v"|"V"|"mov_ver"|"Mov_Ver"|"MOV_VER" {return new Symbol(sym.VERTICAL);} 
"(" {return new Symbol(sym.PAR_A);}
")" {return new Symbol(sym.PAR_C);}
"[" {return new Symbol(sym.COR_A);}
"]" {return new Symbol(sym.COR_C);}
"+" {return new Symbol(sym.SUMA);}
"-" {return new Symbol(sym.RESTA);}
"," {return new Symbol(sym.COMA);}
[:digit:]+ { return new Symbol(sym.ENTERO, new Integer(yytext())); }
[ \t\r\n]+ {;}