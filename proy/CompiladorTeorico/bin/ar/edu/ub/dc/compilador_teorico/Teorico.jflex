// Herramienta se descarga de: https://www.cs.princeton.edu/~appel/modern/java/JLex/
// Formato: Los caracteres %% se usan para separar cada una de las secciones.

//Código de Usuario: Ejemplo importaciones de clases:
import java_cup.runtime.Symbol;
import java.io.*;

%%  //Directivas

%class nombre  //especifica el nombre de la clase generada como salida de JFlex.
%cup    //se le informa a JLex que se va a utilizar Cup.
%line   //activa la cuenta de linea en la variable yyline, de tipo int, indica la línea en la que empieza el token que está siendo reconocido.
%column //activa la cuenta de columna en la variable yycolumn, de tipo int.
%standalone //genera programa que acepta un archivo de entrada en línea de comando.
%debug      //durante la ejecución muestra: nº línea de la especificación, lexema reconocido y acción ejecutada.

%{ 
	// código java copiado tal cual en la clase.
%} 

%init{ 
	//código java copiado tal cual en el constructor de la clase.
%init} 

%eofval{  
	//código java copiado tal cual, se ejecutará cada vez que se alcanza el final de archivo.
	//permiten especificar el token que devuelve el analizador léxico cuando se llega al final del archivo.
	//última sentencia return 
%eofval} 

// Macros: permite dar un nombre a una cierta expresión regular 
NUMERO = [1-9][0-9]*
DigitoHex = [0-9a-fA-F]
NumeroBinario = “b” [01]+

%% //Expresiones Regulares

“[a-b][a-b0-9]*” {return new Symbol(sym.ID); }
{NUMERO} {return new Symbol(sym.NUMERO, new Integer(yytext()); }
"!" { return new Symbol(sym.NEQ); }
\s { }  //ignorar espacio en blanco
[:digit:]+ { return new Symbol(sym.NUMERO, new Integer(yytext())); }
[:letter:] { return new Symbol(sym.LETRA); }
. { System.out.print(yytext() + "[Caracter Inválido]"); }