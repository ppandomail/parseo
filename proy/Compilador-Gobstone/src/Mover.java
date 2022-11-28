
public class Mover implements Consumer {    
    @Override
    public void exec(String param) {
        Tablero.mover(param);
    }
}
