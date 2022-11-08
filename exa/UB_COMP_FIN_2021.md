# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Final diciembre 2021

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

* Dada la siguiente GIC:

  ```grammar
  P -> mDAend
  D -> d;X
  X -> d;X | λ
  A -> pY
  Y -> ;pY | λ
  ```

1. [2 puntos]: Constrúyase un anagrama con la palabra COMPILADORES (sin opciones)
1. [2 puntos]: Declárese en JFlex las componentes léxicas.

    * **Solución:**

    ```plain
    "module" {} ";" {} "end" {} "d" {} "p" {}
    ```

1. [3 puntos]: Diséñese ASDP LL(1), si es posible, sino transformar la GIC para que sea LL(1) y poder reconocer la cadena: module d ; d ; p ; p end
1. [3 puntos]: Diséñese SLR y también reconocer la cadena: module d ; d ; p ; p end

---
