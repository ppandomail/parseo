# Análisis Sintáctico (Parser)

## Funciones del Parser

* La función principal es comprobar que los tokens que le suministra el Scanner van ordenados según la especificación de la gramática del lenguaje a compilar. Y si no es así, dar los mensajes de error adecuados, pero continuar funcionando sin detenerse, hasta que se llegue al final del archivo de entrada.
* Es esencial que el proceso de análisis no se detenga al primer error encontrado, ya que así podrá informar al usuario en un solo informe de todos los errores generados.
* El Parser es la unidad que guía todo el proceso, o casi todo, de la compilación. Esto es así porque por un lado va solicitando al Scanner los tokens y al mismo tiempo va dirigiendo el proceso de análisis semántico y generación de código intermedio (ambos procesos se les llama, traducción dirigida por la sintaxis).
* Las fases de compilación, entre las que se encontraba la fase de análisis sintáctico, son las siguientes:

![Fases de compilación](img/fase-parser.png)

* Generalmente, los Parsers obtienen un árbol teórico, árbol de análisis sintáctico (AAS) que permite expresar el orden de los lexemas según van apareciendo.
* Si se utiliza el método de traducción dirigida por la sintaxis no se llega ni siquiera a plantearse la generación del árbol ya que el Parser realizará las acciones semánticas e incorporará los métodos para realizar la generación de código intermedio y avisará de errores y su recuperación.
* Es decir, el Parser hará las funciones de las dos fases siguientes (analizador semántico y generación de código intermedio).
* Si se le da la oportunidad a la creación del AAS, recorriéndolo es posible crear una representación intermedia del programa fuente, ya sea en forma de árbol sintáctico abstracto o en forma de programa en un lenguaje intermedio.
* Para generar el Parser, se pueden utilizar dos técnicas:
  * o bien se hace a mano (es difícil aunque eficiente en su funcionamiento)
  * o mediante herramientas que lo generan automáticamente (es menos eficiente pero más fácil de implementar).

## Especificación del Lenguaje

* Para que un Parser funcione, se debe especificar el lenguaje que debe poder leer.
* El lenguaje debe ser representado con unas reglas únicas y bien formadas de manera que el Parser funcione de una manera bien definida.
* Es decir, el lenguaje debe ser formal (tener unas reglas bien definidas). A estas reglas se las llama gramática.
* Por lo tanto, el primer paso para poder implementar un AS es definir la gramática que debe ser capaz de analizar.
* Ejemplo: "Cobertorzinho"

```grammar
<exp> ::= a 
<exp> ::= b
<exp> ::= virar(<exp>)
<exp> ::= costurar(<exp>,<exp>)
```

## Diseño de GIC

* Una Gramática Independiente de Contexto (GIC) se define por tuplas de 4 elementos:
  * ΣT: conjunto finito de símbolos terminales
  * ΣN: conjunto finito de símbolos no terminales
  * P: conjunto de reglas de reescritura o producción
  * S: axioma inicial o símbolo distinguido
* Generalmente, se representan los no terminales por palabras que comienzan con mayúsculas y los terminales con negrita. También se representará en las reglas de reescritura la flecha (::= ó ->)
* Ejemplo: sintaxis del lenguaje de fechas (GIC no recursiva)

  ```grammar
  <date> ::= <d><d>/<d><d>/<d><d><d><d>
  ```

* Ejemplo: sintaxis del lenguaje de secuencia de números (GIC recursiva)

  ```grammar
  <real_number> ::= <digit_seq> . <digit_seq>
  <digit_seq> ::= <d> | <d> <digit_seq>
  <d> ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
  ```

* Ejemplo sintaxis del lenguaje de expresiones aritméticas

  ```grammar
  E -> E + E | E * E | num | id | (E)
  ```

* Derivaciones para la cadena (ristra de tokens): id1 + id2 * id3
  * Por la izquierda: E -> E \* E -> E + E \* E -> id1 + E \* E -> id1 + id2 \* E -> id1 + id2 \* id3
  * Por la derecha: E -> E \* E -> E \* id3 -> E + E \* id3 -> E + id2 \* id3 -> id1 + id2 \* id3

* Se observa que la gramática es **ambigua**, para su implementación, es necesario evitar la ambigüedad.

  ```grammar
  E -> E + T | T
  T -> T * F | F
  F -> id | F | (E)
  ```

## Gramática ambigua

* Cuando una GIC genera una palabra para la que hay más de un AAS se dice que es ambigua.
* Debido a que una GIC de estas características permite que a partir del mismo código fuente se puedan obtener diferentes códigos intermedios, no es válido para construir un compilador.
* Técnicas que aseguran que si una GIC cumple ciertas reglas, se sabrá con seguridad que es ambigua y por lo tanto no se podrá construir un compilador con ella. En algunos casos se podrá hacer una serie de modificaciones en la GIC que la convierta en no ambigua.
* Si una GIC tiene alguna de estas características, se podrá afirmar que es ambigua:
  * GIC con ciclos:  S -> A | a       A -> S
  * GIC con alguna regla de la forma: E -> E ... E
  * GIC con unas reglas que ofrezcan caminos alternativos entre dos puntos:  S -> B | C    B -> C
  * Producciones recursivas en las que las variables no recursivas de la producción puedan derivar a la palabra vacía. Por ejemplo:  S -> A B S | S         A -> a | λ        B -> b | λ
  * Símbolos no terminales que puedan derivar a la palabra vacía y a la misma palabra de terminales, y que aparezcan juntas en la parte derecha de una regla o en alguna forma sentencial. Por ejemplo: A -> A B | a | λ       B -> b | a | λ

