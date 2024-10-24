# Generación de Código (GC)

* En esta fase se genera el código final
* El código final será ejecutado por la máquina o reutilizado si se trata de un traductor
* Antes de obtener el código final, se puede crear una versión de más alto nivel que sirve para dar la posibilidad de que el mismo código pueda servir para distintas máquinas
* Es decir, se suele crear un código intermedio (CI) entre el código del lenguaje que se quiere compilar y el lenguaje máquina de la máquina destino
* El CI es un código que no concreta las características de la máquina final pero que organiza el código objeto (el código del programa a compilar) para que sea el mismo o casi el mismo, independientemente del lenguaje

  | Beneficios de crear un CI son ahorro de esfuerzo para crear: |
  | -- |
  | varios compiladores para la misma máquina |
  | un compilador para varias máquinas diferentes, ya que el mismo código intermedio sirve para todas las máquinas |

* La generación de CI, es una tarea asociada a la comprobación de tipos, símbolos y al análisis semántico, por lo que se suele hacer paralelamente
* Por esto, todo el proceso de análisis semántico y la generación de CI están guiados por la sintaxis
* Si en vez de hacer un compilador lo que se quiere hacer es un traductor, no suele ser necesario generar CI y luego código final, sino que directamente se genera código final en el lenguaje de destino de la traducción

| Tipos de CI |
| -- |
| Código de tres direcciones         |
| Código de máquina virtual de pila  |

## Código de tres direcciones

* Se trata de un formato de representación en el que todas las operaciones se representan por cuádruplas (registros con cuatro campos)
* Ejemplo: **x := x + y**

  | Instrucción | Operando 1 | Operando 2 | Resultado |
  | -- | -- | -- | -- |
  | ADD  | (9000)     | (9002) | temporal |
  | MOVE | (temporal) |        | 9000     |

* Se habla de direcciones porque son las direcciones de memoria donde finalmente se guarda la información en una máquina real
* Temporal: es una dirección de memoria que sirve para guardar resultados parciales en las operaciones
* Ejemplo: **x := x + y – 2**

  | Instrucción | Operando 1 | Operando 2 | Resultado |
  | -- | -- | -- | -- |
  | ADD  | (9000)      | (9002) | temporal1 |
  | SUB  | (temporal1) | 2      | temporal2 |
  | MOVE | (temporal2) |        | 9000      |

## Código de máquina virtual de pila

* Consiste en utilizar una pila para guardar los resultados intermedios en la evaluación de expresiones y para otras funciones
* Ejemplo: **x := x + y – 2**

  | # | Acción | |
  | -- | -- | -- |
  | 1 | apilar(contenido(9000)) | se apila el contenido de la dirección 9000                                |
  | 2 | apilar(contenido(9001)) | se apila el contenido de la dirección 9001                                |
  | 3 | sumar                   | se desapila los dos valores de la pila y se suman. El resultado se apila  |
  | 4 | apilar(2)               | se apila el valor 2                                                       |
  | 5 | restar                  | se desapila los dos valores de la pila y se restan. El resultado se apila |
  | 6 | asignar(9000)           | se desapila un valor y se guarda en la dirección 9000                     |

* Aunque el funcionamiento de este tipo de generación de CI es tan adecuado como el sistema de tres direcciones, se puede comprobar con la experiencia que es más difícil pasar del CI a código final en sistemas de pila
* Por lo tanto, en los casos prácticos se va a utilizar código de tres direcciones

## CI para expresiones

* Las expresiones constan de uno o más valores (pueden ser números, variables, etc.) que se combinan mediante operadores para obtener como resultado otro valor
* Dependiendo del lenguaje a compilar, deberán ser compatibles en tipos o serán convertidos
* Ejemplo: **x := x + 1**
  * Suponer que la variable x se guarda en la dirección 9000 y la variable global temporal vale 9001.
  * La dirección de la variable x se incluye en su entrada de la tabla de símbolos al ser declarada la variable.

  | # | CI | Assembler |
  | -- |  -- | -- |
  | 1 | "CARGAR_ENTERO", "1", null, "9001"     | MOVE #1, /9001   |
  | 2 | "SUMAR_ENTERO", "9000", "9001", "9002" | ADD /9001, /9002 |
  |   |                                        | MOVE .A, /9002   |

