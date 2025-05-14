package src.BDD;

import src.Classes.Exposicion;
import src.Classes.TipoExposicion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ExposicionesDAO extends MuseoConnection implements BdInterface<Exposicion> {

    @Override
    public void create(Exposicion exposicion) {
        String sql = "Insert into Exposiciones (titulo,tipo,descripcion,fechaCreacion) (?,?,?,?)";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1, exposicion.getTitulo());
            sentencia.setString(2, exposicion.getTipo().name());
            sentencia.setString(3, exposicion.getDescripcion());
            sentencia.setTimestamp(4, exposicion.getFechaCreacion());
            sentencia.executeUpdate();
            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs.next()){
                int idGenearado = rs.getInt("id");
                exposicion.setId(idGenearado);
            }
            rs.close();
            sentencia.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
    //TODO Ver como manejar lo que devuleve si una Exposicion entera o no
    @Override
    public Exposicion get(int id) {
        String sql = "Select From Exposiciones where id=?";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                int idBD = rs.getInt("id");
                String titulo = rs.getString("titulo");
                TipoExposicion tipo = TipoExposicion.valueOf(rs.getString("tipo"));
                String descripcion = rs.getString("descripcion");
                Timestamp timestamp = rs.getTimestamp("fechaCreacion");
                LocalDateTime fechaCreacion = timestamp.toLocalDateTime();

                return new Exposicion(idBD,titulo, descripcion, tipo, fechaCreacion);
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
    public void readAll() {
        String sql = "Select * from Exposiciones";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                TipoExposicion tipo = TipoExposicion.valueOf(rs.getString("tipo"));
                String descripcion = rs.getString("descripcion");
                Timestamp timestamp = rs.getTimestamp("fechaCreacion");
                LocalDateTime fechaCreacion = timestamp.toLocalDateTime();
                System.out.printf("ID: %d Exposicion: %s tipo: %s descripcion: %s fecha: %s\n",
                        id,titulo, tipo, descripcion, fechaCreacion);

            }
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                sentencia.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());;
            }
        }

    }
    @Override
    public void update(Exposicion exposicion) {
        String sql = "UPDATE Exposiciones SET titulo=? tipo=? descripcion=?";
        con = conectar();
        try{
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1,exposicion.getTitulo());
            sentencia.setString(2,exposicion.getTipo().name());
            sentencia.setString(3,exposicion.getDescripcion());
            sentencia.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                sentencia.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());;
            }
        }

    }

    @Override
    public void delete(Exposicion exposicion) {
        String sql = "DELETE FROM Exposicion where id=?";
        con = conectar();
        try{
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1,exposicion.getId());
            sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                sentencia.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }
    public void delete(int id) {
        String sql = "DELETE FROM Exposicion where id=?";
        con = conectar();
        try{
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1,id);
            sentencia.executeUpdate();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                sentencia.close();
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }


}



