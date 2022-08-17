package ar.edu.ub.dc.compilador_condicional;

import java_cup.runtime.Symbol;
%%
%{
	public void imprime(String str){
		System.out.println(str+"-"+yychar+"-"+yyline);
	}
%}
%public
%class Scanner
%char
%line
%ignorecase
%cup
%full
%type java_cup.runtime.Symbol
%eofval{
	System.out.println("FINARCHIVO");
	return null;
%eofval}
letra=[a-zA-Z]
entero=[0-9]
id=[a-zA-Z][A-Za-z0-9]*
%% 
"(" {imprime("Abre Parentesis");
     return new Symbol(csym.open_par,new token(yytext(),yychar,yyline));
    }
")" {imprime("Cierra Parentesis");
     return new Symbol(csym.close_par,new token(yytext(),yychar,yyline));
    }
"verdadero" {imprime("verdadero");
	         return new Symbol(csym.true_,new token(yytext(),yychar,yyline));
            }
"falso" {imprime("falso");
	  	 return new Symbol(csym.false_,new token(yytext(),yychar,yyline));
	    }
"<=" {imprime("menor igual");
      return new Symbol(csym.menor_igual,new token(yytext(),yychar,yyline));
     }
">=" {imprime("mayor igual");
      return new Symbol(csym.mayor_igual,new token(yytext(),yychar,yyline));
     }
"||" {imprime("or");
	  return new Symbol(csym.or_,new token(yytext(),yychar,yyline));
     }
"&&" {imprime("and");
	  return new Symbol(csym.and_,new token(yytext(),yychar,yyline));
     }
"==" {imprime("igual_igual");
	  return new Symbol(csym.igual_igual,new token(yytext(),yychar,yyline));
     }
"!=" {imprime("no igual");
	  return new Symbol(csym.no_igual,new token(yytext(),yychar,yyline));
     }
({id})+("_")*({id})* {imprime("id");
	                  return new Symbol(csym.id,new token(yytext(),yychar,yyline));
	                 }
{entero}+ {imprime("entero");
	  	   return new Symbol(csym.entero,new token(yytext(),yychar,yyline));
	      }
[\t\r\f]  {}
[\n] {yychar=0;
     }
" " {}
 .  {imprime("error: "+yytext());
    }