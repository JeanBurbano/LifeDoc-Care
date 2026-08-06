package model;

import java.sql.*;

public class GestorEstadisticas {

    public Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int[] obtenerTotales() {
        int[] totales = new int[5];

        String sql = "SELECT SUM(citas_agendadas), SUM(citas_confirmadas), "
                + "SUM(citas_canceladas), SUM(citas_reagendadas), SUM(citas_atendidas) "
                + "FROM estadisticas_citas";
        try {
            con = conectar.getConection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                totales[0] = rs.getInt(1);
                totales[1] = rs.getInt(2);
                totales[2] = rs.getInt(3);
                totales[3] = rs.getInt(4);
                totales[4] = rs.getInt(5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            
        }
        return totales;
    }
}
