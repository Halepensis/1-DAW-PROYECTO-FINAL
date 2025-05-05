package src.Classes;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Exposicion {
    private String titulo,descripcion;
    private TipoExposicion tipo;
    private LocalDateTime fechaCreacion;

    public Exposicion(String titulo, String descripcion, TipoExposicion tipo){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        fechaCreacion = LocalDateTime.now();
    }
    //Getters
    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public TipoExposicion getTipo() {
        return tipo;
    }
    public String getFechaCreacion() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return fechaCreacion.format(formatter);
    }

    @Override
    public String toString() {
        return "titulo='" + titulo + '\'' +
                ", descripcion:'" + descripcion + '\'' +
                ", tipo:" + tipo +
                ", fechaCreacion:" + fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exposicion that = (Exposicion) o;
        return Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(titulo);
    }
}


