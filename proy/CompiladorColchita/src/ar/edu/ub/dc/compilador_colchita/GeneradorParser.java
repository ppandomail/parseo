package ar.edu.ub.dc.compilador_colchita;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_colchita" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_colchita/Colchita.cup"};
		java_cup.Main.main(opciones);
	}

}