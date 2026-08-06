package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasCitasModel implements Crud<EstadisticaCitas> {

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public List<EstadisticaCitas> listar() {
        List<EstadisticaCitas> lista = new ArrayList<>();
        String sql = "SELECT id_estadistica, fecha, citas_agendadas, citas_confirmadas, "
                + "citas_canceladas, citas_reagendadas, citas_atendidas "
                + "FROM estadisticas_citas ORDER BY fecha";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                EstadisticaCitas estadistica = new EstadisticaCitas(
                        rs.getInt("id_estadistica"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getInt("citas_agendadas"),
                        rs.getInt("citas_confirmadas"),
                        rs.getInt("citas_canceladas"),
                        rs.getInt("citas_reagendadas"),
                        rs.getInt("citas_atendidas")
                );
                lista.add(estadistica);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public int setAgregar(EstadisticaCitas tr) {
        String sql = "INSERT INTO estadisticas_citas "
                + "(fecha, citas_agendadas, citas_confirmadas, citas_canceladas, citas_reagendadas, citas_atendidas) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);

            ps.setObject(1, tr.getFecha());
            ps.setInt(2, tr.getCitasAgendadas());
            ps.setInt(3, tr.getCitasConfirmada());
            ps.setInt(4, tr.getCitaCancelada());
            ps.setInt(5, tr.getCitaReagendada());
            ps.setInt(6, tr.getCitaAgendida());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setActualizar(EstadisticaCitas tr) {
        String sql = "UPDATE estadisticas_citas SET "
                + "citas_agendadas = ?, citas_confirmadas = ?, citas_canceladas = ?, "
                + "citas_reagendadas = ?, citas_atendidas = ? "
                + "WHERE id_estadistica = ?";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, tr.getCitasAgendadas());
            ps.setInt(2, tr.getCitasConfirmada());
            ps.setInt(3, tr.getCitaCancelada());
            ps.setInt(4, tr.getCitaReagendada());
            ps.setInt(5, tr.getCitaAgendida());
            ps.setInt(6, tr.getIdEstadistica());

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int setEliminar(int id) {
        String sql = "DELETE FROM estadisticas_citas WHERE id_estadistica = ?";

        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
