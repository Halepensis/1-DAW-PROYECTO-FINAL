package src.FileManager;

import src.Classes.Exposicion;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ExposicionesBinario {
    // LinkedHashSet mantiene el orden de inserción y garantiza unicidad
     private LinkedHashSet<Exposicion> listaExposiciones = new LinkedHashSet<>();

     // Leemos los datos que haya antes en el binario
    public ExposicionesBinario(){
        LinkedHashSet<Exposicion> listaExposicion = leerExposicionesBinario();
        if (!listaExposicion.isEmpty()){
           listaExposiciones.addAll(listaExposicion);
        }
    }

    public  void addExposicion(Exposicion exposicion){
        if (!listaExposiciones.contains(exposicion)){
            listaExposiciones.add(exposicion);
            guardarTodasLasExposiciones();

        }

    }
    public void showExposiciones(){
        for (Exposicion exposicion : listaExposiciones) {
            System.out.println(exposicion.toString()); //
        }
    }

    private void guardarTodasLasExposiciones() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("exposiciones.dat"))) {
            oos.writeObject(listaExposiciones);
        } catch (IOException e) {
            System.err.println("Error al guardar las exposiciones: " + e.getMessage());
        }
    }

    private  LinkedHashSet<Exposicion> leerExposicionesBinario(){
        File archivo = new File("exposiciones.dat");
        if (!archivo.exists()){
            return new LinkedHashSet<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("exposiciones.dat"))) {
            return (LinkedHashSet<Exposicion>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new LinkedHashSet<>();
    }
}









