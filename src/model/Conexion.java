package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection conexion;

    public Connection getConection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/bd_lifedoccare", "root", "");
            System.out.println("Base de datos conectada correctamente desde la clase Conexion");
        } catch (Exception e) {
            System.out.println("Base de datos apagada\n"+e.toString());
        }
        return conexion;
    }
}
