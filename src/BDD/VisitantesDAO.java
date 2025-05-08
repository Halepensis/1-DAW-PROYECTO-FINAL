package src.BDD;

import src.Classes.Visitante;

import java.sql.*;

public class VisitantesDAO extends MuseoConnection implements BdInterface<Visitante> {

    public void create(Visitante visitante) {
        String sql = "INSERT INTO Visitantes (nombre,edad,email)" +
                "VALUES (?,?,?)";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1, visitante.getNombre());
            sentencia.setInt(2, visitante.getEdad());
            sentencia.setString(3, visitante.getEmail());
            sentencia.executeUpdate();
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }

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


    public void readAll() {
        String sql = "Select * FROM Visitantes";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getString("nombre") + " "
                                + rs.getString("email") + " "
                                + rs.getInt("edad"));
            }
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ;
        }

    }

    public void update(Visitante visitante) {

    }

    public void delete(Visitante visitante) {
        String sql = "Delete  FROM Visitantes where email=?";
        try {
            con = conectar();
            sentencia = con.prepareStatement(sql);
            sentencia.setString(1, visitante.getEmail());
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
