from robot import Robot
import ply.lex as lex
import ply.yacc as yacc

tokens  = ('COMIENZA', 'NORTE', 'SUR', 'ESTE', 'OESTE', 'FIN')

# Tokens
t_COMIENZA = r'C'
t_NORTE    = r'N'
t_SUR      = r'S'
t_ESTE     = r'E'
t_OESTE    = r'O'
t_FIN      = r'F'
t_ignore   = " \t"

def t_newline(t):
    r'\n+'
    t.lexer.lineno += t.value.count("\n")
    
def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)
    
lexer = lex.lex()

robot = Robot()

# Definición de la gramática

def p_programa(t):
    '''programa : COMIENZA instrucciones FIN 
                | COMIENZA FIN '''
    print(robot)

def p_instrucciones_lista(t):
    '''instrucciones    : instruccion instrucciones
                        | instruccion '''

def p_instruccion_norte(t):
    'instruccion : NORTE'
    robot.mover_arriba()

def p_instruccion_sur(t):
    'instruccion : SUR'
    robot.mover_abajo()

def p_instruccion_este(t):
    'instruccion : ESTE'
    robot.mover_derecha()

def p_instruccion_oeste(t):
    'instruccion : OESTE'
    robot.mover_izquierda()
    
def p_error(t):
    print("Error sintáctico en '%s'" % t.value)

parser = yacc.yacc()

f = open("./entrada.txt", "r")
input = f.read()
print(input)
parser.parse(input)