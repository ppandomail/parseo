# Análisis Léxico (Scanner)

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
1. Pasar los tokens válidos al parser.
1. Gestionar (abrir, leer y cerrar) el archivo que contiene el código fuente.
1. Eliminar comentarios, tabuladores, espacios en blanco, saltos de línea.
1. Relacionar los errores con las líneas del programa.
1. Expansión de macros.
1. Inclusión de archivos.
1. Reconocimientos de las directivas de compilación.
1. Introducir identificadores en la tabla de símbolos (opcional, pudiendo realizarse también por parte del parser).
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
  * Ejemplo: para los identificadores de variables en Java serían:
    * Letra ::= [a-zA-Z]
    * Dígito ::= [0-9]
    * Subrayado ::= [_]
    * Identificador ::= (Subrayado | Letra) (Subrayado | Letra | Dígito)*

### Ejemplos

| Token| Lexema | Patrón |
| -- | -- | -- |
| While | While | While |
| Const | const | const |
| Suma | + | + |
| Relación | <, <=, != | < \| <= \| != |
| Identificador | a, valor, b | [a-zA-Z]+ |
| Número | 5, 3, 25, 56 | [0-9]+(\.[0-9]+)? |

## ¿Cómo funciona el scanner?

* El scanner funciona bajo demanda del parser cuando le pide el siguiente token.
* A partir del archivo que contiene el código fuente va leyendo caracteres que almacena en un buffer de entrada.
* Cuando encuentra un carácter que no le sirve para construir un token válido, se para y envía los caracteres acumulados al parser y espera una nueva petición de lectura de éste.
* Cuando recibe una nueva petición del parser limpia el buffer y vuelve a leer el carácter donde paró la vez anterior (ya que eso no pertenecía al token que envió).
* Ejemplo:

  ```c
    int x; 
    main() {
    }
  ```

  | ENTRADA| BUFFER | ACCIÓN |
  | -- | -- | -- |
  | i | i | Leer otro caracter |
  | n | in | Leer otro caracter |
  | t | int | Leer otro caracter |
  | blanco | int | Enviar token y limpiar buffer |
  | x | x | Leer otro caracter |
  | ; | x | Enviar token y limpiar buffer |
  | ... | ... | ... |
  | Fin archivo | } | Enviar token y finalizar proceso de análisis |

## Diseño de un scanner

* Lo primero que tenemos que hacer para construir un scanner es diseñarlo, pudiendo usarse para ello una tabla o un diagrama de transición que representa los estados por los que va pasando el AF para reconocer un token.

### Tabla de transiciones

| f| a | b |
| -- | -- | -- |
| >q0 | q1 | - |
| *q1 | q0 | q1 |

* En las filas: los estados (q)
* En las columnas: los símbolos de entrada que pertenecen al alfabeto.

### Diagrama de transiciones (DT)

* Es una máquina de estados.
* Es parecida a un autómata finito determinista (AFD) pero con las siguientes diferencias:
  * El AFD sólo dice si la secuencia de caracteres pertenece al lenguaje o no y el DT debe leer la secuencia hasta completar un token y luego retornar ese token y dejar la entrada preparada para leer el siguiente token.
  * En un DT cada secuencia no determinada es un error. En los AFD podía haber estados especiales de error o estados que englobaban secuencias no admitidas en otros estados.
  * Los estados de aceptación de los DT deben ser finales.
  * En un DT, cuando se lea un carácter que no pertenezca a ninguna secuencia especificada, se debe ir a un estado especial final y volver el cursor de lectura de caracteres al carácter siguiente a la secuencia correcta leída.

![AF](img/af.png)

## Ejemplo: Reconocimiento de identificadores

* Un identificador está formado por al menos una **letra mayúscula o minúscula** (ejemplo, a) seguida de forma opcional por mas letras o números (ejemplo, aa, a1, etc.).
* letra ::= [a-zA-Z]
* número ::= [0-9]
* otro ::= [otro]

