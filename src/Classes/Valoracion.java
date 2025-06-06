package src.Classes;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Valoracion implements Serializable {
    private int id;
    private Visitante visitante;
    private Exposicion exposicion;
    private String comentario;
    private int maxPuntuacion=5;
    private int minPuntuacion=0;
    private int puntuacion;
    private LocalDateTime fechaValoracion;

    //Al crearlo
    public Valoracion(Visitante visitante, Exposicion exposicion,String comentario, int puntuacion){
        this.visitante = visitante;
        this.exposicion = exposicion;
        this.comentario = comentario;
        this.puntuacion = rangePuntuacion(puntuacion);
        fechaValoracion = LocalDateTime.now().withNano(0);

    }
    //Cuando se recupera de la BD
    public Valoracion(int id,Visitante visitante, Exposicion exposicion,String comentario, int puntuacion,LocalDateTime fechaValoracion){
        this.id = id;
        this.visitante = visitante;
        this.exposicion = exposicion;
        this.comentario = comentario;
        this.puntuacion = rangePuntuacion(puntuacion);
        this.fechaValoracion = fechaValoracion;

    }
    //Getters

    public int getId() {
        return id;
    }

    public Visitante getVisitante() {
        return visitante;
    }
    public Exposicion getExposicion() {
        return exposicion;
    }
    public String getComentario() {
        return comentario;
    }
    public int getPuntuacion() {
        return puntuacion;
    }
    public Timestamp getFechaValoracion() {
        return Timestamp.valueOf(fechaValoracion);
    }

    //Setters

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int rangePuntuacion(int puntuacion) {
        if (puntuacion >=maxPuntuacion){
            return maxPuntuacion;
        } else if (puntuacion<=minPuntuacion) {
            return minPuntuacion;
        }
        return puntuacion;



     }


    @Override
    public String toString() {
        return "visitante: " + visitante.getNombre()+
                ", exposicion: " + exposicion.getTitulo()+
                ", puntuacion: " + puntuacion +
                ", comentario: "+comentario;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Valoracion that = (Valoracion) o;
        // La igualdad se define por visitante y exposición,
        // lo que significa que un visitante solo puede valorar una exposición una vez
        return Objects.equals(visitante, that.visitante) && Objects.equals(exposicion,that.exposicion);

    }

    @Override
    public int hashCode() {
        return Objects.hash(visitante,exposicion);
    }
}
