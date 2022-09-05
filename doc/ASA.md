# Análisis Sintáctico Ascendente (ASA)

* Consiste en ir construyendo la inversa de la derivación por la derecha, a partir de una cadena de entrada, de manera que se parte de las hojas del árbol de análisis y se llega a la raíz.
* Lo mismo que había GIC LL(1), las hay LR(1), pero estas últimas forman un conjunto más amplio.
  * L: left to right: lee la entrada de izquierda a derecha.
  * R: righmost derivation: produce una derivación más a la derecha.
* Hay métodos de análisis para gramáticas lineales por la derecha del tipo LR(1), pero el más utilizado, sobre todo por su sencillez, es el método SLR.
* Ventajas que hacen adecuado utilizar métodos ascendentes en vez de descendentes:
  * Reconocen muchos más lenguajes que las GIC LL y en general, reconocen casi todos los lenguajes más comúnmente usados en programación.
  * Permiten localizar errores sintácticos muy precisamente.
* Un inconveniente es que construir las tablas de análisis necesarias a mano es una tarea larga y compleja, por lo que se hace necesaria la utilización de herramientas de ayuda.
* La mayoría de los métodos o algoritmos para aplicar sobre gramáticas LR emplean lo que se llama un algoritmo por desplazamiento y reducción. Se utiliza una pila y una tabla de análisis.
* Recorren el árbol de derivación de abajo hacia arriba, por lo que se llaman también compiladores ascendentes. Cadena reconocida con el símbolo distinguido en el tope de la Pila.
* Se analiza el tope de la Pila.

## Algoritmo de desplazamiento y reducción

* Operaciones:
  * **Shift**: pasar un símbolo de la entrada al tope de la pila y se pide el siguiente símbolo al analizador léxico.
  * **Reduce**: reemplazar en el tope de la pila el lado derecho de una regla gramatical por el lado izquierdo de la misma.
  * **Accept**: se acepta la palabra.
  * **Reject**: se rechaza la palabra.

* Hay varios tipos de ASA:
  * Análisis con retroceso: ASA con retroceso
  * Análisis predictivo: ASAP
    * LR(0) o SLR
    * LR(1)
    * LALR

## Ejemplo ASA con retroceso

```grammar
S -> c(S,S) | g(S) | 1 | 2
```

![ASA con retroceso](img/asa.png)

