
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Recuperatorio noviembre 2024

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

1. Constrúyase el parser ASDP LL(1) y muéstrese el parsing para una entrada válida y otra inválida con longitudes mayores a cinco

    ```plain
    stmt      -> if-stmt | other
    if-stmt   -> if (exp) stmt else-part
    else-part -> else stmt | λ
    exp       -> 0 | 1
    ```


1. Constrúyase el parser ASAP SLR y muéstrese el parsing para una entrada válida y otra inválida

    ```plain
    stmt      -> if-stmt | other
    if-stmt   -> if (exp) stmt else-part
    else-part -> else stmt | λ
    exp       -> 0 | 1
    ```

---