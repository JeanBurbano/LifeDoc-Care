/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author lunaa
 */
public class ConsultorioDao implements Crud<Consultorio>{
    public Conexion conectar = new Conexion();
    java.sql.Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
     @Override
    public List<Consultorio> listar() {
        List<Consultorio> consultorios = new ArrayList<>();
        String sql = "SELECT id_consultorio, numero_consultorio FROM consultorio ORDER BY numero_consultorio";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                consultorios.add(new Consultorio(
                        rs.getInt("id_consultorio"),
                        rs.getInt("numero_consultorio")
                ));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error de consulta" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (con != null) {
                try {
                    con.close();
                    ps.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString());
                }
            }
        }
        return consultorios;
    }
    
    @Override
    public int setAgregar(Consultorio tr) {
        return 0;
    }

    @Override
    public int setActualizar(Consultorio tr) {
        return 0;
    }
    
    @Override
    public int setEliminar(int Id) {
        return 0;
    }
    
}
