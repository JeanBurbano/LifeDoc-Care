/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author lunaa
 */
public class PersonalCentroDao implements Crud<PersonalCentro>{
    
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public PersonalCentroDao(){}
    
    @Override
    public List<PersonalCentro> listar() {
        List<PersonalCentro> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE id_rol != 5"; // Excluye pacientes

        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sql);
            this.rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCentro p = new PersonalCentro();
                p.setIdUsuario(rs.getInt("id_usuario"));
                p.setIdRol(rs.getInt("id_rol"));
                p.setIdTipoIdentificacion(rs.getInt("id_tipo_identificacion"));
                p.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                p.setPrimerNombre(rs.getString("primer_nombre"));
                p.setSegundoNombre(rs.getString("segundo_nombre"));
                p.setPrimerApellido(rs.getString("primer_apellido"));
                p.setSegundoApellido(rs.getString("segundo_apellido"));
                p.setCorreoElectronico(rs.getString("correo_electronico"));
                p.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                p.setSexoBiologico(rs.getString("sexo_biologico"));
                p.setNumeroCelular(rs.getString("numero_celular"));
                p.setSisben(rs.getString("sisben"));
                p.setEstado(rs.getBoolean("estado"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return lista;
    }
    
    @Override
    public int setAgregar(PersonalCentro p) {
        int idGenerado = -1;
        String sqlUsuario = "INSERT INTO usuario "
                + "(id_rol, id_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, correo_electronico, contrasena, fecha_nacimiento, "
                + "sexo_biologico, numero_celular, edad, sisben) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            this.con = conectar.getConection();
            this.ps = con.prepareStatement(sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, p.getIdRol());
            ps.setInt(2, p.getIdTipoIdentificacion());
            ps.setString(3, p.getNumeroIdentificacion());
            ps.setString(4, p.getPrimerNombre());
            ps.setString(5, (p.getSegundoNombre() == null || p.getSegundoNombre().isBlank()) ? null : p.getSegundoNombre());
            ps.setString(6, p.getPrimerApellido());
            ps.setString(7, (p.getSegundoApellido() == null || p.getSegundoApellido().isBlank()) ? null : p.getSegundoApellido());
            ps.setString(8, p.getCorreoElectronico());
            ps.setString(9, p.getContrasena());
            ps.setDate(10, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setString(11, p.getSexoBiologico());
            ps.setString(12, (p.getNumeroCelular() == null || p.getNumeroCelular().isBlank()) ? null : p.getNumeroCelular());
            ps.setByte(13, p.getEdad()); // Obtiene la edad calculada internamente en Personal
            ps.setString(14, (p.getSisben() == null || p.getSisben().isBlank()) ? "No aplica" : p.getSisben());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                this.rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error de consulta del personal del centro" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
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
        return idGenerado;
    }
    
    @Override
    public int setActualizar(PersonalCentro h) {
        return 0;
    }
    
    @Override
    public int setEliminar(int id) {
        return 0;
    }
    
    public boolean validarCampoIdBs(String valor, String tabla, String columna) {
    boolean existe = false;
    // Sentencia SQL dinámica para verificar existencia
    String sql = "SELECT COUNT(*) FROM usuario WHERE numero_identificacion = ?";

    try (Connection con = conectar.getConection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, valor);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                existe = rs.getInt(1) > 0; // Si el conteo es mayor a 0, ya existe
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return existe;
}
}
