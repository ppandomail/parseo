# UNIVERSIDAD DE BELGRANO

## Facultad de Ingeniería y Tecnología Informática

## DISEÑO DE COMPILADORES

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Final noviembre 2019

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

1. [2 puntos]: Constrúyase la tabla léxica Entrada/Buffer/Acción para la siguiente entrada: class A {}

    * **Solución:**
      | Entrada | Buffer | Acción |
      | -- | -- | -- |
      | c | c | Leer otro caracter |
      | l | cl | Leer otro caracter |
      | a | cla | Leer otro caracter |
      | s | clas | Leer otro caracter |
      | s | class | Leer otro caracter |
      | espacio | class | Enviar token y limpiar buffer |
      | A | A | Leer otro caracter |
      | espacio | espacio | Enviar token y limpiar buffer |
      | { | { | Leer otro caracter |
      | } | {} | Enviar token y limpiar buffer |
      | EOF | | Finalizar proceso de análisis |

1. [2 puntos]: Diséñese una gramática para un lenguaje de programación en el que sus expresiones aritméticas están formadas por los números enteros 0 y 1, el operador de suma y siempre termina con un “punto y coma”. Algunas expresiones son: 1; 0+0+1;

    * **Solución:**

    ```grammar
    S -> E;
    E -> T | E + T
    T -> 0 | 1
    ```

1. [2 puntos]: Constrúyase la tabla de tipos para la siguiente línea: type vector = array [0..10] of integer;

    * **Solución:**

    | Cod | Nom | TipoBase | Padre | Dim | Min | Max | Ambito |
    | -- | -- | -- | -- | -- | -- | -- | -- |
    | 0 | integer | -1 | -1 | 1 | -1 | -1 | 0 |
    | 1 | vector | 0 | -1 | 10 | 0 | 9 | 0 |

1. [2 puntos]: Enúnciese y defínase el cálculo de los tipos de atributos utilizados en las gramáticas atribuidas.

    * **Solución:**
    * Atributos sintetizados: se calculan a partir de los valores de los atributos de sus nodos hijos en el árbol adornado.
    * Atributos heredados: se calculan a partir de los atributos de los nodos hermanos o padres del árbol adornado.

1. [2 puntos]: Impleméntese el CI de la siguiente producción: Condición ::= Expresión1 == Expresión2

    * **Solución:**

    ```grammar
    Condición ::= Expresión1 == Expresión2
                  {:
                    if (Expresion1.tipo == Expresion2.tipo) {
                      Condicion.dir = temporal;
                      Condicion.tipo = BOOLEANO;
                      insertarCI(“IGUAL”, Expresion1.dir, Expresion2.dir, Condicion.dir);
                      temporal++;
                    } else 
                        ErrorSemantico(“Tipos incompatibles”);
                  :}
    ```

---
