tokens  = (
    'COMIENZA',
    'NORTE',
    'SUR',
    'ESTE',
    'OESTE',
    'FIN'
)

# Tokens
t_COMIENZA = r'C'
t_NORTE    = r'N'
t_SUR      = r'S'
t_ESTE     = r'E'
t_OESTE    = r'O'
t_FIN      = r'F'

# Caracteres ignorados
t_ignore = " \t"

def t_newline(t):
    r'\n+'
    t.lexer.lineno += t.value.count("\n")
    
def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)
    
# Construyendo el analizador léxico
import ply.lex as lex
lexer = lex.lex()

class Punto:
    
    def __init__(self):
        self.x = 0
        self.y = 0
    
    def up(self):
        self.y = self.y + 1
    
    def down(self):
        self.y = self.y - 1
    
    def right(self):
        self.x = self.x + 1
    
    def left(self):
        self.x = self.x - 1 
        
    def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'   

p = Punto()

# Definición de la gramática

def p_programa(t):
    '''programa : COMIENZA instrucciones FIN 
                | COMIENZA FIN '''
    print(p)

def p_instrucciones_lista(t):
    '''instrucciones    : instruccion instrucciones
                        | instruccion '''

def p_instruccion_norte(t):
    'instruccion : NORTE'
    p.up()

def p_instruccion_sur(t):
    'instruccion : SUR'
    p.down()

def p_instruccion_este(t):
    'instruccion : ESTE'
    p.right()

def p_instruccion_oeste(t):
    'instruccion : OESTE'
    p.left()
    
def p_error(t):
    print("Error sintáctico en '%s'" % t.value)

import ply.yacc as yacc
parser = yacc.yacc()

f = open("./entrada.txt", "r")
input = f.read()
print(input)
parser.parse(input)