
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Recuperatorio noviembre 2023

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

1. [2 puntos] Diséñese el DT para un scanner que reconoce identificadores que inician con una letra, le pueden o no seguir letras o dígitos o guiones bajos. No pueden ocurrir dos guiones bajos seguidos, tampoco pueden aparecer al final de un identificador.

    ```plain
    L ::= [a-zA-Z]
    D ::= [0-9]
    G ::= [_]
    ID ::= L (L | D | G L | G D)*
    ```

1. [4 puntos] Constrúyase el parser ASDP LL(1) y muéstrese el parsing para una entrada de longitud mayor a 4. Palabras válidas del lenguaje {[], [id], [id,id], [id,id,id], ... }

    ```plain
    S -> [A
    A -> B] | ]
    B -> idC
    C -> λ | ,idC

    PRIM(S) = {[}
    PRIM(A) = {id, ]}
    PRIM(B) = {id}
    PRIM(C) = {λ, ','}

    SIG(S) = {$}
    SIG(A) = {$}
    SIG(B) = {]}
    SIG(C) = {]}

    PRED(S -> [A) = {[}
    PRED(A -> B]) = {id}
    PRED(A -> ]) = {]}
    PRED(B -> idC) = {id}
    PRED(C -> λ) = {]}
    PRED(C -> ,idC) = {','}
    ```

    | VN | [ | id | ] | ',' | $ |
    | -- | -- | -- | -- | -- | -- |
    | S | S -> [A | error | error | error | error |
    | A | error | A -> B] | A -> ] | error | error |
    | B | error | B -> idC | error | error | error |
    | C | error | error | C -> λ | C -> ,idC | error |

    | Pila | Cadena | Regla / acción |
    | -- | -- | -- |
    | $S | [id,id]$ | S -> [A |
    | $A[ | [id,id]$ | Emparejar([) |
    | $A  | id,id]$ | A -> B] |
    | $]B | id,id]$ | B -> idC |
    | $]Cid | id,id]$ | Emparejar(id) |
    | $]C | ,id]$ | C -> ,idC |
    | $]Cid, | ,id]$ | Emparejar(,) |
    | $]Cid | id]$ | Emparejar(id) |
    | $]C | ]$ | C -> λ |
    | $] | ]$ | Emparejar(]) |
    | $ | $ | Accept |

1. [4 puntos] Constrúyase el parser ASAP SLR y muéstrese el parsing para la entrada [[][]]. Palabras válidas del lenguaje {[], [[]], [][], [[][]], ... }

    ```plain
    S -> SS | [S] | []

       S' -> S
    R1 S  -> SS
    R2 S  -> [S]
    R3 S  -> []
    ```

---
