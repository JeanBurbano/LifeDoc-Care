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

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public ForoDao() {

    }

    @Override
    public List<Foro> listar() {
        List<Foro> lista = new ArrayList<>();
        String sql = "SELECT f.id_foro, f.tipo_mensaje, f.asunto, f.descripcion, f.fecha_publicacion, "
                + "f.id_usuario, u.primer_nombre, u.primer_apellido "
                + "FROM foro f LEFT JOIN usuario u ON f.id_usuario = u.id_usuario "
                + "ORDER BY f.fecha_publicacion DESC";

        try {
            con = conectar.getConection();
            if (con == null) {
                return lista;
            }
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
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

        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    this.rs.close();
                }
                if (ps != null) {
                    this.ps.close();
                }
                if (con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public int setAgregar(Foro tr) {
        String sql = "INSERT INTO foro (tipo_mensaje, asunto, descripcion, id_usuario) VALUES (?, ?, ?, ?)";

        try {
            con = conectar.getConection();
            if (con == null) {
                return 0;
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, tr.getTipoMensaje());
            ps.setString(2, tr.getAsunto());
            ps.setString(3, tr.getDescripcion());
            ps.setInt(4, tr.getIdUsuario());
            return ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            try {
                if (rs != null) {
                    this.rs.close();
                }
                if (ps != null) {
                    this.ps.close();
                }
                if (con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int setActualizar(Foro tr) {
        String sql = "UPDATE foro SET asunto = ?, descripcion = ? WHERE id_foro = ?";

        try {
            con = conectar.getConection();
            if (con == null) {
                return 0;
            }
            ps = con.prepareStatement(sql);
            ps.setString(1, tr.getAsunto());
            ps.setString(2, tr.getDescripcion());
            ps.setInt(3, tr.getIdForo());
            return ps.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            try {
                if (rs != null) {
                    this.rs.close();
                }
                if (ps != null) {
                    this.ps.close();
                }
                if (con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int setEliminar(int id) {
        String sql = "DELETE FROM foro WHERE id_foro = ?";

        try {
            con = conectar.getConection();
            if (con == null) {
                return 0;
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(ForoDao.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        } finally {
            try {
                if (rs != null) {
                    this.rs.close();
                }
                if (ps != null) {
                    this.ps.close();
                }
                if (con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
