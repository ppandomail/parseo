import java.io.File;

public class GeneradorScanner {

	public static void main(String[] args) {
		jflex.Main.generate(new File("src/Gobstone.jflex"));
	}

}
