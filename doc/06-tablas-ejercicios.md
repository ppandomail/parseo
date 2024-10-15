# Tabla de Tipos y de Símbolos

## Ejercicios

1. Dado el siguiente programa en un sublenguaje de Pascal:
    * Suponer que este sublenguaje tiene dos tipos predefinidos: integer y boolean
    * Suponer que es sensible a las mayúsculas
    * Mostrar el estado de la tabla de tipos y de símbolos tras procesar cada una de las líneas

        ```pascal
        1: program p;
        2: 
        3:  type vector = array[0..7] of integer;
        4:  var x : integer;   v : vector;
        5: 
        6:  function factorial (x: integer): integer
        7:    var y, z : integer;
        8:   begin
        9:    y := 1;
        10:   for z := 1 to x do y := y * z;
        11:   factorial := y;
        12:  end;
        13: 
        14:  function suma (v: vector; k: integer): vector
        15:   type vect = array[1..8] of integer;
        16:   var x: integer;   v1: vect;
        17:  begin
        18:   for x := 0 to k do v1[x+1] := v[x];
        19:   suma := v1;
        20:  end;
        21: 
        22: begin
        23:   x := 5:
        24:   x := factorial(x);
        25:   for x := 0 to 7 do v[x] := x;
        26:   v := suma(v, x);
        27: end.
        ```

    * TT

    | Linea PRG | Cod | Nombre | TipoBase | Padre | Dimensión | Mínimo | Máximo | Ámbito | Observaciones |
    | -- | -- | -- | -- | -- | -- | -- | -- | -- | -- |
    | L1  | 0 | integer | -1 | -1 | 1 | -1 | -1 | 0 | primitivo        |
    | L1  | 1 | boolean | -1 | -1 | 1 | -1 | -1 | 0 | primitivo        |
    | L3  | 2 | vector  | 0  | -1 | 8 | 0  | 7  | 0 |                  |
    | L15 | 3 | vect    | 0  | -1 | 8 | 1  | 8  | 1 |                  |
    | L20 |   |         |    |    |   |    |    |   | Se elimina Cod 3 |

    * TS

    | Linea PRG | Cod | Nombre | Categoria | Tipo | NumPar | ListaPar | Ámbito | Obervaciones |
    | -- | -- | -- | -- | -- | -- | -- | -- | -- |
    | L4  | 0 | x         | variable  | 0 | -1 | null  | 0 |                               |
    | L4  | 1 | v         | variable  | 2 | -1 | null  | 0 |                               |
    | L6  | 2 | factorial | función   | 0 | 1  | [0]   | 0 | [0] referencia a Cod de TS    |
    | L6  | 3 | x         | parámetro | 0 | -1 | null  | 1 |                               |
    | L7  | 4 | y         | variable  | 0 | -1 | null  | 1 |                               |
    | L7  | 5 | z         | variable  | 0 | -1 | null  | 1 |                               |
    | L12 |   |           |           |   |    |       |   | Se elimina Cod 3, 4 y 5       |
    | L14 | 3 | suma      | función   | 2 | 2  | [2,0] | 0 |                               |
    | L14 | 4 | v         | parámetro | 2 | -1 | null  | 1 |                               |
    | L14 | 5 | k         | parámetro | 0 | -1 | null  | 1 |                               |
    | L16 | 6 | x         | variable  | 0 | -1 | null  | 1 |                               |
    | L16 | 7 | v1        | variable  | 3 | -1 | null  | 1 |                               |
    | L20 |   |           |           |   |    |       |   | Se elimina Cod 3, 4, 5, 6 y 7 |
    | L27 |   |           |           |   |    |       |   | Se eliminan todas las lineas  |
