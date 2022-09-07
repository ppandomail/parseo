# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Final diciembre 2018

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

1. [2.5 puntos]: Diséñese una tabla con dos columnas: lexema y token. Complétese la tabla con todos los lexemas hallados en el Análisis Léxico y las categorías a las que pertenecen para el siguiente fragmento de programa en ANSI C:

    ```c
    ...
    int WHILE;
    while (WHILE > (32)) -2.46;
    ...
    ```

1. [2.5 puntos]: Diséñese una gramática y diagrama CONWAY para: "Un objeto es un conjunto desordenado de pares nombre/valor. Un objeto comienza con { (llave abre) y termina con } (llave cierra). Cada nombre es seguido por : (dos puntos) y el par nombre/valor son separados por , (coma)".

1. [2.5 puntos]: Compruébese si es LL(1) y si lo es, constrúyase su tabla de análisis y verifíquese si la entrada siguiente es analizada correctamente: (∞ # ∞)

    ```grammar
    I -> F | (I # F)
    F -> ∞
    ```

1. [2.5 puntos]: Constrúyase el autómata, la tabla SLR y analícese la cadena:  (∞ # ∞)

    ```grammar
    I -> F
    I -> (I # F)
    F -> ∞ 
    ```

---
