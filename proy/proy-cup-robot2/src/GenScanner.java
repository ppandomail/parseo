import java.io.File;

public class GenScanner {

  public static void main(String[] args) {
    jflex.Main.generate(new File("src/robot2.jflex"));
  }

}