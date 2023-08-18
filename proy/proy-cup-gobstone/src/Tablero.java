public class Tablero {

    private static Colores [][] tablero;
    private static int X = 0;
    private static int Y = 0;    
    private static int filas = 0;
    private static int columnas = 0;

    public Tablero(int filas, int columnas){
        tablero = new Colores [filas][columnas];
        this.inicializarTablero();        
    }    

    private void inicializarTablero(){
        filas = tablero.length;
        columnas = tablero[0].length;
        for (int i = 0; i < filas; i++){
            for (int j = 0; j < columnas; j++){                
                tablero[i][j] = new Colores(0, 0);
            }
        }
    }

    public static void poner(String color){
        Colores colorAPoner = tablero[X][Y];
        tablero[X][Y] = colorAPoner.ponerColor(color);
    };

    public static void mover(String direccion){
        switch (direccion) {
            case "N": {
                Tablero.arriba();
                break;
            }
            case "O": {
                Tablero.izquierda();
                break;
            }
            case "S": {
                Tablero.abajo();
                break;
            }
            case "E": {
                Tablero.derecha();
                break;
            }
            default: throw new IllegalArgumentException("Elemento inválido: " + direccion);
        }
    };
    
    public static void sacar(String color){        
        Colores colorASacar = tablero[X][Y];
        if(colorASacar.devolverColor(color) != 0){
            tablero[X][Y] = colorASacar.sacarColor(color);
        }
        else{
            throw new IllegalArgumentException(String.format("No podes sacar mas del color: %1$s", color));
        }
    };

    public static Boolean hayColor(String color){
        return tablero[X][Y].hayColor(color);
    }

    public static void arriba() {
		if (Y >= filas - 1)
            throw new IllegalArgumentException("No se puede ir al 'Norte'");
        Y++;        
	}
 
	public static void abajo() {
		if (Y <= 0)
          throw new IllegalArgumentException("No se puede ir al 'Sur'");
        Y--;    
	}
 
	public static void izquierda() {
		if (X <= 0)
            throw new IllegalArgumentException("No se puede ir al 'Oeste'");        
        X--;
	}
 
	public static void derecha() {
		if (X >= columnas - 1)
            throw new IllegalArgumentException("No se puede ir al 'Este'");
        X++;        
	}

    public static void imprimirTablero(){
        for (int j = tablero[0].length - 1; j >= 0; j--){
            for (int i = 0; i < tablero.length - 1 ; i++){
                if(i == X && j == Y){
                    System.out.print("[ " + tablero[i][j].toString() + " ] | ");
                }
                else{
                    System.out.print(tablero[i][j].toString() + " | ");
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
