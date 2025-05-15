package src.BDD;
import src.Classes.Exposicion;
import src.Classes.Valoracion;
import src.Classes.Visitante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ValoracionDAO extends MuseoConnection implements BdInterface<Valoracion> {

    @Override
    public void create(Valoracion valoracion) {
        String sql = "INSERT INTO Valoraciones (visitanteId,exposicionId,nota,comentario,fechaValoracion)" +
                " VALUES (?,?,?,?,?)";

        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1,valoracion.getVisitante().getId());
            sentencia.setInt(2,valoracion.getExposicion().getId());
            sentencia.setInt(3,valoracion.getPuntuacion());
            sentencia.setString(4,valoracion.getComentario());
            sentencia.setTimestamp(5,valoracion.getFechaValoracion());
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs.next()){
                int idGenerado = rs.getInt("id");
                valoracion.setId(idGenerado);
            }
            rs.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try{
                sentencia.close();
                con.close();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());

            }
        }
    }

    @Override
    public Valoracion get(int id) {
        String sql = "Select From Valoraciones where id=?";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                int idBD = rs.getInt("id");
                int idVisitante = rs.getInt("visitanteId");
                int idExposicon = rs.getInt("exposicionId");
                int nota = rs.getInt("nota");
                String comentario = rs.getString("comentario");
                Timestamp timestamp = rs.getTimestamp("fechaValoracion");
                LocalDateTime fechaValoracion = timestamp.toLocalDateTime();
                Visitante visitante = new VisitantesDAO().get(idVisitante);
                Exposicion exposicion = new ExposicionesDAO().get(idExposicon);
                return new Valoracion(idBD,visitante,exposicion,comentario,nota,fechaValoracion);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                sentencia.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Valoracion> getAll() {
        String sql = "Select * FROM Valoraciones";
        ArrayList<Valoracion> lista = new ArrayList<>();
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Valoracion visitante = this.get(rs.getInt("id"));
                lista.add(visitante);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }finally {
            try{
                sentencia.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }


    @Override
    public void update(Valoracion objeto) {

    }

    @Override
    public void delete(Valoracion objeto) {

    }
}
