# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial setiembre 2021

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

1. [2 puntos]: Justifíquese: "En el lenguaje Python, def es un lexema e Identificador es un token". Luego, complétese la tabla Token, Lexema, Patron con 4 filas.
1. [2 puntos]: Reconózcase el identificador de la expresión a2 = b + 5, mediante el siguiente DT

    | Q | letra | numero | otro |
    | -- | -- | -- | -- |
    | >q0 | q1 | - | - |
    | q1 | q1 | q1 | q2 |
    | *q2 | - | - | - |

1. [3 puntos]: Diséñese y formalice la gramática en BNF para la siguiente especificación: concreto. "Una declaración de variables está formada por el tipo de la variable y las variables, que pueden ser una variable o varias (una lista de identificadores), es decir uno o varios identificadores. Los tipos de las variables pueden ser enteros (int), reales (real) o de tipo caracter (char)". Luego, derívese por izquierda la cadena int a, b;
1. [2 puntos]: Compruébese si la siguiente gramática es LL(1), mostrando los conjuntos primeros, siguientes y predicciones.

    ```grammar
    E -> TA
    A -> +TA | λ
    T -> VB
    B -> *VB | λ
    V -> id | (E)
    ```

1. [2 puntos]: Muéstrese los movimientos realizados por el analizador sintáctico predictivo con la entrada abba

---
