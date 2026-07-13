package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    public  Conexion conectar = new Conexion();
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
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, valorComparar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    valor = rs.getBoolean("existe");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valor;
    }
}
