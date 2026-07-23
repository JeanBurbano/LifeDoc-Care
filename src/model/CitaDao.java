package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDao implements Crud<Cita> {

    // setAgregar devuelve esto cuando el medico ya tiene otra cita en esa fecha,hora.
    public static final int CONFLICTO_HORARIO = -1;

    public Conexion conectar = new Conexion();

    public CitaDao() {

    }

    public int setReagendar(int idCita, int idMedico, LocalDate nuevaFecha, LocalTime nuevaHora) {
        String sql = "UPDATE cita SET fecha_cita = ?, hora_cita = ? WHERE id_cita = ? AND id_Medico = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nuevaFecha);
            ps.setObject(2, nuevaHora);
            ps.setInt(3, idCita);
            ps.setInt(4, idMedico);
            return ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return CONFLICTO_HORARIO;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<LocalTime> listarHorasOcupadas(int idMedico, LocalDate fecha) {
        List<LocalTime> horas = new ArrayList<>();
        String sql = "SELECT hora_cita FROM cita WHERE id_Medico = ? AND fecha_cita = ? AND estado = 1";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
            ps.setObject(2, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horas.add(rs.getTime("hora_cita").toLocalTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horas;
    }

    public Cita[] listarTodasPorUsuario(int idUsuario) {
        List<Cita> citas = new ArrayList<>();

        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, up.primer_nombre, up.primer_apellido, "
                + "c.id_Medico, um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido, "
                + "e.nombre_especialidad, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "WHERE c.id_Usuario = ? "
                + "ORDER BY c.fecha_cita DESC, c.hora_cita DESC";

        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombrePaciente = rs.getString("primer_nombre") + " "
                            + rs.getString("primer_apellido");

                    String nombreMedico = rs.getString("medico_nombre") + " "
                            + rs.getString("medico_apellido");

                    citas.add(new Cita(
                            rs.getByte("id_cita"),
                            rs.getBoolean("estado"),
                            rs.getTime("hora_cita").toLocalTime(),
                            rs.getDate("fecha_cita").toLocalDate(),
                            rs.getByte("id_Usuario"),
                            nombrePaciente,
                            rs.getByte("id_Medico"),
                            nombreMedico,
                            rs.getString("nombre_especialidad"),
                            rs.getByte("id_usuario_agenda")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas.toArray(new Cita[0]);
    }

    public Cita[] listarPorUsuario(int idUsuario) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, up.primer_nombre, up.primer_apellido, "
                + "c.id_Medico, um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido, "
                + "e.nombre_especialidad, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
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
                            rs.getString("nombre_especialidad"),
                            rs.getByte("id_usuario_agenda")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas.toArray(new Cita[0]);
    }

    public Cita[] listarPorMedico(int idMedico) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, up.primer_nombre, up.primer_apellido, "
                + "c.id_Medico, um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido, "
                + "e.nombre_especialidad, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "WHERE c.id_medico = ? AND c.estado = 1 "
                + "ORDER BY c.fecha_cita, c.hora_cita";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMedico);
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
                            rs.getString("nombre_especialidad"),
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
        String sql = "SELECT c.id_cita, c.estado, c.hora_cita, c.fecha_cita, "
                + "c.id_Usuario, up.primer_nombre, up.primer_apellido, "
                + "c.id_Medico, um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido, "
                + "e.nombre_especialidad, "
                + "c.id_usuario_agenda "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "ORDER BY c.fecha_cita DESC, c.hora_cita DESC";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
                            rs.getString("nombre_especialidad"),
                            rs.getByte("id_usuario_agenda")
                    ));
                }
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
    
    public int reagendar(int idCita, java.time.LocalDate nuevaFecha, java.time.LocalTime nuevaHora, int idConsultorio) {
        String sql = "UPDATE cita SET fecha_cita = ?, hora_cita = ?, id_consultorio = ? WHERE id_cita = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, nuevaFecha);
            ps.setObject(2, nuevaHora);
            ps.setInt(3, idConsultorio);
            ps.setInt(4, idCita);
            return ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
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
        String sql = "UPDATE cita SET estado = 0 WHERE id_cita = ?";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
