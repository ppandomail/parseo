# JFlex

* Es un generador de scanners.
* Generador de programas java diseñado para procesamiento léxico.
* Parte de un conjunto de reglas léxicas.
* JLex produce un programa llamado Yylex que reconoce las cadenas que cumplen dichas reglas.

![JFlex](img/jlex.png)

* Lo primero es crear un archivo para el análisis léxico con JLex.
* Este archivo especificará los lexemas aceptables por el compilador.
* Por ejemplo, si se va a utilizar la sentencia while para los bucles pero no la for, el scanner dará un aviso de error si se encuentra una sentencia for.

## Uso de JFlex

* El formato de especificación es el siguiente:

  ```plain
  Código de Usuario
  %% {opciones y declaraciones}
  Directivas JFlex
  %% {reglas léxicas}
  Expresiones Regulares
  ```

* Los caracteres %% se usan para separar cada una de las secciones.

### Código de usuario

* El  código auxiliar necesario para el traductor léxico.
* El contenido se copia tal y como aparece en la especificación, al principio del código fuente generado por JFlex.
* Se pone, por ejemplo, el package, las importaciones de clases que se podrían necesitar:

  ```plain
  import java_cup.runtime.Symbol;
  import java.io.*;
  ```

### Opciones y declaraciones

* Opciones de código generado:

| Código | Descripción |
| -- | -- |
| %class nombre | Especifica el nombre de la clase generada como salida de JFlex |
| %line | Activa cuenta de líneas (yyline) |
| %column | Activa cuenta de columnas (yycolumn) |
| %standalone | Genera programa que acepta un archivo de entrada en línea de comando |
| %debug | Durante la ejecución muestra: nº línea de la especificación, lexema reconocido y acción ejecutada |
| %cup | Se le informa a JLex que se va a utilizar Cup |

* Código fuente específico:

| Código Fuente específico | Descripción |
| -- | -- |
| %{ ... %}  | Código copiado tal cual en la clase |
| %init{... %init} | Código copiado tal cual en el constructor de la clase |
| %eofval{ ... %eofval} | Código que se ejecutará cada vez que alcanzamos un final de archivo. Permiten especificar el token que devuelve el analizador léxico cuando se llega al final del archivo. Estas directivas encapsulan el código Java que se va a ejecutar cuando se invoque el método que proporciona el analizador léxico para producir el siguiente token (es decir, si se utiliza la directiva %cup, cuando se invoca el método next_token) y se ha llegado al final del archivo de entrada al analizador léxico. |

* Macros y estados:
  * Macros: definiciones regulares. Ejemplos:
    * DigitoHex = [0-9a-fA-F]
    * NumeroBinario = “b” [01]+
    * NumeroDecimal = \[1-9][0-9]*
  * Estados: condicionan las reglas léxicas que se comprueban. Ejemplos:
    * %state nombre1, nombre2, ...
    * %xstate nombre3, nombre4, ...

### Reglas léxicas

* Formato: expresión { ... acciones ...}
* Funcionamiento: Cuando se detecta un lexema que cumple el patrón definido en la expresión se ejecutan las acciones asociadas (código java).
* Metacaracteres: ? * + | () ^ $ . [ ] { } “ \
* Sean a y b expresiones válidas:

| ER | Significado |
| -- | -- |
| a \| b  | Unión |
| ab  | Concatenación |
| a*  | Repetición 0 o N veces |
| a+  | Repetición 1 o N veces (= a a*) |
| a?  | Opcionalidad |
| !a  | Negación |
| ~a  | Cualquier cosa que termine en a |
| { nombre }  | Utilización de una macro |
| "..."  | Cadena de caracteres |
| [...]  | Clases de caracteres |
| .  | Cualquier carácter excepto \n |
| [:letter:]  | Letras |
| [^...]  | Complementario de clases de caracteres |

* Ejemplo: identificadores que comiencen con la letra a o b y después tengan 0 o más letras a, b, o números del 0 al 9. Y además reconocería números.
  * Reconocería estos lexemas: a12, b, b0223, 1, 123, 102, 20, 1123
  * Daría errores para: c12, 01, ad, 12a

  ```plain
  import java_cup.runtime.Symbol;
  %%
  %public
  %class Prueba
  %standalone
  %%
  [a-b][a-b0-9]* {System.out.println("OK"); }
  \r|\n|\r\n {System.out.println("ENTER"); }
  [ \t\f] {System.out.println("ESPACIO"); }
  . {System.out.println("FAIL"); }
  ```

### Funciones

| Función | Significado |
| -- | -- |
| yytext() | Devuelve el lexema reconocido |
| yylength() | Devuelve la longitud del lexema |
| yycharat(int n) | Devuelve el enésimo carácter del lexema reconocido |
| yypushback(int n) | Considera los n últimos caracteres del lexema reconocido como no procesados |

## Ejemplo JLex

  ```plain
  %%
  %class Scanner
  %standalone
  %line
  %column
  Digito = [0-9]
  Letra = [a-zA-Z]
  ID = {Letra}({Letra}|{Digito})*
  %% 
  {Digito} { System.out.println("[" + yyline + "," + yycolumn + "] DIGITO: " + yytext()); }
  {Letra}  { System.out.println("[" + yyline + "," + yycolumn + "] LETRA: " + yytext()); }
  {ID}     { System.out.println("[" + yyline + "," + yycolumn + "] ID: " + yytext()); }
  .        { System.out.println("[" + yyline + "," + yycolumn + "] OTRO: " + yytext()); }
  ```

## Ejercicios

