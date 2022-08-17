package ar.edu.ub.dc.compilador_aritmetico;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_aritmetico" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_aritmetico/Aritmetico.cup"};
		java_cup.Main.main(opciones);
	}

}