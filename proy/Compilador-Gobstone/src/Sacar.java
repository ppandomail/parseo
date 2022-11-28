
public class Sacar implements Consumer {    
    @Override
    public void exec(String param) {
        Tablero.sacar(param);
    }
}
