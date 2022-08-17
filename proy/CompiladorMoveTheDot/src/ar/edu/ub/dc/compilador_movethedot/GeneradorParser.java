package ar.edu.ub.dc.compilador_movethedot;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_movethedot" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_movethedot/MoveTheDot.cup"};
		java_cup.Main.main(opciones);
	}

}