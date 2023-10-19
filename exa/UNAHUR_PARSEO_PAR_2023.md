
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial octubre 2023

* ALUMNO:  
* LU:
* CARRERA:

---

### NOTA: EL EXAMEN ESCRITO ES UN DOCUMENTO DE GRAN IMPORTANCIA PARA LA EVALUACIÓN DE LOS CONOCIMIENTOS ADQUIRIDOS, POR LO TANTO, SE SOLICITA LEER ATENTAMENTE LO SIGUIENTE

* Responda claramente cada punto, detallando con la mayor precisión posible lo solicitado.
* Sea prolijo y ordenado en el desarrollo de los temas.
* Sea cuidadoso con las faltas de ortografía y sus oraciones.
* No desarrollar el examen en lápiz.
* Aprobación del examen: Con nota mayor o igual a 4 (cuatro)
* Condiciones de aprobación: 60%
* Duración de examen: 3 horas.

---

1. [2 puntos]: Diséñese el diagrama de transiciones para un scanner del lenguaje de programación U#
    * Las variables y nombres se forman con la combinación de las letras A, B, C y siempre deben terminar con un número. Ejemplos: A5, BCA15, AABA2.
    * Las sentencias válidas son:
      * Sentencia declarativa: variable 1, variable 2,..., variable n : tipo. Ejemplo: A5, BCA15: entero
      * Sentencia de impresión: Imprimir variables separadas por ';'' Ejemplo: imprimir A5; BCA15
    * Los tipos de datos válidos son: entero, letra o palabra.
    * Un programa tiene una estructura de la forma:

    ```plain
    INICIO nombrePrograma Sentencias...FIN.
    (No es obligatorio darle un nombre al programa)
    ```

    ```plain
    ID = [A-C] [A-C0-9]* [0-9]
    COMA = ","
    PUNTO_Y_COMA = ";"
    DOS_PUNTOA = ":"
    TIPO = "entero" | "letra" | "palabra"
    IMPRIMIR = "imprimir"
    INICIO = "inicio"
    FIN = "fin"
    ```

1. [4 puntos]: Constrúyase el parser ASDP LL(1) y muéstrese el parsing para la entrada S -> a | g ( S ). Sabiendo que, una gramática consta de una lista de una o más reglas, cada una de las cuales tiene tres partes: (1) una metavariable, que se utiliza como nombre de la regla; (2) el símbolo -> ; y (3) una lista de una o más producciones.

    ```grammar
    G  -> R LR
    LR -> R LR | λ
    R  -> N '->' P LP
    N  -> cualquier símbolo no terminal: {S}
    LP -> '|' P LP | λ
    P  -> N P' | T P'
    P' -> N P' | T P' | λ 
    T  -> cualquier símbolo terminal: {a, g, (, )}

    PRIM(G) = {S}
    PRIM(LR) = {S, λ}
    PRIM(R) = {S}
    PRIM(N) = {S}
    PRIM(LP) = {|, λ}
    PRIM(P) = {S, a, g, (, )}
    PRIM(P') = {S, a, g, (, ), λ}
    PRIM(T) = {a, g, (, )}

    SIG(G) = {$}
    SIG(LR) = {$}
    SIG(R) = {S, $}
    SIG(N) = {'->', S, a, g, (, ), |, $} 
    SIG(LP) = {S, $}  
    SIG(P) = {|, S, $}
    SIG(P') = {|, S, $}
    SIG(T) = {S, a, g, (, ), |, $}

    PRED(G -> R LR) = {S}
    PRED(LR -> R LR) = {S}
    PRED(LR -> λ) = {$}
    PRED(R -> N '->' P LP) = {S}
    PRED(N -> S) = {S}
    PRED(LP -> '|' P LP) = {'|'}
    PRED(LP -> λ) = {S, $} 
    PRED(P -> N P') = {S}
    PRED(P -> T P') = {a, g, (, )}
    PRED(P' -> N P') = {S}
    PRED(P' -> T P') = {a, g, (, )}
    PRED(P' -> λ) = {|, S, $}
    PRED(T -> a) = {a}
    PRED(T -> g) = {g}
    PRED(T -> () = {(}
    PRED(T -> )) = {)}
    ```

1. [4 puntos]: Constrúyase el parser ASAP SLR y muéstrese el parsing para la entrada ( ( λ ( x ) x ) y ). Sabiendo que, una expresión puede ser una de tres cosas: a. una variable b. una secuencia que consta de: un paréntesis izquierdo, una expresión, una segunda expresión y un paréntesis derecho. Tenga en cuenta que no es necesario que las dos expresiones internas sean iguales. Esta regla suele denominarse aplicación. c. una secuencia que consta de: un paréntesis izquierdo, un carácter lambda literal, un paréntesis izquierdo, cualquier variable, un paréntesis derecho, cualquier expresión y un paréntesis derecho. Esta regla suele denominarse abstracción.

    ```grammar
    S ->  v
    S -> (SS)
    S -> (λ(v)S)
    
       S' -> S
    R1 S -> v
    R2 S -> (SS)
    R3 S -> (λ(v)S)

    SIG(S) = {$, v, (, )}
    ```

    |  Q  |  (  |  )  |  v  |  λ  |  $  |  S  |
    | --- | --- | --- | --- | --- | --- | --- |
    | q0  | D(3)| err | D(2)| err | err |  1  |
    | q1  | err | err | err | err | OK  | err |
    | q2  | R(1)| R(1)| R(1)| err | R(1)| err |
    | q3  | D(3)| err | D(2)| D(7)| err |  4  |
    | q4  | D(3)| err | D(2)| err | err |  5  |
    | q5  | err | D(6)| err | err | err | err |
    | q6  | R(2)| R(2)| R(2)| err | R(2)| err |
    | q7  | D(8)| err | err | err | err | err |
    | q8  | err | err | D(9)| err | err | err |
    | q9  | err |D(10)| err | err | err | err |
    | q10 | D(3)| err | D(2)| err | err | 11  |
    | q11 | err |D(12)| err | err | err | err |
    | q12 | R(3)| R(3)| R(3)| err | R(3)| err |

    | Pila            | Entrada     | Acción |
    | ---             | ---         | --- |
    | 0               | ((λ(x)x)y)$ | D(3) |
    | 03              | (λ(x)x)y)$  | D(3) |
    | 033             | λ(x)x)y)$   | D(7) |
    | 0337            | (x)x)y)$    | D(8) |
    | 03378           | x)x)y)$     | D(9) |
    | 033789          | )x)y)$      | D(10)|
    | 033789 10       | x)y)$       | D(2) |
    | 033789 10 2     | )y)$        | R(1): S -> v |
    | 033789 10 11    | )y)$        | D(12)|
    | 033789 10 11 12 | y)$         | R(3): S -> (λ(v)S) |
    | 034             | y)$         | D(2) |
    | 0342            | )$          | R(1): S -> v |
    | 0345            | )$          | D(6) |
    | 03456           | $           | R(2): S -> (SS) |
    | 01              | $           | Accept |

---
