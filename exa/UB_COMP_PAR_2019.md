# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial setiembre 2019

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

1. [2 puntos]: Diagrámese la estructura de un compilador.
1. [2 puntos]: Diséñese el Diagrama de Transición para reconocer el siguiente componente léxico: FECHA: secuencia de dos números concatenada con símbolo "/" concatenada con secuencia de dos números concatenada con símbolo "/" concatenada con secuencia de cuatro números.
1. [2 puntos]: Diséñese la gramática en BNF para la siguiente especificación: "Un identificador es una secuencia de letras mayúsculas A, B y C, posiblemente con la inclusión de guiones bajos (no consecutivos) en medio". Formalizar.
1. [2 puntos]: Compruébese si la siguiente gramática es LL(1), mostrando los conjuntos primeros, siguientes y predicciones. Si es LL(1) analizar la palabra "[num id num cte id]" con el ASDP construido: G = <{S, L},  {id, num, cte, [, ]}, S, P}, donde S es el axioma, las producciones P:

    ```grammar
    S -> [L]
    L -> numL | cteL | idL | λ
    ```

1. [2 puntos]: Muéstrese el contenido de la pila antes de la primera reducción en la tabla de Análisis Sintáctico para reconocer la palabra girar\<coser\<&;∆\>\>. Considerar la siguiente gramática independiente de contexto: G = <ƩT, ƩN, S, P>, donde ƩT = {coser, girar, <, >, ; , &, ∆}, ƩN = {INICIO}, INICIO es el axioma, y las producciones P = { INICIO -> & | ∆ | girar\<INICIO\> | coser\<INICIO;INICIO\>}


---
