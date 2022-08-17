package ar.edu.ub.dc.compilador_robotgrid;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/ar/edu/ub/dc/compilador_robotgrid" , "-parser", 
				"Parser", "src/ar/edu/ub/dc/compilador_robotgrid/RobotGrid.cup"};
		java_cup.Main.main(opciones);
	}

}