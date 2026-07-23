package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class HistorialMedicoDao implements Crud<HistorialMedico> {

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<HistorialMedico> listarId(String id) {
        List<HistorialMedico> historialM = new ArrayList<>();
        String sql = "SELECT h.id_historial_medico, h.id_cita, h.dia, h.hora, h.descripcion, "
                + "up.primer_nombre AS paciente_primer_nombre, up.segundo_nombre AS paciente_segundo_nombre, "
                + "up.primer_apellido AS paciente_primer_apellido, up.segundo_apellido AS paciente_segundo_apellido, "
                + "um.primer_nombre AS medico_primer_nombre, um.primer_apellido AS medico_primer_apellido "
                + "FROM historial_medico h "
                + "JOIN usuario up ON h.id_usuario = up.id_usuario "
                + "JOIN medico m ON h.id_medico = m.id_medico "
                + "JOIN usuario um ON m.id_usuario = um.id_usuario "
                + "WHERE up.numero_identificacion = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialMedico h = new HistorialMedico();
                h.setId_historial_medico(rs.getInt("id_historial_medico"));
                h.setId_cita(rs.getInt("id_cita"));
                h.setDia(rs.getDate("dia").toLocalDate());
                h.setHora(rs.getTime("hora").toLocalTime());
                h.setDescripcion(rs.getString("descripcion"));

                String nombrePaciente = rs.getString("paciente_primer_nombre") + " "
                        + rs.getString("paciente_primer_apellido");
                h.setNombrePaciente(nombrePaciente);

                String nombreMedico = rs.getString("medico_primer_nombre") + " "
                        + rs.getString("medico_primer_apellido");
                h.setNombreMedico(nombreMedico);

                historialM.add(h);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error en la consulta", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
        return historialM;
    }

    @Override
    public List<HistorialMedico> listar() {
        List<HistorialMedico> historialM = new ArrayList<HistorialMedico>();
        String sql = "SELECT id_historial_medico, id_usuario, id_medico, id_cita, dia, hora, descripcion "
                + "FROM historial_medico";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                HistorialMedico h = new HistorialMedico();
                h.setId_historial_medico(rs.getInt("id_historial_medico"));
                h.setId_usuario(rs.getInt("id_usuario"));
                h.setId_medico(rs.getInt("id_medico"));
                h.setId_cita(rs.getInt("id_cita"));
                h.setDia(rs.getDate("dia").toLocalDate());
                h.setHora(rs.getTime("hora").toLocalTime());
                h.setDescripcion(rs.getString("descripcion"));
                historialM.add(h);
            }
        } catch (Exception e) {
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
        return historialM;
    }

    public boolean agregar(HistorialMedico h) {
        boolean resultado = false;
        String sql = "INSERT INTO historial_medico (id_usuario, id_medico, id_cita, dia, hora, descripcion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, h.getId_usuario());
            ps.setInt(2, h.getId_medico());
            ps.setInt(3, h.getId_cita());
            ps.setDate(4, java.sql.Date.valueOf(h.getDia()));
            ps.setTime(5, java.sql.Time.valueOf(h.getHora()));
            ps.setString(6, h.getDescripcion());

            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error al guardar", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
        return resultado;
    }

    public boolean actualizarEstadoCita(int idCita) {
        boolean resultado = false;
        String sql = "UPDATE cita SET estado = 0 WHERE id_cita = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCita);
            int filasAfectadas = ps.executeUpdate();
            resultado = filasAfectadas > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al actualizar estado de cita", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }
        return resultado;
    }

    @Override
    public int setAgregar(HistorialMedico tr) {
        return 0;
    }

    @Override
    public int setActualizar(HistorialMedico tr) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        String sql = "DELETE FROM historial_medico WHERE id_historial_medico=" + id;
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error en la eliminacion" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE
            );
            return 0;
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
    }
}
