package ar.edu.ub.dc.compilador_booleano;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_booleano" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_booleano/Booleano.cup"};
		java_cup.Main.main(opciones);
	}

}