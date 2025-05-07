package src.FileManager;

import src.Classes.Valoracion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogsTexto {
    static List<String> logs = new ArrayList<>();
    static String routePath="src/FileManager/visitas.txt";
    public static void readLogs() throws IOException {
            Path path = Paths.get(routePath);
            if(!Files.exists(path)){
                Files.createFile(path);
            }
            logs = Files.readAllLines(path);
            for(String linea : logs){
                System.out.println(linea);
            }
    }
    public static void writeLogs(Valoracion valoracion){
        String nuevoLog = convertirValoracion(valoracion);
        logs.add(nuevoLog);
        try {
            Files.write(Paths.get(routePath),logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertirValoracion(Valoracion valoracion){
        return String.format("%s - VISITA - %s accedió a exposición %s",

                valoracion.getFechaValoracion(),valoracion.getVisitante().getNombre(),valoracion.getExposicion().getTitulo());
    }



}
