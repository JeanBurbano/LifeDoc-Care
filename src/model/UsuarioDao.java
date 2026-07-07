package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UsuarioDao implements Crud<Cita>{

    public static Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public UsuarioDao() {

    }

    public Usuarios login(String id, String contrasena) {
        Usuarios usu = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.primer_nombre, u.estado, f.url_foto_perfil "
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
                usu = new Usuarios(
                        rs.getInt("id_usuario"),
                        rs.getByte("id_rol"),
                        rs.getString("primer_nombre"),
                        rs.getBoolean("estado"),
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

    public boolean validarCampoIdBs(String id) {
        boolean valor = false;
        String sql = "SELECT EXISTS ("
                + "    SELECT 1 "
                + "    FROM usuario "
                + "    WHERE numero_identificacion = ? "
                + ") AS existe";
        try (Connection con = conectar.getConection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
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

    public Usuarios getDatosUsu(int idUsu) {
        Usuarios usu = null;
        String sql = "SELECT primer_nombre,correo_electronico,numero_celular,sexo_biologico,fecha_nacimiento,sisben "
                + "FROM usuarios where id_usuario = ?";
        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.ps.setString(1, String.valueOf(idUsu));
            this.rs = ps.executeQuery();
            if (rs.next()) {
                LocalDate fechaNacimiento = rs.getDate("fecha_nacimiento").toLocalDate();
                usu = new Usuarios(rs.getString("primer_nombre"),
                        MetodosPublicos.calcularEdad(String.valueOf(fechaNacimiento.getYear()), String.valueOf(fechaNacimiento.getMonthValue())),
                        rs.getString("correo_electronico"), rs.getString("numero_celular"), rs.getString("sexo_biologico"),
                        fechaNacimiento, rs.getString("sisben"));
                fechaNacimiento = null;
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
    
    @Override
    public void listar(Cita lista){
        
    }
    
    @Override
    public int setAgregar(Cita tr){
        return 0;
    }
    @Override
    public int setActualizar(Cita tr){
        return 0;
    }
    @Override
    public int setEliminar(int id){
        return 0;
    }
    
}
