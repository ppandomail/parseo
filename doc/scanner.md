# Analisis Léxico (Scanner)

## Conceptos básicos

* Entrada: código fuente del LP que acepta el compilador.
* Salida: proporciona al parser los tokens.

### Token

* Es una agrupación de caracteres reconocidos por el scanner que constituyen los símbolos con los que se forman las sentencias del lenguaje.
* El scanner devuelve al parser el **nombre de ese símbolo junto con el valor del atributo**.

## El análisis léxico

* Una vez que empieza a leer el código fuente y reconoce el primer token, se lo envía al parser y este, en cuanto lo recibe, le pide el siguiente token para que siga reconociendo la entrada. Por tanto, los tokens son enviados al parser **bajo demanda**.
* Esta forma de funcionar se denomina **"dirigida por la sintaxis"** (parser driven).
* Si reconoce un identificador lo almacena en la tabla de símbolos, y posteriormente, si el parser reconoce que ese identificador lleva asociada **información de tipo** (entero, real, etc.) o de valor, también agrega esta información a la mencionada tabla.
* En cuanto al sistema de gestión de errores, se encarga de **detectar símbolos que no pertenezcan a la gramática** porque no encajen con ningún patrón. Bien porque haya caracteres inválidos, ejemplo @, o bien porque se escriban mal las palabras reservadas del lenguaje.

## Funciones del Scanner

1. Agrupar los caracteres que va leyendo uno a uno del programa fuente y formar los tokens.
1. Gestionar (abrir, leer y cerrar) el archivo que contiene el código fuente.
1. Eliminar comentarios, tabuladores, espaciones en blanco, saltos de línea.
1. Relacionar los errores con las líneas del programa.
1. Expansión de macros.
1. Inclusión de archivos.
1. Reconocimientos de las directivas de compilación.
1. Introducir identificadores en la tabla de símbolos (opcional, pudiendo realizarsee tambien por parte del parser).
1. Dependiendo de la naturaleza del código fuente, podría ser necesario realizar una pasada previa para examinarlo y posteriormente procesarlo.

## Definiciones básicas

* Token:
  * Es una agrupación de caracteres reconocidos por el scanner que constituyen los símbolos con los que se forman las sentencias del lenguaje y también se les denomina **componentes léxicos**
  * Constituyen los símbolos terminales de la gramática:
    * Palabras reservadas.
    * Identificadores.
    * Operadores y constantes.
    * Símbolos de puntuación y especiales.

* Lexema:
  * Es la secuencia de caracteres, ya agrupados, que coinciden con un determinado token, como por ejemplo el nombre de un identificador, o el valor de un número.
  * Un token puede tener uno o infinitos lexemas.

* Patrón:
  * Es la forma de describir los tipos de lexemas.
  * Esto se realiza utilizando expresiones regulares (ER).

### Ejemplos

| Token| Lexema | Patrón |
| -- | -- | -- |
| While | While | While |
| Suma | + | + |
| Identificador | a, valor, b | [a-zA-Z]+ |
| Número | 5, 3, 25, 56 | [0-9]+(\.[0-9]+)? |

## ¿Cómo funciona el scanner?

* El scanner funciona bajo demanda del parser cuando le pide el siguiente token.
* A partir del archivo que contiene el código fuente va leyendo caracteres que almacena en un buffer de entrada.

## Diseño de un scanner

* Lo primero que tenemos que hacer para construir un scanner es diseñarlo, pudiendo usarse para ello una tabla o un diagrama de transición que representa los estados por los que va pasando el AF para reconocer un token.

### Tabla de transiciones

| f| a | b |
| -- | -- | -- |
| >q0 | q1 | q1 |
| *q1 | q0 | q1 |

* En las filas: los estados (q)
* En las columnas: los símbolos de entrada que pertenecen al alfabeto.

### Diagrama de transiciones

![AF](img/af.png)

## Reconocimiento de identificadores

* Un identificador está formado por al menos una **letra mayúscula o minúscula** (ejemplo, a) seguida de forma opcional por mas letras o números (ejemplo, aa, a1, etc.).
* letra = [a-z][A-Z]
* número = [0-9]
* otro = [otro]

![AF ID](img/af-id.png)

## Formas de implementar un scanner

* **Utilizando un generador de scaners**:
  * Son herramientas que a partir de las ER generan un programa que permite reconocer los tokens o componentes léxicos.
  * Suelen estar escritos en C (FLEX) o Java (JFLEX o JLEX)
  * Ventajas: comodidad y rapidez de desarrollo.
  * Desventajas: programas ineficientes y dificultad de mantenimiento del código generado.

* **Utilizando un lenguaje de alto nivel**:
  * A partir del diagrama de transiciones y del pseudocódigo correspondiente se programa un scanner.
  * Ventajas: eficiente y compacto (lo que facilita el mantenimiento)
  * Desventajas: hay que realizarlo todo a mano.

* **Utilizando un lenguaje de bajo nivel (ensamblador)**:
  * Ventajas: más eficiente y compacto.
  * Desventajas: más difícil de desarrollar.

## Errores léxicos

* 