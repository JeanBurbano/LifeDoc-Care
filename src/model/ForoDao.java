package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ForoDao implements Crud<Foro> {

    @Override
    public List<Foro> listar() {
        List<Foro> lista = new ArrayList<>();
        String sql = "SELECT f.id_foro, f.tipo_mensaje, f.asunto, f.descripcion, f.fecha_publicacion, "
                + "f.id_usuario, u.primer_nombre, u.primer_apellido "
                + "FROM foro f LEFT JOIN usuario u ON f.id_usuario = u.id_usuario "
                + "ORDER BY f.fecha_publicacion DESC";

        try (Connection conexion = new Conexion().getConection()) {
            if (conexion == null) {
                return lista;
            }
            try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String nombreCompleto = (rs.getString("primer_nombre") + " " + rs.getString("primer_apellido")).trim();
                    Timestamp fecha = rs.getTimestamp("fecha_publicacion");
                    lista.add(new Foro(
                            rs.getInt("id_foro"),
                            rs.getString("tipo_mensaje"),
                            rs.getString("asunto"),
                            rs.getString("descripcion"),
                            fecha != null ? fecha.toLocalDateTime() : null,
                            rs.getInt("id_usuario"),
                            nombreCompleto));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }

    @Override
    public int setAgregar(Foro tr) {
        String sql = "INSERT INTO foro (tipo_mensaje, asunto, descripcion, id_usuario) VALUES (?, ?, ?, ?)";

        try (Connection conexion = new Conexion().getConection()) {
            if (conexion == null) {
                return 0;
            }
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, tr.getTipoMensaje());
                ps.setString(2, tr.getAsunto());
                ps.setString(3, tr.getDescripcion());
                ps.setInt(4, tr.getIdUsuario());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    @Override
    public int setActualizar(Foro tr) {
        String sql = "UPDATE foro SET asunto = ?, descripcion = ? WHERE id_foro = ?";

        try (Connection conexion = new Conexion().getConection()) {
            if (conexion == null) {
                return 0;
            }
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, tr.getAsunto());
                ps.setString(2, tr.getDescripcion());
                ps.setInt(3, tr.getIdForo());
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }

    @Override
    public int setEliminar(int id) {
        String sql = "DELETE FROM foro WHERE id_foro = ?";

        try (Connection conexion = new Conexion().getConection()) {
            if (conexion == null) {
                return 0;
            }
            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
    }
}
