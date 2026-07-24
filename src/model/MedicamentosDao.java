package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.Date;

public class MedicamentosDao implements Crud<Medicamentos> {

    Conexion conectar = new Conexion();
    Connection con;

    PreparedStatement ps;
    ResultSet rs;

    public List<Medicamentos> listarNombres() {
        List<Medicamentos> nombres = new ArrayList<>();
        String sql = "SELECT nombre FROM medicamento";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                nombres.add(new Medicamentos(rs.getString("nombre")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), 
                    "Error en la consulta", JOptionPane.ERROR_MESSAGE);
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
        return nombres;
    }

    @Override
    public List<Medicamentos> listar() {
        List<Medicamentos> listamedicamentos = new ArrayList();
        String sql = "SELECT m.n_registro_sanitario, m.nombre, m.descripcion, m.fecha_vencimiento, m.cantidad, "
                + "m.id_tipo_medicamento, tm.nombre_tipo_medicamento, "
                + "m.id_categoria, cm.nombre_categoria, m.estado, "
                + "i.stock, f.url_foto_medicamento "
                + "FROM medicamento m "
                + "JOIN tipo_medicamento tm ON tm.id_tipo_medicamento = m.id_tipo_medicamento "
                + "JOIN categoria_medicamento cm ON cm.id_categoria = m.id_categoria "
                + "LEFT JOIN inventario_medicamentos i ON i.id_medicamento = m.n_registro_sanitario "
                + "LEFT JOIN fotos_medicamento f ON f.id_medicamento = m.n_registro_sanitario AND f.es_actual = 1 "
                + "ORDER BY m.nombre";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Medicamentos med = new Medicamentos();
                med.setnRegistroSanitario(rs.getString("n_registro_sanitario"));
                med.setNombre(rs.getString("nombre"));
                med.setDescripcion(rs.getString("descripcion"));
                med.setFechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate());
                med.setCantidad(rs.getString("cantidad"));
                med.setIdTipoMedicamento(rs.getInt("id_tipo_medicamento"));
                med.setNombreTipoMedicamento(rs.getString("nombre_tipo_medicamento"));
                med.setIdCategoria(rs.getInt("id_categoria"));
                med.setNombreCategoria(rs.getString("nombre_categoria"));
                med.setEstado(rs.getInt("estado") == 1);
                med.setStock(rs.getInt("stock"));
                med.setUrlFoto(rs.getString("url_foto_medicamento"));
                listamedicamentos.add(med);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de consulta del medicamento", JOptionPane.ERROR_MESSAGE);
        }

        return listamedicamentos;
    }

    @Override
    public int setAgregar(Medicamentos m) {
        String sqlMedi = "INSERT INTO medicamento "
                + "(n_registro_sanitario, nombre, descripcion, fecha_vencimiento, cantidad, id_tipo_medicamento, id_categoria, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, 1)";
        String sqlInventario = "INSERT INTO inventario_medicamentos (id_medicamento, stock) VALUES (?, ?)";
        String sqlFoto = "INSERT INTO fotos_medicamento (id_medicamento, url_foto_medicamento, es_actual) VALUES (?, ?, 1)";

        try {
            con = conectar.getConection();

            ps = con.prepareStatement(sqlMedi);
            ps.setString(1, m.getnRegistroSanitario());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getDescripcion());
            ps.setDate(4, Date.valueOf(m.getFechaVencimiento()));
            ps.setString(5, m.getCantidad());
            ps.setInt(6, m.getIdTipoMedicamento());
            ps.setInt(7, m.getIdCategoria());
            ps.executeUpdate();

            ps = con.prepareStatement(sqlInventario);
            ps.setString(1, m.getnRegistroSanitario());
            ps.setInt(2, m.getStock());
            ps.executeUpdate();

            ps = con.prepareStatement(sqlFoto);
            ps.setString(1, m.getnRegistroSanitario());
            ps.setString(2, m.getUrlFoto());
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error en la inserción de medicamento", JOptionPane.ERROR_MESSAGE);
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

    @Override
    public int setActualizar(Medicamentos tr) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        return 0;
    }

    public int deshabilitar(String nRegistroSanitario) {
        String sql = "UPDATE medicamento SET estado = 0 WHERE n_registro_sanitario = ?";
        try (Connection c = conectar.getConection(); PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, nRegistroSanitario);
            return pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al inhabilitar el medicamento", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    public int habilitar(String nRegistroSanitario) {
        String sql = "UPDATE medicamento SET estado = 1 WHERE n_registro_sanitario = ?";
        try (Connection c = conectar.getConection(); PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setString(1, nRegistroSanitario);
            return pst.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al habilitar el medicamento", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}
