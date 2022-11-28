
public class Poner implements Consumer {    
    @Override
    public void exec(String param) {
        Tablero.poner(param);
    }
}
