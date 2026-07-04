package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {
    Conexion con = new Conexion();
    Usuarios usu;

    public Usuarios login(String correo, String contrasena) {
        String sql = """
            SELECT
                id_rol
                tipo_documento,
                numero_identificacion,
                primer_nombre,
                segundo_nombre,
                primer_apellido,
                segundo_apellido,
                sexo_biologico,
                numero_celular,
                id_subgrupo_sisben,
                fecha_nacimiento,
                edad
            FROM usuario
            WHERE correo_electronico = ? AND contraseña = ?
            """;

        try (Connection conexion = this.con.getConection(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.usu = new Usuarios(rs.getString("id_rol"),
                        rs.getString("tipo_documento"),
                        rs.getString("numero_identificacion"),
                        rs.getString("primer_nombre"),
                        rs.getString("segundo_nombre"),
                        rs.getString("primer_apellido"),
                        rs.getString("segundo_apellido"),
                        rs.getString("sexo_biologico"),
                        rs.getString("numero_celular"),
                        rs.getString("id_subgrupo_sisben"),
                        rs.getDate("fecha_nacimiento").toLocalDate(),
                        rs.getByte("edad")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usu;
    }
}
