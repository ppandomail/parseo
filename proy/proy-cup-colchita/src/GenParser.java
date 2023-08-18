public class GenParser {
  
  public static void main(String[] args) throws Exception {
		java_cup.Main.main(new String[] {"-destdir", "src" , "-parser", "Parser", "src/colchita.cup"});
  }

}