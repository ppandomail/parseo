# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial setiembre 2018

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

1. [2 puntos]: Enumérese y defínase las etapas del proceso de compilación.
1. [2 puntos]: Diséñese el Diagrama de Transición para reconocer el siguiente componente léxico: IDENTIFICADOR: secuencia de letras, dígitos y guiones bajos. Debe empezar con una letra y no puede haber dos guiones seguidos. Tampoco puede terminar con guión. Ejemplos: a, ZA, P3pe_int, ...
1. [2 puntos]: Diséñese la gramática en BNF para la siguiente especificación: "Expresiones aritméticas con multiplicación y suma como únicos operadores. Se reconoce el uso de paréntesis, empleados para modificar las prioridades. Los operandos son números enteros sin signo". Formalizar.
1. [2 puntos]: Compruébese si la siguiente gramática es LL(1), mostrando los conjuntos primeros, siguientes y predicciones. Si es LL(1) analizar la palabra "id;a+a;" con el ASDP construido:

    ```grammar
    B -> DL
    D -> id ; D | λ
    L -> S ; L | λ
    S -> a + a
    ```

1. [2 puntos]: Muéstrese el contenido de la pila antes de la primera reducción en la tabla de Análisis Sintáctico para reconocer la palabra hggeeddd. Considerar la siguiente gramática independiente de contexto: G = <ƩT, ƩN, S, P> dónde: ƩT = {d, e, g, h}, ƩN = {S, E, D, G}, S es el axioma, y las producciones P:

    ```grammar
    S -> ED
    E -> hEee | G 
    G -> gG | g 
    D -> dddD | ddd
    ```

---