## Tipos de análisis sintáctico y GICs

* Al derivar una secuencia de tokens, si existe más de un no terminal en una cadena de derivación se debe elegir cuál es el próximo no terminal que se va a expandir, es decir, cuál será reemplazado por su lado derecho de la producción.
* Por ello se utilizan dos tipos de derivaciones que determinan con precisión cuál será el no terminal a tratar:
  * **Derivación a izquierda**: ocurre cuando siempre se reemplaza el primer no terminal que se encuentre en una cadena derivación leída de izquierda a derecha.
  * **Derivación a derecha**: ocurre cuando siempre reemplaza el último no terminal de la cadena de derivación leída de izquierda a derecha.

## Estrategias de análisis sintáctico

* Hay varios algoritmos de análisis sintáctico (incluso para las gramáticas ambiguas), pero su costo computacional es elevado (del orden de n^3).
* Por lo que se debe modificar la GIC (si es necesario) para que se pueda utilizar un algoritmo de menor costo computacional (de costo lineal, n).
* Si se consigue eliminar la ambigüedad, se pueden utilizar dos estrategias:
  * **Análisis Sintáctico Descendente (ASD)**: produce una derivación por izquierda, que comienza en el no terminal llamado axioma y finaliza con los terminales que forman la construcción analizada.
  * **Análisis Sintáctico Ascendente (ASA)**: utiliza una derivación a derecha, pero en orden inverso, esto es: la última producción aplicada en la derivación a derecha, es la primera producción que es “descubierta”, mientras que la primera producción utilizada, la que involucra al axioma, es la última producción en ser “descubierta”. En otras palabras, “reduce el árbol de análisis sintáctico” hasta llegar al axioma.

### Análisis Sintáctico Descendente (ASD)

* Se parte de la raíz del AAS y se van aplicando **reglas por la izquierda** para obtener una derivación por la izquierda del símbolo inicial.
* Para saber la regla a aplicar, se van leyendo tokens de la entrada.
* De esta manera se construye el AAS.
* Recorriendo el árbol en profundidad, de izquierda a derecha, se tendrá en las hojas los tokens ordenados.
* Las gramáticas de tipo LL(k) se pueden analizar en tiempo lineal por el método de análisis descendente.
  * k: número de símbolos de entrada que es necesario conocer en cada momento para poder realizar el análisis.

* Ejemplo: derivar la cadena aabcdd

```grammar
S -> aST | b
T -> cT | d
```

| Cadena de derivación obtenida | Próxima producción a aplicar |
| -- | -- |
| S | S -> aST |
| aST | S -> aST |
| aaSTT | S -> b |
| aabTT | T -> cT |
| aabcTT | T -> d |
| aabcdT | T -> d |
| aabcdd | accept |

* Tipos de análisis:
  * **Análisis Sintáctico Descendente con retroceso** -> ASD con retroceso
  * **Análisis Sintáctico Descendente Predictivo** -> ASDP LL(1)

### Análisis Sintáctico Ascendente (ASA)

* Se parte de la palabra de entrada y se va construyendo el árbol a partir de las hojas para llegar a la raíz.
* Si se recorre el árbol generado, se encontrarán los tokens ordenados.
* Las gramáticas de tipo LR(k) se pueden analizar en tiempo lineal por el método de análisis ascendente.

* Ejemplo: derivar la cadena aabcdd

```grammar
S -> aST | b
T -> cT | d
```

1. Derivación a derecha:

    | Cadena de derivación obtenida | Próxima producción a aplicar |
    | -- | -- |
    | S | S -> aST |
    | aST | T -> d |
    | aSd | S -> aST |
    | aaSTd | T -> cT |
    | aaScTd | T -> d |
    | aaScdd | S -> b |
    | aabcdd | |

1. Orden Inverso a la derivación por derecha

    | Cadena de derivación obtenida | Próxima producción a aplicar |
    | -- | -- |
    | aabcdd | S -> b |
    | aaScdd | T -> d |
    | aaScTd | T -> cT |
    | aaSTd | S -> aST |
    | aSd | T -> d |
    | aST | S -> aST |
    | S | accept |

* Tipos de análisis:
  * **Análisis Sintáctico Ascendente con retroceso** -> ASA con retroceso
  * **Análisis Sintáctico Ascendente Predictivo** -> ASAP SLR

## Diagrama de sintaxis

* Son grafos dirigidos donde los elementos no terminales de la GIC aparecen como rectángulos y los terminales como círculos o elipses.
* Todo diagrama de sintaxis se supone que tiene un origen y un destino, aunque no se dibujan (se supone que el origen está a la izquierda y el destino a la derecha).

