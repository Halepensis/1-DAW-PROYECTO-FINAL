package src.Classes;

import java.util.Objects;

public class Valoracion {
    Visitante visitante;
    Exposicion exposicion;
    int maxPuntuacion=5;
    int minPuntuacion=0;
    int puntuacion;

    public Valoracion(Visitante visitante, Exposicion exposicion, int puntuacion){
        this.visitante = visitante;
        this.exposicion = exposicion;
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
        return "visitante= " + visitante.getNombre()+
                ", exposicion= " + exposicion.getTitulo()+
                ", puntuacion= " + puntuacion;
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
