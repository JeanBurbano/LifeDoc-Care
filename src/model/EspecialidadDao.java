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
public class EspecialidadDao implements Crud<Especialidad>{
    Conexion conectar = new Conexion();
    
    @Override
    public List<Especialidad> listar(){
        List<Especialidad> lista = new ArrayList<>();
        String sql = "SELECT id_especialidad, nombre_especialidad FROM especialidad ORDER BY nombre_especialidad";
        try(Connection con = conectar.getConection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                lista.add(new Especialidad(rs.getInt("id_especialidad"), rs.getString("nombre_especialidad")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error de consulta de la especialidad", JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    @Override
    public int setAgregar(Especialidad t) {
        return 0; 
    }

    @Override
    public int setActualizar(Especialidad t) {
        return 0; 
    }

    @Override
    public int setEliminar(int id) {
        return 0; 
    }
}
