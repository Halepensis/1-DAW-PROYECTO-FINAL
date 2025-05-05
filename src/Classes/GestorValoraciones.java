package src.Classes;

import java.util.LinkedHashSet;

public class GestorValoraciones {
    static LinkedHashSet<Valoracion> valoraciones = new LinkedHashSet<>();

    public static void addValoracion(Valoracion valoracion){
        valoraciones.add(valoracion);
    }

    public static void showValoraciones(){
        for (Valoracion v : valoraciones){
            System.out.println(v);
        }
    }

}
