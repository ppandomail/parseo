# CUP

* Cup (Constructor of Useful Parsers)  
* Desarrollado en el Instituto de Tecnología de Georgia (EEUU)
* Genera analizadores sintácticos LALR (LookAhead LR).
* Genera código Java y permite introducir acciones semánticas escritas en dicho lenguaje a partir de un archivo con la especificación sintáctica del lenguaje.
* Utiliza una notación basada en reglas de producción.
* Cup es la herramienta principal de todo el proceso.
* Se encarga de ir demandando a JLex los lexemas válidos a analizar y genera el código necesario para el análisis sintáctico y semántico.
* Además, genera el CI.
* Cup analiza las reglas de la gramática del lenguaje a compilar y genera una serie de acciones a partir del análisis de dichas reglas.
* Cup distingue entre terminales (devueltos por JLex) y no terminales, que son las reglas de la gramática.
* Dentro de cada regla, Cup puede ejecutar código Java para realizar su trabajo de análisis.

## Uso de Cup

* Un archivo de entrada para Cup consta de las siguientes partes:
  * Definición de paquete y sentencias de import
  * Sección de código de usuario
  * Declaración de símbolos terminales y no terminales
  * Declaraciones de precedencia
  * Definición del símbolo inicial de la gramática y las reglas de la producción

### Definición de paquete y sentencias de import

* Se indican opcionalmente las clases que se necesitan importar.
* También puede indicarse el paquete al que se quieren hacer pertenecer las clases generadas:

```java
import java_cup.runtime.*;
```

### Sección de código de usuario

* El código de usuario es código Java que se va a incluir en el analizador generado.
* Son declaraciones opcionales.

| Código | Descripción |
| -- | -- |
| **action code {: bloque_java :}** | Todo lo que aquí se declare será accesible a las acciones semánticas |
| **parser code {: bloque_java :}** | Declaraciones Java dentro de la propia clase parser |
| **init with {: bloque_java :}** | Ejecuta el código indicado justo antes de realizar la solicitud del primer token; el objetivo puede ser abrir un fichero, inicializar estructuras de almacenamiento, etc. |
| **scan with {: bloque_java :}** | Permite especificar el bloque de código que devuelve el siguiente token a la entrada |

### Declaración de símbolos terminales y no terminales

* Se enumeran todos los terminales y no terminales de la gramática y, opcionalmente, especifica el tipo (clase-objeto) de cada uno de ellos.
* Si se omite el tipo, al terminal o no terminal correspondiente no se le pueden asignar valores.
* Ejemplo:

```plain
terminal NombreClase terminal1, terminal2, etc.;
non terminal NombreClase noTerminal1, noTerminal2, etc.;
```

### Declaraciones de precedencia

* Permite resolver los conflictos desplazar/reducir ante determinados terminales. Ejemplo:

| Tipo de precedencia | Descripción |
| -- | -- |
| **precedence left terminal1, terminal2, etc.;** | Opta por reducir en vez de desplazar al encontrarse un conflicto en el que el siguiente token es terminal1 o terminal2, etc. |
| **precedence right terminal1, terminal2, etc.;** | Opta por desplazar en los mismos casos |
| **precedence nonassoc terminal1, terminal2, etc.;**  | Produciría un error sintáctico en caso de encontrarse con un conflicto desplazar/reducir en tiempo de análisis |

* Mientras más abajo se encuentre una cláusula de precedencia, más prioridad tendrá a la hora de reducir por ella. Ejemplo:

```plain
 precedence left SUMAR, RESTAR;
 precedence left MULTIPLICAR, DIVIDIR;
```

* Lo que quiere decir que, en caso de ambigüedad, MULTIPLICAR y DIVIDIR tiene más prioridad que SUMAR y RESTAR

### Definición del símbolo inicial de la gramática y las reglas de la producción

* Para definir el símbolo inicial de la gramática, se utiliza la construcción start with ...:

```plain
start with Prog;
```

* Para definir todas las reglas de producción que tengan a un mismo símbolo no terminal como antecedente, se escribe el símbolo no terminal en cuestión, seguido de ::= y, a continuación, las reglas de producción que le tengan como antecedente, separadas por el símbolo |.
* Después de la última regla de producción se termina con  punto y coma.
* Definición de la gramática:

| Operación | ER | CUP |
| -- | -- | -- |
| Concatenación | AB | C::=AB; |
| Unión | A \| B | C::=A \| B; |
| Unión | λ \| A | C::= \| A; |
| Positiva | A+ | C::= A \| AC; |
| Estrella | A* | C::= \| AC; |

* Recordar que cuando se presentó la declaración de símbolos terminales y no terminales, se dijo que estos podían tener asociado un objeto Java.
* Los identificadores que vienen después de un símbolo terminal o no terminal representan variables Java en las que se guarda el objeto asociado a ese símbolo terminal o no terminal.
* Estas variables Java pueden ser utilizadas en la parte {: ... :} que viene a continuación.
* Entre {: ... :} se incluye el código Java que se ejecutará cuando se reduzca la regla de producción en la que se encuentra dicha cláusula {: ... :} -> Acciones semánticas, que puede colocarse cualquier bloque de código Java, siendo de especial importancia los accesos a los atributos de los símbolos de la regla.
* Ejemplo:

