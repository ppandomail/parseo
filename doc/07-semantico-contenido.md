# Análisis Semántico

* Comprobar que una serie de símbolos tiene un sentido respecto a lo que el creador del compilador desea. Es decir, comprobar que en el programa hay una cohesión y un sentido.
* Esta fase verifica que:
  * los tipos que intervienen en las expresiones sean compatibles entre sí,
  * los parámetros que se le pasan a los subprogramas sean los adecuados tanto en número como en tipo,
  * las funciones devuelvan valores adecuados en cuanto al tipo,
  * etc.
* Generalmente, en la construcción de un compilador hay una relación muy intensa entre el análisis semántico y la siguiente fase (generación de código intermedio y final).
* El análisis sintáctico guía el proceso de análisis semántico, por lo que se habla de **traducción dirigida por la sintaxis**. Esto es así porque el analizador sintáctico es el que invoca los procesos de análisis semántico y generación de código al tiempo que realiza sus propias tareas de análisis sintáctico. Es decir, hay una superposición de tareas de análisis sintáctico y generación de código.
* Es esencial que el ser humano dote de significado a su gramática para poder implementar el análisis semántico sobre ella.
* Para poder dotar de significado a los símbolos, hay que asignarles cierta información llamada **atributos**.
* Para poder realizar el análisis semántico, es necesario, que las reglas de la gramática puedan realizar **acciones semánticas** y manipular los atributos. Estas acciones y esta manipulación se realizan incluyendo código de un lenguaje de programación determinado.
* En la práctica, las fases de análisis sintáctico, semántico y generación de código intermedio se hacen todo junto y a la vez, por lo que se suele utilizar una misma herramienta para todo.

## Atributos semánticos (ATR)

* Son información personalizada (semántica) de los símbolos.
* Cada tipo de símbolo puede tener ATR diferentes.
* Esta información viaja a través del AAS adjunta a cada símbolo.

## Acciones semánticas (ACC)

* Se encargan de manipular el contenido de los ATR para verificar que existe un significado correcto en la relación de los símbolos entre sí.
* Símbolos hace referencia tanto a terminales como a no terminales.

## Atributos y acciones semánticos

* La estructura de datos formada por los símbolos y sus ATR es parecida a una estructura de registro.
* Cada tipo de símbolo es un tipo de registro.
* Cada ATR es un campo del registro.
* Cada ATR puede ser de un tipo concreto.
* Ejemplo: Reglas en Pascal

```grammar
DeclVar -> var nombreVar dospuntos TipoVar puntocoma
TipoVar -> entero | booleano
```

* Atributos: var.lexema, nombreVar.lexema, nombreVar.dirección, nombreVar.nombreTipo, dospuntos.lexema, puntocoma.lexema, entero.lexema, booleano.lexema

* Los lexemas de **entero** y **booleano** serían "integer" y "boolean" respectivamente.
* Los diferentes ATR serían de diferentes tipos, por ejemplo, los lexemas podrían ser de tipo cadena, la dirección de tipo entero y el nombre del tipo de una variable de tipo cadena.
* Hay dos tipos de terminales, los que están definidos en el análisis léxico como fijos y los que siguen un patrón definido, por lo que representan a una variedad de posibles lexemas.
* En el caso del ejemplo, hay un terminal que no tiene por qué tener un lexema fijo. Se trata del terminal **nombreVar**, que representa el nombre de la variable concreta.
* Para poder llenar la TS con la información necesaria de cada símbolo, es preciso que los diferentes ATR tengan sus valores establecidos.
* Para calcular los valores de los ATR, es preciso incluir las ACC, que no son más que trozos de código en un LP concreto, que manipulan los símbolos y sus atributos.
* Por ejemplo, en el caso anterior, se debe incluir en la TS la dirección de la variable declarada y además su tipo.
* La dirección se puede calcular a partir de la información que hay en la TS y de información global que se utilice, pero el tipo sólo se puede obtener de la propia gramática.
* Para pasar la información del tipo del no terminal TipoVar al atributo nombreTipo del terminal nombreVar, se utilizan ACC inmersas en las reglas.
* Las ACC se suelen contener entre llaves para separarlas de la gramática.

```grammar
DeclVar ::= var nombreVar dospuntos TipoVar puntocoma
            {: nombreVar.nombreTipo = TipoVar.nombreTipo; :}
TipoVar ::= entero 
            {: TipoVar.nombreTipo = entero.lexema; :}
          | booleano 
            {: TipoVar.nombreTipo = booleano.lexema; :}
```

