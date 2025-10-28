package resol.DacundaS.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL  = "jdbc:postgresql://localhost:5432/tutifruti";
    private static final String USER = "postgres";
    private static final String PASS = "adminICOP2025";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static Connection obtener() throws SQLException {
        return obtenerConexion();
    }
}