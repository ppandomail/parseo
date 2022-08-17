package ar.edu.ub.dc.compilador_condicional;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_condicional" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_condicional/Condicional.cup"};
		java_cup.Main.main(opciones);
	}

}