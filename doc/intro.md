# Introducción

## Compilador (Compiler)

* Es un tipo especial de traductor en el que el lenguaje fuente es un lenguaje de alto nivel y el lenguaje objeto es de bajo nivel.

![Compilador](img/compilador.JPG)

![Ejemplo Compilador](img/ejemplo-compilador.png)

![Ejemplo Traductor](img/ejemplo-traductor.JPG)

* 1951: Grace Hooper, desarrolla el primer compilador, el A-0. La primera rutina “compilada” se probó con éxito en un UNIVAC.

![Univac](img/univac.png)

* 1954: John Backus, comenzó el desarrollo de un compilador de FORTRAN para IBM 704, que le llevó dos años y medio y 18 hombres para realizarlo.

* Los lenguajes compilados tienen como resultado archivos binarios que lee el sistema operativo o directamente el hardware y los ejecuta. Ejemplo C. C++, Pascal, Fortran, COBOL, Go, etc.

## Herramientas software necesarias para los compiladores

### Editores (Editors)

* Son herramientas software que se utilizan para leer y escribir los programas que posteriormente el compilador traducirá a código máquina.
* Actualmente, se integran con los compiladores (.NET) constituyendo un IDE (entorno de desarrollo interactivo), aunque se puede utilizar por separado (Eclipse).
* Para que los archivos que generan se puedan leer por cualquier editor, es necesario que tengan un formato estándar, concretamente ASCII.

### Traductores (Translators)

* Son programas que convierten un programa escrito en un lenguaje (lenguaje fuente) a otro lenguaje (lenguaje objeto)
* Ejemplo: programa de lenguaje C a Pascal

![Traductor](img/traductor.JPG)

![Traductores](img/traductores.JPG)

### Intérpretes (Interpreters)

* Son traductores de lenguajes. A diferencia de un  compilador:
  *+* El compilador analiza todo el programa fuente, crea el programa objeto y luego permite su ejecución (sólo del programa objeto obtenido).
  * El intérprete lee sentencia por sentencia del programa fuente, la convierte en el código objeto y la ejecuta.
* Es fácil comprender que, tras compilar un programa, su ejecución es mucho más rápida que la ejecución de un programa interpretado.
* Uno de los motivos de la existencia de programas interpretados es que hay algunos lenguajes de programación que permiten agregar sentencias durante la ejecución, cosa que no se podría hacer si fueran compilados.
* Algunos ejemplos son las versiones más antiguas de Basic, Python, SmallTalk, Ruby, Javascript, etc.

![Intérprete](img/interprete.png)

![Diferencias entre Intérpretes y Compiladores](img/diferencias-interpretes-compiladores.JPG)

### Ensambladores (Assemblers)

* Es un traductor para una máquina en particular, donde el lenguaje fuente es un lenguaje ensamblador y el lenguaje objeto es el código de la máquina.
* Es un traductor sencillo, en el que el lenguaje fuente tiene una estructura tan sencilla que permite la traducción de cada sentencia fuente a una única instrucción en código máquina.
* En definitiva, existe una correspondencia uno a uno entre las instrucciones ensamblador y las instrucciones máquina.
* Ejemplo: Instrucción ensamblador: LD HL, #0100 -> Código máquina generado: 65h.00h.01h
* Ventajas: Veloces y exactos
* Desventajas:
  * No es fácil de escribir.
  * Es difícil de leer y entender.
  * Depende de la máquina para la que se ha escrito, y esto implica reescribir otra vez el programa si se va a ejecutar en otra máquina.

![Ensamblador](img/ensamblador.png)

### Preprocesadores (Preprocessors)

* Son programas que funcionan de forma independiente una vez que el compilador lo llama, antes de comenzar el proceso de compilación.
* Las tareas que realizan son:
  * Sustituir las macros por las sentencias que las componen.
  * Incluir otros archivos o librerías.
  * Eliminar comentarios.
* Permitiendo modificar el programa fuente antes de la verdadera compilación.

![Preprocesadores](img/preprocesador.png)

### Enlazadores (Linkers)

* Se encargan de unir los diferentes módulos con sus respectivos códigos objeto para producir un archivo ejecutable.
* También sirven para enlazar el código objeto con las funciones que necesita de una librería de funciones u otros módulos (archivos con extensión “lib” o “dll”), además de con la memoria o los dispositivos de entrada y salida utilizando para ellos llamadas a funciones del sistema operativo.

![Enlazador](img/enlazador.png)

![Enlazador](img/enlazador2.JPG)

### Cargadores (Loaders)

* Se encargan de asignar las direcciones y el espacio de memoria necesario para la ejecución del programa.
* Esto se puede hacer porque el enlazador le proporciona direcciones de memoria relativas, denominándose este tipo de código reubicable o relocalizable, y el cargador le asigna posiciones reales o absolutas.
* Es una herramienta que trae el sistema operativo, en el caso de Linux se denomina ld (/lib/ld-linux.so).

![Cargador](img/cargador.JPG)

### Depuradores (Debbugers)

* Son herramientas que permiten encontrar errores y solucionarlos en un programa, una vez que ha sido compilado.
* Normalmente permiten seleccionar variables y visualizar los valores que van teniendo a partir de una ejecución paso a paso.

![Depurador](img/depurador.png)

### Desensambladores (Disassemblers)

* Son herramientas que traducen el lenguaje máquina a lenguaje ensamblador.
* Se utilizan para realizar ingeniería inversa.

### Decompiladores (Decompilers)

