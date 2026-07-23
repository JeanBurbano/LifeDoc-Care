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
public class TipoMedicamentoDao implements Crud<TipoMedicamento>{
    Conexion conectar = new Conexion();
    
    @Override
    public List<TipoMedicamento> listar(){
        List<TipoMedicamento> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_medicamento, nombre_tipo_medicamento FROM tipo_medicamento ORDER BY nombre_tipo_medicamento";
        try(Connection con = conectar.getConection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                lista.add(new TipoMedicamento(rs.getInt("id_tipo_medicamento"), rs.getString("nombre_tipo_medicamento")));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error de consulta del tipo de medicamento", JOptionPane.ERROR_MESSAGE);
        }
        
        return lista;
    }
    
    @Override
    public int setAgregar(TipoMedicamento t) {
        return 0; 
    }

    @Override
    public int setActualizar(TipoMedicamento t) {
        return 0; 
    }

    @Override
    public int setEliminar(int id) {
        return 0; 
    }
}
