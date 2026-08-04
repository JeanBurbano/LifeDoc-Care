package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuDao implements Crud<Usuario> {

    private static final String[] TABLAS = {"usuario", "cita", "medico"};
    private static final String[] COLUMNAS = {"id_usuario", "numero_identificacion", "correo_electronico"};

    public static Conexion conectar = new Conexion();
    java.sql.Connection con;
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

    public List<UsuarioPublico> listarPersonal() {
        List<UsuarioPublico> lista = new ArrayList<>();
        String sql = "SELECT u.id_usuario, u.id_rol, u.id_tipo_identificacion, u.numero_identificacion, "
                + "u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido, "
                + "u.correo_electronico, u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, "
                + "u.edad, u.sisben, u.estado, u.foto_perfil "
                + "FROM usuario u "
                + "WHERE u.id_rol IN (3, 4) "
                + "ORDER BY u.primer_apellido, u.primer_nombre";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioPublico u = new UsuarioPublico(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getByte("id_tipo_identificacion"),
                        rs.getString("numero_identificacion"),
                        rs.getString("primer_nombre"),
                        nullASeguro(rs.getString("segundo_nombre")),
                        rs.getString("primer_apellido"),
                        nullASeguro(rs.getString("segundo_apellido")),
                        rs.getString("correo_electronico"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getString("sexo_biologico"),
                        rs.getString("numero_celular"),
                        rs.getByte("edad"),
                        rs.getString("sisben"),
                        rs.getBoolean("estado"),
                        rs.getString("foto_perfil")
                );
                lista.add(u);
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

    private String nullASeguro(String valor) {
        return valor == null ? "" : valor;
    }

    public Usuario getUsuario(String numeroIdentifi, String contrasena) {
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
                if (!Hashed.verifyPassword(contrasena, rs.getString("contrasena"))) {
                    return usu;
                }
                usu = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getByte("id_tipo_identificacion"),
                        rs.getString("numero_identificacion"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre") == null ? "No aplica" : rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido") == null ? "No aplica" : rs.getString("segundo_apellido"),
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

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario,id_rol,id_tipo_identificacion,numero_identificacion,"
                + "primer_nombre,segundo_nombre,primer_apellido,segundo_apellido,correo_electronico,"
                + "fecha_nacimiento,sexo_biologico,numero_celular,edad,"
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
                        "*************",
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

    public int setAgregar(Operario p, String contrasena) {
        int idGenerado = -1;
        String sqlUsuario = "INSERT INTO usuario "
                + "(id_rol, id_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, contrasena, fecha_nacimiento, "
                + "sexo_biologico, numero_celular, edad, sisben) "
                + "VALUES (4, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlOperario = "INSERT INTO operario (id_usuario) VALUES (?)";

        PreparedStatement psOperario = null;
        try {
            this.con = conectar.getConection();
            con.setAutoCommit(false);

            this.ps = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, p.getIdTipoIdentificacion());
            ps.setString(2, p.getNumeroIdentificacion());
            ps.setString(3, p.getPrimerNombre());
            ps.setString(4, p.getSegundoNombre());
            ps.setString(5, p.getPrimerApellido());
            ps.setString(6, p.getSegundoApellido());
            ps.setString(7, p.getCorreo());
            ps.setString(8, contrasena);
            ps.setDate(9, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setString(10, p.getSexoBiologico());
            ps.setString(11, p.getNumeroCelular());
            ps.setByte(12, p.getEdad());
            ps.setString(13, p.getSisben());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                this.rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
            if (idGenerado == -1) {
                throw new SQLException("No se pudo generar el usuario.");
            }

            psOperario = con.prepareStatement(sqlOperario);
            psOperario.setInt(1, idGenerado);
            if (psOperario.executeUpdate() <= 0) {
                throw new SQLException("No se pudo vincular el operario con el usuario generado.");
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            idGenerado = -1;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psOperario != null) {
                    psOperario.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idGenerado;
    }

    public int setAgregar(Medico m, String contrasena, int idEspecialidad) {
        int idGenerado = -1;
        String sqlUsuario = "INSERT INTO usuario "
                + "(id_rol, id_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, contrasena, fecha_nacimiento, "
                + "sexo_biologico, numero_celular, edad, sisben) "
                + "VALUES (3, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlMedico = "INSERT INTO medico (id_usuario, id_especialidad) VALUES (?, ?)";

        PreparedStatement psMedico = null;
        try {
            this.con = conectar.getConection();
            con.setAutoCommit(false);

            this.ps = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, m.getIdTipoIdentificacion());
            ps.setString(2, m.getNumeroIdentificacion());
            ps.setString(3, m.getPrimerNombre());
            ps.setString(4, m.getSegundoNombre());
            ps.setString(5, m.getPrimerApellido());
            ps.setString(6, m.getSegundoApellido());
            ps.setString(7, m.getCorreo());
            ps.setString(8, contrasena);
            ps.setDate(9, java.sql.Date.valueOf(m.getFechaNacimiento()));
            ps.setString(10, m.getSexoBiologico());
            ps.setString(11, m.getNumeroCelular());
            ps.setByte(12, m.getEdad());
            ps.setString(13, m.getSisben());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                this.rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
            if (idGenerado == -1) {
                throw new SQLException("No se pudo generar el usuario.");
            }

            psMedico = con.prepareStatement(sqlMedico);
            psMedico.setInt(1, idGenerado);
            psMedico.setInt(2, idEspecialidad);
            if (psMedico.executeUpdate() <= 0) {
                throw new SQLException("No se pudo vincular el médico con el usuario generado.");
            }

            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            idGenerado = -1;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psMedico != null) {
                    psMedico.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idGenerado;
    }

    @Override
    public int setAgregar(Usuario tr) {
        int r = 0;
        String sql = "INSERT INTO usuario (id_rol,id_tipo_identificacion, numero_identificacion,primer_nombre,segundo_nombre,primer_apellido,"
                + "segundo_apellido,correo_electronico,contrasena,fecha_nacimiento,sexo_biologico,numero_celular,edad,sisben,estado,foto_perfil) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO paciente(id_usuario) VALUES (?)";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, tr.getIdRol());
            ps.setInt(2, tr.getIdTipoIdentificacion());
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
            ps.setString(16, tr.getFotoPerfil());
            r = ps.executeUpdate();
            con.close();
            ps.close();
            //Agregar a la tabla paciente
            Paciente usu = new PacienteDao().getUsuario(tr.getNumeroIdentificacion());
            r = new PacienteDao().setAgregar(usu);
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

    @Override
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
            ps.setByte(2, tr.getIdTipoIdentificacion());
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
            ps.setString(16, tr.getFotoPerfil());
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
