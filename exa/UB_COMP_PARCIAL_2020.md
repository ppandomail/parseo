# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Parcial setiembre 2020

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

1. [1 punto]: Diagrámese la conexión analizador léxico-sintáctico.
1. [1 punto]: Ejemplifíquese la tabla Entrada-Buffer-Acción mediante un archivo de entrada con el siguiente contenido: c := c + 1;
1. [2 puntos]: Diséñese el Diagrama de Transición para reconocer operadores relacionales de cualquier lenguaje de programación.
1. [2 puntos]: Diséñese y formalice la gramática en BNF para la siguiente especificación: Sentencia de formato de impresión: printf ( [ #format, #format,..., #format ] -> cte, cte,..., cte ); Donde: 1) #format y cte son terminales. 2) printf es terminal. 3) No existe límite en la cantidad de #format y de cte. 4) La lista de formatos (#format ) debe tener igual cantidad de elementos que la lista de constantes (cte). Debe haber al menos un formato y una constante. 5) Todos los símbolos unarios son parte del lenguaje.
1. [2 puntos]: Compruébese si la siguiente gramática es LL(1), mostrando los conjuntos primeros, siguientes y predicciones. Si es LL(1) analizar la palabra “ibtaea” con el ASDP construido: G = <{P, P’, E},  {a, b, e, i. t}, P, R}, donde P es el axioma, las producciones R: P -> iEtPP’ | a     P’ -> eP | λ      E -> b
1. [2 puntos]: Muéstrese los movimientos realizados por el analizador sintáctico predictivo con la entrada id + id

    | | id | + | * | ( | ) | $ |
    | -- | -- | -- | -- | -- | -- | -- |
    | E | E -> TE'| error | error | E -> TE' | error | error |
    | E' | error | E' -> +TE' | error | error | E' -> λ | E' -> λ |
    | T | T -> FT' | error | error | T -> FT' | error | error |
    | T' | error | T' -> λ | T' -> *FT' | error | T' -> λ | T' -> λ |
    | F | F -> id | error | error | F -> (E) | error | error |

---
