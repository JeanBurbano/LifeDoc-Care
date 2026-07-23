package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatosPagoCitaDao {
    
    public Conexion conectar = new Conexion();
    
    public List<DatosPagoCita> listarCitasPendientesPago(int idUsuarioPaciente) {
        List<DatosPagoCita> citasPendientes = new ArrayList<>();
        String sql = "SELECT c.id_cita, c.fecha_cita, c.hora_cita, "
                + "up.primer_nombre, up.segundo_nombre, up.primer_apellido, up.segundo_apellido, "
                + "up.numero_identificacion, up.sisben, "
                + "e.nombre_especialidad, "
                + "um.primer_nombre AS medico_nombre, um.primer_apellido AS medico_apellido "
                + "FROM cita c "
                + "JOIN usuario up ON up.id_usuario = c.id_Usuario "
                + "JOIN medico m ON m.id_medico = c.id_Medico "
                + "JOIN usuario um ON um.id_usuario = m.id_usuario "
                + "JOIN especialidad e ON e.id_especialidad = m.id_especialidad "
                + "LEFT JOIN factura f ON f.id_cita = c.id_cita "
                + "WHERE c.id_Usuario = ? AND c.estado = 1 AND f.id_factura IS NULL "
                + "ORDER BY c.fecha_cita, c.hora_cita";

        Connection con = conectar.getConection();
        if (con == null) {
            return citasPendientes;
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuarioPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombrePaciente = rs.getString("primer_nombre")
                            + (rs.getString("segundo_nombre") != null ? " " + rs.getString("segundo_nombre") : "")
                            + " " + rs.getString("primer_apellido")
                            + (rs.getString("segundo_apellido") != null ? " " + rs.getString("segundo_apellido") : "");
                    String nombreMedico = rs.getString("medico_nombre") + " " + rs.getString("medico_apellido");

                    citasPendientes.add(new DatosPagoCita(
                            rs.getInt("id_cita"),
                            nombrePaciente,
                            rs.getString("numero_identificacion"),
                            rs.getString("sisben"),
                            rs.getString("nombre_especialidad"),
                            nombreMedico,
                            rs.getDate("fecha_cita").toLocalDate(),
                            rs.getTime("hora_cita").toLocalTime()
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citasPendientes;
    }
    
    
    // Guarda la factura real en la BD. Retorna el numero de filas insertadas
    // (1 si quedo bien guardada, 0 si algo fallo).
    
    public int crear(int idCita, String descripcion, double valorTotal, String metodoPago) {
        String sql = "INSERT INTO factura (id_cita, descripcion, valor_total, metodo_pago) "
                + "VALUES (?, ?, ?, ?)";
 
        Connection con = conectar.getConection();
        if (con == null) {
            return 0;
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCita);
            ps.setString(2, descripcion);
            ps.setDouble(3, valorTotal);
            ps.setString(4, metodoPago);
            return ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
            
        }
    }
}
