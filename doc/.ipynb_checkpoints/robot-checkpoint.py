import ply.lex as lex
import ply.yacc as yacc

# definir tokens
tokens = ('COMIENZA', 'NORTE', 'SUR', 'ESTE', 'OESTE', 'FIN')

# definir patrones
t_COMIENZA = r'C'
t_NORTE    = r'N'
t_SUR      = r'S'
t_ESTE     = r'E'
t_OESTE    = r'O'
t_FIN      = r'F' 
t_ignore   = ' \t'

def t_newline(t):
    r'\n+'
    t.lexer.lineno += t.value.count("\n")

def t_error(t):
    print("Carácter ilegal '%s'" % t.value[0])
    t.lexer.skip(1)

# construir scanner
lexer = lex.lex()

robot = {'x': 0, 'y':0}

def p_programa(t):
    '''programa : COMIENZA instrucciones FIN 
                | COMIENZA FIN '''
    print(robot)

def p_instrucciones_lista(t):
    '''instrucciones    : instruccion instrucciones
                        | instruccion '''

def p_instruccion_norte(t):
    'instruccion : NORTE'
    robot['y'] += 1

def p_instruccion_sur(t):
    'instruccion : SUR'
    robot['y'] -= 1

def p_instruccion_este(t):
    'instruccion : ESTE'
    robot['x'] += 1

def p_instruccion_oeste(t):
    'instruccion : OESTE'
    robot['y'] -= 1
    
def p_error(t):
    print("Error sintáctico en '%s'" % t.value)
