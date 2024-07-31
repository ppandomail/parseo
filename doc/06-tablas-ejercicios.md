# Tabla de Tipos y de Símbolos

## Ejercicios

1. Dado el siguiente programa en un sublenguaje de Pascal:
    * Suponer que este sublenguaje tiene dos tipos predefinidos: integer y boolean.
    * Suponer que es sensible a las mayúsculas.
    * Mostrar el estado de la tabla de tipos y de símbolos tras procesar cada una de las líneas.

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

![Resuelto](img/tt-ts.png)
