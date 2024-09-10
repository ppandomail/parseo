

class Lexer:
  
  ERROR = -1
  ACEPTAR = 0
  
  def __init__(self, palabra):
    self.__palabra = palabra
    self.__cursor = -1
  
  def get_caracter(self):
    if self.__cursor + 1 < len(self.__palabra):
      self.__cursor = self.__cursor + 1
      return self.__palabra[self.__cursor]
    else:
      return '*'
  
  def q0(self): 
    c = self.get_caracter()
    if c == 'a': return self.q1()
    if c == 'b': return self.q4()
    return Lexer.ERROR
  
  def q1(self):
    c = self.get_caracter()
    if c == 'a': return self.q1()
    if c == 'b': return self.q2()
    return Lexer.ERROR
  
  def q2(self): 
    c = self.get_caracter()
    if c == 'a': return self.q1()
    if c == 'b': return self.q3()
    return Lexer.ERROR
  
  def q3(self): 
    return Lexer.ACEPTAR
  
  def q4(self):
    c = self.get_caracter()
    if c == 'a': return self.q1()
    if c == 'b': return self.q4()
    return Lexer.ERROR
