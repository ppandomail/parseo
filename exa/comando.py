import ply.lex as lex
import ply.yacc as yacc

# definir tokens
tokens  = ('DEL', 'ID')

# definir patrones
t_DEL    = r'(. | ;)'
t_ID     = r'[a-zA-Z_][a-zA-Z_0-9]*'
t_ignore = " \t"

def t_newline(t):
    r'\n+'
    t.lexer.lineno += t.value.count("\n")
    
def t_error(t):
    print("Illegal character '%s'" % t.value[0])
    t.lexer.skip(1)

# construir scanner
lexer = lex.lex()

def p_comando(t):
    '''comando : cadena 
               | cadena DEL comando'''

def p_cadena(t):
    '''cadena  : ID
               | ID parametros'''

def p_parametros(t):
    '''parametros : parametro
                  | parametro parametros'''

def p_parametro(t):
    'parametro : ID'

def p_error(t):
    print("Error sintáctico en '%s'" % t.value)

parser = yacc.yacc()
parser.parse('run test1 test2;build main;deploy')
