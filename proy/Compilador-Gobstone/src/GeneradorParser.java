import java.util.function.Consumer;

public class GeneradorParser {

	public static void main(String[] args) throws Exception {
		String opciones[] = new String[] {"-destdir", "src/" , "-parser", "Parser", "src/Gobstone.cup"};
		java_cup.Main.main(opciones);
	}
}
