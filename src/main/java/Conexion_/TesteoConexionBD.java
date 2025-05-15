package Conexion_;



public class TesteoConexionBD {

    public static void main(String[] args) {
        
        //Prueba de conexion con la base de datos
        Conexion dbc = new Conexion();
        dbc.conectar();

    }
    
}
