package src.Classes;

import java.util.Objects;

public class Valoracion {
    private Visitante visitante;
    private Exposicion exposicion;
    private String comentario;
    private int maxPuntuacion=5;
    private int minPuntuacion=0;
    private int puntuacion;

    public Valoracion(Visitante visitante, Exposicion exposicion,String comentario, int puntuacion){
        this.visitante = visitante;
        this.exposicion = exposicion;
        this.comentario = comentario;
        this.puntuacion = rangePuntuacion(puntuacion);

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
        return Objects.equals(visitante, that.visitante) && Objects.equals(exposicion,that.exposicion);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(visitante);
    }
}
