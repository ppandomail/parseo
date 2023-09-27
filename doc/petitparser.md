# PetitParser

* Permite crear parsers eficientes en diferentes lenguajes de programación: C#, Clojure, Dart, Java, Kotlin, PHP, Python, Smalltalk, Swift y TypeScript.

## Instalación

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
