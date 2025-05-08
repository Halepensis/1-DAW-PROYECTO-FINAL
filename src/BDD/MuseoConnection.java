package src.BDD;

import java.sql.*;

abstract public class MuseoConnection {
    protected PreparedStatement sentencia;
    private  final String url = "jdbc:mysql://localhost:3306/MUSEO";
    private  final String user = "root";
    private  final String password = "NuevaContraseñaSegura123!";
    protected  Connection con;

    protected  Connection conectar() {
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }

}