| Pila | Entrada | Transición |
| -- | -- | -- |
| λ | c(g(1),2) | δ(q0, λ, λ) = (q1, #) |
| # | c(g(1),2) | shift |
| #c | (g(1),2) | shift |
| #c( | g(1),2) | shift |
| #c(g | (1),2) | shift |
| #c(g( | 1),2) | shift |
| #c(g(1 | ),2) | reduce |
| #c(g(S | ),2) | shift |
| #c(g(S) | ,2) | reduce |
| $c(S | ,2) | shift |
| $c(S, | 2) | shift |
| $c(S,2 | ) | reduce |
| $c(S,S | ) | shift |
| $c(S,S) | λ | reduce |
| #S | λ | δ(q1, λ, S) = (q2, λ) |
| # | λ | δ(q2, λ, #) = (q3, λ) |
| λ | λ | accept |

## SLR

* Analizadores sintácticos ascendentes predictivos.
* Dada la naturaleza inversa de la construcción del árbol, resulta conveniente introducir el concepto de reducción como contrapartida de una derivación.
* Si A -> Bβ, entonces -> Bβ puede reducirse a A, reemplazando el cuerpo de la regla por su cabeza.
* Así, el parsing ascendente consiste en reducir la entrada al símbolo inicial de la gramática.
* De forma equivalente, puede verse como la manera de encontrar la inversa de la derivación por la derecha de la entrada analizada.
* Como ventajas con respecto al parsing descendente, esta técnica no exige que la gramática no sea recursiva a izquierda o que esté factorizada.
* Sin embargo, a diferencia de un parser descendente, resulta sumamente complejo implementar un analizador de este tipo “a mano”.
* Existen herramientas que permiten automatizar la construcción de un analizador sintáctico ascendente.
* Este analizador funciona desplazando (shift) símbolos leídos de la entrada hasta que en el tope de la pila queden los símbolos necesarios para realizar una reducción (reduce), aplicando a la inversa una de las reglas de producción de la gramática.
* En ese momento, se reemplaza dichos símbolos (que coinciden con del cuerpo de la regla) por la cabeza de la regla, la cual ahora queda como nuevo tope.

* El analizador en cada paso realizará una de cuatro acciones posibles:
  * **Shift**: Desplazar el siguiente símbolo de entrada al tope de la pila, es decir, desplazar el puntero de entrada y apilar un estado. Formato: D(q), donde q es el estado a apilar.
  * **Reduce**: Reducir los símbolos a partir del tope de la pila, reemplazandolos por la cabeza de la regla usada para la reducción, es decir, en deshacer una regla n. Formato: R(n), donde n es el número de regla. Para reducir una regla, se desapilan tantos estados como símbolos tenga la parte derecha de la regla y luego se llama al método GOTO(q, A) donde q es el estado que hay en el tope de la pila (tras desapilar los estados correspondientes) y A es la parte izquierda de la regla.
  * **Accept**: Terminar el análisis exitosamente.
  * **Reject**: Reportar que la entrada no pudo ser analizada correctamente.

* Algoritmo de desplazamiento y reducción:
  * Se utiliza una pila de estados y una tabla de análisis. En función del tope de la pila y del estado actual, se pasa a otro estado o se realiza una acción (de las cuatro descritas).
  * A la acción de ir a un estado se lo llama método GOTO.
    * Formato: GOTO(q, A), donde q es un estado y A un no terminal  (el de la parte izquierda de la producción).
    * El estado es el de la pila de estados.
    * Cuando se hace GOTO se debe apilar el estado indicado por esta acción.
    * Ejemplo: GOTO(2, A) su valor en la celda es el que hay que apilar.  

* Ejemplo 1:

```grammar
E -> E + T
E -> T
T -> T * F 
T -> F
F -> (E)
F -> id
```

* Paso 1: Extiender o aumentar la GIC para armar los estados y enumerar las producciones

```grammar
    X -> E
R1: E -> E + T
R2: E -> T
R3: T -> T * F 
R4: T -> F
R5: F -> (E)
R6: F -> id
```

* Paso 2: Armar el AF canónico

![AF SLR](img/afslr.png)

* Paso 3: Armar la Tabla en base al AF
  * GOTO: mirar los estados con no terminal. Ejemplo: GOTO(0, E) =  1, GOTO(0, T) = 2, ...
  * Desplazamientos: mirar los estados con terminal. Ejemplo: D(0, '(') = 4 -> D(4)
  * Reducciones:
    * Mirar los estados con terminal
    * Calcular el conjunto de siguientes de cada no terminal
      * SIG(E) = {$, +, )}
      * SIG(T) = {$, +, ), *}
      * SIG(F) = {$, +, ), *}
    * Mirar las reglas que tienen un punto final
      * Ejemplo: en estado 2: E -> T.  (que es la regla 2) -> en los SIG(E) coloco la regla R(2); es decir, en $, + y ).
  * Los huecos en blanco significan que si se llegan a esa celda en la tabla hay un error.

![Tabla SLR](img/slr.png)

Paso 4: Armar la tabla de parsing

| Pila | Entrada | Acción o GOTO |
| -- | -- | -- |
| 0 | id+id$ | D(5) |
| 0 5| +id$ | R(6)  F -> id (se desapila 1 símbolo) |
| 0 3| +id$ | R(4)  T -> F (se desapila 1 símbolo) |
| 0 2| +id$ | R(2)  E -> T (se desapila 1 símbolo) |
| 0 1| +id$ | D(6) |
| 0 1 6| id$ | D(5) |
| 0 1 6 5 | $ | R(6) F -> id (se desapila 1 símbolo) |
| 0 1 6 3 | $ | R(4)  T -> F (se desapila 1 símbolo) |
| 0 1 6 9 | $ | R(1) F -> E + T (se desapilan 3 símbolos) |
| 0 1 | $ | accept |

* Ejemplo 2:

```grammar
S -> aST
S -> b
T -> cT
T -> d
```

| Q | a | b | c | d | $ | S | T |
| -- | -- | -- | -- | -- | -- | -- | -- |
| 0 | D(2) | D(3) | | | | 1 | |
| 1 | | | | | OK | | |
| 2 | D(2) | D(3) | | | | 4 | |
| 3 | | | R(2) | R(2) | R(2) | | |
| 4 | | | D(6) | D(7) | | | 5 |
| 5 | | | R(1) | R(1) | R(1) | | |
| 6 | | | D(6) | D(7) | | | 8 |
| 7 | | | R(4) | R(4) | R(4) | | |
| 8 | | | R(3) | R(3) | R(3) | | |

