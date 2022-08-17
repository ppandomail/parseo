package ar.edu.ub.dc.compilador_robotgrid;

public class GridMap {

	public static final int NORTE = 0;
	public static final int OESTE = 1;
	public static final int SUR = 2;
	public static final int ESTE = 3;
	public static final int VACIO = 4;
	public static final int PARED = 5;
	public static final int BEEPER = 6;
	public static final int MUERTE = 7;
	
	public static final int VIVO = 0;
	public static final int MUERTO = 1;
	
	private static int nRows; // height
	private static int nColumns; // width
	private static int [] grid;
	private static int robotPos;
	private static int robotStatus;

	public GridMap(int rows, int cols) {
		assert (rows > 0 && cols > 0) : "La Grilla no puede estar vacia";
		nRows = rows;
		nColumns = cols;
		grid = new int [rows*cols];
		robotStatus = GridMap.VIVO;
		for (int i=0; i < grid.length; i++) 
			grid[i] = GridMap.VACIO;
	}

	public static int getHeight() {
		return nRows;
	}

	public static int getWidth() {
		return nColumns;
	}

	public static int getRobotPos() {
		return robotPos;
	}

	public static char getRobotDirection() {
		switch (grid[robotPos]) {
		case GridMap.NORTE: return 'N';
		case GridMap.OESTE: return 'W';
		case GridMap.SUR: return 'S';
		case GridMap.ESTE: return 'E';
		default: throw new IllegalArgumentException("Elemento inválido: " + grid[robotPos]);
		}
	}

	public static void setElement(int row, int col, String elemento) {
		int position = nColumns * row + col; 
		try {
			switch(elemento) {
			case "North" : grid[position] = GridMap.NORTE; robotPos = position; break;
			case "West" : grid[position] = GridMap.OESTE; robotPos = position; break;
			case "South" : grid[position] = GridMap.SUR; robotPos = position; break;
			case "East" : grid[position] = GridMap.ESTE; robotPos = position; break;
			case "Empty" : grid[position] = GridMap.VACIO; break;
			case "Wall" : grid[position] = GridMap.PARED; break;
			case "Beeper" : grid[position] = GridMap.BEEPER; break;
			default : System.out.println("Elemento inválido: " + elemento);
			}
		} catch (Exception e) { 
			System.out.println("Posición [" + row + "," + col + "] fuera de límites"); 
		}
	}

	public static void setElement(int row, int col, char elemento) {
		int position = nColumns * row + col; 
		try {
			switch(elemento) {
			case 'N' : grid[position] = GridMap.NORTE; robotPos = position; break;
			case 'W' : grid[position] = GridMap.OESTE; robotPos = position; break;
			case 'S' : grid[position] = GridMap.SUR; robotPos = position; break;
			case 'E' : grid[position] = GridMap.ESTE; robotPos = position; break;
			case 'O' : grid[position] = GridMap.VACIO; break;
			case 'X' : grid[position] = GridMap.PARED; break;
			case 'B' : grid[position] = GridMap.BEEPER; break;
			default : System.out.println("Elemento inválido: " + elemento);
			}
		} catch (Exception e) { 
			System.out.println("Posición [" + row + "," + col + "] fuera de límites"); 
		}
	}

	public static String getElement(int row, int col) {
		try {
			switch(grid[nColumns * row + col]) {
			case GridMap.NORTE: return "North";
			case GridMap.OESTE: return "West";
			case GridMap.SUR: return "South";
			case GridMap.ESTE: return "East";
			case GridMap.VACIO: return "Empty";
			case GridMap.PARED: return "Wall";
			case GridMap.BEEPER: return "Beeper";
			default : System.out.println("THIS SHOULD NEVER BE PRINTED");
			}
		} catch (Exception e) { 
			System.out.println("Posición [" + row + "," + col + "] fuera de límites"); 
		}
		return "";	
	}

	public static boolean isCellBlocked(int row, int col) {
		return grid[nColumns * row + col] ==  GridMap.PARED;
	}

	public static boolean isFrontClear() {
		switch (grid[robotPos]) { // check direction of the robot before determining if front is clear
		case GridMap.NORTE: return (getRobotRow() > 0) && !(grid[robotPos-nColumns] == GridMap.PARED);
		case GridMap.OESTE: return (getRobotCol() > 0) && !(grid[robotPos-1] == GridMap.PARED);
		case GridMap.SUR: return (getRobotRow() < nRows-1) && !(grid[robotPos+nColumns] == GridMap.PARED);
		case GridMap.ESTE: return (getRobotCol() < nColumns-1) && !(grid[robotPos+1] == GridMap.PARED);
		case GridMap.MUERTE: return false;
		default: throw new IllegalArgumentException("frontClear: dirección inválida");
		}
	}

	public static boolean isRobotAlive() {
		return robotStatus == GridMap.VIVO;
	}

	public static void turnLeft() {
		if (GridMap.isRobotAlive()) {
			switch (grid[robotPos]) {
			case GridMap.NORTE: grid[robotPos] = GridMap.OESTE; break;
			case GridMap.OESTE: grid[robotPos] = GridMap.SUR; break;
			case GridMap.SUR: grid[robotPos] = GridMap.ESTE; break;
			case GridMap.ESTE: grid[robotPos] = GridMap.NORTE; break;
			default: throw new IllegalArgumentException("TurnLeft: dirección inválida");
			}
			GridMap.printState();
		}
	}

	public static void move() {
		if (GridMap.isRobotAlive()) {
			if (GridMap.isFrontClear()) {
				switch (grid[robotPos]) { // check direction of the robot before moving
				case GridMap.NORTE: grid[robotPos] = GridMap.VACIO; robotPos -= nColumns; grid[robotPos] = GridMap.NORTE; break;
				case GridMap.OESTE: grid[robotPos] = GridMap.VACIO; robotPos--; grid[robotPos] = GridMap.OESTE; break;
				case GridMap.SUR: grid[robotPos] = GridMap.VACIO; robotPos += nColumns; grid[robotPos] = GridMap.SUR; break;
				case GridMap.ESTE: grid[robotPos] = GridMap.VACIO; robotPos++; grid[robotPos] = GridMap.ESTE; break;
				default: throw new IllegalArgumentException("Move: dirección inválida");
				}
			} else {
				grid[robotPos] = GridMap.MUERTE; 
				robotStatus = GridMap.MUERTO;
				System.out.println("You hit a Wall, GAME OVER");
			}
			GridMap.printState();
		}
	}

	private static int getRobotRow() {
		return robotPos / nColumns;
	}

	private static int getRobotCol() {
		return robotPos % nColumns;
	}


	public static void printState() {
		System.out.println();
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nColumns; j++) 
				System.out.print("[" + intToChar(grid[nColumns * i + j]) + "] ");
			System.out.println();
		}
		System.out.println();
	}
	
	private static char intToChar(int elemento) {
		switch(elemento) {
		case GridMap.NORTE: return '^';
		case GridMap.OESTE: return '<';
		case GridMap.SUR: return 'v';
		case GridMap.ESTE: return '>';
		case GridMap.VACIO: return ' ';
		case GridMap.PARED: return 'W';
		case GridMap.BEEPER: return 'B';
		case GridMap.MUERTE: return 'X';
		default: throw new IllegalArgumentException("Elemento inválido: " + elemento);
		}
	}

}