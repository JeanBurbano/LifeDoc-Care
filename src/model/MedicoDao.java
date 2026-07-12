package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements Crud<Medico> {

    public static Conexion conectar = new Conexion();

    @Override
    public List<Medico> listar() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.id_medico, m.id_usuario, "
                + "CONCAT(u.primer_nombre, ' ', u.primer_apellido) AS nombre_completo, "
                + "e.id_especialidad, e.nombre_especialidad "
                + "FROM medico m "
                + "JOIN usuario u ON u.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "WHERE u.estado = 1 "
                + "ORDER BY nombre_completo";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                medicos.add(new Medico(
                        rs.getInt("id_medico"),
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_completo"),
                        rs.getInt("id_especialidad"),
                        rs.getString("nombre_especialidad")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos;
    }

    @Override
    public int setAgregar(Medico tr) {
        String sql = "INSERT INTO medico (id_usuario, id_especialidad) VALUES (?, ?)";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tr.getIdUsuario());
            ps.setInt(2, tr.getIdEspecialidad());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setActualizar(Medico tr) {
        String sql = "UPDATE medico SET id_especialidad = ? WHERE id_medico = ?";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tr.getIdEspecialidad());
            ps.setInt(2, tr.getIdMedico());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setEliminar(int id) {
        // no se borra el medico (rompe historial_medico / horario_medico):
        // se deshabilita el usuario asociado a ese medico.
        String sql = "UPDATE usuario u "
                + "JOIN medico m ON m.id_usuario = u.id_usuario "
                + "SET u.estado = 0 "
                + "WHERE m.id_medico = ?";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}