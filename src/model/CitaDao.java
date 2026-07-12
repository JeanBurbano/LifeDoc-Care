package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class CitaDao implements Crud<Cita> {
    // setAgregar devuelve esto cuando el medico ya tiene otra cita en esa fecha/hora.
    public static final int CONFLICTO_HORARIO = -1;

    public static Conexion conectar = new Conexion();

    @Override
    public List<Cita> listar() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, CONCAT(up.primer_nombre, ' ', up.primer_apellido) AS nombre_paciente, "
                + "c.id_Medico, CONCAT(um.primer_nombre, ' ', um.primer_apellido) AS nombre_medico, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "ORDER BY c.fecha_cita, c.hora_cita";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                citas.add(new Cita(
                        rs.getInt("id_cita"),
                        rs.getString("estado"),
                        rs.getTime("hora_cita").toLocalTime(),
                        rs.getDate("fecha_cita").toLocalDate(),
                        rs.getInt("id_Usuario"),
                        rs.getString("nombre_paciente"),
                        rs.getInt("id_Medico"),
                        rs.getString("nombre_medico"),
                        rs.getInt("id_usuario_agenda")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    @Override
    public int setAgregar(Cita tr) {
        String sql = "INSERT INTO cita (hora_cita, fecha_cita, id_Usuario, id_Medico, id_usuario_agenda) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql)) {
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
        String sql = "UPDATE cita SET estado = ? WHERE id_cita = ?";
        try (Connection con = conectar.getConection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tr.getEstado());
            ps.setInt(2, tr.getIdCita());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setEliminar(int id) {
        // no se borra la cita: queda en la BD como 'Cancelada' para mantener
        // el historial (y para que los reportes del admin del centro cuadren).
        String sql = "UPDATE cita SET estado = 'Cancelada' WHERE id_cita = ?";
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