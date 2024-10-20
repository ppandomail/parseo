# Análisis Sintáctico (Parser)

## Funciones del Parser

* Comprobar que los tokens que le suministra el Scanner van ordenados según la especificación de la gramática del lenguaje a compilar
* Si no es así, dar los mensajes de error adecuados, pero continuar funcionando sin detenerse, hasta que se llegue al final del archivo de entrada
* Guia casi todo el proceso de la compilación. Esto es así porque por un lado va solicitando al Scanner los tokens y al mismo tiempo va dirigiendo el proceso de análisis semántico y generación de código intermedio (ambos procesos se les llama, traducción dirigida por la sintaxis)

  ![Fases de compilación](img/fase-parser.png)

* Generalmente, los Parsers obtienen un árbol teórico, árbol de análisis sintáctico (AAS) que permite expresar el orden de los lexemas según van apareciendo
* Si se utiliza el método de traducción dirigida por la sintaxis no se llega ni siquiera a plantearse la generación del árbol ya que el Parser realizará las acciones semánticas e incorporará los métodos para realizar la generación de código intermedio y avisará de errores y su recuperación
* Es decir, el Parser hará las funciones de las dos fases siguientes (analizador semántico y generación de código intermedio)
* Si se le da la oportunidad a la creación del AAS, recorriéndolo es posible crear una representación intermedia del programa fuente, ya sea en forma de árbol sintáctico abstracto o en forma de programa en un lenguaje intermedio
* Para generar el Parser, se pueden utilizar dos técnicas:

  | Técnica | Implementación | Eficiencia |
  | -- | -- | -- |
  | **a mano**                                               | difícil | eficiente |
  | **mediante herramientas que lo generan automáticamente** | fácil   | menos eficiente |

## Especificación del Lenguaje (L)

* Para que un Parser funcione, se debe especificar formalmente L
* L debe ser representado con reglas únicas y bien formadas (GIC) de manera que el Parser funcione de una manera bien definida
* Primer paso para implementar un Parser es definir la GIC que debe ser capaz de analizar
* Una GIC es una especificación (formal) de un conjunto de strings válidos (programas)
* La GIC no explica el "significado" de las partes del programa
* Ejemplo: "Cobertorzinho"

  ```grammar
  <exp> ::= a 
  <exp> ::= b
  <exp> ::= virar(<exp>)
  <exp> ::= costurar(<exp>,<exp>)
  ```

## Diseño de Gramáticas Independientes/Libre de Contexto (GIC)

* GIC es aquella en donde sus reglas de producción se pueden aplicar sin considerar el contexto del no-terminal
* GIC se define por tuplas de 4 elementos:

  | Elementos || Representación |
  | -- | -- | -- |
  | **ΣT** | conjunto finito de símbolos terminales | negrita |
  | **ΣN** | conjunto finito de símbolos no terminales | palabras que comienzan con mayúsculas |
  | **P**  | conjunto de reglas de reescritura o producción | ::= ó -> |
  | **S**  | axioma inicial o símbolo distinguido | |

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

* Derivaciones para la cadena: id1 + id2 * id3

  |||
  | -- | -- |
  | **Por la izquierda** | E -> E \* E -> E + E \* E -> id1 + E \* E -> id1 + id2 \* E -> id1 + id2 \* id3 |
  | **Por la derecha**   | E -> E \* E -> E \* id3 -> E + E \* id3 -> E + id2 \* id3 -> id1 + id2 \* id3   |

* Se observa que la gramática es **ambigua**, para su implementación, es necesario evitar la ambigüedad

  ```grammar
  E -> E + T | T
  T -> T * F | F
  F -> id | F | (E)
  ```

## Gramática ambigua

* Cuando una GIC genera una palabra para la que hay más de un AAS se dice que es ambigua
* Debido a que una GIC de estas características permite que a partir del mismo código fuente se puedan obtener diferentes códigos intermedios, no es válido para construir un compilador
* Técnicas que aseguran que si una GIC cumple ciertas reglas, se sabrá con seguridad que es ambigua y por lo tanto no se podrá construir un compilador con ella. En algunos casos se podrá hacer una serie de modificaciones en la GIC que la convierta en no ambigua
* Si una GIC tiene alguna de estas características, se podrá afirmar que es ambigua:

  |||
  | -- | -- |
  | **GIC con ciclos**                   | S -> A    S -> a    A -> S |
  | **GIC con alguna regla de la forma** |  E -> E ... E |
  | **GIC con unas reglas que ofrezcan caminos alternativos entre dos puntos** | S -> B    S -> C    B -> C |
  | **Producciones recursivas en las que las variables no recursivas de la producción puedan derivar a la palabra vacía** | S -> A B S \| S    A -> a \| λ    B -> b \| λ |
  | **Símbolos no terminales que puedan derivar a la palabra vacía y a la misma palabra de terminales, y que aparezcan juntas en la parte derecha de una regla o en alguna forma sentencial** | A -> A B \| a \| λ    B -> b \| a \| λ |