* Se observa que se ha utilizado un ATR para el no terminal TipoVar.
* Este ATR se encarga de guardar la información necesaria para poder ser utilizada por el terminal nombreVar.
* Reglas para la utilización de ATR en ACC:
  1. Las ACC en las que intervengan ATR de la parte izquierda de una regla se pondrán al final de la regla.
  1. Sólo se podrán incluir ACC en que intervengan ATR de un símbolo detrás de la aparición del símbolo en la regla.
* En el ejemplo anterior se ha visto que se ha pasado información entre una regla y otra.
* Para entender esto, se puede asemejar un no terminal con una función de un LP.
* Esa función se encarga de leer de la entrada una serie de lexemas y luego devolver esa serie de lexemas a quien la ha llamado y, además, adjuntarle alguna información en forma de ATR.
* En el caso anterior, cuando se procesa la 1ra. regla y se llega al no terminal TipoVar, es como si se llamara al procesamiento de la 2da. regla. En la 2da. regla, se hace una serie de reconocimiento de lexemas y se devuelve cierta información, que en este caso es el nombre del tipo de la variable.
* Esa información se devuelve en el ATR del no terminal TipoVar y es utilizada después para llenar el ATR nombreTipo del terminal nombreVar.
* Por eso es necesario que se utilice un ATR sólo después de que aparezca en una regla.
* Dependiendo de la herramienta que se utilice, los no terminales y los terminales pueden tener todos sus ATR en una clase. De manera que el terminal o no terminal es un objeto que pertenece a una clase. De esta manera, al devolver información en el caso anterior, se podría devolver no sólo un ATR sino un objeto completo (así se podría pasar mucha información entre reglas).
* Por ejemplo, para el caso anterior, y utilizando Java, se implementará más detalladamente las acciones y los tipos que se utilizarán. Suponer la siguiente clase:

```java
class Variable {
  String lexema;
  String tipoVariable;
  
  Variable(String lexema) {
    this.lexema = lexema;
    this.tipoVariable = "";
  }

  void setTipoVariable(String tipoVariable) {
    this.tipoVariable = tipoVariable;
  }

  String getTipoVariable() {
    return this.tipoVariable;
  }

}
```

* Ahora, suponer que se usa una herramienta que permita utilizar código Java y reglas de la gramática. Se tendría algo parecido a:

```java
Analizador {

  Terminales {
    String var;
    String nombreVar;
    String dospuntos;
    String puntocoma;
    String entero;
    String booleano;
  }

  NoTerminales {
    String DeclVar;
    String TipVar;
  }

}
```

* Se observa que delante del nombre del terminal o no terminal se pone el tipo al que pertenece.
* En principio, todos son de tipo cadena y sólo contendrá un ATR que es el nombre del lexema.
* Ahora, se podría incluir las reglas y las ACC.

```grammar
DeclVar ::= var nombreVar dospuntos TipoVar puntocoma
            {: Variable variable = new Variable(nombreVar);
               variable.setTipoVariable(TipoVar);
               // TipoVar es una cadena “integer” o “boolean”
               // si_existe_simbolo_en_tabla_simbolos(nombreVar) -> errorSemantico;
               // si_no -> insertar_en_tabla_simbolos(variable);
               // Aquí no se devuelve nada, pero habría gramáticas en
               // que sí hiciera falta.
            :}
TipoVar ::= entero 
            {: return "integer";  // Devuelve la cadena "integer" :}
          | booleano 
            {: return "boolean";  // Devuelve la cadena "boolean" :}
```

* Se observa que se aprovechan las acciones semánticas tanto para verificar la semántica como para manipular las tablas.

## Tipos de atributos

* Cuando se realiza una **traducción dirigida por la sintaxis**, se está construyendo un AAS y dotando a cada nodo una serie de características, llamadas ATR.
* El proceso de análisis consiste en recorrer la cadena de componentes léxicos de entrada e ir construyendo su AAS y después se recorre el árbol y se van ejecutando las ACC correspondientes.
* A los AAS en los que sus nodos se guardan ATR se les suele llamar **árboles adornados** y a las gramáticas que se organizan de esta manera, **gramáticas atribuidas**.
* Los ATR utilizados pueden ser de dos tipos:
  * **Atributos sintetizados**: se calculan a partir de los valores de los ATR de sus nodos hijos en el árbol adornado.
  * **Atributos heredados**: se calculan a partir de los ATR de los nodos hermanos o padres del árbol adornado. Se suelen utilizar para pasar información entre diferentes reglas de la gramática.
