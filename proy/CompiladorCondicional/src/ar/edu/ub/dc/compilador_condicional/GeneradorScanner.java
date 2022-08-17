package ar.edu.ub.dc.compilador_condicional;

import java.io.File;

public class GeneradorScanner {

	public static void main(String[] args) {
		jflex.Main.generate(new File("src/ar/edu/ub/dc/compilador_condicional/Condicional.jflex"));
	}

}
