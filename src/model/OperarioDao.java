package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperarioDao implements Crud<Operario>{
    
    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
            
    public List<Operario> listarOp() {
        List<Operario> operarios = new ArrayList<>();
        String sql = "SELECT o.id_operario, u.primer_nombre, u.primer_apellido "
                   + "FROM operario o "
                   + "JOIN usuario u ON u.id_usuario = o.id_usuario "
                   + "WHERE u.estado = 1 "
                   + "ORDER BY u.primer_apellido, u.primer_nombre";
        
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operarios.add(new Operario(
                        rs.getInt("id_operario"),
                        rs.getString("primer_nombre"),
                        rs.getString("primer_apellido")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operarios;
    }
    
    @Override
    public List<Operario> listar() {
        List<Operario> operarios = new ArrayList<>();
        String sql = "SELECT o.id_operario, u.primer_nombre, u.primer_apellido "
                   + "FROM operario o "
                   + "JOIN usuario u ON u.id_usuario = o.id_usuario "
                   + "WHERE u.estado = 1 "  
                   + "ORDER BY u.primer_apellido, u.primer_nombre";
        
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    operarios.add(new Operario(
                        rs.getInt("id_operario"),
                        rs.getString("primer_nombre"),
                        rs.getString("primer_apellido")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operarios;
    }

    @Override
    public int setAgregar(Operario tr) {
        String sql = "INSERT INTO operario (id_usuario) VALUES (?)";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, tr.getId_usuario());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int setActualizar(Operario tr) {
    
        return 0;
    }

    @Override
    public int setEliminar(int id) {

        String sql = "UPDATE usuario u "
                   + "JOIN operario o ON o.id_usuario = u.id_usuario "
                   + "SET u.estado = 0 "
                   + "WHERE o.id_operario = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