* Cada nodo del árbol representa una instancia del símbolo gramatical de que se trata y, por lo tanto, tiene valores propios para sus atributos. Es decir, dos árboles que son iguales en cuanto a su estructura, no tienen porqué tener los mismos valores en los atributos de sus nodos.
* Generalmente, los terminales no tienen atributos heredados sino sólo sintetizados ya que sus valores vienen dados por etapas anteriores del análisis y no cambian.
* Sea la siguiente gramática en la que se especifica la declaración de variables en un lenguaje como por ejemplo Java:

```grammar
DeclVar -> TipoVar ListaVar puntoycoma
TipoVar -> entero | real
ListaVar -> ListaVar coma nombreVar | nombreVar
```

* El lexema del terminal **entero** es "int" y el del terminal **real** es "float".
* Para ver mejor que dos no terminales son el mismo símbolo pero con atributos diferentes, se va a diferenciarlos en el nombre, pero sin perder el significado. Es decir, se va a utilizar la siguiente regla:

```grammar
ListaVar -> ListaVar1 coma nombreVar | nombreVar
```

* Atributos: nombreVar.lexema, nombreVar.tipo, ListaVar.tipo, TipoVar.tipo, puntocoma.lexema, coma.lexema, entero.lexema, real.lexema.
* Se introducen las ACC necesarias:

```grammar
DeclVar ::= TipoVar ListaVar puntoycoma
            {: ListaVar.tipo = TipoVar.tipo; :}
TipoVar ::= entero
            {: TipoVar.tipo = entero.lexema; :}
          | real
            {: TipoVar.tipo = real.lexema; :}
ListaVar ::= ListaVar1 coma nombreVar 
             {: ListaVar1.tipo = ListaVar.tipo;  // ATR heredado, el resto son sintetizados
                insertarSimbolo(nombreVar.lexema, ListaVar.tipo); 
             :}             
           | nombreVar
             {: insertarSimbolo(nombreVar.lexema, ListaVar.tipo); :}
```

## Notación para la especificación de un traductor

* Existen dos formas de asociar acciones semánticas con reglas de producción:
  * Definición Dirigida por la sintaxis (DDS)
  * Esquema de traducción (ETDS)

## Definición Dirigida por la sintaxis (DDS)

* Consiste en asociar una ACC a una regla de producción pero sin indicar cuándo se debe ejecutar dicha acción.
* Las gramáticas con atributos son un ejemplo restringido de DDS. En ellas, los atributos son calculados a partir de otros atributos, pero sin que intervenga o se modifique nada externo a la gramática.
* En una DDS se hace lo mismo que en las gramáticas con atributos pero además se puede manipular información externa a la gramática. De todas formas, no se indica el orden de ejecución de las acciones semánticas.
* Se suele representar en una tabla con dos columnas y tantas filas como reglas de la gramática.
* Ejemplo:

| Producción | Regla Semántica |
| -- | -- |
| Asignación -> nombreVar igual Suma | if (Suma.tipo == nombreVar.tipo) nombreVar.valor = Suma.valor; else Error_semantico; |
| Suma -> nombreVar mas nombreVar1 puntoycoma | if (nombreVar.tipo == nombreVar1.tipo) { Suma.tipo = nombreVar.tipo; Suma.valor = nombreVar.valor + nombreVar1.valor; } else Error_semantico; |

* Un caso particular de gramática con atributos es una GAI (gramática con atributos por la izquierda) o gramática L-atribuida.
* En este tipo de gramáticas toda la información necesaria para su manipulación por las ACC está disponible en el momento de la ejecución de cada regla.
* Una gramática DDS es GAI si para cada regla de la forma A -> B1 B2 B3 ... Bn cada atributo heredado de Bj (con 1 ≤ j ≤ n) depende sólo de:
  1. Los atributos heredados o sintetizados de los símbolos B1 B2 B3 .. Bj-1
  2. Los atributos heredados de A

### Ejemplo DDS

* Suponer que un robot se puede instruir para moverse un paso al este, norte, oeste o sur desde su posición inicial.
* Una secuencia de estas instrucciones se genera con la gramática siguiente:

```grammar
sec -> sec instr | comienza
instr -> este | norte | oeste | sur
```

