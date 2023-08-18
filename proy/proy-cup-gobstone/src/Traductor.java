import java.io.FileReader;

public class Traductor {
  
  public static void main(String[] args) throws Exception {
    new Parser(new Scanner(new FileReader("src/entrada.txt"))).parse();
  }

}