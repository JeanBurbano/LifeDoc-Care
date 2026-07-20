/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lunaa
 */
public class HorarioDao implements Crud<Horario> {

    Conexion conectar = new Conexion();
    Connection con;

    PreparedStatement ps;
    ResultSet rs;

    private static final String[] NOMBRES_DIAS = {"Lunes", "Martes", "Miercoles",
        "Jueves", "Viernes", "Sabado"};

    @Override
    public List<Horario> listar() {
        List<Horario> listaHorarios = new ArrayList<Horario>();
        String sql = "SELECT h.id_horario, h.nombre, h.color_etiqueta, "
                + "hd.dia_semana, hd.hora_inicio, hd.hora_fin, hd.descanso_inicio, hd.descanso_fin "
                + "FROM horarios h "
                + "LEFT JOIN horario_dias hd ON hd.id_horarios = h.id_horario "
                + "ORDER BY h.id_horario";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            Horario horarioActual = null;
            int idHorarioAnterior = -1;

            while (rs.next()) {
                int idHorario = rs.getInt("id_horario");

                if (idHorario != idHorarioAnterior) {
                    horarioActual = new Horario();
                    horarioActual.setId(idHorario);
                    horarioActual.setNombre(rs.getString("nombre"));
                    horarioActual.setColorEtiqueta(rs.getString("color_etiqueta"));
                    horarioActual.setDias(new ArrayList<HorarioDia>());
                    listaHorarios.add(horarioActual);
                    idHorarioAnterior = idHorario;
                }

                if (rs.getObject("dia_semana") != null) {
                    HorarioDia dia = new HorarioDia();
                    dia.setDiaSemana(rs.getString("dia_semana"));
                    dia.setHoraInicio(rs.getString("hora_inicio"));
                    dia.setHoraFin(rs.getString("hora_fin"));
                    dia.setDescansoInicio(rs.getString("descanso_inicio"));
                    dia.setDescansoFin(rs.getString("descanso_fin"));
                    horarioActual.getDias().add(dia);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error de consulta" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
        }
        return listaHorarios;
    }

    @Override
    public int setAgregar(Horario h) {
        String sqlHorario = "INSERT INTO horarios (nombre, color_etiqueta) VALUES (?, ?)";
        String sqlDia = "INSERT INTO horario_dias "
                + "(id_horarios, dia_semana, hora_inicio, hora_fin, descanso_inicio, descanso_fin) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            con = conectar.getConection();

            ps = con.prepareStatement(sqlHorario, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, h.getNombre());
            ps.setString(2, h.getColorEtiqueta());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            int idHorarioNuevo = 0;
            if (rs.next()) {
                idHorarioNuevo = rs.getInt(1);
            }

            for (HorarioDia dia : h.getDias()) {
                ps = con.prepareStatement(sqlDia);
                ps.setInt(1, idHorarioNuevo);
                ps.setString(2, dia.getDiaSemana());
                ps.setString(3, dia.getHoraInicio());
                ps.setString(4, dia.getHoraFin());
                ps.setString(5, dia.getDescansoInicio());
                ps.setString(6, dia.getDescansoFin());
                ps.executeUpdate();
            }

            return idHorarioNuevo;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error en la inserción" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
    }

    @Override
    public int setActualizar(Horario h) {
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        try {
            con = conectar.getConection();
            ps = con.prepareStatement("DELETE FROM horario_dias WHERE id_horarios = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

            ps = con.prepareStatement("DELETE FROM horarios WHERE id_horario = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.toString(),
                    "Error en la Eliminación" + e.getMessage(),
                    JOptionPane.ERROR_MESSAGE
            );
            return 0;
        }
        

    }
    
    public static String indiceANombreDia(int indice) {
        return NOMBRES_DIAS[indice];
    }

}
