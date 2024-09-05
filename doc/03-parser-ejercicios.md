# Parser

## Ejercicios

1. Diseñar una GIC no ambigua para el lenguaje de las expresiones que se pueden construir con true y false y los operadores booleanos or, and, not y los paréntesis

    ```grammar
    E -> T or E | T
    T -> F and T | F
    F -> not F | true | false | (E)
    ```

1. Construir una GIC no ambigua que reconozca todas las declaraciones posibles de variables de los siguientes tipos: int, String, boolean y double en Java. Por ejemplo:

    ```java
    int x, y;
    String cadena;
    double a;
    boolean b;
    ```

    ```grammar
    S -> SE | E
    E -> T F ptocoma
    T -> int | String | double | boolean
    F -> F id coma | id
    ```

1. Crear los diagramas de sintaxis y el programa para esta GIC:

    ```grammar
    Programa -> Declaraciones Sentencias
    Declaraciones -> (Decl “;’)+
    Decl -> Entero Identificador 
    Sentencias -> (Asignación “;”)+
    Asignación -> ParteIzquierda “=“ ParteDerecha
    ParteIzquierda -> Identificador
    ParteDerecha -> Expresión
    Expresión -> (Expresión “+” Expresión) | (Expresión “-” Expresión) 
    Expresión -> (Identificador | Numero)
    ```

    ```plain
    --->[D]--->[S]--->
       _____________  
      |             |    
    --->[D']--->(;)--->

    etc.
    ```

    ```java
    class Parser {

      Token token;
      int cursor = 0;
      Token[] programa;

      enum Token {
        PUNTOCOMA, Entero, Identificador, IGUAL, Numero, MENOS, MAS
      }

      Parser(Token[] programa) {
        this.programa = programa;
      }

      void programa() {
        declaraciones();
        sentencias();
      }

      void declaraciones() {
        do {
          decl();
          while (token != Token.PUNTOCOMA) {
            error("';'");
            token = getToken();
          }
          token = getToken();
        } while (token == Token.Entero);
      }

      void decl() {
        token = getToken();
        if (token == Token.Entero) {
          token = getToken();
          if (token == Token.Identificador)
            token = getToken();
          else
            error("'Identificador'");
        } else
            error("'Entero'");
      }

      void sentencias() {
        do {
          asignacion();
          while (token != Token.PUNTOCOMA) {
            error("';'");
            token = getToken();
          }
          token = getToken();
        } while (token == Token.Identificador);
      }
      
      void asignacion() {
      if (token == Token.Identificador) {
      token = getToken();
      if (token == Token.IGUAL) {
        token = getToken();
        expresion();
      } else
        error("'='");
    } else
        error("'Identificador'");
    }

    void expresion() {
      if (token == Token.Identificador || token == Token.Numero) {
        token = getToken();
        if (token == Token.MAS || token == Token.MENOS) {
          token = getToken();
          expresion();
        }
      } else
        error("'Identificador' o 'Numero'");
    }

    Token getToken() {
      if (cursor < programa.length) {
        Token tkn = programa[cursor++];
        System.out.println(tkn);
        return tkn;
      }
      return null;
    }

    void error(String mensaje) {
      System.err.println("Se esperaba: " + mensaje + "\n");
      System.exit(0);
    }

    public static void main(String[] args) {
      new Parser(new Token[] {
        Token.Entero, Token.Identificador, Token.PUNTOCOMA, Token.Identificador, Token.IGUAL,
        Token.Identificador, Token.PUNTOCOMA
      }).programa();
    }

  }

1. Identificar no terminales y terminales en la siguiente GIC

    ```grammar
    <Programa> -> <Instrucciones> 
    <Instrucciones> -> <Instrucción> | <Instrucción> <Instrucciones>
    <Instrucción> -> <Operando> <Operador> <Operando>;
    <Operando> -> true | false
    <Operador> -> AND | OR | NOT
    ```

    ```plain
    TERMINALES = {true, false, AND, OR, NOT}
    NO TERMINALES = {<Programa>, <Instrucciones>, <Instrucción>, <Operando>, <Operador>}
    ```

1. Dibujar el AAS correspondiente al reconocimiento de la siguiente secuencia:

  ```java
  true AND false;     
  false OR true;
  ```

1. Implementar un parser para sumar números 0 y 1 terminados con punto y coma. Ejemplo:
    * 0+1+1+1;      //3
    * 1+1;          //2
    * 1+0+0+0+0+1;  //2

1. Implementar un parser para operar con cadenas. Ejemplo:
    * [P]                             // Inicio del programa
    * [m] El cielo es celeste [/m]    // el cielo es celeste  
    * [M] El cielo es celeste [/M]    // EL CIELO ES CELESTE
    * [C] El cielo es celeste  [/C]   // 19
    * [W] El cielo es celeste [/W]    // 4
    * [Q] Hola , Mundo[/Q]            // NO
    * [/P]                            // Fin del programa

1. Implementar un parser que reconozca los siguientes casos:
    * En publicaciones científicas las lista de autores contiene
      * Un nombre, iniciales (opcional), al menos un apellido por cada autor
      * Cada autor separado por coma con el siguiente autor excepto el último autor que es precedido por el conector "and"
    * Posibles direcciones de e-mail, implemente test cases del parser

1. Implementar un parser para el **Lenguaje Micro**:
    * El único tipo de dato es entero
    * Todos los identificadores son declarados implícitamente y con una longitud máxima de 5 caracteres
    * Los identificadores deben comenzar con una letra y están compuestos de letras y dígitos
    * Las constantes son secuencias de dígitos (números enteros)
    * Hay 2 tipos de sentencias:
      * Asignación  ID:=Expresión;
        * Expresión es infija y se construye con identificadores, constantes y los operadores + y -; los paréntesis están permitidos
      * Entrada/Salida
        * leer(lista de IDs);
        * escribir(lista de Expresiones);
    * Cada sentencia termina con un “punto y coma” (;). El cuerpo de un programa está delimitado por inicio y fin
    * inicio, fin, leer y escribir son palabras reservadas y deben escribirse en minúsculas
    * Ejemplo de programa fuente:

        ```plain
        inicio
        leer (a,b);
        cc := a+(b-2);
        escribir (cc, a+4);
        fin
        ```

    * GIC:

        ```grammar
        <programa> -> inicio <listaSentencias> fin
        <listaSentencias> -> <sentencia> {<sentencia>}
        <sentencia> -> <identificador> := <expresión>; |  
                      leer ( <listaIdentificadores> ); | 
                      escribir ( <listaExpresiones> );
        <listaIdentificadores> -> <identificador> { , <identificador> }
        <listaExpresiones> -> <expresión> { , <expresión> } 
        <expresión> -> <primaria> {<operadorAditivo> <primaria>}
        <primaria> -> <identificador> | <constante> | ( <expresión> )
        ```

1. Implementar un parser para el **Lenguaje Esencial**:
    * Único tipo de datos que se requiere es el entero no negativo
    * No requiere enunciados de declaración de tipo, sino que los identificadores, que consisten en letras y dígitos (comenzando por una letra) se declaran automáticamente como de tipo entero no negativo con solo aparecer por primera vez en un programa
    * Nuestro lenguaje contiene los dos enunciados de asignación siguiente:
      * incr nombre;
      * decr nombre;
    * El primero incrementa en uno el valor asignado al identificador nombre, mientras el segundo lo decrementa en uno (a menos que el valor por decrementar sea cero, en cuyo caso permanece con dicho valor)
    * El único otro enunciado de nuestro lenguaje es el par de enunciados de control:
      * while nombre <> 0 do; ... end;
    * El cual indica que es necesario repetir los enunciados que se encuentran entre los enunciados while y end mientras el valor asignado al identificador nombre sea cero
    * Ejemplo de programa fuente:

      ```plain
      while aux <> 0 do;
        decr aux;
      end;
      while nombre1 <> 0 do;
        decr nombre1;
      end;
      while nombre2 <> 0 do;
        incr aux;
        decr nombre2;
      end;
      while aux <> 0 do;
        incr nombre1;
        incr nombre2;
        decr aux;
      end;
      ```