## CI para asignaciones

* Asignar el valor de la expresión de la derecha de la asignación a la variable de la izquierda de la misma
* Habría que realizar algunas comprobaciones semánticas, dependiendo del lenguaje (por ejemplo, si los operandos son compatibles, si la variable de la parte izquierda está en la tabla de símbolos, etc.)
* En C, la GIC: Asignación -> id igual Expresión puntocoma

  | # | CI | Assembler |
  | -- |  -- | -- |
  | 1 | "COPIAR", "9005", null, "9000" | MOVE /9005, /9000 |

## CI para sentencias de entrada y salida

* Estas sentencias se tratan de manera diferente para máquinas diferentes, por lo que el CI dependería de la máquina final
* Por lo general, se trataría de comprobar que en la sentencia de:
  * **Entrada**, la variable donde se guarda la entrada existe y es del tipo adecuado
  * **Salida**, los datos de salida son los adecuados

## CI para sentencia condicional

* Esta sentencia es parecida en todos los lenguajes imperativos
* En C, la GIC:

  ```grammar
  SentenciaIf -> if parentIzq Condicion parentDer llaveIzq Bloque llaveDer SentenciaElse
  SentenciaElse -> λ | else llaveIzq Bloque llaveDer
  ```

* Si la condición es verdadera se ejecuta el primer bloque, y si es falsa puede ocurrir dos cosas: si existe la sentencia else se ejecuta su bloque, y si no existe se termina sin hacer nada

  | # | CI |
  | -- |  -- |
  | 1 | "SALTO_SI_FALSO", Condicion1.dir, "ETIQ_ELSE_1", null |
  | 2 | "SALTO", "ETIQ_FIN_1", null, null                     |
  | 3 | "ETIQ", "ETIQ_ELSE_1", null, null                     |
  | 4 | "ETIQ", "ETIQ_FIN_1", null, null                      |
  | 5 | "SALTO_SI_FALSO", Condicion2.dir, "ETIQ_ELSE_2", null |
  | 6 | "SALTO", "ETIQ_FIN_2", null, null                     |
  | 7 | "ETIQ", "ETIQ_ELSE_2", null, null                     |
  | 8 | "ETIQ", "ETIQ_FIN_2", null, null                      |

## CI para iteración tipo while

* Esta sentencia también es parecida en todos los lenguajes imperativos
* Tiene mucha similitud con la sentencia condicional salvo estas dos diferencias:
  * No hay bloque else
  * Al final del bloque de sentencias hay un salto al comienzo de la condición
* Debido a estas dos diferencias, las operaciones a realizar están situadas de distinta forma

  | # | CI |
  | -- |  -- |
  | 1 | "ETIQ", "ETIQ_INICIO", null, null                 |
  | 2 | "SALTO_SI_FALSO", Condicion.dir, "ETIQ_FIN", null |
  | 3 | "SALTO", "ETIQ_INICIO", null, null                |
  | 4 | "ETIQ", "ETIQ_FIN", null, null                    |

* Generalmente, se permite salir de la iteración con una sentencia de ruptura (por ejemplo, break). Para implementar esta sentencia, hay que provocar un salto a la etiqueta del final de la iteración
* Hay LP que permiten la declaración de variables locales dentro de los ámbitos de este tipo de instrucciones. Habrá que insertar estas variables locales en la tabla de símbolos con un nuevo ámbito. Al salir del bloque de estas instrucciones, habrá que eliminar estas variables locales de todo el ámbito
