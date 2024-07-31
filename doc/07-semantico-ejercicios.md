# Análisis Semántico

## Ejercicios

1. Construir un ETDS para traducir declaraciones de variables en Modula 2 a C. La gramática en Modula 2 sería:

    ```grammar
    S -> VAR id : T;
    T -> ARRAY [num..num] OF T | REAL | INTEGER | CHAR
    ```

    * Por ejemplo, se debería traducir:

    | Modula 2 | C |
    | -- | -- |
    | VAR x:INTEGER; | int x; |
    | VAR y:ARRAY[1..5] OF REAL; | float y[4]; |
    | VAR z:ARRAY[0..3] OF ARRAY[0..6] OF CHAR; | char z\[3][6]; |

    * Suponer que el lexema del terminal num es un número natural. Utilizar como lenguaje de apoyo Java.

    ```grammar
    S ::= VAR id : T ;
          {: if (T.array == null) {
                S.trad = T.tipo + id.lexema + ";";
              } else {
                S.trad = T.tipo + id.lexema + T.array + ";";
              }
          :}
    T ::= ARRAY [num .. num1] OF T1
          {:  T.tipo = T1.tipo;
              int valor = (new Integer(num.lexema)).intValue;
              int valor1 = (new Integer(num1.lexema)).intValue; 
              int indice = valor1 – valor;
              if (T1.array == null) {
                T.array = "[" + String.valueOf(indice) + "]";
              } else {
                T.array = "[" + String.valueOf(indice) + "]" + T1.array;
              }
          :}
    T ::= REAL
          {:  T.array = null;
              T.tipo = “float”;
          :}
        | INTEGER
          {:  T.array = null;
              T.tipo = “int”;
          :}
        | CHAR
          {:  T.array = null;
              T.tipo = “char”;
          :}
    ```

1. Realizar un esquema de traducción para comprobar los tipos en las expresiones de un sublenguaje del tipo Pascal. Las reglas para las expresiones son:

    ```grammar
    E -> E1 + E2;
    E -> E1 * E2;
    E -> (E1)
    E -> num | id
    ```

    * Suponer que **num** es del tipo entero (integer) e **id** es el identificador de una variable que está en la tabla de símbolos (junto a la información de su tipo).

    ```grammar
    E ::= E1 + E2 ;
          {:  if (E1.tipo == E2.tipo) {
                E.tipo = E1.tipo;
              } else {
                ErrorSemantico("Tipos incompatibles en la suma");
              }
          :}
    E ::= E1 * E2 ;
          {:  if (E1.tipo == E2.tipo) {
                E.tipo = E1.tipo;
              } else {
                ErrorSemantico("Tipos incompatibles en producto");
              }
          :}
    E ::= ( E1 )
          {:  E.tipo = E1.tipo; :}
    E ::= num
          {:  E.tipo = "integer"; :}
    E ::= id
          {:  E.tipo = id.tipo; :}
    ```

1. Utilizar la información del ejercicio anterior y comprobar que las sentencias son válidas semánticamente hablando:

    ```grammar
    S -> id := E
    S -> if E then S1
    S -> while E do S1
    S -> S1 ; S2
    ```

    ```grammar
    S ::= id := E
          {:  if (id.tipo == E.tipo) {
                S.tipo = null;
              } else {
                ErrorSemantico("Tipos incompatibles en la asignacion");
              }
          :}
    S ::= if E then S1
          {:  if (E.tipo == “boolean”) {
                S1.tipo = S.tipo;
              } else {
                ErrorSemantico("La condicion del if no es del tipo logico");
              }
          :}
    S ::= while E do S1
          {:  if (E.tipo == "boolean") {
                S1.tipo = S.tipo;
              } else {
                ErrorSemantico("La condicion del while no es del tipo logico");
              }
          :}
    S ::= S1 ; S2
          {:  if (S1.tipo == null  &&  S2.tipo == null) {
                S.tipo = null;
              } else {
                ErrorSemantico("Sentencias con un tipo");
            }
        :}
    ```
