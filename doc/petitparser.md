# PetitParser

* Es un (black-box) framework para construir parsers:
  * Validar texto
  * Extraer un substring que matchea las reglas
  * Ejecutar acciones custom
* Es aplicable para casos en donde:
  * Se conoce la gramática
  * Se estudian programas (válidos) de un lenguaje
* Puede implementar cosas que (ANSI) Regex no pueden
* Es mucho mejor que implementar reglas de validación con condicionales
* Permite crear parsers eficientes en diferentes lenguajes de programación: C#, Clojure, Dart, Java, Kotlin, PHP, Python, Smalltalk, Swift y TypeScript.
* [Sitio Web](https://petitparser.github.io/)

## Instalación PetitParser for Python

```shell
$pip install petitparser 
```

## Ejemplo gramática identificadores

```python
from petitparser import character as c

ident = c.letter() & (c.letter() | c.digit()).star()

# Análisis de cadenas:
id1 = ident.parse('yeah')
print(id1.value) # ['y', ['e', 'a', 'h']]

id2 = ident.parse('f12')
print(id2.value) # ['f', ['1', '2']]

id3 = ident.parse('123')
print(id3.message)  # letter expected
print(id3.position) # 0

print(ident.accept('foo')) # True
print(ident.accept('123')) # False

```

## Ejercicios

1. Instale Petit Parser en su lenguaje de preferencia
1. Implemente un parser que reconozca los siguientes casos:
    * En publicaciones científicas las lista de autores contiene
      * Un nombre, iniciales (opcional), al menos un apellido por cada autor
      * Cada autor separado por coma con el siguiente autor excepto el último autor que es precedido por el conector "and"
      * Implemente test cases que prueben el parser
    * Posibles direcciones de e-mail, implemente test cases del parser
