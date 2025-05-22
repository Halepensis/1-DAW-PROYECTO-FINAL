package src.BDD;

import src.Classes.Exposicion;
import src.Classes.TipoExposicion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Implementación del DAO para la entidad Exposicion
 * Gestiona la persistencia en base de datos de las exposiciones
 */
public class ExposicionesDAO extends MuseoConnection implements BdInterface<Exposicion> {

    @Override
    public void create(Exposicion exposicion) {
        String sql = "Insert into Exposiciones (titulo,tipo,descripcion,fechaCreacion) VALUES (?,?,?,?)";
        String sqlSelect = "SELECT id FROM Exposiciones WHERE titulo = ?";
        con = conectar();
        try {
            // Primero verificamos si ya existe una exposición con el mismo título
            // para evitar duplicados en la base de datos
            sentencia = con.prepareStatement(sqlSelect);
            sentencia.setString(1,exposicion.getTitulo());
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                // Si ya existe, simplemente asignamos el ID existente al objeto
                exposicion.setId(rs.getInt("id"));
                System.out.println("La exposicion ya existe");
                rs.close();
                return;
            }
            //En caso de no estar repetido lo introducimos en la tabla
            // RETURN_GENERATED_KEYS es necesario para obtener el ID autogenerado
            sentencia = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, exposicion.getTitulo());
            sentencia.setString(2, exposicion.getTipo().name());
            sentencia.setString(3, exposicion.getDescripcion());
            sentencia.setTimestamp(4, exposicion.getFechaCreacion());
            sentencia.executeUpdate();
            rs = sentencia.getGeneratedKeys();
            if (rs.next()) {
                int idGenearado = rs.getInt(1);
                exposicion.setId(idGenearado);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                if(sentencia!=null)sentencia.close();
               if(con!=null) con.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }


    }


    @Override
    public Exposicion get(int id) {
        String sql = "Select * From Exposiciones where id=?";
        con = conectar();
        ResultSet rs=null;
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            rs = sentencia.executeQuery();
            if (rs.next()) {
                int idBD = rs.getInt("id");
                String titulo = rs.getString("titulo");
                TipoExposicion tipo = TipoExposicion.valueOf(rs.getString("tipo"));
                String descripcion = rs.getString("descripcion");
                Timestamp timestamp = rs.getTimestamp("fechaCreacion");
                LocalDateTime fechaCreacion = timestamp.toLocalDateTime();
                return new Exposicion(idBD, titulo, descripcion, tipo, fechaCreacion);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(rs!=null) rs.close();
                if(sentencia!=null)sentencia.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                ;
            }
        }

        return null;
    }

    @Override
    public ArrayList<Exposicion> getAll() {
        String sql = "Select * FROM Exposiciones";
        ArrayList<Exposicion> lista = new ArrayList<>();
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Exposicion exposicion = this.get(rs.getInt("id"));
                lista.add(exposicion);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(sentencia!=null)sentencia.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }

    @Override
    public void update(Exposicion exposicion) {
        String sql = "UPDATE Exposiciones SET titulo=?, tipo=?, descripcion=? WHERE id=?";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1, exposicion.getTitulo());
            sentencia.setString(2, exposicion.getTipo().name());
            sentencia.setString(3, exposicion.getDescripcion());
            sentencia.setInt(4,exposicion.getId());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(sentencia!=null)sentencia.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void delete(Exposicion exposicion) {
        String sql = "DELETE FROM Exposiciones where id=?";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, exposicion.getId());
            sentencia.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(sentencia!=null)sentencia.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }

    public void delete(int id) {
        String sql = "DELETE FROM Exposiciones where id=?";
        con = conectar();
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(sentencia!=null)sentencia.close();
                if(con!=null) con.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }

    }


}



