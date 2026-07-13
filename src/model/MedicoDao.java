package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements Crud<Medico> {

    public static Conexion conectar = new Conexion();

    public Medico[] listarPorEspecialidad(int idEspecialidad) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.id_rol, ti.nombre_tipo_identificacion, u.numero_identificacion, "
                + "u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido, "
                + "u.correo_electronico, u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, "
                + "u.edad, u.estado, u.sisben, "
                + "COALESCE(f.url_foto_perfil, 'fotosPerfil/fotoDefecto.png') AS foto_perfil, "
                + "esp.nombre_especialidad "
                + "FROM medico m "
                + "JOIN usuario u ON u.id_usuario = m.id_usuario "
                + "JOIN especialidad esp ON esp.id_especialidad = m.id_especialidad "
                + "JOIN tipo_identificacion ti ON ti.id_tipo_identificacion = u.id_tipo_identificacion "
                + "LEFT JOIN fotos_perfil f ON f.id_usuario = u.id_usuario AND f.es_actual = 1 "
                + "WHERE m.id_especialidad = ? AND u.estado = 1 AND u.id_rol = 3 "
                + "ORDER BY u.primer_apellido, u.primer_nombre";

        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEspecialidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    medicos.add(new Medico(
                            rs.getInt("id_usuario"),
                            rs.getByte("id_rol"),
                            rs.getString("nombre_tipo_identificacion"),
                            rs.getString("numero_identificacion"),
                            rs.getString("primer_nombre"),
                            rs.getString("segundo_nombre"),
                            rs.getString("primer_apellido"),
                            rs.getString("segundo_apellido"),
                            rs.getString("correo_electronico"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("sexo_biologico"),
                            rs.getString("numero_celular"),
                            rs.getByte("edad"),
                            rs.getBoolean("estado"),
                            rs.getString("sisben"),
                            rs.getString("foto_perfil"),
                            rs.getString("nombre_especialidad")
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
