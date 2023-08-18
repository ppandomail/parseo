# PLY

* Es una implementación en Python de lex y yacc, generadores de analizadores léxicos y sintácticos respectivamente.
* Se definen los patrones de los diferentes tokens que se desean reconocer, esto se hace a través de expresiones regulares.
* Se definen las producciones y acciones para formar la gramática a través de funciones.

## Pasos

1. Instalar la herramienta ply. Ejemplo: $pip install ply
1. Crear carpeta del proyecto. Ejemplo: proy-ply-robot
1. Crear archivo .py dentro del proyecto. Ejemplo: traductor.py
    1. Importar los metacompiladores

        ```python
        import ply.lex as lex
        import ply.yacc as yacc
        ```

    1. Definir tokens:

        ```python
        tokens = ('COMIENZO', 'NORTE', 'SUR', 'ESTE', 'OESTE', 'FIN')
        ```

    1. Definir patrones (ER):

        ```python
        t_COMIENZO = r'C'
        t_NORTE    = r'N'
        ...
        t_ignore   = ' \t'
        ```

    1. Construir el Scanner:

        ```python
        lexer = lex.lex()
        ```

    1. Definir GIC: El parámetro t es una tupla, en cada posición tiene el valor de los terminales y no terminales de la producción

       ```python
       def p_programa(t):
        '''prog : COMIENZO sentencias FIN
                | COMIENZO FIN '''
        # acciones semánticas
        ```

    1. Construir el Parser, leer la entrada y hacer el parsing:

        ```python
        parser = yacc.yacc()
        f = open("./entrada.txt", "r")
        input = f.read()
        print(input)
        parser.parse(input)
        ```

1. Crear archivo .txt dentro del proyecto. Ejemplo: entrada.txt

    ```plain
    CSNNSOOONF
    ```

1. Ejecutar el archivo .py. Ejemplo: $python traductor.py
