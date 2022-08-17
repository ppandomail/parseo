package ar.edu.ub.dc.compilador_robotgrid;

import java.io.FileReader;

public class Main {

	public static void main(String[] args) {
		for (int i = 1; i<=3; i++) {
			System.out.println("*********** Programa " + i + " ************");
			try {
			new Parser(new Scanner(new FileReader("resources/programa" + i + ".txt"))).parse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}

}

/*
Programa 1: OK
Programa 2: Error sintáctico, se provee un número de dos dígitos al método drawGrid, por lo que el
scanner lo separa en dos token (1 y 0).
Programa 3: Error léxico "placRobot" no es reconocido por el Jflex como un lexema válido.
*/