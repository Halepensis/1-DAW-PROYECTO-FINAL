package src.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LogsTexto {
    static List<String> logs;
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
    public static void writeLogs(String log){
        logs.add(log);
        try {
            Files.write(Paths.get(routePath),logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
