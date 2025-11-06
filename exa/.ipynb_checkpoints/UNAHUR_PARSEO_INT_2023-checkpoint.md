
# UNIVERSIDAD NACIONAL DE HURLINGHAM

## Inst. de Tecnología e Ingeniería

## PARSEO Y GENERACIÓN DE CÓDIGO

### Profesor: Mag. Ing. Pablo Pandolfo

---

### Integrador diciembre 2023

* ALUMNO:  
* LU:
* CARRERA:

---

### NOTA: EL EXAMEN ESCRITO ES UN DOCUMENTO DE GRAN IMPORTANCIA PARA LA EVALUACIÓN DE LOS CONOCIMIENTOS ADQUIRIDOS, POR LO TANTO, SE SOLICITA LEER ATENTAMENTE LO SIGUIENTE

* Responda claramente cada punto, detallando con la mayor precisión posible lo solicitado.
* Sea prolijo y ordenado en el desarrollo de los temas.
* Sea cuidadoso con las faltas de ortografía y sus oraciones.
* No desarrollar el examen en lápiz.
* Aprobación del examen: Con nota mayor o igual a 4 (cuatro)
* Condiciones de aprobación: 60%
* Duración de examen: 2 horas.

---

1. [2 puntos] Diséñese el DT para un scanner que reconoce componentes léxicos de versión simplificada del Lenguaje Logo Nivel 1. [Sitio](https://www.transum.org/Software/Logo/)

```plain
COMANDOS = 'fd' | 'bk' | 'rt' | 'lt' | 'ct' | 'cs' | 'pu' | 'pd' | 'repeat' | 'pc'
NUMERO = 0 | [1-9][0-9]*
CORCHETE = '[' | ']'
```

1. [2 puntos] Diséñese la GIC del lenguaje anterior.

```grammar
P -> I | IP
I -> fd N | bk N | rt N | lt N | ct | cs | pu | pd | repeat [P] | pc N
N -> 0 | ... | 9 | 1N | ... | 9N
```

1. [3 puntos] Constrúyase el parser ASDP LL(1) y muéstrese el parsing para **(int id)**. GIC <{(,),int, id,';'}, {E, L, W, L'}, E, {E -> (L), E -> id, L -> intEW, W -> L', W -> λ, L' -> ;EW}>

|| ( | ) | int | id | ; | $ |
| -- | -- | -- | -- | -- | -- | -- |
| E | E -> (L) | error | error | E -> id | error | error |
| L | error | error | L -> intEW | error | error | error |
| W | error | W -> λ | error | error | W -> L' | error |
| L' | error | error | error | error | L' -> ;EW | error |

| Pila | Entrada | Regla / Acción |
| -- | -- | -- |
| $E | (int id)$ | E -> (L) |
| $)L( | (int id)$ | emparejar(()|
| $)L | int id)$ | L -> intEW |
| $)WEint | int id)$ | emparejar(int) |
| $)WE | id)$ | E -> id |
| $)Wid | id)$ | emparejar(id) |
| $)W | )$ | W -> λ |
| $) | )$ | emparejar()) |
| $ | $ | accept |

1. [3 puntos] Constrúyase el parser ASAP SLR y muéstrese el parsing para **ab**. GIC <{a, b}, {S, A}, S, {S -> a, S -> Ab, A -> a}>

| Q | a | b | $ | S | A |
| -- | -- | -- | -- | -- | -- |
| 0 | D(2) | | | 1 | 3 |
| 1 | | | OK | | |
| 2 | | R(3) | R(1) | | |
| 3 | | D(4) | | | |
| 4 | | | R(2) | | |

| Pila | Entrada | Regla |
| -- | -- | -- |
| 0 | ab$ | D(2) |
| 02 | b$ | R(3) |
| 03 | b$ | D(4) |
| 034 | $ | R(2) |
| 01 | $ | accept |

---
