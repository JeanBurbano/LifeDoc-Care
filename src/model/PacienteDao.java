package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class PacienteDao implements Crud<Paciente> {

    public Conexion conectar = new Conexion();

    Connection con;
    PreparedStatement ps;
    ResultSet r;

    public Paciente buscarPorId(int idUsuario) {
        Paciente p = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.id_tipo_identificacion, u.numero_identificacion,"
                + " u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido,u.correo_electronico,"
                + " u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, u.edad, u.sisben, u.estado, p.id_paciente"
                + " FROM usuario u INNER JOIN paciente p ON u.id_usuario=p.id_usuario WHERE u.id_usuario = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            r = ps.executeQuery();
            if (r.next()) {
                String segundoNombre = r.getString("segundo_nombre") == null ? "No aplica" : r.getString("segundo_nombre");
                String segundoApellido = r.getString("segundo_apellido") == null ? "No aplica" : r.getString("segundo_apellido");
                p = new Paciente(r.getInt("id_usuario"),
                        r.getByte("id_rol"),
                        r.getByte("id_tipo_identificacion"),
                        r.getString("numero_identificacion"),
                        r.getString("primer_nombre"),
                        segundoNombre,
                        r.getString("primer_apellido"),
                        segundoApellido,
                        r.getString("correo_electronico"),
                        r.getDate("fecha_nacimiento").toLocalDate(),
                        r.getString("sexo_biologico"),
                        r.getString("numero_celular"),
                        r.getByte("edad"),
                        r.getString("sisben"),
                        r.getBoolean("estado"),
                        "fotosPerfil/fotoDefecto.png",
                        r.getInt("id_paciente"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (r != null) {
                    r.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    protected Paciente getUsuario(String numeroIdentifi) {
        Paciente usu = null;

        String sql = "SELECT id_usuario, id_rol, id_tipo_identificacion, "
                + "numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, "
                + "fecha_nacimiento, sexo_biologico, "
                + "numero_celular, edad, sisben, estado, foto_perfil "
                + "FROM usuario WHERE numero_identificacion = ?";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setString(1, numeroIdentifi);

            r = ps.executeQuery();

            if (r.next()) {
                usu = new Paciente(
                        r.getInt("id_usuario"),
                        r.getByte("id_rol"),
                        r.getByte("id_tipo_identificacion"),
                        r.getString("numero_identificacion"),
                        r.getString("primer_nombre"),
                        r.getString("segundo_nombre") == null ? "No aplica" : r.getString("segundo_nombre"),
                        r.getString("primer_apellido"),
                        r.getString("segundo_apellido") == null ? "No aplica" : r.getString("segundo_apellido"),
                        r.getString("correo_electronico"),
                        r.getDate("fecha_nacimiento").toLocalDate(),
                        r.getString("sexo_biologico"),
                        r.getString("numero_celular"),
                        r.getByte("edad"),
                        r.getString("sisben"),
                        r.getBoolean("estado"),
                        r.getString("foto_perfil")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usu;
    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> pacientes = new ArrayList<>();
        return pacientes;
    }

    @Override
    public int setAgregar(Paciente p) {
        int resultado = 0;
        String sql = "INSERT INTO paciente(id_usuario) VALUES (?)";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, p.getIdUsuario());
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultado;
    }

    @Override
    public int setActualizar(Paciente tr) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        return 0;
    }

    public Paciente buscarPorId(String numeroIdentificacion) {
        Paciente paciente = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.id_tipo_identificacion, u.numero_identificacion, "
                + "u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido, "
                + "u.correo_electronico, u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, "
                + "u.edad, u.estado, u.sisben, p.id_paciente "
                + "FROM usuario u "
                + "JOIN paciente p ON p.id_usuario = u.id_usuario "
                + "WHERE u.numero_identificacion = ? AND u.id_rol = 5 AND u.estado = 1";

        Connection con = conectar.getConection();
        if (con == null) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos.\nVerifica que la base de datos (MySQL) este activo.",
                    "Error de conexion",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;

        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numeroIdentificacion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipoIdentificacion;
                    paciente = new Paciente(
                            rs.getInt("id_usuario"),
                            rs.getByte("id_rol"),
                            rs.getByte("id_tipo_identificacion"),
                            rs.getString("numero_identificacion"),
                            rs.getString("primer_nombre"),
                            rs.getString("segundo_nombre") == null ? "No aplica" : rs.getString("segundo_nombre"),
                            rs.getString("primer_apellido"),
                            rs.getString("segundo_apellido") == null ? "No aplica" : rs.getString("segundo_apellido"),
                            rs.getString("correo_electronico"),
                            rs.getDate("fecha_nacimiento").toLocalDate(),
                            rs.getString("sexo_biologico"),
                            rs.getString("numero_celular"),
                            rs.getByte("edad"),
                            rs.getString("sisben"),
                            rs.getBoolean("estado"),
                            null, // es null puesto que aqui no necesitamos la foto de perfil
                            rs.getInt("id_paciente")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }
}
