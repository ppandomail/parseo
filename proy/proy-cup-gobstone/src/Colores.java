import java.util.HashMap;
import java.util.Map;

public class Colores {
    Map<String, Integer> map = new HashMap<>();  
    
    public Colores(int cantidad_rojo, int cantidad_azul){
        map.put("R", cantidad_rojo);
        map.put("A", cantidad_azul);
    };

    public Colores ponerColor(String color){
        map.put(color, map.get(color) + 1);
        return this;
    }

    public int devolverColor(String color){
        return map.get(color);
    }

    public Colores sacarColor(String color){
        map.put(color, map.get(color) - 1);
        return this;
    }

    public Boolean hayColor(String color){
        return this.devolverColor(color) != 0;
    }
    
    @Override
    public String toString() {
        return String.format("Rojos: %1$s; Azules: %2$s", this.devolverColor("R"), this.devolverColor("A"));
    }
    
}
