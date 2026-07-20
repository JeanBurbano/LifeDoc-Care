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
import java.sql.SQLException;
/**
 *
 * @author lunaa
 */
public class MedicosDao implements Crud<Medicos>{
    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    @Override
    public List<Medicos> listar() {
        List<Medicos> medico = new ArrayList<Medicos>();
        String sql = "SELECT m.id_medico, u.numero_identificacion, e.nombre_especialidad, u.primer_nombre,"
                + " u.primer_apellido FROM medico m JOIN usuario u ON u.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "WHERE u.estado = 1 ORDER BY m.id_medico;";
        try{
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Medicos m = new Medicos();
                m.setId_medico(rs.getInt(1));
                m.setNumeroId(rs.getString(2));
                m.setEspecialidad(rs.getString(3));
                m.setPrimerNombre(rs.getString(4));
                m.setPrimerApellido(rs.getString(5));
                medico.add(m);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error en la consulta" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE
            );
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
        return medico;
    }
    
    @Override
    public int setAgregar(Medicos tr) {
        String sql = "INSERT INTO medico (id_usuario, id_especialidad) VALUES (?, ?)";
        return 0;
    }

    @Override
    public int setActualizar(Medicos tr) {
        String sql = "UPDATE medico SET id_especialidad = ? WHERE id_medico = ?";
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        // no se borra el medico (rompe historial_medico / horario_medico):
        // se deshabilita el usuario asociado a ese medico.
        String sql = "UPDATE usuario u "
                + "JOIN medico m ON m.id_usuario = u.id_usuario "
                + "SET u.estado = 0 "
                + "WHERE m.id_medico = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
