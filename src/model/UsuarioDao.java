package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao implements Crud<Paciente> {

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public UsuarioDao() {

    }

    public Paciente login(String id, String contrasena) {
        Paciente usu = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.id_tipo_identificacion, u.numero_identificacion, u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido, u.correo_electronico, u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, u.edad, u.estado, u.sisben, f.url_foto_perfil "
                + "FROM usuario AS u "
                + "LEFT JOIN fotos_perfil AS f "
                + "     ON f.id_usuario = u.id_usuario AND f.es_actual = 1 "
                + "WHERE u.numero_identificacion = ? AND u.contrasena = ?";

        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.ps.setString(1, id);
            this.ps.setString(2, contrasena);
            this.rs = ps.executeQuery();

            if (rs.next()) {
                String tipoIdentificacion;
                switch (rs.getInt("id_tipo_identificacion")) {
                    case 1:
                        tipoIdentificacion = "Registro civil";
                        break;
                    case 2:
                        tipoIdentificacion = "Tarjeta de identidad";
                        break;
                    case 3:
                        tipoIdentificacion = "Cedula de ciudadania";
                        break;
                    default:
                        tipoIdentificacion = "Tarjeta de identidad";
                        break;
                }
                usu = new Paciente(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        tipoIdentificacion,
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
                        rs.getString("url_foto_perfil")
                );
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
        return usu;
    }

    public boolean validarCampoIdBs(String valorComparar, String tabla, String campo) {
        boolean valor = false;
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

    public byte sacarIdRol(String id, String contrasena) {
        byte idRol = -1;
        String sql = "SELECT id_rol FROM usuario WHERE numero_identificacion = ? AND contrasena = ?";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.ps.setString(1, id);
            this.ps.setString(2, contrasena);
            this.rs = ps.executeQuery();
            if (rs.next()) {
                idRol = rs.getByte("id_rol");
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
        return idRol;
    }

    public int registrarUsuario(int idRol, int idTipoIdentificacion, String numeroIdentificacion,
            String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
            String correoElectronico, String contrasena, LocalDate fechaNacimiento,
            String sexoBiologico, String numeroCelular, byte edad, String sisben) {

        int idGenerado = -1;
        String sqlUsuario = "INSERT INTO usuario "
                + "(id_rol, id_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, contrasena, fecha_nacimiento, "
                + "sexo_biologico, numero_celular, edad, sisben) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO paciente (id_usuario) VALUES (?)";

        PreparedStatement psPaciente = null;
        try {
            this.con = conectar.getConection();
            con.setAutoCommit(false); // arranca la transacción

            this.ps = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idRol);
            ps.setInt(2, idTipoIdentificacion);
            ps.setString(3, numeroIdentificacion);
            ps.setString(4, primerNombre);
            ps.setString(5, (segundoNombre == null || segundoNombre.isBlank()) ? null : segundoNombre);
            ps.setString(6, primerApellido);
            ps.setString(7, (segundoApellido == null || segundoApellido.isBlank()) ? null : segundoApellido);
            ps.setString(8, correoElectronico);
            ps.setString(9, contrasena);
            ps.setDate(10, java.sql.Date.valueOf(fechaNacimiento));
            ps.setString(11, sexoBiologico);
            ps.setString(12, (numeroCelular == null || numeroCelular.isBlank()) ? null : numeroCelular);
            ps.setByte(13, edad);
            ps.setString(14, sisben);

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

            psPaciente = con.prepareStatement(sqlPaciente);
            psPaciente.setInt(1, idGenerado);
            if (psPaciente.executeUpdate() <= 0) {
                throw new SQLException("No se pudo vincular el paciente con el usuario generado.");
            }

            con.commit(); // ambos inserts ok -> confirma

        } catch (SQLException e) {
            e.printStackTrace();
            idGenerado = -1;
            try {
                if (con != null) {
                    con.rollback(); // algo falló -> revierte todo
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (psPaciente != null) {
                    psPaciente.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.setAutoCommit(true); // regresa la conexión a su modo normal antes de cerrarla/reusarla
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idGenerado;
    }

    public boolean actualizarCampoUsuario(int id_usuario, String nombreCampo, String nuevoValor) {
        boolean validador = false;
        String sql = "UPDATE usuario SET " + nombreCampo + " = ? WHERE id_usuario = ?";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            ps.setString(1, nuevoValor);
            ps.setInt(2, id_usuario);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                validador = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return validador;
    }

    public boolean habilitarUsuario(int idUsuario) {
        boolean actualizado = false;
        String sql = "UPDATE usuario SET estado = 1 WHERE id_usuario = ? AND estado = 0";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            actualizado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return actualizado;
    }

    public boolean deshabilitarUsuario(int idUsuario) {
        boolean actualizado = false;
        String sql = "UPDATE usuario SET estado = 0 WHERE id_usuario = ? AND estado = 1";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            actualizado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return actualizado;
    }

    @Override
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, id_rol, primer_nombre, segundo_nombre, primer_apellido, "
                + "segundo_apellido, edad, correo_electronico, numero_celular, estado "
                + "FROM usuario";

        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                Paciente p = new Paciente(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getByte("edad"),
                        rs.getString("correo_electronico"),
                        rs.getString("numero_celular"),
                        rs.getBoolean("estado")
                );
                lista.add(p);
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

    @Override
    public int setAgregar(Paciente tr) {
        return registrarUsuario(
                tr.getId_rol(), Integer.parseInt(tr.getTipoId()), tr.getNumeroId(),
                tr.getPrimerNombre(), tr.getSegundoNombre(), tr.getPrimerApellido(), tr.getSegundoApellido(),
                tr.getCorreoElectronico(), tr.getContrasena(), tr.getFechaNacimiento(),
                tr.getSexoBiologico(), tr.getNumeroTelefonico(), tr.getEdad(), tr.getSisben()
        );
    }

    @Override
    public int setActualizar(Paciente tr) {
        int validador = 0;
        String sql = "UPDATE usuario SET estado = ? WHERE id_usuario = ?";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            ps.setBoolean(1, tr.getEstado());
            ps.setInt(2, tr.getIdUsuario());
            validador = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return validador;
    }

    public int setEliminar(int id) {
        int validador = 0;
        return validador;
    }
}