* Son herramientas que traducen de código máquina a un lenguaje de alto nivel, como por ejemplo Java, en lugar de traducir a ensamblador, que es lo que hace el desensamblador.

### Transpilador

* Son herramientas que leen código fuente escrito en un lenguaje de programación y producen el código equivalente en otro lenguaje o en una versión distinta del mismo, en javascript es común escribir código moderno y usar Babel para dar soporte a navegadores antiguos.

![Transpilador](img/transpilador.png)

## ¿Cómo funciona la compilación de Código?

![Funcionamiento](img/funcionamiento.png)

## Estructura de un compilador

* Un compilador es un programa complejo que consta de una serie de pasos, generalmente entrelazados, y que como resultado convierte un programa en un lenguaje de alto nivel en otro de bajo nivel (generalmente código máquina o lenguaje ensamblador).
* Los pasos o fases de la compilación están actualmente bien definidos y en cierta medida sistematizados, aunque no están faltos de dificultad. Esta aumenta conforme se incrementa la riqueza del lenguaje a compilar.
* Las etapas del proceso de compilación son las siguientes:
  * **Análisis**: analiza la entrada y genera estructuras intermedias.
  * **Síntesis**: sintetiza la salida a partir de dichas estructuras.

![Etapas](img/etapas.png)

### Fases del proceso de compilación

* Las fases del proceso se pueden agrupar en una fase inicial o frontend, y en una fase final o backend.
  * **Frontend**: agrupa las fases dependientes del lenguaje fuente e independientes de la máquina final.
  * **Backend**: agrupa las fases independientes del lenguaje fuente, pero dependientes del código intermedio y de la máquina de destino.
* Cada fase comienza con un código y termina con otro diferente.
  * **Frontend** comienza con el código fuente y finaliza con el código intermedio.
  * **Backend** comienza con el código intermedio y acaba con el código final.

![Estructura](img/estructura.png)

### Tablas de símbolos y de tipos

* La **tabla de símbolos** es una estructura de datos auxiliar donde se va a guardar información sobre las variables declaradas, las funciones y procedimientos, sus nombres, sus parámetros y en general cuanta información vaya a ser necesaria para realizar todo el proceso de compilación.
* La **tabla de tipos** va a guardar información tanto sobre los tipos básicos como sobre los tipos definidos en el programa a compilar.

### Gestión de errores

* Se realiza durante todas las fases del compilador.
* Es vital en el proceso ya que informa al programador dónde hay errores, de que tipo son, etc.
* Cuanta más información sea capaz de dar al programador (claridad y exactitud), mejor, evitando ralentizar los programas correctos.

![Tipos de errores en la programación](img/errores.jpg)

### Analizador léxico (scanner)

* Se comienza a leer la cadena de entrada y los caracteres que se reconocen se agrupan en componentes léxicos (tokens)

### Analizador sintáctico (parser)

* Agrupa los tokens en frases que la gramática del lenguaje reconoce construyendo un árbol sintáctico.
* Los dos analizadores suelen trabajar unidos e incluso el léxico suele ser una subrutina del sintáctico.

### Analizador semántico

* El análisis semántico identifica el comportamiento (significado), busca errores semánticos, verifica los tipos, determina operadores y operandos. Para ello utiliza atributos que se agregan al árbol, construyendo así el árbol con anotaciones (o decorado)

### Generador de código intermedio

* Con él pasamos de la fase de análisis a la de síntesis y genera código para la máquina objetivo. Por tanto, en lenguaje ensamblador.
* Cambiando esta parte de síntesis, nos permite tener compiladores para las máquinas que se necesite.

### Optimizador

* Se intenta mejorar (reducir el número de instrucciones necesarias para realizar las mismas operaciones) el código intermedio y, por tanto, el código objetivo mediante distintas técnicas.

### Generador de código

* Se genera el código binario de la máquina objetivo una vez que ha sido optimizado.
* Es un proceso más mecánico, ya que consiste en ir pasando las distintas instrucciones de CI al lenguaje ensamblador de la máquina que se vaya a utilizar.

## Resumen

![Funcionamiento Compilador](img/funcionamiento-compilador.png)

## Cómo se crea un LP

![Creación LP](img/creacion-lenguaje.png)

## Herramientas y descripciones del lenguaje

* A la hora de hacer todo el proceso de confección de un compilador se suelen utilizar herramientas, que, al no abarcar todas las fases, suelen combinarse.
* Aparte de las herramientas, se debe utilizar un lenguaje base para programar los pasos a seguir. Este lenguaje base debe ser compatible con las herramientas que se utilicen.
* Antes de nada, hay que saber para qué lenguaje se va a hacer el compilador.
* Se debe definir su:
  * **Especificación léxica** (los lexemas que utiliza).
  * **Especificación sintáctica** (la ordenación válida de esos lexemas).
  * **Especificación semántica** (descripción del significado de cada lexema y las reglas que deben cumplirse).
* Se debe saber si existe ya un código intermedio que se pueda utilizar o hay que crear uno nuevo y el código final que se utilizará.
* Otro aspecto que se debe tener en cuenta es el lenguaje base que se va a utilizar (lenguaje de implementación del propio compilador), que dependerá tanto de las preferencias del creador del compilador como de las herramientas de ayuda que se utilicen.

![LP](img/lenguajes.png)

## Diseño del compilador

* A la hora de diseñar un compilador, se utilizan los diagramas de Tombstone:

![Diagramas de Tombstone](img/tombstone1.png)
![Diagramas de Tombstone](img/tombstone2.png)

## Tipos de Lenguajes

![Tipos de Lenguajes](img/lenguajes-tipos.jpg)
