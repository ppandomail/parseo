# Análisis Sintáctico Descendente (ASD)

## ASD con retroceso

* Consiste en recorrer el árbol de izquierda a derecha y de arriba abajo de manera que para la sentencia a analizar, se la compara con cada nodo del árbol que va recorriendo y en el caso de que los terminales a la izquierda del primer no terminal no coincidan, se termina la búsqueda por esa rama y se vuelve hacia atrás para buscar otra rama
* Problema: ineficiente

### Ejemplo ASD con retroceso

```grammar
S -> c(S,S) | g(S) | 1 | 2
```

![ASD con retroceso](img/asd.png)

| Pila | Entrada | Transición |
| -- | -- | -- |
| λ | c(g(1),2) | δ(q0, λ, λ) = (q1, #) |
| # | c(g(1),2) | δ(q1, λ, λ) = (q2, S) |
| #S | c(g(1),2) | δ(q2, λ, S) = (q2, c(S,S)) |
| #)S,S(c | c(g(1),2) | δ(q2, c, c) = (q2, λ) |
| #)S,S( | (g(1),2) | δ(q2, (, () = (q2, λ) |
| #)S,S | g(1),2) | δ(q2, λ, S) = (q2, g(S)) |
| #)S,)S(g | g(1),2) | δ(q2, g, g) = (q2, λ) |
| #)S,)S( | (1),2) | δ(q2, (, () = (q2, λ) |
| #)S,)S | 1),2) | δ(q2, λ, S) = (q2, 1) |
| #)S,)1 | 1),2) | δ(q2, 1, 1) = (q2, λ) |
| #)S,) | ),2) | δ(q2, ), )) = (q2, λ) |
| #)S, | ,2) | δ(q2, ,, ,) = (q2, λ) |
| #)S | 2) | δ(q2, λ, S) = (q2, 2) |
| #)2 | 2) | δ(q2, 2, 2) = (q2, λ) |
| #) | ) | δ(q2, ), )) = (q2, λ) |
| # | λ |  δ(q2, λ, #) = (q3, λ) |
| λ | λ |  accept |

## ASDP LL(1)

* Analizador Sintáctico Descendente Predictivo (ADSP) o Left to right Leftmost derivation (LL)

  ||||
  | -- | -- | -- |
  | **L** | left to right       | lee la entrada de izquierda a derecha   |
  | **L** | leftmost derivation | produce una derivación por la izquierda |

* Se aumenta la eficiencia, siendo capaces de saber que producción aplicar. Es decir, si se tiene un token y una serie de reglas a aplicar, se debe poder elegir una de ellas mirando si el primer token coincide con el que se tiene seleccionado (un solo símbolo de preanálisis)
* A las GIC que cumplen estos requisitos, se les llaman LL(1)
* Para implementar un ASDP, se utiliza el concepto de predicción
* Se trata de relacionar cada producción con todos los posibles terminales a que se puede acceder aplicada dicha producción
* Cuando el Parser no puede decidir qué producción aplicar se llama CONFLICTO y una de las tareas más difíciles en el diseño de un compilador es la creación de una GIC que no tenga conflictos
* Varias técnicas producen GIC equivalentes y sin conflictos
* En resumen: LL(1) es una GIC que trabaja con un Parser que lee la entrada de izquierda a derecha y realiza una derivación por izquierda. LL(1) es muy útil porque sólo necesita conocer un símbolo de preanálisis (un token) para realizar la derivación por izquierda
* Se debe conocer el conjunto de predicción para poder implementar un ASDP
* Para obtener el conjunto de predicción, se deben obtener: el conjunto de primeros y el conjunto de siguientes

### Algoritmo conjunto de primeros

```plain
Sea A -> a1...an una producción

Si (a1...an  =  λ) 
    PRIM(a1...an) = {λ}
Sino    
    Si (a1 ∈ ΣT)
        PRIM(a1...an) = {a1} 
    Si (a1 ∈ ΣN)
        Calcular PRIM(a1). PRIM(a1) es el resultado de unir todos los PRIM de todas las partes derechas de las producciones de a1 en la gramática
        Si (λ ∈ PRIM(a1) y a1 no es el último símbolo de a1...an)
            PRIM(a1...an) = (PRIM(a1) – {λ}) U PRIM(a2...an)
        Sino
            PRIM(a1...an) = PRIM(a1)
```

### Algoritmo conjunto de siguientes

```plain
Sea A -> a1...an una producción
Para obtener SIG(A), se inspecciona la GIC buscando todas las ocurrencias de A

Si (A es el axioma) 
    SIG(A) = {$}
Sino 
    SIG(A) = {}
Para (Produccion p : producciones) se pueden dar estas situaciones
    (...Ax...), donde x ∈ ΣT, entonces SIG(A) = SIG(A) U {x}
    (...AB...), donde B ∈ ΣN, entonces SIG(A) = SIG(A) U (PRIM(B) – {λ})
    (T -> ...A), donde A es el último símbolo del lado derecho en cierta producción o (T -> ...AB) en que λ ∈ PRIM(B), entonces SIG(A) = SIG(A) U SIG(T)
```

### Algoritmo conjunto de predicciones

```plain
Si (λ ∈ PRIM(a1...an))
    PRED(A -> a1...an) = (PRIM(a1...an) – {λ}) U SIG(A)
Sino
    PRED(A -> a1...an) = PRIM(a1...an)
```

### Algoritmo verificación si es gramática LL(1)

* La condición para que se pueda construir un ASDP a partir de una GIC dice si la GIC es del tipo LL(1)
* Es decir, toda GIC del tipo LL(1) permite construir un ASDP sobre ella, y viceversa
* Condición LL(1): para todas las producciones de la gramática para un mismo no terminal A:
  * A -> α1 | α2 | ... | αn
  * Se debe cumplir: Para todo i, j distintos => PRED(A -> αi) ∩ PRED(A -> αj) = {} = Ø

### Ejemplos

* **Ejemplo 1**:

  ```grammar
  S -> aST | b
  T -> cT | d
  ```

  ||
  | -- |
  | PRIM(S) = PRIM(S -> aST) U PRIM(S -> b) = {a, b} |
  | PRIM(T) = PRIM(T -> cT) U PRIM(T -> d) = {c, d} |
  ||
  | SIG(S) = {$, c, d} |
  | SIG(T) = {$, c, d} |
  ||
  | PRED(S -> aST) = {a} |
  | PRED(S -> b) = {b} |
  | PRED(T -> cT) = {c} |
  | PRED(T -> d) = {d} |
  ||
  | PRED(S -> aST) ∩ PRED(S -> b) = {a} ∩ {b} = {} |
  | PRED(T -> cT) ∩ PRED(T -> d) = {c} ∩ {d} = {} |
  | Es LL(1) |

* **Ejemplo 2**:

  ```grammar
  S -> c(S,S) | g(S) | 1 | 2
  ```

  ||
  | -- |
  | PRIM(S) = PRIM(S -> c(S,S)) U PRIM(S -> g(S)) U PRIM(S -> 1) U PRIM(S -> 2) = {c, g, 1, 2} |
  ||
  | SIG(S) = {$, ',', )} |
  ||
  | PRED(S -> c(S,S)) = {c} |
  | PRED(S -> g(S)) = {g} |
  | PRED(S -> 1) = {1} |
  | PRED(S -> 2) = {2} |
  ||
  | PRED(S -> c(S,S)) ∩ PRED(S -> g(S)) ∩ PRED(S -> 1) ∩ PRED(S -> 2) = {c} ∩ {g} ∩ {1} ∩ {2} = {} |
  | Es LL(1) |

