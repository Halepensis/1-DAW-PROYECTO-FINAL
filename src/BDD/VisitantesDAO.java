package src.BDD;

import src.Classes.Visitante;

import java.sql.*;
import java.util.ArrayList;

public class VisitantesDAO extends MuseoConnection implements BdInterface<Visitante> {

    @Override
    public void create(Visitante visitante) {
        String sql = "INSERT INTO Visitantes (nombre,edad,email)" +
                "VALUES (?,?,?)";
        String sqlSelect = "SELECT id FROM Visitantes WHERE email = ?";
        con = conectar();
        try {
            //Miramos si tiene el mismo email, en caso de tenerlo asignamos id y salimos
            sentencia = con.prepareStatement(sqlSelect);
            sentencia.setString(1,visitante.getEmail());
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                visitante.setId(rs.getInt("id"));
                System.out.println("El visitante ya existe");
                rs.close();
                return;
            }
            //En caso de no estar repetido lo introducimos en la tabla
            sentencia = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, visitante.getNombre());
            sentencia.setInt(2, visitante.getEdad());
            sentencia.setString(3, visitante.getEmail());
            sentencia.executeUpdate();
            sentencia.close();
            System.out.println("Visitante añadido correctamente.");
        } catch (Exception e) {
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
    public Visitante get(int id) {
        String sql = "Select * FROM Visitantes where id=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1,id);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()){
                return new Visitante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad")
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Visitante get(String email) {
        Visitante visitante = null;
        String sql = "Select * FROM Visitantes where email=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1,email);
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()){
                visitante = new Visitante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("edad")
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return visitante;
    }

    @Override
    public ArrayList<Visitante> getAll() {
        String sql = "Select * FROM Visitantes";
        ArrayList<Visitante> lista = new ArrayList<>();
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                Visitante visitante = this.get(rs.getInt("id"));
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
    public void update(Visitante visitante) {
        String sql = "UPDATE Visitantes SET nombre=?, edad=?, email=? WHERE id=?";
        try {
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1,visitante.getNombre());
            sentencia.setInt(2,visitante.getEdad());
            sentencia.setString(3,visitante.getEmail());
            sentencia.setInt(4,visitante.getId());
            sentencia.executeUpdate();
            sentencia.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    //Distintas maneras de eliminar
    @Override
    public void delete(Visitante visitante) {
        String sql = "Delete  FROM Visitantes where id=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, visitante.getId());
            sentencia.executeUpdate();
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }
    }
    public void delete(int id) {
        String sql = "Delete  FROM Visitantes where id=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }
    }
    public void delete(String email) {
        String sql = "Delete  FROM Visitantes where email=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1, email);
            sentencia.executeUpdate();
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }
    }


}
