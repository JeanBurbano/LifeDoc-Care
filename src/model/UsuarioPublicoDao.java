package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioPublicoDao implements Crud<UsuarioPublico> {

    private static final String[] TABLAS = {"usuario", "cita", "medico"};
    private static final String[] COLUMNAS = {"numero_identificacion", "correo_electronico"};

    public static Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean validarCampoIdBs(String valorComparar, String tabla, String campo) {
        boolean valor = false;
        if (!(List.of(TABLAS).contains(tabla) && List.of(COLUMNAS).contains(campo))) {
            return valor;
        }

        String sql = "SELECT EXISTS (SELECT 1 FROM " + tabla + " WHERE " + campo + " = ?) AS existe";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.ps.setString(1, valorComparar);
            this.rs = ps.executeQuery();
            if (rs.next()) {
                valor = rs.getBoolean("existe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return valor;
    }

    public UsuarioPublico getUsuario(String numeroIdentifi, String contrasena) {
        UsuarioPublico usu = null;

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
                if (!Hashed.verifyPassword(contrasena, rs.getString("contrasena"))) return usu;
                usu = new UsuarioPublico(
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
                        rs.getString("foto_perfil")
                );
            }

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
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usu;
    }

    @Override
    public List<UsuarioPublico> listar() {
        List<UsuarioPublico> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, id_rol, id_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, fecha_nacimiento, sexo_biologico, numero_celular, edad, sisben, estado, "
                + "foto_perfil FROM usuario";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioPublico usu = new UsuarioPublico(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getByte("id_tipo_identificacion"),
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
                        rs.getString("sisben"),
                        rs.getBoolean("estado"),
                        rs.getString("foto_perfil")
                );
                usuarios.add(usu);
            }
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
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }

    @Override
    public int setAgregar(UsuarioPublico tr) {
        //Aqui iria la logica de agregar pero por ahora no 
        return 0;
    }

    @Override
    public int setActualizar(UsuarioPublico tr) {
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
            ps.setByte(2, tr.getIdTipoIdentificacion());
            ps.setString(3, tr.getNumeroIdentificacion());
            ps.setString(4, tr.getPrimerNombre());
            ps.setString(5, tr.getSegundoNombre());
            ps.setString(6, tr.getPrimerApellido());
            ps.setString(7, tr.getSegundoApellido());
            ps.setString(8, tr.getCorreo());
            ps.setDate(9, java.sql.Date.valueOf(tr.getFechaNacimiento()));
            ps.setString(10, tr.getSexoBiologico());
            ps.setString(11, tr.getNumeroCelular());
            ps.setByte(12, tr.getEdad());
            ps.setString(13, tr.getSisben());
            ps.setBoolean(14, tr.isEstado());
            ps.setString(15, tr.getFotoPerfil());
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

    public int setHabilitar(int id) {
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

    @Override
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
