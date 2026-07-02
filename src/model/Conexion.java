package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conexion;

    public Connection getConection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/bd_lifedoccare", "root", "");
            JOptionPane.showMessageDialog(null, "Conectado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Base de datos apagada" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return conexion;
    }
}
