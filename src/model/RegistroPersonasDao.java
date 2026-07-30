package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroPersonasDao implements Crud<Usuario> {

    public static Conexion conectar = new Conexion();
    java.sql.Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public RegistroPersonasDao() {

    }

    public Usuario getUsuario(String numeroIdentifi) {
        Usuario usu = null;

        String sql = "SELECT id_usuario, id_rol, id_tipo_identificacion, "
                + "numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, "
                + "contrasena, fecha_nacimiento, sexo_biologico, "
                + "numero_celular, edad, sisben, estado, foto_perfil "
                + "FROM usuario WHERE numero_identificacion = ?";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setString(1, numeroIdentifi);

            rs = ps.executeQuery();

            if (rs.next()) {
                usu = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getByte("id_tipo_identificacion"),
                        rs.getString("numero_identificacion"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre") == null ? "" : rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido") == null ? "" : rs.getString("segundo_apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("contrasena"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("sexo_biologico"),
                        rs.getString("numero_celular"),
                        rs.getByte("edad"),
                        rs.getString("sisben"),
                        rs.getBoolean("estado"),
                        rs.getString("foto_perfil")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usu;
    }

    public int habilitarUsuario(int id) {
        int r = 0;
        String sql = "UPDATE usuario SET estado=1 WHERE id_usuario = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
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
        return r;
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario,id_rol,id_tipo_identificacion,numero_identificacion,"
                + "primer_nombre,segundo_nombre,primer_apellido,segundo_apellido,correo_electronico,"
                + "contrasena,fecha_nacimiento,sexo_biologico,numero_celular,edad,"
                + "sisben,estado,foto_perfil FROM usuario";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                String segundoNombre = rs.getString("segundo_nombre") == null ? "null" : rs.getString("segundo_nombre");
                String segundoApellido = rs.getString("segundo_apellido") == null ? "null" : rs.getString("segundo_apellido");
                Usuario usu = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getByte("id_tipo_identificacion"),
                        rs.getString("numero_identificacion"),
                        rs.getString("primer_nombre"),
                        segundoNombre,
                        rs.getString("primer_apellido"),
                        segundoApellido,
                        rs.getString("correo_electronico"),
                        rs.getString("contrasena"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("sexo_biologico"),
                        rs.getString("numero_celular"),
                        rs.getByte("edad"),
                        rs.getString("sisben"),
                        rs.getBoolean("estado"),
                        rs.getString("foto_perfil")
                );
                lista.add(usu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public int setAgregar(Usuario tr) {
        int r = 0;
        String sql = "INSERT INTO usuario (id_rol,id_tipo_identificacion, numero_identificacion,primer_nombre,segundo_nombre,primer_apellido,"
                + "segundo_apellido,correo_electronico,contrasena,fecha_nacimiento,sexo_biologico,numero_celular,edad,sisben,estado,foto_perfil) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setByte(1, tr.getIdRol());
            ps.setByte(2, tr.getIdIipoIdentificacion());
            ps.setString(3, tr.getNumeroIdentificacion());
            ps.setString(4, tr.getPrimerNombre());
            ps.setString(5, tr.getSegundoNombre());
            ps.setString(6, tr.getPrimerApellido());
            ps.setString(7, tr.getSegundoApellido());
            ps.setString(8, tr.getCorreo());
            ps.setString(9, tr.getContrasena());
            ps.setDate(10, java.sql.Date.valueOf(tr.getFechaNacimiento()));
            ps.setString(11, tr.getSexoBiologico());
            ps.setString(12, tr.getNumeroCelular());
            ps.setByte(13, tr.getEdad());
            ps.setString(14, tr.getSisben());
            ps.setBoolean(15, tr.isEstado());
            ps.setString(16, tr.getFotoPeril());
            r = ps.executeUpdate();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
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
        return r;
    }

    public int setActualizar(Usuario tr) {
        int r = 0;
        String sql = "UPDATE usuario SET "
                + "id_rol = ?, "
                + "id_tipo_identificacion = ?, "
                + "numero_identificacion = ?, "
                + "primer_nombre = ?, "
                + "segundo_nombre = ?, "
                + "primer_apellido = ?, "
                + "segundo_apellido = ?, "
                + "correo_electronico = ?, "
                + "contrasena = ?, "
                + "fecha_nacimiento = ?, "
                + "sexo_biologico = ?, "
                + "numero_celular = ?, "
                + "edad = ?, "
                + "sisben = ?, "
                + "estado = ?, "
                + "foto_perfil = ? "
                + "WHERE id_usuario = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setByte(1, tr.getIdRol());
            ps.setByte(2, tr.getIdIipoIdentificacion());
            ps.setString(3, tr.getNumeroIdentificacion());
            ps.setString(4, tr.getPrimerNombre());
            ps.setString(5, tr.getSegundoNombre());
            ps.setString(6, tr.getPrimerApellido());
            ps.setString(7, tr.getSegundoApellido());
            ps.setString(8, tr.getCorreo());
            ps.setString(9, tr.getContrasena());
            ps.setDate(10, java.sql.Date.valueOf(tr.getFechaNacimiento()));
            ps.setString(11, tr.getSexoBiologico());
            ps.setString(12, tr.getNumeroCelular());
            ps.setByte(13, tr.getEdad());
            ps.setString(14, String.valueOf(tr.getSisben()));
            ps.setBoolean(15, tr.isEstado());
            ps.setString(16, tr.getFotoPeril());
            r = ps.executeUpdate();
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
        return r;
    }

    public int setEliminar(int id) {
        int r = 0;
        String sql = "UPDATE usuario SET estado = 0 WHERE id_usuario = ?";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
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
        return r;
    }
}
