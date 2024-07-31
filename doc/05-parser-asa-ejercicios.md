# Análisis Sintáctico Ascendente (ASA)

## Ejercicios

1. Construir el autómata y la tabla de análisis SLR para la siguiente gramática:

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
