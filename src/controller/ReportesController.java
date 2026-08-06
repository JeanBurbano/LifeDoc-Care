package controller;

import java.awt.event.ActionEvent;
import view.ReportesInterfaz;
import java.awt.event.ActionListener;
import model.GestorEstadisticas;
import javax.swing.JPanel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import model.EstadisticaCitas;
import model.EstadisticasCitasModel;

public class ReportesController implements ActionListener {

    ReportesInterfaz rI = new ReportesInterfaz();
    int totalAgendadas, totalConfirmadas, totalCanceladas, totalReagendadas, totalAtendidas;

    public ReportesController(ReportesInterfaz rI) {
        this.rI = rI;
        agregarActionListener();
    }

    public void agregarActionListener() {
        rI.btnCerrar.addActionListener(this);
        rI.btnUsuSisben.addActionListener(this);
        rI.btnInfoCitas.addActionListener(this);
        rI.btnAtencionMedica.addActionListener(this);
        rI.btnFinancieros.addActionListener(this);
    }

    public void procesoBtnInfoCitas() {
        rI.habilitarBotonesMenu(rI.btnInfoCitas);

        new SwingWorker<Void, Void>() {
            int[] totales;
            List<EstadisticaCitas> lista;

            @Override
            protected Void doInBackground() {
                totales = new GestorEstadisticas().obtenerTotales();
                lista = new EstadisticasCitasModel().listar();
                return null;
            }

            @Override
            protected void done() {
                totalAgendadas = totales[0];
                totalConfirmadas = totales[1];
                totalCanceladas = totales[2];
                totalReagendadas = totales[3];
                totalAtendidas = totales[4];

                JPanel panelContenido = rI.crearPanelConValores(
                        String.valueOf(totalAgendadas), String.valueOf(totalConfirmadas),
                        String.valueOf(totalCanceladas), String.valueOf(totalReagendadas),
                        String.valueOf(totalAtendidas));

                JPanel panelGrafico = rI.crearGraficoPorDia(lista);
                rI.cargarGraficosCitasCuerpo2(panelContenido, panelGrafico);
            }
        }.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rI.btnCerrar) {
            rI.dispose();
            return;
        }
        if (e.getSource() == rI.btnUsuSisben) {
            rI.habilitarBotonesMenu(rI.btnUsuSisben);
            rI.vaciarPanelCuerpo2();
            JOptionPane.showMessageDialog(null, "Esta en proceso");
            return;
        }
        if (e.getSource() == rI.btnInfoCitas) {
            procesoBtnInfoCitas();
            return;
        }
        if (e.getSource() == rI.btnAtencionMedica) {
            rI.habilitarBotonesMenu(rI.btnAtencionMedica);
            rI.vaciarPanelCuerpo2();
            JOptionPane.showMessageDialog(null, "Esta en proceso");
            return;
        }
        if (e.getSource() == rI.btnFinancieros) {
            rI.habilitarBotonesMenu(rI.btnFinancieros);
            rI.vaciarPanelCuerpo2();
            JOptionPane.showMessageDialog(null, "Esta en proceso");
            return;
        }
    }
}
