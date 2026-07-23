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
public class CategoriaMedicamentoDao implements Crud<CategoriaMedicamento>{
    Conexion conectar = new Conexion();
    
    @Override
    public List<CategoriaMedicamento> listar(){
        List<CategoriaMedicamento> lista = new ArrayList<>();
        String sql =  "SELECT id_categoria, nombre_categoria FROM categoria_medicamento ORDER BY nombre_categoria";
        try(Connection con = conectar.getConection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                lista.add(new CategoriaMedicamento(rs.getInt("id_categoria"), rs.getString("nombre_categoria")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error de consulta de categorias", JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    @Override
    public int setAgregar(CategoriaMedicamento t) {
        return 0; 
    }

    @Override
    public int setActualizar(CategoriaMedicamento t) {
        return 0; 
    }

    @Override
    public int setEliminar(int id) {
        return 0; 
    }
}
