class Robot:
    
    def __init__(self):
        self.x = 0
        self.y = 0
    
    def mover_arriba(self):
        self.y = self.y + 1
    
    def mover_abajo(self):
        self.y = self.y - 1
    
    def mover_derecha(self):
        self.x = self.x + 1
    
    def mover_izquierda(self):
        self.x = self.x - 1 
        
    def __str__(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'