package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements Crud<Medico> {

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Medico[] listarPorEspecialidad(int idEspecialidad) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.id_medico, u.primer_nombre, u.primer_apellido "
                + "FROM medico m "
                + "JOIN usuario u ON u.id_usuario = m.id_usuario "
                + "WHERE m.id_especialidad = ? AND u.estado = 1 "
                + "ORDER BY u.primer_apellido, u.primer_nombre";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEspecialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medicos.add(new Medico(
                            rs.getInt("id_medico"),
                            rs.getString("primer_nombre"),
                            rs.getString("primer_apellido")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicos.toArray(new Medico[0]);
    }

    @Override
    public List<Medico> listar() {
        List<Medico> medicos = new ArrayList<>();
        return medicos;
    }

    @Override
    public int setAgregar(Medico tr) {
        String sql = "INSERT INTO medico (id_usuario, id_especialidad) VALUES (?, ?)";
        return 0;
    }

    @Override
    public int setActualizar(Medico tr) {
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