| Pila | Entrada | Acción |
| -- | -- | -- |
| 0 | aabcdd$ | D(2) |
| 0 2 | abcdd$ | D(2) |
| 0 2 2 | bcdd$ | D(3) |
| 0 2 2 3 | cdd$ | R(2) |
| 0 2 2 4 | cdd$ | D(6) |
| 0 2 2 4 6 | dd$ | D(7) |
| 0 2 2 4 6 7 | d$ | R(4) |
| 0 2 2 4 6 8 | d$ | R(3) |
| 0 2 2 4 5 | d$ | R(1) |
| 0 2 4 | d$ | D(7) |
| 0 2 4 7 | $ | R(4) |
| 0 2 4 5 | $ | R(1) |
| 0 1 | $ | accept|

* Ejemplo 3:

```grammar
C -> AB
A -> a
B -> a
```

| Q | a | $ | A | B | C |
| -- | -- | -- | -- | -- | -- |
| 0 | D(3) | | 2 | | 1 |
| 1 | | OK | | | |
| 2 | D(5) | | | 4 | |
| 3 | R(2) | | | | |
| 4 | | R(1) | | | |
| 5 | | R(3) | | | |

| Pila | Entrada | Acción |
| -- | -- | -- |
| 0 | aa$ | D(3) |
| 0 3 | a$ | R(2) |
| 0 2 | a$ | D(5) |
| 0 2 5 | $ | R(3) |
| 0 2 4 | $ | R(1) |
| 0 1 | $ | accept |

* Ejemplo 4:

```grammar
E -> id + E
E -> id
```

| Q | id | + | $ | E |
| -- | -- | -- | -- | -- |
| 0 | D(2) | | | 1 |
| 1 | | | OK | |
| 2 | | D(3) | R(2) | |
| 3 | D(2) | | | 4 |
| 4 | | | R(1) | |

| Pila | Entrada | Acción |
| -- | -- | -- |
| 0 | id+id$ | D(2) |
| 0 2 | +id$ | D(3) |
| 0 2 3 | id$ | D(2) |
| 0 2 3 2 | $ | R(2) |
| 0 2 3 4 | $ | R(1) |
| 0 1 | $ | accept |

* Ejemplo 5:

```grammar
S -> aABe
A -> Abc
A -> b
B -> d
```

| Q | a | b | c | d | e | $ | S | A | B |
| -- | -- | -- | -- | -- | -- | -- | -- | -- | -- |
| 0 | D(2) | | | | | | 1 | | |
| 1 | | | | | | OK | | |
| 2 | | D(4) | | | | | | 3 | |
| 3 | | D(6) | | D(7) | | | | | 5 |
| 4 | | R(3) | | R(3) | | | | | | |
| 5 | | | | | D(8) | | | | | |
| 6 | | | D(9) | | | | |  | | |
| 7 | | | | | R(4) | | | | | |
| 8 | | | | | | R(1) | | |  | |
| 9 | | R(2) | | | R(2) | | | | | |

| Pila | Entrada | Acción |
| -- | -- | -- |
| 0 | abde$ | D(2) |
| 0 2 | bde$ | D(4) |
| 0 2 4 | de$ | R(3) |
| 0 2 3 | de$ | D(7) |
| 0 2 3 7 | e$ | R(4) |
| 0 2 3 5 | e$ | D(8) |
| 0 2 3 5 8 | $ | R(1) |
| 0 1 | $ | accept |

* Ejemplo 6

```grammar
S -> (L)
S -> id
L -> SL’
L’ -> ,SL’
L’ -> λ
```

| Q | ( | ) | id | , | $ | S | L | L' |
| -- | -- | -- | -- | -- | -- | -- | -- | -- |
| 0 | D(2) | | D(3) | | | 1 |  | |
| 1 | | | |  | OK  | | |
| 2 | D(2) | | D(3) | | | 5 | 4 | |
| 3 | | R(2) | | R(2) | R(2) | | | |
| 4 | | D(6) | | | | | | | |
| 5 | | R(5) | | D(8) | | | | 7 |
| 6 | | R(1) | | R(1) | R(1) | | | |
| 7 | | R(3) | | | | | | |
| 8 | D(2) | | D(3) | | | 9 | | |
| 9 | | R(5) | | D(8) | | | | 10 |
| 10 | | R(4) | | | | | | |

