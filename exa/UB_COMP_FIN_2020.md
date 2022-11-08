# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Final agosto 2020

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

* Dado el siguiente lenguaje de programación denominado Esencial: El único tipo de datos es entero no negativo. Los identificadores son declarados implícitamente, deben comenzar con una letra y están compuestos de letras y dígitos. El lenguaje contiene 2 enunciados de asignación:
  * **incr** nombre;   //incrementa en 1 el valor asignado al identificador nombre
  * **decr** nombre;   //decrementa en 1 (a menos que el valor por decrementar sea cero, en cuyo caso permanece con dicho valor)
* El único otro enunciado es el par de enunciados de control: **while** nombre **<> 0 do;** ... **end;**, el cual indica que es necesario repetir los enunciados que se encuentran entre los enunciados **while** y **end** mientras el valor asignado al identificador nombre no sea cero.

1. [2 puntos]: Constrúyase la tabla léxica Entrada/Buffer/Acción para la siguiente entrada: incr a; incr a; incr a; decr a;
1. [2 puntos]: Diséñese un DT para reconocer las componentes léxicas del lenguaje de programación Esencial.
1. [2 puntos]: Diséñese una gramática para el lenguaje de programación Esencial.
1. [2 puntos]: Créese los diagramas de sintaxis para el lenguaje de programación Esencial.
1. [2 puntos]: Constrúyase la tabla de tipos para la siguiente línea: incr a;

---
