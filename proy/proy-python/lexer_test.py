import unittest
from lexer import Lexer

class LexerTest(unittest.TestCase):
  
  def test1(self):
    self.assertEquals(Lexer.ACEPTAR, Lexer("abb").q0())
    
  def test2(self):
    self.assertEquals(Lexer.ACEPTAR, Lexer("aabb").q0())
    
  def test3(self):
    self.assertEquals(Lexer.ACEPTAR, Lexer("babb").q0())
    
  def test4(self):
    self.assertEquals(Lexer.ACEPTAR, Lexer("ababb").q0())
    
  def test5(self):
    self.assertEquals(Lexer.ERROR, Lexer("").q0())
    
  def test6(self):
    self.assertEquals(Lexer.ERROR, Lexer("bb").q0())

if __name__ == '__main__':  
    unittest.main()