![AF ID](img/af-id.png)

## Ejemplo: Reconocimiento de números enteros sin signo, suma, incremento y producto

* Son válidos los siguientes lexemas: "01", "32", "+", "++", "*"
* Patrones:
  * ENTERO ::= ("0" | "1" | "2" | … | "9")+
  * SUMA ::= "+"
  * PRODUCTO ::= "*"
  * INCREMENTO ::= "++"
* Un asterisco en un estado de aceptación indica que el puntero que señala la lectura del siguiente símbolo (para reconocer el siguiente token) debe retroceder una unidad (si hubiera más asteriscos, retrocedería tantas unidades como asteriscos)
* Tras la llegada a un estado de aceptación, se le pasaría el token al parser y se esperaría una nueva petición de éste para comenzar otra vez en el estado 0 del autómata.

![AF Aritmética](img/af-aritmetica.png)

| Q | Dígito | + | \* | Otro | Token | Retroceso |
| -- | -- | -- | -- | -- | -- | -- |
| >q0 | q5 | q2 | q1 | Error | -  | - |
| *q1 | - | - | - | - | PRODUCTO | 0 |
| q2 | q3 | q4 | q3 | q3 | - | - |
| *q3 | - | - | - | - | SUMA | 1 |
| *q4 | - | - | - | - | INCREMENTO | 0 |
| q5 | q5 | q6 | q6 | q6 | - | - |
| *q6 | - | - | - | - | ENTERO | 1 |

* Una vez que se tiene la tabla, la implementación obtiene cada estado buscando el estado que hay en la fila correspondiente al estado actual y la entrada actual.
* Este proceso continúa hasta llegar a un estado de aceptación o uno de error.
* Si es de aceptación, devolverá el token junto con los caracteres acumulados hasta el momento.
* Si hay un retroceso en la fila, se retrocederá el cursor de selección de entrada tantas unidades como se indique en el retroceso.
* Se borra el buffer y se comienza en el estado 0.
* Si se ha llegado a un estado de error, se lanzará el error correspondiente.

* Suponer que se tiene  esta entrada: 25 + 5
* El autómata efectuará estos pasos: Estado=q0, Entrada=2, Estado=q5, Entrada=5, Estado=q5, Entrada=+, Estado=q6, Token=ENTERO, Lexema=25, Retroceso=1, Estado=q0, Entrada=+, Estado=q2, Entrada=5, Estado=q3, Token=SUMA, Lexema=+, Retroceso=1, Estado=q0, Entrada=5, Estado=q5 …

## Formas de implementar un scanner

* **Utilizando un generador de scaners**:
  * Son herramientas que a partir de las ER generan un programa que permite reconocer los tokens o componentes léxicos.
  * Suelen estar escritos en C (LEX) o Java (JFLEX)
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

* Son detectados, cuando durante el proceso de reconocimiento de caracteres, los símbolos que tenemos en la entrada no concuerdan con ningún patrón. Hay que tener en cuenta que hay pocos errores detectables por el analizador léxico, entre ellos están:
  * **Nombres incorrectos de los identificadores**: se debe a que se utilizan caracteres inválidos para ese patrón, como por ejemplo un paréntesis, o se empieza por un número.
  * **Números incorrectos**: debido a que está escrito con caracteres inválidos (puntos en lugar de comas) o no está escrito correctamente.
  * **Palabras reservadas escritas incorrectamente**: se producen errores de ortografía. El problema aquí es cómo distingues entre un identificador y una variable reservada.
  * **Caracteres que no pertenecen al alfabeto del lenguaje**: Ejemplos: @, €, ¿, ?, ñ, etc.

## Ejercicios

1. Diseñar un DT para reconocer los siguientes componentes léxicos:
    * LETRAS: cualquier secuencia de una o más letras.
    * ENTERO: cualquier secuencia de uno o más números (si tiene más de un número no deberá comenzar por 0).
    * ASIGNAR: la secuencia =.
    * SUMAR: la secuencia +.
    * RESTAR: la secuencia -.
    * IMPRIMIR: la palabra reservada print.