## Tipos de análisis sintáctico y GICs

* Al derivar una secuencia de tokens, si existe más de un no terminal en una cadena de derivación se debe elegir cuál es el próximo no terminal que se va a expandir, es decir, cuál será reemplazado por su lado derecho de la producción
* Por ello se utilizan dos tipos de derivaciones que determinan con precisión cuál será el no terminal a tratar:

  |||
  | -- | -- |
  | **Derivación a izquierda** | ocurre cuando siempre se reemplaza el primer no terminal que se encuentre en una cadena derivación leída de izquierda a derecha |
  | **Derivación a derecha**   | ocurre cuando siempre reemplaza el último no terminal de la cadena de derivación leída de izquierda a derecha |

## Estrategias de análisis sintáctico

* Hay varios algoritmos de análisis sintáctico (incluso para las gramáticas ambiguas), pero su costo computacional es elevado (del orden de n^3).
* Por lo que se debe modificar la GIC (si es necesario) para que se pueda utilizar un algoritmo de menor costo computacional (de costo lineal, n).
* Si se consigue eliminar la ambigüedad, se pueden utilizar dos estrategias:

|||
| -- | -- |
| **Análisis Sintáctico Descendente (ASD)** | produce una derivación por izquierda, que comienza en el no terminal llamado axioma y finaliza con los terminales que forman la construcción analizada |
| **Análisis Sintáctico Ascendente (ASA)**  | utiliza una derivación a derecha, pero en orden inverso, esto es: la última producción aplicada en la derivación a derecha, es la primera producción que es "descubierta", mientras que la primera producción utilizada, la que involucra al axioma, es la última producción en ser "descubierta". En otras palabras, "reduce el árbol de análisis sintáctico" hasta llegar al axioma |

### Análisis Sintáctico Descendente (ASD)

* Se parte de la raíz del AAS y se van aplicando **reglas por la izquierda** para obtener una derivación por la izquierda del símbolo inicial
* Para saber la regla a aplicar, se van leyendo tokens de la entrada
* De esta manera se construye el AAS
* Recorriendo el árbol en profundidad, de izquierda a derecha, se tendrá en las hojas los tokens ordenados
* Las gramáticas de tipo LL(k) se pueden analizar en tiempo lineal por el método de análisis descendente
  * k: número de símbolos de entrada que es necesario conocer en cada momento para poder realizar el análisis

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

* Se parte de la palabra de entrada y se va construyendo el árbol a partir de las hojas para llegar a la raíz
* Si se recorre el árbol generado, se encontrarán los tokens ordenados
* Las gramáticas de tipo LR(k) se pueden analizar en tiempo lineal por el método de análisis ascendente

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

* Son grafos dirigidos donde los elementos no terminales de la GIC aparecen como rectángulos y los terminales como círculos o elipses
* Todo diagrama de sintaxis se supone que tiene un origen y un destino, aunque no se dibujan (se supone que el origen está a la izquierda y el destino a la derecha)

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

## Tools(Parser(Languages(Program)))

* Un desarrollador está expuesto a un número creciente de tecnología de parsing:
  * (mini) Herramientas
    * Pretty Print (... a colores!!)
    * Autocompletado de código
    * AI (Copilot,  Tabnine, Kite)
  * Herramientas
    * Refactoring
    * Crítica de Código y CodeSmells
    * Source Base Project Analytics

* Por ejemplo: Como se implementa el formateo automático o los colores en el código fuente?

* Programa: al menos un lenguaje
* Lenguajes: Gramática (sintaxis+) y Semántica de operación (y denotativa)
* Parsers
  * "Analizan" la validez de un texto como programa
  * Consideran la gramática de un lenguaje
  * Se basan en el AST (abstract syntax trees)
* Herramientas
  * Analizar/Mantener/Documentar
  * Refactorizar/Optimizar
  * Migrar/Reescribir
