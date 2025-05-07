package src.BDD;

import src.Classes.Visitante;

import java.sql.*;

public class VisitantesDAO {
    static String url = "jdbc:mysql://localhost:3306/MUSEO";
    static String user = "root";
    static String password = "NuevaContraseñaSegura123!";
    static Connection con;
    private static Connection conectar(){
        try{
            con = DriverManager.getConnection(url,user,password);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return con;
    }

    public static void addVisitante(Visitante visitante){
        PreparedStatement sentencia;
        String sql = "INSERT INTO Visitantes (nombre,edad,email)" +
                "VALUES (?,?,?)";
        try{
             con = conectar();
             sentencia = con.prepareStatement(sql);
             sentencia.setString(1,visitante.getNombre());
             sentencia.setInt(2,visitante.getEdad());
             sentencia.setString(3,visitante.getEmail());
             sentencia.executeUpdate();
             sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

    }
    public static void leerVisitantes(){
        PreparedStatement sentencia;
        String sql = "Select * FROM Visitantes";
        try{
            con = conectar();
            sentencia = con.prepareStatement(sql);
            ResultSet rs = sentencia.executeQuery();
            while (rs.next()){
                System.out.println(
                        rs.getString("nombre")+" "
                                +rs.getString("email")+" "
                                +rs.getInt("edad"));
            }
            sentencia.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

    }



}
