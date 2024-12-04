
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Recuperatorio diciembre 2024

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

1. Constrúyase el parser ASDP LL(1) y muéstrese el parsing para buude y beu

    ```plain
    P -> bSe
    S -> uS | dS | λ
    ```

    ```plain
    PRIM(P) = {b}
    PRIM(S) = {u, d, λ}
    SIG(P) = {$}
    SIG(S) = {e}
    PRED(P -> bSe) = {b}
    PRED(S -> uS) = {u}
    PRED(S -> dS) = {d}
    PRED(S -> λ) = {e}
    ````

    |    | b        | u       | d       | e      | $   |
    | -- | --       | --      | --      | --     | --  |
    | P  | P -> bSe | err     | err     | err    | err |
    | S  | err      | S -> uS | S -> dS | S -> λ | err |

    | Pila | Entrada | Regla o Acción |
    | -- | -- | -- |
    | $P   | buude$ | P -> bSe   |
    | $eSb | buude$ | emp(b)     |
    | $eS  | uude$  | S -> uS    |
    | $eSu | uude$  | emp(u)     |
    | $eS  | ude$   | S -> uS    |
    | $eSu | ude$   | emp(u)     |
    | $eS  | de$    | S -> dS    |
    | $eSd | de$    | emp(d)     |
    | $eS  | e$     | S -> λ     |
    | $e   | e$     | emp(e)     |
    | $    | $      | **accept** |

    | Pila | Entrada | Regla o Acción |
    | -- | -- | -- |
    | $P   | beu$ | P -> bSe   |
    | $eSb | beu$ | emp(b)     |
    | $eS  | eu$  | S -> λ     |
    | $e   | eu$  | emp(e)     |
    | $    | u$   | **reject** |

1. Constrúyase el parser ASAP SLR y muéstrese el parsing para buude y beu

    ```plain
    P -> bSe
    S -> uS | dS | λ
    ```

    ´´´plain
       X -> P
    R1 P -> bSe
    R2 S -> uS
    R3 S -> dS
    R4 S -> λ

    SIG(P) = {$}
    SIG(S) = {e}
    ´´´

    | Q  | b  | u  | d  | e  | $  | P  | S  |
    | -- | -- | -- | -- | -- | -- | -- | -- |
    | 0  | D2 |    |    |    |    | 1  |    |
    | 1  |    |    |    |    | OK |    |    |
    | 2  |    | D5 | D6 | R4 |    |    | 3  |
    | 3  |    |    |    | D4 |    |    |    |
    | 4  |    |    |    |    | R1 |    |    |
    | 5  |    | D5 | D6 | R4 |    |    | 8  |
    | 6  |    | D5 | D6 | R4 |    |    | 7  |
    | 7  |    |    |    | R3 |    |    |    |
    | 8  |    |    |    | R2 |    |    |    |

    | Pila  | Entrada | Acción |
    | --    | --      | --     |
    | 0     | buude$  | D2     |
    | 02    | uude$   | D5     |
    | 025   | ude$    | D5     |
    | 0255  | de$     | D6     |
    | 02556 | e$      | R4     |
    | 02558 | e$      | R2     |
    | 0258  | e$      | R2     |
    | 023   | e$      | D4     |
    | 0234  | $       | R1     |
    | 01    | $       | accept |

    | Pila  | Entrada | Acción |
    | --    | --      | --     |
    | 0     | beu$    | D2     |
    | 02    | eu$     | R4     |
    | 0     | eu$     | reject |

---
