package ar.edu.ub.dc.compilador_declaracion;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_declaracion" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_declaracion/Declaracion.cup"};
		java_cup.Main.main(opciones);
	}

}