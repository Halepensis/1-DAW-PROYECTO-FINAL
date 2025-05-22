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
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    gestionarVisitantes();
                    break;
                case 2:
                    gestionarExposiciones();
                    break;
                case 3:
                    gestionarValoraciones();
                    break;
                case 4:
                    verLogsVisita();
                    break;
                case 5:
                    exportarExposicionesABinario();
                    break;
                case 6:
                    importarExposicionesDesdeArchivoBinario();
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

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== MUSEO INTERACTIVO DEL FUTURO =====");
        System.out.println("1. Gestionar Visitantes");
        System.out.println("2. Gestionar Exposiciones");
        System.out.println("3. Gestionar Valoraciones");
        System.out.println("4. Ver logs de visita");
        System.out.println("5. Exportar exposiciones a archivo binario");
        System.out.println("6. Importar exposiciones desde archivo binario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // GESTIÓN DE VISITANTES

    private static void gestionarVisitantes() {
        boolean volver = false;

        while (!volver) {
            mostrarMenuVisitantes();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    addVisitante();
                    break;
                case 2:
                    consultarVisitantes();
                    break;
                case 3:
                    actualizarVisitante();
                    break;
                case 4:
                    eliminarVisitante();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void mostrarMenuVisitantes() {
        System.out.println("\n=== GESTIÓN DE VISITANTES ===");
        System.out.println("1. Añadir visitante");
        System.out.println("2. Consultar visitantes");
        System.out.println("3. Actualizar visitante");
        System.out.println("4. Eliminar visitante");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private static void addVisitante() {
        System.out.println("\n=== AÑADIR VISITANTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
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

    private static void consultarVisitantes() {
        System.out.println("\n=== CONSULTAR VISITANTES ===");
        ArrayList<Visitante> visitantes = visitantesDAO.getAll();

        if (visitantes.isEmpty()) {
            System.out.println("No hay visitantes registrados.");
            return;
        }

        for (Visitante v : visitantes) {
            System.out.println(v.toString());
        }
    }

    private static void actualizarVisitante() {
        System.out.println("\n=== ACTUALIZAR VISITANTE ===");
        consultarVisitantes();

        if (visitantesDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID del visitante a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Visitante visitante = visitantesDAO.get(id);

            if (visitante == null) {
                System.out.println("Visitante no encontrado.");
                return;
            }

            System.out.println("Datos actuales: " + visitante.toString());
            System.out.print("Nuevo nombre (actual: " + visitante.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine().trim();
            if (!nuevoNombre.isEmpty()) {
                visitante.setNombre(nuevoNombre);
            }

            System.out.print("Nuevo email (actual: " + visitante.getEmail() + "): ");
            String nuevoEmail = scanner.nextLine().trim();
            if (!nuevoEmail.isEmpty()) {
                visitante.setEmail(nuevoEmail);
            }

            System.out.print("Nueva edad (actual: " + visitante.getEdad() + "): ");
            String edadStr = scanner.nextLine().trim();
            if (!edadStr.isEmpty()) {
                try {
                    int nuevaEdad = Integer.parseInt(edadStr);
                    visitante.setEdad(nuevaEdad);
                } catch (NumberFormatException e) {
                    System.out.println("Edad no válida, se mantiene la actual.");
                }
            }

            visitantesDAO.update(visitante);
            System.out.println("Visitante actualizado correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    private static void eliminarVisitante() {
        System.out.println("\n=== ELIMINAR VISITANTE ===");
        consultarVisitantes();

        if (visitantesDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID del visitante a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Visitante visitante = visitantesDAO.get(id);

            if (visitante == null) {
                System.out.println("Visitante no encontrado.");
                return;
            }

            System.out.println("¿Está seguro de eliminar al visitante: " + visitante.getNombre() + "? (s/n)");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s")) {
                visitantesDAO.delete(visitante);
                System.out.println("Visitante eliminado correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    // GESTIÓN DE EXPOSICIONES

    private static void gestionarExposiciones() {
        boolean volver = false;

        while (!volver) {
            mostrarMenuExposiciones();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    addExposicion();
                    break;
                case 2:
                    consultarExposiciones();
                    break;
                case 3:
                    actualizarExposicion();
                    break;
                case 4:
                    eliminarExposicion();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void mostrarMenuExposiciones() {
        System.out.println("\n=== GESTIÓN DE EXPOSICIONES ===");
        System.out.println("1. Añadir exposición");
        System.out.println("2. Consultar exposiciones");
        System.out.println("3. Actualizar exposición");
        System.out.println("4. Eliminar exposición");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private static void addExposicion() {
        System.out.println("\n AÑADIR EXPOSICIÓN ");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine().trim();
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
        System.out.println("\nCONSULTAR EXPOSICIONES ");
        ArrayList<Exposicion> exposiciones = exposicionesDAO.getAll();

        if (exposiciones.isEmpty()) {
            System.out.println("No hay exposiciones registradas.");
            return;
        }

        for (Exposicion expo : exposiciones) {
            System.out.println(expo.toString());
        }
    }

    private static void actualizarExposicion() {
        System.out.println("\n ACTUALIZAR EXPOSICIÓN ");
        consultarExposiciones();

        if (exposicionesDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID de la exposición a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Exposicion exposicion = exposicionesDAO.get(id);

            if (exposicion == null) {
                System.out.println("Exposición no encontrada.");
                return;
            }

            System.out.println("Datos actuales: " + exposicion.toString());

            // Crear nueva exposición con los datos actualizados
            System.out.print("Nuevo título (actual: " + exposicion.getTitulo() + "): ");
            String nuevoTitulo = scanner.nextLine().trim();
            if (nuevoTitulo.isEmpty()) {
                nuevoTitulo = exposicion.getTitulo();
            }

            System.out.print("Nueva descripción (actual: " + exposicion.getDescripcion() + "): ");
            String nuevaDescripcion = scanner.nextLine().trim();
            if (nuevaDescripcion.isEmpty()) {
                nuevaDescripcion = exposicion.getDescripcion();
            }

            System.out.println("Tipos disponibles: ");
            TipoExposicion[] tipos = TipoExposicion.values();
            for (int i = 0; i < tipos.length; i++) {
                System.out.println((i + 1) + ". " + tipos[i]);
            }
            System.out.print("Nuevo tipo (actual: " + exposicion.getTipo() + ") - Seleccione número o Enter para mantener: ");
            String tipoStr = scanner.nextLine().trim();
            TipoExposicion nuevoTipo = exposicion.getTipo();

            if (!tipoStr.isEmpty()) {
                try {
                    int tipoSeleccionado = Integer.parseInt(tipoStr);
                    if (tipoSeleccionado >= 1 && tipoSeleccionado <= tipos.length) {
                        nuevoTipo = tipos[tipoSeleccionado - 1];
                    } else {
                        System.out.println("Tipo no válido, se mantiene el actual.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Tipo no válido, se mantiene el actual.");
                }
            }

            // Crear exposición actualizada manteniendo el ID y fecha
            Exposicion exposicionActualizada = new Exposicion(exposicion.getId(), nuevoTitulo, nuevaDescripcion, nuevoTipo, exposicion.getFechaCreacion().toLocalDateTime());
            exposicionesDAO.update(exposicionActualizada);
            System.out.println("Exposición actualizada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    private static void eliminarExposicion() {
        System.out.println("\nELIMINAR EXPOSICIÓN ");
        consultarExposiciones();

        if (exposicionesDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID de la exposición a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Exposicion exposicion = exposicionesDAO.get(id);

            if (exposicion == null) {
                System.out.println("Exposición no encontrada.");
                return;
            }

            System.out.println("¿Está seguro de eliminar la exposición: " + exposicion.getTitulo() + "? (s/n)");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                exposicionesDAO.delete(exposicion);
                System.out.println("Exposición eliminada correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    //  GESTIÓN DE VALORACIONES

    private static void gestionarValoraciones() {
        boolean volver = false;

        while (!volver) {
            mostrarMenuValoraciones();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    addValoracion();
                    break;
                case 2:
                    consultarValoraciones();
                    break;
                case 3:
                    actualizarValoracion();
                    break;
                case 4:
                    eliminarValoracion();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        }
    }

    private static void mostrarMenuValoraciones() {
        System.out.println("\n=== GESTIÓN DE VALORACIONES ===");
        System.out.println("1. Añadir valoración");
        System.out.println("2. Consultar valoraciones");
        System.out.println("3. Actualizar valoración");
        System.out.println("4. Eliminar valoración");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
    }

    private static void addValoracion() {
        System.out.println("\n=== AÑADIR VALORACIÓN ===");

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

    private static void consultarValoraciones() {
        System.out.println("\nCONSULTAR VALORACIONES ");
        ArrayList<Valoracion> valoraciones = valoracionDAO.getAll();

        if (valoraciones.isEmpty()) {
            System.out.println("No hay valoraciones registradas.");
            return;
        }

        for (Valoracion v : valoraciones) {
            System.out.println("ID: " + v.getId());
            System.out.println("Visitante: " + v.getVisitante().getNombre());
            System.out.println("Exposición: " + v.getExposicion().getTitulo());
            System.out.println("Puntuación: " + v.getPuntuacion() + "/5");
            System.out.println("Comentario: " + v.getComentario());
            System.out.println("Fecha: " + v.getFechaValoracion());
            System.out.println("------------------------");
        }
    }

    private static void actualizarValoracion() {
        System.out.println("\n=== ACTUALIZAR VALORACIÓN ===");
        consultarValoraciones();

        if (valoracionDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID de la valoración a actualizar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Valoracion valoracion = valoracionDAO.get(id);

            if (valoracion == null) {
                System.out.println("Valoración no encontrada.");
                return;
            }

            System.out.println("Datos actuales:");
            System.out.println("Puntuación: " + valoracion.getPuntuacion());
            System.out.println("Comentario: " + valoracion.getComentario());

            System.out.print("Nueva puntuación (1-5) o Enter para mantener actual: ");
            String puntuacionStr = scanner.nextLine().trim();
            if (!puntuacionStr.isEmpty()) {
                try {
                    int nuevaPuntuacion = Integer.parseInt(puntuacionStr);
                    if (nuevaPuntuacion >= 1 && nuevaPuntuacion <= 5) {
                        valoracion.setPuntuacion(nuevaPuntuacion);
                    } else {
                        System.out.println("Puntuación no válida, se mantiene la actual.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Puntuación no válida, se mantiene la actual.");
                }
            }

            System.out.print("Nuevo comentario o Enter para mantener actual: ");
            String nuevoComentario = scanner.nextLine().trim();
            if (!nuevoComentario.isEmpty()) {
                valoracion.setComentario(nuevoComentario);
            }

            valoracionDAO.update(valoracion);
            System.out.println("Valoración actualizada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    private static void eliminarValoracion() {
        System.out.println("\n=== ELIMINAR VALORACIÓN ===");
        consultarValoraciones();

        if (valoracionDAO.getAll().isEmpty()) {
            return;
        }

        System.out.print("Ingrese el ID de la valoración a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Valoracion valoracion = valoracionDAO.get(id);

            if (valoracion == null) {
                System.out.println("Valoración no encontrada.");
                return;
            }

            System.out.println("¿Está seguro de eliminar la valoración de " +
                    valoracion.getVisitante().getNombre() +
                    " para " + valoracion.getExposicion().getTitulo() + "? (s/n)");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                valoracionDAO.delete(valoracion);
                System.out.println("Valoración eliminada correctamente.");
            } else {
                System.out.println("Operación cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("ID no válido.");
        }
    }

    // MÉTODOS AUXILIARES

    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void verLogsVisita() {
        System.out.println("\n LOGS DE VISITA ");
        try {
            LogsTexto.readLogs();
        } catch (IOException e) {
            System.out.println("Error al leer los logs: " + e.getMessage());
        }
    }

    private static void exportarExposicionesABinario() {
        System.out.println("\n EXPORTAR EXPOSICIONES A ARCHIVO BINARIO ");
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
        System.out.println("\nIMPORTAR EXPOSICIONES DESDE ARCHIVO BINARIO ");
        System.out.println("Exposiciones disponibles en el archivo binario:");
        exposicionesBinario.showExposiciones();
    }
}