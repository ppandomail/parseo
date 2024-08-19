# Scanner - Herramientas

## JFlex

* Es un generador de scanners.
* Generador de programas java diseñado para procesamiento léxico.
* Parte de un conjunto de reglas léxicas.
* [Descarga](https://www.cs.princeton.edu/~appel/modern/java/JLex/)

![JFlex](img/jlex.png)

* Lo primero es crear un archivo para el análisis léxico con JLex.
* Este archivo especificará los lexemas aceptables por el compilador.
* Por ejemplo, si se va a utilizar la sentencia while para los bucles pero no la for, el scanner dará un aviso de error si se encuentra una sentencia for.

### Uso de JFlex

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

  ```java
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

### Ejemplo JLex

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