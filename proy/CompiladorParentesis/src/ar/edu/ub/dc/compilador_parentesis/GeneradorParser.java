package ar.edu.ub.dc.compilador_parentesis;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_parentesis" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_parentesis/Parentesis.cup"};
		java_cup.Main.main(opciones);
	}

}