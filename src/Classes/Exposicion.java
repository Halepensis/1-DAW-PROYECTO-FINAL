package src.Classes;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class Exposicion {
    private int id;
    private String titulo, descripcion;
    private TipoExposicion tipo;
    private LocalDateTime fechaCreacion;


    public Exposicion(String titulo, String descripcion, TipoExposicion tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        fechaCreacion = LocalDateTime.now().withNano(0);
    }

    //Constructor cuando recuperamos la exposicion de la base de datos
    public Exposicion(int id, String titulo, String descripcion, TipoExposicion tipo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
    }
    //Getters

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoExposicion getTipo() {
        return tipo;
    }

    public Timestamp getFechaCreacion() {
        return Timestamp.valueOf(fechaCreacion);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipo(TipoExposicion tipo) {
        this.tipo = tipo;
    }

    public String getFechaCreacionFormated() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return formatter.format(fechaCreacion);

    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                "titulo='" + titulo + '\'' +
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


