package model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conexion;
    String url, user, password;
    
    public Connection getConection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString() ,"Base de datos apagada"+e.getMessage(),JOptionPane.ERROR_MESSAGE);
        }
        return conexion;
    }
}