* **Ejemplo 3**:

  ```grammar
  P -> HDB | Pf 
  H -> i | λ
  D -> v | λ
  B -> s | Dfd | λ
  ```

  ||
  | -- |
  | PRIM(H) = {i, λ} |
  | PRIM(D) = {v, λ} |
  | PRIM(B) = {s, v, fd, λ} |
  | PRIM(P) = {i, s, v, fd, λ, f} |
  ||
  | SIG(P) = {$, f} |
  | SIG(H) = {s, v, fd, $, f} |
  | SIG(D) = {s, v, fd, $, f} |
  | SIG(B) = {$, f} |

* **Ejemplo 4**:

  ```grammar
  A -> BCD 
  B -> aCb | λ
  C -> cAd | eBf | gDh | λ
  D -> i
  ```

  ||
  | -- |
  | PRIM(D) = {i} |
  | PRIM(C) = {c, e, g, λ} |
  | PRIM(B) = {a, λ} |
  | PRIM(A) = {a, c, e, g, i} |
  ||
  | SIG(A) = {$, d} |
  | SIG(B) = {c, e, g, i, f} |
  | SIG(C) = {i, b} |
  | SIG(D) = {$, d, h} |
  ||
  | PRED(A -> BCD) = {a, c, e, g, i} |
  | PRED(B -> aCb) = {a} |
  | PRED(B -> λ) = {c, e, g, i, f} |
  | PRED(C -> cAd) = {c} |
  | PRED(C -> eBf) = {e} |
  | PRED(C -> gDh) = {g} |
  | PRED(C -> λ) = {i, b} |
  | PRED(D -> i) = {i} |
  ||
  | Es LL(1) |

