import src.BDD.ExposicionesDAO;

import src.Classes.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ExposicionesDAO exposicionesDAO = new ExposicionesDAO();
        ArrayList<Exposicion> listaExposiciones = exposicionesDAO.getAll();
        for (Exposicion exposicion : listaExposiciones){
            System.out.println(exposicion);
        }
        Exposicion prueba =  new Exposicion("Prueba","una prueba a ver que sale",TipoExposicion.Tecnologia);
        exposicionesDAO.create(prueba);

        listaExposiciones = exposicionesDAO.getAll();
        for (Exposicion exposicion : listaExposiciones){
            System.out.println(exposicion);
        }
        System.out.println(prueba.getId());








    }


}
