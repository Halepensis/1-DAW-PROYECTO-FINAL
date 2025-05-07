package src.FileManager;

import src.Classes.Exposicion;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class ExposicionesBinario {
     LinkedHashSet<String> listaExposiciones = new LinkedHashSet<>();

    public ExposicionesBinario(){
        ArrayList<String> listaExposicion = leerExposicionesBinario();
        if (!listaExposicion.isEmpty()){
            for (String exposicion:listaExposicion){
                listaExposiciones.add(exposicion);
            }
        }
    }

    public  void addExposicion(Exposicion exposicion){
        String linea = exposicion.getTitulo();
        if (!listaExposiciones.contains(linea)){
            listaExposiciones.add(linea);
            addExposicionesBinario(linea);
        }

    }
    public void showExposiciones(){
        for (String exposicion:listaExposiciones){
            System.out.println(exposicion);
        }
    }

    private  void addExposicionesBinario(String linea){
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("exposiciones.dat", true))) {
            dos.writeUTF(linea);  // Escribe la cadena en binario (con longitud incluida)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  ArrayList<String> leerExposicionesBinario(){
        ArrayList<String> exposicionesLeidas = new ArrayList<>();
        File archivo = new File("exposiciones.dat");
        if (!archivo.exists()){
            return exposicionesLeidas;
        }
        try (DataInputStream dis = new DataInputStream(new FileInputStream("exposiciones.dat"))) {
            while (dis.available() > 0) {
                String lineaLeida = dis.readUTF();
                exposicionesLeidas.add(lineaLeida);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exposicionesLeidas;
    }
}