* **Ejemplo 5**:

  ```grammar
  S -> aSe | B
  B -> bBe | C
  C -> cCe | d
  ```

  ||
  | -- |
  | PRIM(S) = {a, b, c, d} |
  | PRIM(B) = {b, c, d} |
  | PRIM(C) = {c, d} |
  ||
  | SIG(S) = {e, $} |
  | SIG(B) = {e} |
  | SIG(C) = {e} |
  ||
  | PRED(S -> aSe) = {a} |
  | PRED(S -> B) = {b, c, d} |
  | PRED(B -> bBe) = {b} |
  | PRED(B -> C) = {c, d} |
  | PRED(C -> cCe) = {c} |
  | PRED(C -> e) = {d} |
  ||
  | Es LL(1) |

* **Ejemplo 6**:

  ```grammar
  S -> ABe | C
  A -> dB | aS | c
  B -> AS | b
  C -> b
  ```

  ||
  | -- |
  | PRIM(S) = {a, b, c, d} |
  | PRIM(A) = {a, c, d} |
  | PRIM(B) = {a, b, c, d} |
  | PRIM(C) = {b} |

* **Ejemplo 7**:

  ```grammar
  S  -> iCtP
  C -> CoD | D | (C)
  D -> DaE | E
  E -> l
  P -> P+Q | Q
  Q -> Q*R | R
  R -> d
  ```

  ||
  | -- |
  | PRIM(S) = {i} |
  | PRIM(C) = {(, l} |
  | PRIM(D) = {l} |
  | PRIM(E) = {l} |
  | PRIM(P) = {d} |
  | PRIM(Q) = {d} |
  | PRIM(R) = {d} |
  ||
  | SIG(S) = {$} |
  | SIG(C) = {t, o, )} |
  | SIG(D) = {t, a} |
  | SIG(E) = {t, a} |
  | SIG(P) = {$, +} |
  | SIG(Q) = {$, +, *} |
  | SIG(R) = {$, +, *} |

* **Ejemplo 8**:

  ```grammar
  E -> TS
  T -> + | -
  S -> num | λ
  ```

  ||
  | -- |
  | PRED(E -> TS) = {+, -} |
  | PRED(T -> +) = {+} |
  | PRED(T -> -) = {-} |
  | PRED(S -> num) = {num} |
  | PRED(S -> λ) = {$} |
  ||
  | Es LL(1) |

* **Ejemplo 9**:

  ```grammar
  S -> AB
  A -> a | λ
  B -> bCd
  C -> c | λ
  ```

  ||
  | -- |
  | PRED(S -> AB) = {a, b} |
  | PRED(A -> a) = {a} |
  | PRED(A -> λ) = {b} |
  | PRED(B -> bCd) = {b} |
  | PRED(C -> c) = {c} |
  | PRED(C -> λ) = {d} |
  ||
  | Es LL(1) |

* **Ejemplo 10**:

  ```grammar
  S -> zMNz
  M -> aMa | z
  N -> bNb | z
  ```

  ||
  | -- |
  | PRED(S -> zMNz) = {z} |
  | PRED(M -> aMa) = {a} |
  | PRED(M -> z) = {z} |
  | PRED(N -> bNb) = {b} |
  | PRED(N -> z) = {z} |
  ||
  | Es LL(1) |