1. Escriba un analizador léxico que:
    1. Sustituya las apariciones de un número par escrito en notación binaria por la cadena “BINARIO_PAR”.

        ```plain
        %%
        %class Ejer
        %standalone
        %line
        %column
        BINARIO_PAR = ([0-1]*)0
        BINARIO_NO_PAR = ([0-1]*)1
        %% 
        {BINARIO_PAR} {System.out.print("BINARIO_PAR ");}
        {BINARIO_NO_PAR} {System.out.print(yytext()+" ");}
        (" "|\t|\r)+ {}
        (\n) {System.out.println(" ");}
        ```

    1. Sustituya las cadenas casa, camisa y carcasa que figuran en un texto por la cadena “CA_SA”.

        ```plain
        CA_SA = "ca"("rca"|"mi")?"sa"
        %% 
        {CA_SA} {System.out.print("CA_SA");}
        (\n) {System.out.println(" ");}
        .    {System.out.print(yytext());}
        ```

    1. Reduzca a un único espacio en blanco todas las secuencias de espacios en blanco y tabuladores de un texto.

        ```plain
        ESPACIOS = [" " | \t]+
        %% 
        {ESPACIOS} {System.out.print(" ");}
        (\n) {}
        ```

    1. Suprima los comentarios de línea de un texto (desde un # hasta el fin de la línea).

        ```plain
        Comentario = #(.)*
        %% 
        {Comentario} {System.out.print("");}
        . {System.out.print(yytext());}
        ```

    1. Inserte un * delante y detrás de cada carácter + que figura en un texto.

        ```plain
        %% 
        "+" {System.out.print("*" + yytext() + "*");}
        ```

    1. Enmarca entre corchetes el carácter que precede a cada uno de los puntos que figuran en un texto.

        ```plain
        CORCHETE = (.)"."
        %% 
        {CORCHETE} {System.out.print("["+yycharat(0)+"].");}
        ```

    1. Sustituya las apariciones de un número de la semana (de 1 a 7) por su correspondiente nombre de día.
    1. Sustituya los operadores de suma +, resta -, producto * y división / y potencia ^ por la cadena "OPERADOR“.
    1. Sustituya las apariciones de un número escrito en base hexadecimal por la cadena "HEXADECIMAL".
    1. Escriba en mayúsculas todas las palabras de un texto que comienzan por mayúsculas. (Considere que una palabra es una secuencia constituida por letras minúsculas o mayúsculas).
    1. Sustituya todas las cadenas de un texto por el contenido de la cadena sin las comillas (Por ejemplo: "hola" sería sustituido por hola). Se define como cadena cualquier texto enmarcado entre comillas dobles con la condición de que en su contenido no figuran ni comillas dobles ni saltos de línea.
    1. Sustituya las apariciones de número reales en notación científica por la cadena "REAL". Se admiten como cadenas (lexemas): -3.4 .4 3.E10 .6e-2. No se admiten las siguientes: 45 4.6E .
    1. Sustituya las expresiones horarias que encajan o bien con el patrón HH:MM o bien con el patrón H:MM por la cadena "HORA". Tenga en cuenta que las horas deben ser correctas. Por ejemplo: 29:80 no sería una hora correcta.
    1. Sustituye las palabras de un texto que empiezan por minúsculas por la cadena MINÚSCULA y las palabras que empiecen por mayúsculas por la cadena MAYÚSCULA.
    1. Para representar fechas existen dos formatos: el americano (AAAA/DD/MM) y el europeo (DD/MM/AAAA). Escriba un analizador léxico que imprima todas las fechas de un texto que se ajusten a uno de dichos formatos indicando además qué formato fue usado (Ignore la problemática relacionada con los años bisiestos).
    1. Sustituya cada identificador de un texto por la cadena IDENT salvo que el identificador sea una de las palabras reservadas: If, Then o Else. En este último caso deberá imprimirse respectivamente las cadenas IF, THEN y ELSE. Las anteriores palabras reservadas no son sensibles a la capitalización, es decir, podrán aparecer mezcladas arbitrariamente letras minúsculas y mayúsculas. Por ejemplo, para la palabra reservada Else se admiten también las formas ELSe o eLsE.
    1. Distinga entre los siguientes tipos de números: entero (INT) entero largo (LONG) y real (FLOAT). Los enteros largos son enteros terminados con una letra l mayúscula o minúscula. Los reales deben incluir de forma obligatoria parte entera y parte decimal separadas por un punto.
    1. Reconozca “Identificadores de cualquier longitud que comience con a y contenga a, b o c. No pueden terminar con c y además no pueden contener dos c seguidas.
    1. Reconozca “Identificadores sobre el alfabeto {a,b,c} que tienen por lo menos tres letras”.
    1. Reconozca “Identificadores sobre el alfabeto {%, &} cuyas palabras verificar las siguientes restricciones: 1) Si una palabra tiene menos de cinco &, entonces tiene un número par de &. 2) Si una palabra tiene cinco & o más, entonces contiene un número impar de &. 3) Cualquier palabra contiene al menos un &.
    1. Una cadena comando es el nombre del comando seguido de uno o más espacios en blanco, y a continuación una lista de parámetros que puede ser vacía. Un comando es una secuencia no vacía de cadenas comando separadas por un delimitador. Un delimitador es un elemento del conjunto {. , ;}. Un nombre comienza con una letra y sigue con cero o más letras o dígitos. La lista de parámetros es una secuencia de uno o más nombres separados por uno o más espacios en blanco. Opciones: Expresión regular o gramática regular o autómata finito determinista que reconozca las palabras de dicho lenguaje. Se acepta cerradura positiva. Usar como símbolo de blanco la b tachada.
