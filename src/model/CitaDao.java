package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CitaDao implements Crud<Cita> {

    public CitaDao() {

    }
    // setAgregar devuelve esto cuando el medico ya tiene otra cita en esa fecha/hora.
    public static final int CONFLICTO_HORARIO = -1;

    public static Conexion conectar = new Conexion();

    public Cita[] listarPorUsuario(int idUsuario) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, up.primer_nombre, up.primer_apellido, "
                + "c.id_Medico, um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "WHERE c.id_Usuario = ? AND c.estado = 1 "
                + "ORDER BY c.fecha_cita, c.hora_cita";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombrePaciente = rs.getString("primer_nombre") + " " + rs.getString("primer_apellido");
                    String nombreMedico = rs.getString("medico_nombre") + " " + rs.getString("medico_apellido");
                    citas.add(new Cita(
                            rs.getByte("id_cita"),
                            rs.getBoolean("estado"),
                            rs.getTime("hora_cita").toLocalTime(),
                            rs.getDate("fecha_cita").toLocalDate(),
                            rs.getByte("id_Usuario"),
                            nombrePaciente,
                            rs.getByte("id_Medico"),
                            nombreMedico,
                            rs.getByte("id_usuario_agenda")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas.toArray(new Cita[0]);
    }

    @Override
    public List<Cita> listar() {
        List<Cita> citas = new ArrayList<>();
        return citas;
    }

    @Override
    public int setAgregar(Cita tr) {
        String sql = "INSERT INTO cita (hora_cita, fecha_cita, id_Usuario, id_Medico, id_usuario_agenda) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, tr.getHoraCita());
            ps.setObject(2, tr.getFechaCita());
            ps.setInt(3, tr.getIdUsuario());
            ps.setInt(4, tr.getIdMedico());
            ps.setInt(5, tr.getIdUsuarioAgenda());
            return ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            // dispara la restriccion UNIQUE (id_Medico, fecha_cita, hora_cita):
            // ese medico ya tiene otra cita en ese mismo horario.
            return CONFLICTO_HORARIO;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setActualizar(Cita tr) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        // no se borra la cita: queda en la BD como 'Cancelada' para mantener
        // el historial (y para que los reportes del admin del centro cuadren).
        String sql = "UPDATE cita SET estado = 'Cancelada' WHERE id_cita = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
