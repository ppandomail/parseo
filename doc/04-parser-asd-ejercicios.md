# Análisis Sintáctico Descendente (ASD)

## Ejercicios

1. Sea la siguiente gramática, comprobar si es LL(1) y si lo es, construir su tabla de análisis y verificar si la entrada siguiente es analizada correctamente: a b ( 0 ) a c a

    ```grammar
    S -> A | a
    A -> b ( E ) S L
    L -> c S | λ
    E -> 0 | 1
    ```

    ```plain
    PRIM(S) = {b, a}
    PRIM(A) = {b}
    PRIM(L)  = {c, λ}
    PRIM(E) = {0, 1}
    SIG(S) = {$, c}
    SIG(A) = {$, c}
    SIG(L) = {$, c}
    SIG(E) = {)}
    PRED(S -> A) = {b}
    PRED(S -> a) = {a}
    PRED(A -> b(E)SL) = {b}
    PRED(L -> cS) = {c}
    PRED(L -> λ) = {$, c}
    PRED(E -> 0) = {0}
    PRED(E -> 1) = {1}

    Se observa que para L hay dos conjuntos de predicción con el mismo terminal, por lo que la gramática no es LL(1)
    ```

1. Hacer lo mismo que en caso anterior y si no es LL(1) hacer las modificaciones pertinentes (si se puede) para convertirla en LL(1). Construir su tabla de análisis y verificar si la entrada siguiente es analizada correctamente: z and or y x

    ```grammar
    S -> S or Q | Q
    Q -> Q R | R
    R -> F and | x | y
    F -> z
    ```

    ```plain
    No es LL(1) porque es recursiva por la izquierda. Se elimina esta recursividad.

    S -> QS’ 
    S’ -> orQS’
    S’ -> λ
    Q -> RQ’
    Q’ -> RQ’
    Q’ -> λ
    R -> Fand 
    R -> x
    R -> y
    F -> z

    PRIM(S) = {z, x, y}
    PRIM(S’) = {or, λ}
    PRIM(Q) = {z, x, y}
    PRIM(Q’) = {z, x, y, λ}
    PRIM(R) = {z, x, y}
    PRIM(F) = {z}
    SIG(S) = {$}
    SIG(S’) = {$}
    SIG(Q) = {or, $}
    SIG(Q’) = {or, $}
    SIG(R) = {z, x, y, or, $}
    SIG(F) = {and}
    PRED(S -> QS’) = {z, x, y}
    PRED(S’ -> orQS’) = {or}
    PRED(S’ -> λ) = {$}
    PRED(Q -> RQ’) = {z, x, y}
    PRED(Q’ -> RQ’) = {z, x, y}
    PRED(Q’ -> λ) = {or, $}
    PRED(R -> Fand) = {z} 
    PRED(R -> x) = {x}
    PRED(R -> y) = {y}
    PRED(F -> z) = {z}

    Se observa que es LL(1).
    ```

|| or | and | x | y | z | $ |
| -- | -- | -- | -- | -- | -- | -- |
| S | error | error | S -> QS' | S -> QS' | S -> QS' | error |
| S' | S’ -> orQS’ | error | error | error | error | S’ -> λ |
| Q | error | error | Q -> RQ' | Q -> RQ' | Q -> RQ' | error |
| Q' | Q' -> λ | error | Q' -> RQ' | Q' -> RQ' | Q' -> RQ' | Q' -> λ |
| R | error | error | R -> x | R -> y | R -> Fand | error |
| F | error | error | error | error | F -> z | error |

| Pila | Entrada | Regla o Acción |
| -- | -- | -- |
| $ S | z and or y x $ | S -> QS’ |
| $ S’ Q | z and or y x $ | Q -> RQ’ |
| $ S’ Q’ R | z and or y x $ | R -> Fand |
| $ S’ Q’ and F | z and or y x $ | F -> z |
| $ S’ Q’ and z | z and or y x $ | Emparejar(z) |
| $ S’ Q’ and | and or y x $ | Emparejar(and) |
| $ S’ Q’ | or y x $ | Q’ -> λ |
| $ S’ | or y x $ | S’ -> orQS’ |
| $ S’ Q or | or y x $ | Emparejar(or) |
| $ S’ Q | y x $ | Q -> RQ’ |
| $ S’ Q’ R  | y x $ | R -> y |
| $ S’ Q’ y | y x $ | Emparejar(y) |
| $ S’ Q’ | x $ | Q’ -> RQ’ |
| $ S’ Q’ R | x $ | R -> x |
| $ S’ Q’ x | x $ | Emparejar(x) |
| $ S’ Q’ | $ | Q’ -> λ |
| $ S’ | $ | S’ -> λ |
| $ | $ | Aceptar |