### Conversión a GIC LL(1)

* Con seguridad se sabe que una GIC no es LL(1) si:
  * Es una GIC ambigua -> replantear la GIC
  * Tiene factores comunes por la izquierda

    ```grammar
    Cambiar las reglas del tipo: A -> aβ1 | aβ2 | ... | aβn
    Por estas reglas:
        A -> aA’
        A’-> β1 | β2 | ... | βn
    ```

  * Es recursiva por la izquierda

    ```grammar
    Cambiar las reglas del tipo: A ->  Aa | β
    Por estas reglas:
        A -> βA’
        A’-> aA’ | λ
    ```

* El caso contrario no tiene porqué ser cierto. Es decir:

  | Si una GIC no ... | |
  | -- | -- |
  | es **ambigua**                              | no permite decir que es LL(1)       |
  | tiene **factores comunes por la izquierda** | no indica con certeza que sea LL(1) |
  | es **recursiva por la izquierda**           | no asegura que sea LL(1)            |

### Implementación de ASDP’s

* Si se conoce el siguiente símbolo a analizar, se puede construir un ASDP mediante diagramas de sintaxis y un lenguaje que admita recursión
* Hay otra manera de construir este tipo de analizadores: **tabla de análisis**
* La tabla que se construye representa el Parser LL (Autómata Finito a Pila de la gramática LL), por lo que es capaz de analizar cualquier palabra y ver si es reconocida por el Parser LL correspondiente

* **Ejemplo 1**:

  | Predicciones |
  | -- |
  | PRED(S -> aST) = {a} |
  | PRED(S -> b) = {b} |
  | PRED(T -> cT) = {c} |
  | PRED(T -> d) = {d} |

  | | a | b | c | d | $ |
  | -- | -- | -- | -- | -- | -- |
  | S | S -> aST | S -> b | error | error | error |
  | T | error | error | T -> cT | T -> d | error |

  | Pila | Entrada | Regla o Acción |
  | -- | -- | -- |
  | $S | aabcdd$ | S -> aST |
  | $TSa | aabcdd$ | Emparejar(a) |
  | $TS | abcdd$ | S -> aST |
  | $TTSa | abcdd$ | Emparejar(a) |
  | $TTS | bcdd$ | S -> b |
  | $TTb | bcdd$ | Emparejar(b) |
  | $TT | cdd$ | T -> cT |
  | $TTc | cdd$ | Emparejar(c) |
  | $TT | dd$ | T -> d |
  | $Td | dd$ | Emparejar(d) |
  | $T | d$ | T -> d |
  | $d | d$ | Emparejar(d) |
  | $ | $ | accept |

* **Ejemplo 2**:

  | Predicciones |
  | -- |
  | PRED(S -> c(S,S)) = {c} |
  | PRED(S -> g(S)) = {g} |
  | PRED(S -> 1) = {1} |
  | PRED(S -> 2) = {2} |

  | | c | ( | , | ) | g | 1 | 2 | $ |
  | -- | -- | -- | -- | -- | -- | -- | -- | -- |
  | S | S -> c(S,S) | error | error | error | S -> g(S) | S -> 1 | S -> 2 | error |

  | Pila | Entrada | Regla o Acción |
  | -- | -- | -- |
  | $S | c(g(1),2)$ | S -> c(S,S) |
  | $)S,S(c | c(g(1),2)$ | Emparejar(c) |
  | $)S,S( | (g(1),2)$ | Emparejar('(') |
  | $)S,S | g(1),2)$ | S -> g(S) |
  | $)S,)S(g | g(1),2)$ | Emparejar(g) |
  | $)S,)S( | (1),2)$ | Emparejar('(') |
  | $)S,)S | 1),2)$ | S -> 1 |
  | $)S,)1 | 1),2)$ | Emparejar(1) |
  | $)S,) | ),2)$ | Emparejar(')') |
  | $)S, | ,2)$ | Emparejar(,) |
  | $)S | 2)$ | S -> 2 |
  | $)2 | 2)$ | Emparejar(2) |
  | $) | )$ | Emparejar(')') |
  | $ | $ | accept |
