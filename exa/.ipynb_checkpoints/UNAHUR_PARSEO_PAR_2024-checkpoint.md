
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial noviembre 2024

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

1. [2 puntos] Diséñese el diagrama y tabla de transiciones para el token ID con las siguientes características: a.- Las cadenas empiezan con al menos un dígito, y terminan en letra b.- El último dígito debe ser par y la primera letra debe ser vocal (el cero se considera par). Lexemas: 764a, 6E, 596432izlkam, 11118M, 0Abcxyz, ...

    ```plain
    ID = [0-9]*(0 | 2 | 4 | 6 | 8)(A | a | E | e | I | i | O | o | U | u)[a-zA-Z]*
    ```

1. [4 puntos] Constrúyase el parser ASDP LL(1) y muéstrese el parsing para la entrada #hur#. Sabiendo que G <{hur, #}, {S, A}, S, {(A -> hurA), (A -> #), (S -> AA)}>

    ```grammar
    S  -> AA
    A -> hurA | #

    PRIM(S) = {hur, #}
    PRIM(A) = {hur, #}
    SIG(S) = {$}
    SIG(A) = {hur, #, $}
    PRED(S -> AA) = {hur, #}
    PRED(A -> hurA) = {hur}
    PRED(A -> #) = {#}
    ```

    |    | hur | # | $ |
    | -- | -- | -- |
    | S  | S -> AA   | S -> AA | error |
    | A  | A -> hurA | A -> #  | error |

    | Pila | Cadena | Acción |
    | -- | -- | -- |
    | $S     | #hur#$  | S -> AA   |
    | $AA    | #hur#$  | A -> #    |
    | $A#    | #hur#$  | emp(#)    |
    | $A     | hur#$   | A -> hurA |
    | $Ahur  | hur#$   | emp(hur)  |
    | $A     | #$      | A -> #    |
    | $#     | #$      | emp(#)    |
    | $      | $       | OK        |  

1. [4 puntos] Constrúyase el parser ASAP SLR y muéstrese el parsing para la entrada #hur#. Sabiendo que G <{hur, #}, {S, A}, S, {(A -> hurA), (A -> #), (S -> AA)}>

    ```grammar
    S -> AA
    A -> hurA
    A -> #
    
       S' -> S
    R1 S -> AA
    R2 A -> hurA
    R3 A -> #

    SIG(S) = {$}
    SIG(A) = {hur, #, $}
    ```

    |  Q  |  hur |  #  |  $ |  S  |  A  |
    | --- | --- | --- | --- | --- | --- |
    | q0  | D(3)| D(4) | err | 1   | 2   |
    | q1  | err | err  | OK  | err | err |
    | q2  | D(3)| D(4) | err | err | 5   |
    | q3  | D(3)| D(4) | err | err | 6   |
    | q4  | R(3)| R(3) | R(3)| err | err |
    | q5  | err | err  | R(1)| err | err |
    | q6  | R(2)| R(2) | R(2)| err | err |

    | Pila | Entrada | Acción |
    | ---  | ---     | --- |
    | 0    | #hur#$  | D(4) |
    | 04   | hur#$   | R(3): A -> # |
    | 02   | hur#$   | D(3) |
    | 023  | #$      | D(4) |
    | 0234 | $       | R(3): A -> # |
    | 0236 | $       | R(2): A -> hurA |
    | 025  | $       | R(1): S -> AA |
    | 01   | $       | accept |

---