| Pila | Entrada | Acción |
| -- | -- | -- |
| 0 | (id)$ | D(2) |
| 0 2 | id)$ | D(3) |
| 0 2 3 | )$ | R(2) |
| 0 2 5 | )$ | R(5) |
| 0 2 5 7 | )$ | R(3) |
| 0 2 4 | )$ | D(6) |
| 0 2 4 6 | $ | R(1) |
| 0 1 | $ | accept |

## Conflictos en las tablas SLR

* Hay dos posibles conflictos a la hora de construir una tabla SLR:
  * **Desplazamiento-reducción**: se produce cuando en una misma celda se puede poner un desplazamiento y una reducción. Esto significa que la gramática no es SLR. Se puede resolver una de las dos opciones adecuada para que el analizador reconozca el lenguaje descrito por la gramática.
  * **Reducción-reducción**: aparece cuando en una misma celda es posible reducir de dos maneras diferentes. Esto también implica que la gramática no es SLR. Su solución puede ser elegir una de las dos reducciones, teniendo cuidado de que el analizador reconozca bien el lenguaje definido por la gramática. También se puede optar por modificar la gramática para que eso no ocurra.

## Ejercicios

1. Para la siguiente gramática, construir el autómata y la tabla de análisis SLR:

    ```grammar
    S -> id X | id Y end
    X -> otro | λ
    Y -> begin X end | λ
    ```

    | Q | id | end | begin | otro | $ | S | X | Y |
    | -- | -- | -- | -- | -- | -- | --  | -- | -- |
    | 0 | D(2) | | | | | 1 | | |
    | 1 | | | | | OK | | | |
    | 2 | | R(4)/R(6) | D(7) | D(3) | R(4) | | 4 | 5 |
    | 3 | | R(3) | | | R(3) | | | |
    | 4 | | | | | R(1) | | | |
    | 5 | | D(6) | | | | | | |
    | 6 | | | | | R(2) | | | |
    | 7 | | R(4) | | D(3) | D(4) | | 8 | |
    | 8 | | D(9) | | | | | | |
    | 9 | | R(5) | | | | | | |

1. Para la siguiente gramática:

    ```grammar
    D -> var V : T ;
    V -> id , V | id
    T -> int | bool
    ```

    1. Construir el autómata.
    1. Construir la tabla de análisis SLR.
    1. Analizar la palabra var id , id : int ;

    | Q | var | : | ; | id | , | int | bool | $ | D | V | T |
    | -- | -- | -- | -- | -- | -- | --  | -- | -- | -- | -- | -- |
    | 0 | D(2) | | | | | | | | 1 | | |
    | 1 | | | | | | | | OK | | | |
    | 2 | | | | D(4) | | | | | | 3 | |
    | 3 | | D(5) | | | | | | | | | |
    | 4 | | R(3) | | | D(6) | | | | | | |
    | 5 | | | | | | D(8) | D(9) | | | | 7 |
    | 6 | | | | D(4) | | | | | | 10 | |
    | 7 | | | D(11) | | | | | | | | |
    | 8 | | | R(4) | | | | | | | | |
    | 9 | | | R(5) | | | | | | | | |
    | 10 | | R(2) | | | | | | | | | |
    | 11 | | | | | | | | R(1) | | | |

    | Pila | Entrada | Acción |
    | -- | -- | -- |
    | 0 | var id , id : int ; $ | D(2) |
    | 0 2 | id , id : int ; $ | D(4) |
    | 0 2 4 | , id : int ; $ | D(6) |
    | 0 2 4 6 | id : int ; $ | D(4) |
    | 0 2 4 6 4 | : int ; $ | R(3) (V -> id) |
    | 0 2 4 6 10 | : int ; $ | R(2) (V -> id, V) |
    | 0 2 3 | : int ; $ | D(5) |
    | 0 2 3 5 | int ; $ | D(8) |
    | 0 2 3 5 8 | ;$ | R(4) (T -> int) |
    | 0 2 3 5 7 | ;$ | D(11) |
    | 0 2 3 5 7 11 | $ | R(1) (D -> var V : T ; ) |
    | 0 1 | $ | accept |
