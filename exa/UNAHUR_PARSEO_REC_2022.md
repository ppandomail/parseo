# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Recuperatorio noviembre 2022

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
* Duración de examen: 2 horas.

---

1. [2 puntos]: Diseñese un DT y posterior traducción a una TT para especificar un scanner que reconozca operadores aritméticos (+,-,++,--) y la palabra reservada if. Usar tokens: OP y IF.

    * **Solución:**

    | Q | + | - | if | token | retroceso |
    | -- | -- | -- | -- | -- | -- |
    | q0 | q1 | q3 | q5 | - | - |
    | q1 | q2 | - | - | OP | 0 |
    | q2 | - | - | - | OP | 0 |
    | q3 | - | q4 | - | OP | 0 |
    | q4 | - | - | - | OP | 0 |
    | q5 | - | - | - | IF | 0 |

1. [3 puntos]: Compruébese si la siguiente GIC es LL(1), mostrando los conjuntos PRIM, SIG y PRED.

    ```grammar
    S -> (A)
    A -> CB
    B -> ;A | λ
    C -> x | S 
    ```

    * **Solución:**
    * PRIM(S) = {(}
    * PRIM(A) = {x, (}
    * PRIM(B) = {;, λ}
    * PRIM(C) = {x, (}
    * SIG(S) = {$}
    * SIG(A) = {)}
    * SIG(B) = {)}
    * SIG(C) = {;, )}
    * PRED(S -> (A)) = {(}
    * PRED(A -> CB) = {x, (}
    * PRED(B -> ;A) = {;}
    * PRED(B -> λ) = {)}
    * PRED(C -> x) = {x}
    * PRED(C -> S) = {(}

    | | ; | x | ( | ) | $ |
    | -- | -- | -- | -- | -- | -- |
    | S | error | error | S -> (A) | error | error |
    | A | error | A -> CB | A -> CB | error | error |
    | B | B -> ;A | error | error | B -> λ | error |
    | C | error | C -> x | C -> S | error | error |

1. [1 punto]: Muéstrese los movimientos realizados por el ASDP LL(1) con la entrada (x)

    * **Solución:**

    | Pila | Entrada | Salida |
    | -- | -- | -- |
    | $S | (x)$ | S -> (A) |
    | $)A( | (x)$ | emparejar(() |
    | $)A | x)$ | A -> CB |
    | $)BC | x)$ | C -> x |
    | $)Bx | x)$ | emparejar(x) |
    | $)B | )$ | B -> λ |
    | $) | )$ | emparejar()) |
    | $ | $ | accept |

1. [3 puntos]: Constrúyase la tabla de parsing SLR para la siguiente GIC: P = {(S -> (S)), (S -> x)}

    * **Solución:**

    ```grammar
       S' -> S
    R1 S -> (S)
    R2 S -> x
    
    SIG(S) = {$, )}

      (0)               (1)
    S'-> .S   __ S __ S'-> S.
    S -> .(S)           (2)               (4)               (5)
    S -> .x   __ ( __ S -> (.S)  __ S __ S -> (S.) __ ) __ S -> (S). 
                      S -> .(S)
                      S -> .x
                        (3)
              __ x __ S -> x.    
                            

    Luego: (2,()=2; (2,x)=3   
    ```

    | Q | x | ( | ) | $ | S |
    | -- | -- | -- | -- | -- | -- |
    | 0 | D(3) | D(2) | | | 1 |
    | 1 | | | | OK | |
    | 2 | D(3) | D(2) | | | 4 |
    | 3 | | | R(2) | R(2) | |
    | 4 | | | D(5) | | |
    | 5 | | | R(1) | R(1) | |

1. [1 punto]: Muéstrese los movimientos realizados por el ASAP SLR con la entrada (x)

    * **Solución:**

    | Pila | Entrada | Acción |
    | -- | -- | -- |
    | $0 | (x)$ | D(2) |
    | $02 | x)$ | D(3) |
    | $023 | )$ | R(2): S -> x |
    | $024 | )$ | D(5) |
    | $0245 | $ | R(1): S -> (S) |
    | $01 | $ | accept |

---
