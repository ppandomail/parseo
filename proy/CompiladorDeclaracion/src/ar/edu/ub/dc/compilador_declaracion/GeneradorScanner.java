package ar.edu.ub.dc.compilador_declaracion;

import java.io.File;

public class GeneradorScanner {

	public static void main(String[] args) {
		jflex.Main.generate(new File("src/ar/edu/ub/dc/compilador_declaracion/Declaracion.jflex"));
	}

}