* Posible entrada: comienza oeste sur este este este norte norte

![Robot](img/robot.png)

| Producción | Acción Semántica |
| -- | -- |
| sec -> **comienza**| sec.x = 0; sec.y = 0; |
| sec -> sec1 instr | sec.x = sec1.x + instr.dx;  sec.y = sec1.y + instr.dy; |
| instr -> **este** | instr.dx = 1; instr.dy = 0; |
| instr -> **norte** | instr.dx = 0; instr.dy = 1; |
| instr -> **oeste**| instr.dx = -1;  instr.dy = 0; |
| instr -> **sur** | instr.dx = 0;  instr.dy = -1; |

## Esquema de traducción (ETDS)

* Es una gramática atribuida en la que hay intercalados en el lado derecho de las reglas de producción, fragmentos de código en un LP, que implementan ACC.
* Es un DDS en que se da un orden en la ejecución de las ACC.
* Una característica fundamental de un ETDS es que la traducción pueda realizarse de una sola pasada. Por lo tanto, un ETDS no permite herencia de los atributos desde la derecha hacia la izquierda.
* Por todo ello, un ETDS es un GAI en que se insertan ACC.
* Los ETDS se utilizan a menudo para convertir un formato de un lenguaje en el formato de otro lenguaje.
* Si se tiene una gramática y se quiere que sea un ETDS, se deberá decidir los atributos necesarios y asignarlos a los símbolos de la gramática.
* Luego, se deben insertar las ACC necesarias. En este segundo paso, se debe tener en cuenta que se deben cumplir las restricciones de un ETDS, es decir:
  1. Si todos los atributos son sintetizados, se pondrán las ACC después de los atributos implicados. Lo mejor es situarlas al final de la regla de producción. Para cada atributo sintetizado, siempre hay que calcularlo después de que hayan tomado un valor todos los demás atributos que intervienen en el cálculo.
  1. Si hay atributos heredados:
      * Un atributo heredado A.h debe calcularse antes que aparezca A.
      * Un atributo sintetizado A.s no debe usarse antes de aparezca A.
  1. Una ACC no debe referirse a un atributo sintetizado de un símbolo que esté a la derecha de la acción.

### Ejemplo ETDS

* Se quiere convertir la declaración de variables en C en declaración de variables en Pascal.
  * int x, y;  ==> var x,y: integer;
* La gramática para la declaración de variables en C sería:

```grammar
DeclVar -> TipoVar ListaVar puntocoma
TipoVar -> entero
ListaVar -> ListaVar coma nombreVar
ListaVar -> nombreVar
```

* Como se va a traducir, se debe tener un atributo en cada no terminal que se encargue de almacenar la traducción.

```grammar
DeclVar -> TipoVar ListaVar puntocoma
           {: DeclVar.trad = "var" + ListaVar.trad + ":" + TipoVar.trad + puntocoma.lexema; :}
TipoVar -> entero
           {: TipoVar.trad = "integer"; :}
ListaVar -> ListaVar coma nombreVar
           {: ListaVar.trad = ListaVar1.trad + coma.lexema + nombreVar.lexema; :}
ListaVar -> nombreVar
           {: ListaVar.trad = nombreVar.lexema :}
```

* **TipoVar.trad** es sintetizado ya que es de un no terminal a la izquierda de una regla y se calcula a partir de atributos a la derecha de la regla.
* **ListaVar.trad** es sintetizado ya que está también a la izquierda en las reglas en que se calcula.
* **nombreVar.lexema**, **coma.lexema** y **puntocoma.lexema** son también sintetizados ya que pertenecen a terminales.
* **DeclVar.trad** también es sintetizado, porque el no terminal está a la izquierda de la regla y se calcula a partir de los de la derecha de la regla.
* Por tanto, como todos los atributos son sintetizados, se sabe que se cumplen la propiedades de ETDS y además se pueden poner todas las ACC al final de las reglas.

## Comprobaciones semánticas

* Una vez que se haya dotado a una gramática de sus atributos y de las acciones semánticas oportunas.  Se puede darle la capacidad de detectar errores semánticos.
* La detección de errores semánticos depende mucho de las características del lenguaje del que se vaya a realizar el compilador y de la implementación elegida.
* Ejemplos: declaraciones de variables, tipos predefinidos, tipos estructurados, subprogramas, tipos devueltos por una función, utilización de variables antes de ser inicializada, etc.
