import src.BDD.ExposicionesDAO;
import src.BDD.ValoracionDAO;
import src.BDD.VisitantesDAO;
import src.Classes.Exposicion;
import src.Classes.TipoExposicion;
import src.Classes.Valoracion;
import src.Classes.Visitante;
import src.FileManager.ExposicionesBinario;
import src.FileManager.GestorValoraciones;
import src.FileManager.LogsTexto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final VisitantesDAO visitantesDAO = new VisitantesDAO();
    private static final ExposicionesDAO exposicionesDAO = new ExposicionesDAO();
    private static final ValoracionDAO valoracionDAO = new ValoracionDAO();
    private static final ExposicionesBinario exposicionesBinario = new ExposicionesBinario();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    addVisitante();
                    break;
                case 2:
                    addExposicion();
                    break;
                case 3:
                    consultarExposiciones();
                    break;
                case 4:
                    valorarExposicion();
                    break;
                case 5:
                    verLogsVisita();
                    break;
                case 6:
                    exportarExposicionesABinario();
                    break;
                case 7:
                    importarExposicionesDesdeArchivoBinario();
                    break;
                case 8:
                    consultarVisitantes();
                    break;
                case 9:
                    consultarValoraciones();
                    break;
                case 0:
                    salir = true;
                    System.out.println("¡Gracias por usar el Museo Interactivo del Futuro!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MUSEO INTERACTIVO DEL FUTURO =====");
        System.out.println("1. Añadir visitante");
        System.out.println("2. Añadir exposición");
        System.out.println("3. Consultar exposiciones");
        System.out.println("4. Valorar exposición");
        System.out.println("5. Ver logs de visita");
        System.out.println("6. Exportar exposiciones a archivo binario");
        System.out.println("7. Importar exposiciones desde archivo binario");
        System.out.println("8. Consultar visitantes");
        System.out.println("9. Consultar valoraciones");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addVisitante() {
        System.out.println("\n=== AÑADIR VISITANTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Edad: ");
        try {
            int edad = Integer.parseInt(scanner.nextLine());
            Visitante visitante = new Visitante(nombre, email, edad);
            visitantesDAO.create(visitante);
            System.out.println("Visitante añadido correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: La edad debe ser un número entero.");
        }
    }

    private static void addExposicion() {
        System.out.println("\n=== AÑADIR EXPOSICIÓN ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.println("Tipos disponibles: ");

        TipoExposicion[] tipos = TipoExposicion.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }

        System.out.print("Seleccione un tipo (1-" + tipos.length + "): ");
        try {
            int tipoSeleccionado = Integer.parseInt(scanner.nextLine());
            if (tipoSeleccionado >= 1 && tipoSeleccionado <= tipos.length) {
                TipoExposicion tipo = tipos[tipoSeleccionado - 1];
                Exposicion exposicion = new Exposicion(titulo, descripcion, tipo);
                exposicionesDAO.create(exposicion);
                System.out.println("Exposición añadida correctamente con ID: " + exposicion.getId());
            } else {
                System.out.println("Tipo de exposición no válido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número.");
        }
    }

    private static void consultarExposiciones() {
        System.out.println("\n=== CONSULTAR EXPOSICIONES ===");
        ArrayList<Exposicion> exposiciones = exposicionesDAO.getAll();

        if (exposiciones.isEmpty()) {
            System.out.println("No hay exposiciones registradas.");
            return;
        }

        for (Exposicion expo : exposiciones) {
            System.out.println("ID: " + expo.getId());
            System.out.println("Título: " + expo.getTitulo());
            System.out.println("Tipo: " + expo.getTipo());
            System.out.println("Descripción: " + expo.getDescripcion());
            System.out.println("Fecha de creación: " + expo.getFechaCreacionFormated());
            System.out.println("------------------------");
        }
    }

    private static void valorarExposicion() {
        System.out.println("\n=== VALORAR EXPOSICIÓN ===");

        // Mostrar visitantes
        ArrayList<Visitante> visitantes = visitantesDAO.getAll();
        if (visitantes.isEmpty()) {
            System.out.println("No hay visitantes registrados. Primero debe añadir un visitante.");
            return;
        }

        System.out.println("Visitantes disponibles:");
        for (Visitante v : visitantes) {
            System.out.println(v.getId() + ". " + v.getNombre() + " (" + v.getEmail() + ")");
        }

        System.out.print("Seleccione ID del visitante: ");
        try {
            int idVisitante = Integer.parseInt(scanner.nextLine());
            Visitante visitante = visitantesDAO.get(idVisitante);

            if (visitante == null) {
                System.out.println("Visitante no encontrado.");
                return;
            }

            // Mostrar exposiciones
            ArrayList<Exposicion> exposiciones = exposicionesDAO.getAll();
            if (exposiciones.isEmpty()) {
                System.out.println("No hay exposiciones registradas. Primero debe añadir una exposición.");
                return;
            }

            System.out.println("Exposiciones disponibles:");
            for (Exposicion e : exposiciones) {
                System.out.println(e.getId() + ". " + e.getTitulo() + " (" + e.getTipo() + ")");
            }

            System.out.print("Seleccione ID de la exposición: ");
            int idExposicion = Integer.parseInt(scanner.nextLine());
            Exposicion exposicion = exposicionesDAO.get(idExposicion);

            if (exposicion == null) {
                System.out.println("Exposición no encontrada.");
                return;
            }

            System.out.print("Puntuación (1-5): ");
            int puntuacion = Integer.parseInt(scanner.nextLine());

            System.out.print("Comentario: ");
            String comentario = scanner.nextLine();

            Valoracion valoracion = new Valoracion(visitante, exposicion, comentario, puntuacion);
            valoracionDAO.create(valoracion);
            GestorValoraciones.addValoracion(valoracion);

            System.out.println("Valoración registrada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("Error: Debe ingresar un número válido.");
        }
    }

    private static void verLogsVisita() {
        System.out.println("\n=== LOGS DE VISITA ===");
        try {
            LogsTexto.readLogs();
        } catch (IOException e) {
            System.out.println("Error al leer los logs: " + e.getMessage());
        }
    }

    private static void exportarExposicionesABinario() {
        System.out.println("\n=== EXPORTAR EXPOSICIONES A ARCHIVO BINARIO ===");
        ArrayList<Exposicion> exposiciones = exposicionesDAO.getAll();

        if (exposiciones.isEmpty()) {
            System.out.println("No hay exposiciones para exportar.");
            return;
        }

        for (Exposicion exposicion : exposiciones) {
            exposicionesBinario.addExposicion(exposicion);
        }

        System.out.println("Exposiciones exportadas correctamente al archivo binario.");
    }

    private static void importarExposicionesDesdeArchivoBinario() {
        System.out.println("\n=== IMPORTAR EXPOSICIONES DESDE ARCHIVO BINARIO ===");
        System.out.println("Exposiciones disponibles en el archivo binario:");
        exposicionesBinario.showExposiciones();
    }

    private static void consultarVisitantes() {
        System.out.println("\n=== CONSULTAR VISITANTES ===");
        ArrayList<Visitante> visitantes = visitantesDAO.getAll();

        if (visitantes.isEmpty()) {
            System.out.println("No hay visitantes registrados.");
            return;
        }

        for (Visitante v : visitantes) {
            System.out.println("ID: " + v.getId());
            System.out.println("Nombre: " + v.getNombre());
            System.out.println("Email: " + v.getEmail());
            System.out.println("Edad: " + v.getEdad());
            System.out.println("------------------------");
        }
    }

    private static void consultarValoraciones() {
        System.out.println("\n=== CONSULTAR VALORACIONES ===");
        ArrayList<Valoracion> valoraciones = valoracionDAO.getAll();

        if (valoraciones.isEmpty()) {
            System.out.println("No hay valoraciones registradas.");
            return;
        }

        for (Valoracion v : valoraciones) {
            System.out.println("Visitante: " + v.getVisitante().getNombre());
            System.out.println("Exposición: " + v.getExposicion().getTitulo());
            System.out.println("Puntuación: " + v.getPuntuacion() + "/5");
            System.out.println("Comentario: " + v.getComentario());
            System.out.println("------------------------");
        }
    }
}