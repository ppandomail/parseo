package ar.edu.ub.dc.compilador_logo;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_logo" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_logo/Logo.cup"};
		java_cup.Main.main(opciones);
	}

}