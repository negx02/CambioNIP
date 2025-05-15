package Conexion_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Conexión con la base de datos "registrosclientes"
    static String url = "jdbc:mysql://localhost:3306/registrosclientes";
    static String user = "root";
    static String password = "Guaymas";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexion exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
        return con;
    }
}

