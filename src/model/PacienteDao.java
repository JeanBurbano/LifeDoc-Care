package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteDao  {
    
    public Conexion conectar = new Conexion();
    
    public Paciente buscarPorId(String numeroIdentificacion) {
        Paciente paciente = null;
        String sql = "SELECT u.id_usuario, u.id_rol, u.id_tipo_identificacion, u.numero_identificacion, "
                + "u.primer_nombre, u.segundo_nombre, u.primer_apellido, u.segundo_apellido, "
                + "u.correo_electronico, u.fecha_nacimiento, u.sexo_biologico, u.numero_celular, "
                + "u.edad, u.estado, u.sisben "
                + "FROM usuario u "
                + "JOIN paciente p ON p.id_usuario = u.id_usuario "
                + "WHERE u.numero_identificacion = ? AND u.id_rol = 5";
        
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
                    
                    paciente = new Paciente(
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
                            null // es null puesto que aqui no necesitamos la foto de perfil
                    );
                }
            }
    }catch (SQLException e) {
            e.printStackTrace();
        }
        return paciente;
    }
}