| Q | + | - | = | 0 | 1-9 | p | r | i | n | t | Otras | Token | Retroceso |
| -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- |
| 0 | 1 | 2 | 3 | error | 4 | 8 | 6 | 6 | 6 | 6 | 6 | - | - |
| 1 | - | - | - | - | - | - | - | - |- | - | - | SUMAR | 0 |
| 2 | - | - | - | - | - | - | - | - |- | - | - | RESTAR | 0 |
| 3 | - | - | - | - | - | - | - | - |- | - | - | ASIGNAR | 0 |
| 4 | 5 | 5 | 5 | 4 | 4 | 5 | 5 | 5 | 5 | 5 | 5 | - | - |
| 5 | - | - | - | - | - | - | - | - | - | - | - | ENTERO | 1 |
| 6 | 7 | 7 | 7 | 7 | 7 | 6 | 6 | 6 | 6 | 6 | 6 | - | - |
| 7 | - | - | - | - | - | - | - | - | - | - | - | LETRAS | 1 |
| 8 | 13 | 13 | 13 | 13 | 13 | 6 | 9 | 6 | 6 | 6 | 6 | - | - |
| 9 | 13 | 13 | 13 | 13 | 13 | 6 | 6 | 10 | 6 | 6 | 6 | - | - |
| 10 | 13 | 13 | 13 | 13 | 13 | 6 | 6 | 6 | 11 | 6 | 6 | - | - |
| 11 | 13 | 13 | 13 | 13 | 13 | 6 | 6 | 6 | 6 | 12 | 6 | - | - |
| 12 | - | - | - | - | - | - | - | - | - | - | - | IMPRIMIR | 0 |
| 13 | - | - | - | - | - | - | - | - | - | - | - | LETRAS | 1 |

1. Para el lenguaje generado por la expresión regular  (a | b)* abb
    1. Crear el DT.
    1. Generar la tabla de transiciones.
    1. Escribir un programa en Java para implementar la tabla.

    | Q | a | b | Token | Retroceso |
    | -- | -- | -- | -- | -- |
    | 0 | 1 | 4 | - | - |
    | 1 | 1 | 2 | - | - |
    | 2 | 1 | 3 | - | - |
    | 3 | - | - | accept | 0|
    | 4 | 1 | 4 | - | - |

    ```java
    class Lexer {

      static final int ERROR = -1;
      static final int ACEPTAR = 0;
      String palabra;
      int cursor = 0;
      
      Lexer(String palabra) {
        this.palabra = palabra;
      }

      char getCaracter() {
        if (cursor < palabra.length()) {
          return palabra.charAt(cursor++);
        }
        return '*';
      }

      int q0() {
        switch (this.getCaracter()) {
        case 'a‘:  return q1();
        case 'b‘:  return q4();
        default:   return ERROR;
        }
      }

      int q1() {
        switch (this.getCaracter()) {
        case 'a‘:  return q1();
        case 'b‘:  return q2();
        default:   return ERROR;
        }
      }

      int q2() {
        switch (this.getCaracter()) {
        case 'a‘:  return q1();
        case 'b‘:  return q3();
        default:   return ERROR;
        }
      }

      int q3() {
        return ACEPTAR;
      }

      int q4() {
        switch (this.getCaracter()) {
        case 'a‘:  return q1();
        case 'b‘:  return q4();
        default:   return ERROR;
        }
      }  

      public static void main(String[] args) {
        System.out.println(new Lexer("abb").q0());
        System.out.println(new Lexer("aabb").q0());
        System.out.println(new Lexer("babb").q0());
        System.out.println(new Lexer("ababb").q0());
        System.out.println(new Lexer("").q0());
        System.out.println(new Lexer("bb").q0());
      }

    }
    ```
