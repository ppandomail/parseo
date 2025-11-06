# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial octubre 2022

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

1. [2 puntos]: Constrúyase la tabla de transiciones para un scanner del lenguaje L sobre el Σ = {letra, digito} cuyas palabras verifican las siguientes restricciones:
    * Si una palabra tiene menos de cinco letras, entonces tiene un número par de letras.
    * Si una palabra tiene cinco letras o más, entonces contiene un número impar de letras.
    * Cualquier palabra contiene al menos una letra.

    * **Solución:**

    | Q | letra | digito | token | retroceso |
    | -- | -- | -- | -- | -- |
    | q0 | q1 | q0 | - | - |
    | q1 | q2 | q1 | - | - |
    | q2 | q3 | q2 | accept | 0 |
    | q3 | q4 | q3 | - | - |
    | q4 | q5 | q4 | accept | 0 |
    | q5 | q6 | q5 | accept | 0 |
    | q6 | q5 | q6 | - | - |

1. [3 puntos]: Compruébese si la siguiente GIC es LL(1), mostrando los conjuntos PRIM, SIG y PRED.

    ```grammar
    E -> yE'E''
    E'' -> xE'' | λ
    E' -> A | B
    B -> bB'
    B' -> A | bA
    A -> λ
    ```

    * **Solución:**
    * PRIM(E) = {y}
    * PRIM(E'') = {x, λ}
    * PRIM(E') = {λ, b}
    * PRIM(B) = {b}
    * PRIM(B') = {λ, b}
    * PRIM(A) = {λ}
    * SIG(E) = {$}
    * SIG(E'') = {$}
    * SIG(E') = {x, $}
    * SIG(B) = {x, $}
    * SIG(B') = {x, $}
    * SIG(A) = {x, $}
    * PRED(E -> yE'E'') = {y}
    * PRED(E'' -> xE'') = {x}
    * PRED(E'' -> λ) = {$}
    * PRED(E' -> A) = {x, $}
    * PRED(E' -> B) = {b}
    * PRED(B -> bB') = {b}
    * PRED(B' -> A) = {x, $}
    * PRED(B' -> bA) = {b}
    * PRED(A -> λ) = {x, $}

    | | x | y | b | $ |
    | -- | -- | -- | -- | -- |
    | E | error | E -> yE'E'' | error | error |
    | E' | E' -> A | error | E' -> B | E' -> A |
    | E'' | E'' -> xE'' | error | error | E'' -> λ |
    | B | error | error | B -> bB' | error |
    | B' | B' -> A | error | B' -> bA | B' -> A |
    | A | A -> λ | error | error | A -> λ |

1. [1 punto]: Muéstrese los movimientos realizados por el ASDP LL(1) con la entrada ybxx

    * **Solución:**

    | Pila | Entrada | Salida |
    | -- | -- | -- |
    | $E | ybxx$ | E -> yE'E'' |
    | $E''E'y | ybxx$ | emparejar(y) |
    | $E''E' | bxx$ | E' -> B |
    | $E''B | bxx$ | B -> bB' |
    | $E''B'b | bxx$ | emparejar(b) |
    | $E''B' | xx$ | B' -> A |
    | $E''A | xx$ | A -> λ |
    | $E'' | xx$ | E'' -> xE'' |
    | $E''x | xx$ | emparejar(x) |
    | $E'' | x$ | E'' -> xE'' |
    | $E''x | x$ | emparejar(x) |
    | $E'' | $ | E'' -> λ |
    | $ | $ | accept |

1. [3 puntos]: Constrúyase la tabla de parsing SLR para la siguiente GIC: P = {(S -> CC), (C -> cC), (C -> d)}

    * **Solución:**

    ```grammar
       S' -> S
    R1 S -> CC
    R2 C -> cC
    R3 C -> d

    SIG(S) = {$}
    SIG(C) = {c, d, $}


      (0)              (1)
    S'-> .S  __ S __ S'-> S.
    S -> .CC           (2)               (5)
    C -> .cC __ C __ S -> C.C  __ C __ S -> CC. 
    C -> .d          C -> .cC
                     C -> .d
                       (3)               (6)
            __  c __ C -> c.C  __ C __ C -> cC.   
                     C -> .cC
                     C -> .d
                       (4)
            __  d __ C -> d.          

    Luego: (2,c)=3; (2,d)=4; (3,c)=3; (3,d)=4    
    ```

    | Q | c | d | $ | S | C |
    | -- | -- | -- | -- | -- | -- |
    | 0 | D(3) | D(4) | | 1 | 2 |
    | 1 | | | OK | | |
    | 2 | D(3) | D(4) | | | 5 |
    | 3 | D(3) | D(4) | | | 6 |
    | 4 | R(3) | R(3) | R(3) | | |
    | 5 | | | R(1) | | |
    | 6 | R(2) | R(2) | R(2) | | |

1. [1 punto]: Muéstrese los movimientos realizados por el ASAP SLR con la entrada cdd

    * **Solución:**

    | Pila | Entrada | Acción |
    | -- | -- | -- |
    | 0 | cdd$ | D(3) |
    | 03 | dd$ | D(4) |
    | 034 | d$ | R(3): C -> d |
    | 036 | d$ | R(2): C -> cC |
    | 02 | d$ | D(4) |
    | 024 | $ | R(3): C -> d |
    | 025 | $ | R(1): S -> CC |
    | 01 | $ | accept |

---