![Diagrama IF](img/if.png)

* Ejemplo: Sentencia -> Identificador "=" Número

  ```plain
  ---->[Identificador]---->(=)---->[Número]---->
  ```

  ```c
  void secuencia() {
    if (token == Identificador) token = getToken();
    else if (token == IGUAL) token = getToken();
    else if (token == Numero) token = getToken();
    else error();
  }  
  ```

* Ejemplo: Sentencia -> (Sentencia ";")+

  ```plain
    -----------------------------
    |                           ^ 
    v                           |
  ---->[Sentencia]---->(;) ------->
  ```

  ```c
  void secuencia() {
    do {
      sentencia();
      while (token != PUNTOCOMA) {
        error(); 
        token = getToken();
      }
      token = getToken();
    } while (token != EOF);
  }
  ```

## Ejercicios

1. Diseñar una gramática no ambigua para el lenguaje de las expresiones que se pueden construir con true y false y los operadores booleanos or, and, not y los paréntesis. La precedencia de mayor a menor es not and or. Los dos últimos son asociativos por la derecha.

    ```grammar
    E -> T or E | T
    T -> F and T | F
    F -> not F | true | false | (E)
    ```

1. Construir una gramática no ambigua que reconozca todas las declaraciones posibles de variables de los siguientes tipos: int, String, boolean y double en Java. Por ejemplo:

    ```java
    int x, y;
    String cadena;
    double a;
    boolean b;
    ```

    ```grammar
    S -> SE | E
    E -> T F ptocoma
    T -> int | String | double | boolean
    F -> F id coma | id
    ```

1. Crear los diagramas de sintaxis y el programa para esta gramática:

    ```grammar
    Programa -> Declaraciones Sentencias
    Declaraciones -> (Decl “;’)+
    Decl -> Entero Identificador 
    Sentencias -> (Asignación “;”)+
    Asignación -> ParteIzquierda “=“ ParteDerecha
    ParteIzquierda -> Identificador
    ParteDerecha -> Expresión
    Expresión -> (Expresión “+” Expresión) | (Expresión “-” Expresión) 
    Expresión -> (Identificador | Numero)
    ```

    ```plain
    --->[D]--->[S]--->
       _____________  
      |             |    
    --->[D']--->(;)--->

    etc.
    ```

    ```java
    class Parser {

      Token token;
      int cursor = 0;
      Token[] programa;

      enum Token {
        PUNTOCOMA, Entero, Identificador, IGUAL, Numero, MENOS, MAS
      }

      Parser(Token[] programa) {
        this.programa = programa;
      }

      void programa() {
        declaraciones();
        sentencias();
      }

      void declaraciones() {
        do {
          decl();
          while (token != Token.PUNTOCOMA) {
            error("';'");
            token = getToken();
          }
          token = getToken();
        } while (token == Token.Entero);
      }

      void decl() {
        token = getToken();
        if (token == Token.Entero) {
          token = getToken();
          if (token == Token.Identificador)
            token = getToken();
          else
            error("'Identificador'");
        } else
            error("'Entero'");
      }

      void sentencias() {
        do {
          asignacion();
          while (token != Token.PUNTOCOMA) {
            error("';'");
            token = getToken();
          }
          token = getToken();
        } while (token == Token.Identificador);
      }
      
      void asignacion() {
      if (token == Token.Identificador) {
      token = getToken();
      if (token == Token.IGUAL) {
        token = getToken();
        expresion();
      } else
        error("'='");
    } else
        error("'Identificador'");
    }

    void expresion() {
      if (token == Token.Identificador || token == Token.Numero) {
        token = getToken();
        if (token == Token.MAS || token == Token.MENOS) {
          token = getToken();
          expresion();
        }
      } else
        error("'Identificador' o 'Numero'");
    }

    Token getToken() {
      if (cursor < programa.length) {
        Token tkn = programa[cursor++];
        System.out.println(tkn);
        return tkn;
      }
      return null;
    }

    void error(String mensaje) {
      System.err.println("Se esperaba: " + mensaje + "\n");
      System.exit(0);
    }

    public static void main(String[] args) {
      new Parser(new Token[] {
        Token.Entero, Token.Identificador, Token.PUNTOCOMA, Token.Identificador, Token.IGUAL,
        Token.Identificador, Token.PUNTOCOMA
      }).programa();
    }

  }

1. En la siguiente gramática incontextual, identifique no terminales y terminales

    ```grammar
    <Programa> -> <Instrucciones> 
    <Instrucciones> -> <Instrucción> | <Instrucción> <Instrucciones>
    <Instrucción> -> <Operando> <Operador> <Operando>;
    <Operando> -> true | false
    <Operador> -> AND | OR | NOT
    ```

    ```plain
    TERMINALES = {true, false, AND, OR, NOT}
    NO TERMINALES = {<Programa>, <Instrucciones>, <Instrucción>, <Operando>, <Operador>}
    ```

1. Dibuje el Árbol de Análisis Sintáctico correspondiente al reconocimiento de la siguiente secuencia:

  ```java
  true AND false;     
  false OR true;
  ```
