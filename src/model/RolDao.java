/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lunaa
 */
public class RolDao implements Crud<Rol>{
    
    Conexion conectar = new Conexion();
    
    @Override
    public List<Rol> listar(){
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT id_rol, nombre_rol FROM rol WHERE id_rol IN (2, 3, 4) ORDER BY id_rol";
        try (Connection con = conectar.getConection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre_rol")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de consulta del rol", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }
    
    @Override
    public int setAgregar(Rol t) {
        return 0; 
    }

    @Override
    public int setActualizar(Rol t) {
        return 0; 
    }

    @Override
    public int setEliminar(int id) {
        return 0; 
    }
}
