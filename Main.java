import src.BDD.VisitantesDAO;
import src.Classes.*;
import src.FileManager.ExposicionesBinario;
import src.FileManager.GestorValoraciones;
import src.FileManager.LogsTexto;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Exposicion ejemplo = new Exposicion("Ejemplo","La que sea", TipoExposicion.Tecnologia);
        Exposicion ejemplo2 = new Exposicion("Ejemplo2","La que sea", TipoExposicion.Tecnologia);
        Visitante visitante = new Visitante("Pedro","ejemplo@gmail.com",55);
        Visitante visitante2 = new Visitante("Carlos","ejemplo@gmail.com",55);
        Valoracion valoracion = new Valoracion(visitante,ejemplo,"no esta mal",4);
        Valoracion valoracion3 = new Valoracion(visitante,ejemplo2,"esta bien",4);
        Valoracion valoracion2 = new Valoracion(visitante2,ejemplo,"meh",4);
        GestorValoraciones.addValoracion(valoracion);
        GestorValoraciones.addValoracion(valoracion);
        GestorValoraciones.addValoracion(valoracion2);
        GestorValoraciones.addValoracion(valoracion3);
        ExposicionesBinario binario = new ExposicionesBinario();
        VisitantesDAO.addVisitante(visitante);
        VisitantesDAO.leerVisitantes();





    }


}
