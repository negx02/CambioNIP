package Conexion_;

import java.sql.*;

public class LogicaCambioNIP {

    // Cambia los datos según tu BD
    static String url = "jdbc:mysql://localhost:3306/registrosclientes";
    static String user = "root";
    static String password = "Guaymas";

    public static Connection conectar() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static boolean verificarLogin(String numeroTarjeta, String nip) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE NumeroTarjeta = ? AND NipTarjeta = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroTarjeta);
            ps.setString(2, nip);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String obtenerNombre(String numeroTarjeta) {
        String sql = "SELECT Nombre FROM usuarios WHERE NumeroTarjeta = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroTarjeta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String obtenerCorreo(String numeroTarjeta) {
        String sql = "SELECT Correo FROM usuarios WHERE NumeroTarjeta = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroTarjeta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Correo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean actualizarNIP(String numeroTarjeta, String nuevoNIP) {
        String sql = "UPDATE usuarios SET NipTarjeta = ? WHERE NumeroTarjeta = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNIP);
            ps.setString(2, numeroTarjeta);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