```plain
expr ::= expr:e1 MAS expr:e2 
         {: RESULT = new Integer(e1.intValue() + e2.intValue()); :}

```

* Si el no terminal antecedente tiene asociada una cierta clase Java, obligatoriamente dentro de la cláusula {: ... :} habrá una sentencia RESULT = ...;
* El objeto Java guardado en la variable RESULT será el objeto Java que se asocie al no terminal antecedente cuando se reduzca la regla de producción en la que se encuentre esa cláusula. {: ... :}

## Errores sintácticos

* Cuando se produce un error en el análisis sintáctico, Cup invoca a los siguientes métodos:

```java
public void sintax_error(Symbol s);
public void unrecovered_sintax_error(Symbol s) throws Exception;
```

* Una vez que se produce el error, se invoca el método syntax_error. Después se intenta recuperar el error. Si el intento de recuperar el error falla, entonces se invoca al método unrecovered_sintax_error. El objeto de la clase Symbol representa el último token consumido por el analizador.
* Estos métodos se pueden redefinir dentro de la declaración parser code {: ... :}

## El código generado

* **sym.java**: contiene las definiciones de constantes de la clase sym, que asigna un valor entero a cada terminal y, opcionalmente, a cada no terminal. Ejemplo:

```java
/** CUP generated class containing symbol constants. */
public class sym {
    /* terminals */
    public static final int RPARENT = 10;
    public static final int DIGITO = 6;
    public static final int POTENCIA = 7;
    public static final int SUMA = 2;
    public static final int MULTIPLICACION = 5;
    public static final int EOF = 0;
    public static final int SIGNO = 11;
    public static final int RESULTADO = 8;
    public static final int DIVISION = 4;
    public static final int RESTA = 3;
    public static final int LPARENT = 9;
}
```

* **parser.java**: clase pública del analizador sintáctico. Contiene 2 clases:
  * public class parser extends java_cup.runtime.lr_parser { ... }
    * Clase pública del analizador.
    * Subclase de java_cup.runtime.lr_parser que implementa la tabla de acciones de un analizador LALR.
  * class CUP$parser$actions
    * Clase no pública incluida en el archivo que encapsula las acciones de usuario contenidasen la gramática.
    * Contiene el método que selecciona y ejecuta las diferentes acciones definidas en cada regla sintáctica: public final java final java_cup.runtime.Symbol CUP$parser$do_action

* El análisis es realizado por el método **public Symbol parse()**
* La invocación del analizador puede realizarse con un código como el siguiente:

```java
/* declara el objeto parser */
parser analizer;
/* creación */
analizer = new parser();
/* llamada al método parse */
analizer.parse();
```

## Ejemplo calculadora

### Declaraciones JLex

```plain
import java_cup.runtime.Symbol;
%%
%full
%notunix
%cup
%%
[\r\n\t ]+ {/*prescindir de blancos*/}
"+" { return new Symbol (sym.SUMA); }
"-" { return new Symbol (sym.RESTA); }
"*" { return new Symbol (sym.MULTIPLICACION); }
"/" { return new Symbol (sym.DIVISION); }
"^" { return new Symbol (sym.POTENCIA); }
"(" { return new Symbol (sym.LPARENT); }
")" { return new Symbol (sym.RPARENT); }
"=" { return new Symbol (sym.RESULTADO); }
[0-9]+ {return new Symbol (sym.ENTERO, new Integer (yytext())); }
[^0-9\r\n\t \+\-\*"^"/]+ { System.out.println("Error léxico: "+ yytext() ); }
```

### Declaraciones CUP

```plain
// terminales y no terminales
terminal SUMA, RESTA,DIVISION,MULTIPLICACION;
terminal Integer ENTERO;
terminal POTENCIA, RESULTADO, LPARENT, RPARENT, SIGNO;
non terminal sesion, ecuacion;
non terminal Integer expresion;
precedence left RESTA, SUMA;
precedence left MULTIPLICACION, DIVISION;
precedence right SIGNO;
// gramática
sesion ::= ecuacion
         | ecuacion sesion;
ecuacion ::= expresion:E1
             {: System.out.println(E1.intValue()); :}
RESULTADO;
expresion ::= ENTERO:E1
              {:RESULT = new Integer (E1.intValue()); :}
            | expresion:E1 SUMA expresion:E2
              {:RESULT=new Integer( E1.intValue() + E2.intValue()); :}
            | expresion:E1 RESTA expresion:E2
              {:RESULT=new Integer( E1.intValue() - E2.intValue()); :}
            | expresion:E1 MULTIPLICACION expresion:E2
              {:RESULT=new Integer( E1.intValue() * E2.intValue()); :}
            | expresion:E1 DIVISION expresion:E2
              {:RESULT=new Integer(E1.intValue() / E2.intValue()); :}
            | LPARENT expresion:E1 RPARENT
              {:RESULT=new Integer(E1.intValue()); :}
            | RESTA expresion:E1
              {: RESULT=new Integer(0-E1.intValue()); :}
%prec SIGNO;
```
