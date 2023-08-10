package modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL implements AutoCloseable {
    private String database_name = "died_tp";
	private String user = "root";
    private String password = "root";
    private String url = "jdbc:mysql://localhost:8800/" + database_name;
    //private String url = "jdbc:mysql://tp_died_mySQL/" + database_name;
    private Connection conn = null;
    

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Ha ocurrido un ClassNotFouendException " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Ha ocurrido un SQLException " + e.getMessage());
        }
        return conn;
    }

    @Override
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
